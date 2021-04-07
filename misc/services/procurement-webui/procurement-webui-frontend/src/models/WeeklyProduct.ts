import { types } from 'mobx-state-tree';
import { DailyQuantity } from './DailyQuantity';

export const WeeklyProduct = types
  .model({
    dailyQuantities: types.optional(types.array(DailyQuantity), []),
    nextWeekTrend: types.maybeNull(types.string),
    packingInfo: types.string,
    productId: types.string,
    productName: types.string,
    qty: types.number,
  })
  .actions((self) => ({
    changeWeekTrend(newTrend: string) {
      self.packingInfo = newTrend;
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
  }))
  .views((self) => ({
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
    get getTrend() {
      return self.nextWeekTrend;
    },
  }));
