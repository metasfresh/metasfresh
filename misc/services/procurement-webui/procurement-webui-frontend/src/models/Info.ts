import { types, flow } from 'mobx-state-tree';
import { infoMessages } from '../api';

export const Info = types
  .model({
    content: types.string,
  })
  .actions((self) => ({
    fetchInfo: flow(function* getInfo() {
      self.content = '';
      let result;

      try {
        const { data } = yield infoMessages();
        result = data;
      } catch (error) {
        result = error;
      }
      self.content = result;
    }),
  }))
  .views((self) => ({
    get getContent() {
      return self.content;
    },
  }));
