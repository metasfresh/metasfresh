import { types } from 'mobx-state-tree';

const Navigation = types
  .model({
    topViewName: types.string,
    bottomViewName: types.string,
  })
  .actions((self) => ({
    setViewNames(viewName: string) {
      self.topViewName = viewName;
      self.bottomViewName = viewName;
    },
    setTopViewName(viewName: string) {
      self.topViewName = viewName;
    },
    setBottomViewName(viewName: string) {
      self.bottomViewName = viewName;
    },
  }));

export default Navigation;
