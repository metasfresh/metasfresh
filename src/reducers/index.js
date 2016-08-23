import { combineReducers } from 'redux';
import appHandler from './appHandler';
import windowHandler from './windowHandler';
import { routerReducer as routing } from 'react-router-redux';

const rootReducer = combineReducers({
    appHandler,
    windowHandler,
    routing
});

export default rootReducer;
