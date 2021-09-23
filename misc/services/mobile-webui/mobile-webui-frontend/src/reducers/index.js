import { combineReducers } from '@reduxjs/toolkit';
import { connectRouter } from 'connected-react-router';
import appHandler from './appHandler';
import launcher from './launcher'; 

const createRootReducer = (history) => combineReducers({
    router: connectRouter(history),
    appHandler,
    launcher,
})

export default createRootReducer;