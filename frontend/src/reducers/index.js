import { routerReducer as routing } from 'react-router-redux';

import appHandler from './appHandler';
import listHandler from './listHandler';
import menuHandler from './menuHandler';
import windowHandler from './windowHandler';
import pluginsHandler from './pluginsHandler';
import viewHandler from './viewHandler';
import filters from './filterHandler';
import commentsPanel from './commentsPanel';
import tables from './tables';

export default {
  appHandler,
  listHandler,
  menuHandler,
  windowHandler,
  viewHandler,
  pluginsHandler,
  filters,
  commentsPanel,
  tables,
  routing,
};
