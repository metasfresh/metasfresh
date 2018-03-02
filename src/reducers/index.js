import { routerReducer as routing } from 'react-router-redux';
import { combineReducers } from 'redux';

import appHandler from './appHandler';
import listHandler from './listHandler';
import menuHandler from './menuHandler';
import windowHandler from './windowHandler';

const rootReducer = combineReducers({
  appHandler,
  listHandler,
  menuHandler,
  windowHandler,
  routing,
});

export default rootReducer;
