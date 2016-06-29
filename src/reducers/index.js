import { combineReducers } from 'redux';
import salesOrderStateHandler from './salesOrderStateHandler';
import { routerReducer as routing } from 'react-router-redux';

const rootReducer = combineReducers({
    salesOrderStateHandler,
    routing
});

export default rootReducer;
