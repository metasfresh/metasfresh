import { types } from 'mobx-state-tree';

const Navigation = types
  .model({
    viewName: types.string,
  })
  .actions((self) => ({
    setViewName(viewName: string) {
      self.viewName = viewName;
    },
  }));

export default Navigation;
