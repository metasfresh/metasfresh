import { types, flow } from 'mobx-state-tree';
import { getNotFavorite, favoriteAdd, favoriteRemove } from '../api';
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
    setShowMoreVisibility(visibility: boolean) {
      self.showMoreBtnVisible = visibility;
    },
    favoriteAdd: flow(function* favAddGen(dataObj: Array<string>) {
      try {
        yield favoriteAdd(dataObj);
      } catch (error) {
        console.error('Failed to post', error);
      }
    }),
    favoriteRemove: flow(function* favRemGen(dataObj: Array<string>) {
      try {
        yield favoriteRemove(dataObj);
      } catch (error) {
        console.error('Failed to post', error);
      }
    }),
  }))
  .views((self) => ({
    get retrieveProducts() {
      return self.products;
    },
    get retrieveMoreProducts() {
      return self.moreProducts;
    },
  }));
