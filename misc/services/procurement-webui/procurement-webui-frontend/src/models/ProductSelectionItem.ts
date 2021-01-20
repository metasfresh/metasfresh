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
    changeProductName(newName: string) {
      self.productName = newName;
    },
    changeProductPacking(newPack: string) {
      self.productName = newPack;
    },
  }))
  .views((self) => ({
    get getProductId() {
      return self.productId;
    },
    get getName() {
      return self.productName;
    },
    get getPackingInfo() {
      return self.packingInfo;
    },
  }));
