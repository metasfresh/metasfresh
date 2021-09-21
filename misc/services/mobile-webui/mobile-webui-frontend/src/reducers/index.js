import { combineReducers } from '@reduxjs/toolkit';
import { connectRouter } from 'connected-react-router';
import appHandler from './appHandler';

const createRootReducer = (history) => combineReducers({
    router: connectRouter(history),
    appHandler,
})

export default createRootReducer;