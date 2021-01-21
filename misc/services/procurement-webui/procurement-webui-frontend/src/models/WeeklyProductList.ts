import { types, cast, SnapshotOrInstance } from 'mobx-state-tree';

import { WeeklyProduct } from './WeeklyProduct';

export const WeeklyProductList = types
  .model({
    nextWeek: types.optional(types.string, ''),
    nextWeekCaption: types.optional(types.string, ''),
    previousWeek: types.optional(types.string, ''),
    products: types.optional(types.array(WeeklyProduct), []),
    week: types.optional(types.string, ''),
    weekCaption: types.optional(types.string, ''),
  })
  .actions((self) => ({
    updateNextWeek(nextWeek: string) {
      self.nextWeek = nextWeek;
    },
    updateNextCaption(nextWeekCaption: string) {
      self.nextWeekCaption = nextWeekCaption;
    },
    updatePreviousWeek(previousWeek: string) {
      self.previousWeek = previousWeek;
    },
    updateProductList(newList: SnapshotOrInstance<typeof self.products>) {
      self.products = cast(newList);
    },
    updateWeek(week: string) {
      self.week = week;
    },
    updateWeekCaption(weekCaption: string) {
      self.weekCaption = weekCaption;
    },
  }))
  .views((self) => ({
    get getProducts() {
      return self.products;
    },
    findProductById(productId: string) {
      return self.products.find((item) => item.productId === productId);
    },
  }));

export default WeeklyProductList;
