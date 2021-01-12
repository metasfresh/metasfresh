import { types } from 'mobx-state-tree';

export const Week = types
  .model({
    caption: types.string,
    currentWeek: types.string,
  })
  .actions((self) => ({
    changeCaption(newCaption: string) {
      self.caption = newCaption;
    },
    changeCurrentWeek(newWeek: string) {
      self.currentWeek = newWeek;
    },
  }))
  .views((self) => ({
    get retrieveCaption() {
      return self.caption;
    },
    get retrieveCurrentWeek() {
      return self.currentWeek;
    },
  }));
