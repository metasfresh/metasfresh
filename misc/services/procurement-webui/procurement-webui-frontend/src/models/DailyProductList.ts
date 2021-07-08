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
    updateProductQty(productId, qty) {
      const newList = self.products.map((item) => {
        if (item.productId === productId) {
          const newQty = qty ? parseInt(qty) : 0;
          item.changeQty(newQty);
          item.changeEditMode(true);
        }
        return item;
      });
      self.products = cast(newList);
    },
  }))
  // TODO: This is not really different from `store.productsList.products
  // According to the docs views should be used when computations are needed
  .views((self) => ({
    get getProducts() {
      return self.products;
    },
  }));

export default DailyProductList;
