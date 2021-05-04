import { combineReducers } from 'redux';
// import { connectRouter } from 'connected-react-router';

import appHandler from './appHandler';
import listHandler from './listHandler';
import menuHandler from './menuHandler';
import windowHandler from './windowHandler';
import pluginsHandler from './pluginsHandler';
import viewHandler from './viewHandler';
import filters from './filters';
import commentsPanel from './commentsPanel';
import tables from './tables';
import actionsHandler from './actionsHandler';

export const createRootReducer = () =>
  combineReducers({
    // router: connectRouter(history),
    appHandler,
    listHandler,
    menuHandler,
    windowHandler,
    viewHandler,
    pluginsHandler,
    filters,
    commentsPanel,
    tables,
    // routing,
    actionsHandler,
  });

// import { combineReducers } from 'redux'
// import { connectRouter } from 'connected-react-router'

// const createRootReducer = (history) => combineReducers({
//   router: connectRouter(history),
//   ... // rest of your reducers
// })
// export default createRootReducer