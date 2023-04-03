package com.donjo.backend.api.dto.support.response;

import com.donjo.backend.db.entity.Support;
import com.donjo.backend.solidity.support.SupportSol;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class FindSupportDetailPayload {
    // 서포트 Uid
    private Long supportUid;
    // 서포트 트랜잭션해쉬
    private String transactionHash;
    // 서포트 보내는 사람
    private MemberItem from;
    // 서포트 받는 사람
    private MemberItem to;
    // 서포트 타입
    private String supportType; //  Donation : 0, Item : 1, Wishlist : 2
    // 서포트타입 Uid
    private Long supportTypeUid;
    // 후원 금액
    private Double amount;
    // 보낸 시간
    private LocalDateTime sendTimeStamp ;
    // 받는 시간
    private LocalDateTime arriveTimeStamp ;
    // 보내는 메세지
    private String sendMsg;

    // 입력 받아 Dto에 저장
    public static FindSupportDetailPayload fromSupport(SupportSol supportsol, Support support, MemberItem fromMember, MemberItem toMember){
        FindSupportDetailPayload findSupportDetailPayload = FindSupportDetailPayload.builder()
                .supportUid(support.getSupportUid())
                .transactionHash(support.getTransactionHash())
                .supportType(support.getSupportType())
                .to(toMember)
                .from(fromMember)
                .amount(supportsol.getAmount())
                .sendTimeStamp(support.getSendTimeStamp())
                .sendMsg(support.getSendMsg())
                .arriveTimeStamp(support.getArriveTimeStamp())
                .build();
        return findSupportDetailPayload;
    }
}
