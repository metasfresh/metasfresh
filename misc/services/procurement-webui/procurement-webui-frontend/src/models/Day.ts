import { types } from 'mobx-state-tree';

export const Day = types
  .model({
    caption: types.string,
    currentDay: types.Date,
  })
  .actions((self) => ({
    changeCaption(newCaption: string) {
      self.caption = newCaption;
    },
    changeCurrentDay(newDay: Date) {
      self.currentDay = newDay;
    },
  }))
  .views((self) => ({
    get retrieveCaption() {
      return self.caption;
    },
    get retrieveCurrentDay() {
      return self.currentDay;
    },
  }));
