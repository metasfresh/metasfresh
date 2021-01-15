import { types } from 'mobx-state-tree';

import { DailyProduct } from './DailyProduct';
export const DailyProductList = types
  .model({
    list: types.array(DailyProduct),
  })
  .actions((self) => ({
    updateProductList(newList: any) {
      self.list = newList;
    },
  }))
  .views((self) => ({
    get getProducts() {
      return self.list;
    },
  }));

export default DailyProductList;
