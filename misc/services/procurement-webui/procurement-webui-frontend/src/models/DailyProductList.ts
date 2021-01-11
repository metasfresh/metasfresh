import { types } from 'mobx-state-tree';

import { DailyProduct } from './DailyProduct';
export const DailyProductList = types.model({
  list: types.array(DailyProduct),
});

export default DailyProductList;
