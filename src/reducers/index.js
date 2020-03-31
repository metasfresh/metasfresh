import { routerReducer as routing } from 'react-router-redux';

import appHandler from './appHandler';
import listHandler from './listHandler';
import menuHandler from './menuHandler';
import windowHandler from './windowHandler';
import pluginsHandler from './pluginsHandler';
import viewHandler from './viewHandler';

export default {
  appHandler,
  listHandler,
  menuHandler,
  windowHandler,
  viewHandler,
  pluginsHandler,
  routing,
};
