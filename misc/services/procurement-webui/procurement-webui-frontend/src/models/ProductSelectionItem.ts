import { types } from 'mobx-state-tree';

export const ProductSelectionItem = types
  .model({
    productId: types.string,
    productName: types.string,
    packingInfo: types.string,
  })
  .actions((self) => ({
    changeProductId(newId: string) {
      self.productId = newId;
    },
  }))
  .views((self) => ({
    get getProductId() {
      return self.productId;
    },
  }));
