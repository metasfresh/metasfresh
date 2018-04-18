import { routerReducer as routing } from 'react-router-redux';
import { combineReducers } from 'redux';

import appHandler from './appHandler';
import listHandler from './listHandler';
import menuHandler from './menuHandler';
import windowHandler from './windowHandler';
import pluginsHandler from './pluginsHandler';

const rootReducer = combineReducers({
  appHandler,
  listHandler,
  menuHandler,
  windowHandler,
  pluginsHandler,
  routing,
});

export default rootReducer;
