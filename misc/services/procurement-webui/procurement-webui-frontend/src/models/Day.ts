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
    // fetchDailyReport: flow(function* fetchReport(date: string) {
    //   try {
    //     const response = yield fetchDailyReport(date);

    //     // self.navigation.setViewName(response.data.dayCaption);
    //     // TODO: Do stuff for daily report
    //   } catch (error) {
    //     console.error('Failed to fetch', error);
    //   }
    // }),
  }));
// .views((self) => ({
//   get getCaption() {
//     return self.caption;
//   },
//   get getCurrentDay() {
//     return self.currentDay;
//   },
// }));
