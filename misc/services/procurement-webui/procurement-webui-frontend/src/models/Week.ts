import { types } from 'mobx-state-tree';

export const Week = types
  .model({
    caption: types.string,
    prevWeek: types.string,
    currentWeek: types.string,
    nextWeek: types.string,
  })
  .actions((self) => ({
    changeCaption(newCaption: string) {
      self.caption = newCaption;
    },
    changePrevWeek(newPrevWeek: string) {
      self.prevWeek = newPrevWeek;
    },
    changeCurrentWeek(newWeek: string) {
      self.currentWeek = newWeek;
    },
    changeNextWeek(newNextWeek: string) {
      self.nextWeek = newNextWeek;
    },
  }))
  .views((self) => ({
    get retrieveCaption() {
      return self.caption;
    },
    get retrievePrevWeek() {
      return self.prevWeek;
    },
    get retrieveCurrentWeek() {
      return self.currentWeek;
    },
    get retrieveNextWeek() {
      return self.nextWeek;
    },
  }));
