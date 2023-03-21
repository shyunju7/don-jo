package com.donjo.backend.api.controller;

import com.donjo.backend.api.dto.item.request.AddItemCond;
import com.donjo.backend.api.dto.item.request.UpdateItemCond;
import com.donjo.backend.api.service.item.ItemService;
import com.donjo.backend.config.jwt.JwtFilter;
import com.donjo.backend.config.jwt.TokenProvider;
import com.donjo.backend.exception.NoContentException;
import com.donjo.backend.solidity.Item.Item;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Api(tags = "아이템 관련 기능 API")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final TokenProvider tokenProvider;
    private final ItemService itemService;

    @GetMapping("/api/member/items")
    @ApiOperation(value = "아이템 리스트 가져오기", notes = "<strong>멤버의 주소</strong>를 입력받아 아이템 리스트를 리턴합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK(조회 성공)"),
            @ApiResponse(code = 204, message = "NO CONTENT(정보 없음)"),
            @ApiResponse(code = 400, message = "BAD REQUEST(조회 실패)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getMyItemList(@RequestParam @NotNull String memberAddress){
        List<Item> list = itemService.getItemList(memberAddress)
                .orElseThrow(()-> new NoContentException("Item이 없습니다."));
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/api/member/items")
    @ApiOperation(value = "아이템 상세 조회", notes = "<strong>아이템의 uid</strong>를 입력받아 아이템 상세 정보를 리턴합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK(조회 성공)"),
            @ApiResponse(code = 204, message = "NO CONTENT(정보 없음)"),
            @ApiResponse(code = 400, message = "BAD REQUEST(조회 실패)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getMyItemList(@RequestParam @NotNull Long itemUid){
        Item item = itemService.getItemDetail(itemUid)
                .orElseThrow(()-> new NoContentException("Item이 없습니다."));
        return ResponseEntity.status(200).body(item);
    }

    @PostMapping("/api/auth/member/item")
    @ApiOperation(value = "아이템 등록", notes = "<strong>아이템 정보</strong>를 입력받아 아이템을 등록합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK(조회 성공)"),
            @ApiResponse(code = 400, message = "BAD REQUEST(조회 실패)"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED(권한 없음)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> addMyItem(HttpServletRequest request, @RequestBody @Valid AddItemCond cond){
        String memberAddress = getMemberAddress(request);

        itemService.addItem(memberAddress, cond);

        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/api/auth/member/item")
    @ApiOperation(value = "아이템 삭제", notes = "<strong>아이템 uid</strong>를 입력받아 아이템을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK(삭제 성공)"),
            @ApiResponse(code = 400, message = "BAD REQUEST(삭제 실패)"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED(권한 없음)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> deleteItem(HttpServletRequest request, @RequestParam @NotNull Long itemUid){
        String memberAddress = getMemberAddress(request);
        itemService.deleteMemberItem(memberAddress, itemUid);
        return ResponseEntity.status(200).build();
    }

    @PutMapping("/api/auth/member/item")
    @ApiOperation(value = "아이템 수정", notes = "<strong>아이템 uid</strong>를 입력받아 아이템을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK(삭제 성공)"),
            @ApiResponse(code = 400, message = "BAD REQUEST(삭제 실패)"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED(권한 없음)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> updateItem(HttpServletRequest request, @RequestBody @Valid UpdateItemCond cond){
        String memberAddress = getMemberAddress(request);
        itemService.updateMemberItem(memberAddress, cond);
        return ResponseEntity.status(200).build();
    }

    private String getMemberAddress(HttpServletRequest request) {
        String accessToken = request.getHeader(JwtFilter.ACCESS_HEADER);
        Authentication authentication = tokenProvider.getAuthentication(accessToken.substring(7));
        String memberAddress = authentication.getName();
        return memberAddress;
    }
}