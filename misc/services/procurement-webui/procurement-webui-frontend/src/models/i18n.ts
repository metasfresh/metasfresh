import { types } from 'mobx-state-tree';

export const i18n = types
  .model({
    lang: types.string,
    messages: types.frozen({}),
  })
  .actions((self) => ({
    changeLang(newLang: string) {
      self.lang = newLang;
    },
    changeMessages(newMessages: unknown) {
      self.messages = newMessages;
    },
  }))
  .views((self) => ({
    get retrieveLang() {
      return self.lang;
    },
    get getMessages() {
      return self.messages;
    },
  }));
