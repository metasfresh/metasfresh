import { types, getRoot } from 'mobx-state-tree';

export const Todo = types
  .model({
    text: types.string,
    id: types.identifierNumber,
  })
  .actions((self) => ({
    remove() {
      // @ts-ignore: Not sure how to fix this yet
      getRoot(self).removeTodo(self);
    },
    edit(text: string) {
      self.text = text;
    },
  }));
