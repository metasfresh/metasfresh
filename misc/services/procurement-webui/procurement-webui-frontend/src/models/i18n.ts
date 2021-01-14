import { types } from 'mobx-state-tree';

export const i18n = types
  .model({
    lang: types.string,
  })
  .actions((self) => ({
    changeLang(newLang: string) {
      self.lang = newLang;
    },
  }))
  .views((self) => ({
    get retrieveLang() {
      return self.lang;
    },
  }));
