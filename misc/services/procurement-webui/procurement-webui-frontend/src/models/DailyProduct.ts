import { types } from 'mobx-state-tree';

export const DailyProduct = types
  .model({
    name: types.string,
    pack: types.string,
    items: types.number,
    isEdited: types.boolean,
  })
  .actions((self) => ({
    changeCaption(newName: string) {
      self.name = newName;
    },
    changePack(newPack: string) {
      self.pack = newPack;
    },
  }))
  .views((self) => ({
    get getName() {
      return self.name;
    },
    get getPack() {
      return self.pack;
    },
  }));
