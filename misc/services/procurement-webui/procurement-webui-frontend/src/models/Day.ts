import { types } from 'mobx-state-tree';

export const Day = types
  .model({
    caption: types.string,
    currentDay: types.string,
  })
  .actions((self) => ({
    changeCaption(newCaption: string) {
      self.caption = newCaption;
    },
    changeCurrentDay(newDay: string) {
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
