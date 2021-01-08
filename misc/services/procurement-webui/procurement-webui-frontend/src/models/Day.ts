import { types } from 'mobx-state-tree';

export const Day = types
  .model({
    currentDay: types.string,
  })
  .actions((self) => ({
    changeCurrentDay(newDay: string) {
      self.currentDay = newDay;
    },
  }))
  .views((self) => ({
    get retrieveCurrentDay() {
      return self.currentDay;
    },
  }));
