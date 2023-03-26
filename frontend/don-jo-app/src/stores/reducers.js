import { combineReducers } from "@reduxjs/toolkit";
import { persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage/session";
import member from "./member/index.js";
import items from "./items/index.js";
import web3 from "./web3/index.js";
import donation from "./donation";
const persistConfig = {
  key: "root",
  storage,
  whitelist: ["member"],
};

const reducers = combineReducers({
  member,
  items,
  web3,
  donation,
});

export default persistReducer(persistConfig, reducers);
