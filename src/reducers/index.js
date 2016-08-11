import { combineReducers } from 'redux';
import salesOrderStateHandler from './salesOrderStateHandler';
import windowHandler from './windowHandler';
import { routerReducer as routing } from 'react-router-redux';

const rootReducer = combineReducers({
    salesOrderStateHandler,
    windowHandler,
    routing
});

export default rootReducer;
