import { combineReducers } from 'redux';

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
import widgetHandler from './widgetHandler';

export const createRootReducer = () =>
  combineReducers({
    appHandler,
    listHandler,
    menuHandler,
    windowHandler,
    viewHandler,
    pluginsHandler,
    filters,
    commentsPanel,
    tables,
    actionsHandler,
    widgetHandler,
  });
