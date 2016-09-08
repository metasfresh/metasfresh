import { combineReducers } from 'redux';
import appHandler from './appHandler';
import windowHandler from './windowHandler';
import menuHandler from './menuHandler';
import { routerReducer as routing } from 'react-router-redux';

const rootReducer = combineReducers({
    appHandler,
    menuHandler,
    windowHandler,
    routing
});

export default rootReducer;
