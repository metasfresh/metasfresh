import { types } from 'mobx-state-tree';

export const DailyQuantity = types
  .model({
    confirmedByUser: types.boolean,
    date: types.string,
    dayCaption: types.string,
    qty: types.number,
  })
  .actions((self) => ({
    changeConfirmation(newConf: boolean) {
      self.confirmedByUser = newConf;
    },
    changeDate(newDate: string) {
      self.date = newDate;
    },
    changeDayCaption(newCaption: string) {
      self.dayCaption = newCaption;
    },
    changeQty(newQty: number) {
      self.qty = newQty;
    },
  }))
  .views((self) => ({
    get getConfirmation() {
      return self.confirmedByUser;
    },
    get getDate() {
      return self.date;
    },
    get getDayCaption() {
      return self.dayCaption;
    },
    get getQty() {
      return self.qty;
    },
  }));
