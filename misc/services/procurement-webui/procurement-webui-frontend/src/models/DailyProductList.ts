import { types, cast, SnapshotOrInstance } from 'mobx-state-tree';

import { DailyProduct } from './DailyProduct';

export const DailyProductList = types
  .model({
    products: types.optional(types.array(DailyProduct), []),
  })
  .actions((self) => ({
    updateProductList(newList: SnapshotOrInstance<typeof self.products>) {
      self.products = cast(newList);
    },
  }))
  .views((self) => ({
    get getProducts() {
      return self.products;
    },
  }));

export default DailyProductList;
