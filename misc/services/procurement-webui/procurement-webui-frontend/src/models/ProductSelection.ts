import { types, flow } from 'mobx-state-tree';
import { getNotFavorite } from '../api';
import { ProductSelectionItem } from './ProductSelectionItem';

const ProductSelectionArray = types.array(ProductSelectionItem);

export const ProductSelection = types
  .model({
    products: types.optional(ProductSelectionArray, []),
    moreProducts: types.optional(ProductSelectionArray, []),
    showMoreBtnVisible: types.optional(types.boolean, true),
  })
  .actions((self) => ({
    fetchSelectionProducts: flow(function* getSelectionProducts() {
      self.products = [] as typeof ProductSelectionArray.Type;
      self.moreProducts = [] as typeof ProductSelectionArray.Type;
      try {
        const {
          data: { moreProducts, products },
        } = yield getNotFavorite();
        self.products = products;
        self.moreProducts = moreProducts;
      } catch (error) {
        console.error('Could not fetch the products due to:', error);
      }
    }),
    toggleShowMore() {
      self.showMoreBtnVisible = !self.showMoreBtnVisible;
    },
  }))
  .views((self) => ({
    get retrieveProducts() {
      return self.products;
    },
    get retrieveMoreProducts() {
      return self.moreProducts;
    },
  }));
