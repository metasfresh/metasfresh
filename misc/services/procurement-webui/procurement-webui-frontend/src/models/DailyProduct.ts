import { types } from 'mobx-state-tree';

export const DailyProduct = types
  .model({
    confirmedByUser: types.boolean,
    packingInfo: types.string,
    productId: types.string,
    productName: types.string,
    qty: types.number,
    isEdited: types.optional(types.boolean, false),
  })
  .actions((self) => ({
    changeConfirmation(newConf: boolean) {
      self.confirmedByUser = newConf;
    },
    changePack(newPack: string) {
      self.packingInfo = newPack;
    },
    changeId(newId: string) {
      self.productId = newId;
    },
    changeName(newName: string) {
      self.productName = newName;
    },
    changeQty(newQty: number) {
      self.qty = newQty;
    },
    changeEditMode(newEdit: boolean) {
      self.isEdited = newEdit;
    },
  }))
  .views((self) => ({
    get getConfirmation() {
      return self.confirmedByUser;
    },
    get getPack() {
      return self.packingInfo;
    },
    get getId() {
      return self.productId;
    },
    get getName() {
      return self.productName;
    },
    get getQty() {
      return self.qty;
    },
    get getEditMode() {
      return self.isEdited;
    },
  }));
