import * as S from "./style";
import PropTypes from "prop-types";
import { useEffect, useState } from "react";
import ItemDetailModal from "../../../Common/Modal/ItemDetailModal";
import { buyItemDonation } from "../../../../utils/transactionFunc/buyItemDonation";
import { calculateEth } from "../../../../utils/calculateEth";
import { itemApi } from "../../../../api/items";
import { useSelector } from "react-redux";

const ItemCard = ({ item, isOwner }) => {
  const [isShowItemDetailModal, setIsShowItemDetailModal] = useState(false);
  const [isAlreadyBought, setIsAlreadyBought] = useState(false);
  const [btnText, setBtnText] = useState("");

  const loginUserAddress = useSelector(
    (state) => state.member.walletAddress
  ).toLowerCase();

  const getIsPurchased = async () => {
    try {
      const { data } = await itemApi.getIsPurchased(item.id, loginUserAddress);
      if (data) setBtnText("Download");
      else setBtnText("Buy");
      setIsAlreadyBought(data);
    } catch (error) {
      console.log("error: ", error);
    }
  };

  useEffect(() => {
    if (!isOwner) {
      getIsPurchased();
    }
  }, []);

  const doBuy = () => {
    // 해당 아이템을 구매하는 api
    buyItemDonation(item);
    console.log("buy");
  };

  return (
    <S.Container>
      <S.ItemImg imgPath={item.imgPath} />
      <S.DescriptionContainer>
        <S.Title>{item.title}</S.Title>
        <S.Description>{item.description}</S.Description>
        <S.PriceBtnContainer>
          <S.PriceWrapper>
            <S.Price>{calculateEth(item.price)}</S.Price>
            <S.Unit>matic</S.Unit>
          </S.PriceWrapper>
          {!isOwner && (
            <S.BuyBtn
              color=""
              onClick={() => {
                setIsShowItemDetailModal(true);
              }}
            >
              {btnText}
            </S.BuyBtn>
          )}
        </S.PriceBtnContainer>
      </S.DescriptionContainer>
      {isShowItemDetailModal && (
        <ItemDetailModal
          uid={item.id}
          handleSetShowModal={setIsShowItemDetailModal}
          handleOnClickButton={doBuy}
          isAlreadyBought={isAlreadyBought}
        />
      )}
    </S.Container>
  );
};

export default ItemCard;

ItemCard.propTypes = {
  item: PropTypes.shape({
    imgPath: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
  }).isRequired,
  isOwner: PropTypes.bool,
};
