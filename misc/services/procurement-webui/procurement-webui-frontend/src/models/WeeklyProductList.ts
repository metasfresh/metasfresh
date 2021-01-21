import { types, cast, SnapshotOrInstance } from 'mobx-state-tree';

import { WeeklyProduct } from './WeeklyProduct';

export const WeeklyProductList = types
  .model({
    products: types.optional(types.array(WeeklyProduct), []),
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

export default WeeklyProductList;
