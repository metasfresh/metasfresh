import { types, cast } from 'mobx-state-tree';

const Navigation = types
  .model({
    topViewName: types.string,
    bottomViewName: types.string,
    viewsHistory: types.array(types.string),
  })
  .actions((self) => {
    const setViewNames = function setViewNames(viewName: string) {
      setTopViewName(viewName);
      setBottomViewName(viewName);
      addViewToHistory(viewName);
    };
    const setTopViewName = function setTopViewName(viewName: string) {
      self.topViewName = viewName;
    };
    const setBottomViewName = function setBottomViewName(viewName: string) {
      self.bottomViewName = viewName;
    };
    const addViewToHistory = function (viewName: string) {
      const currentLast = self.viewsHistory[self.viewsHistory.length - 1];
      if (viewName !== currentLast) {
        self.viewsHistory.push(viewName);
      }
    };
    const removeViewFromHistory = function addViewToHistory(): void {
      self.viewsHistory.pop();
    };
    const clearViewsHistory = function clearViewsHistory(): void {
      self.viewsHistory = cast([]);
    };

    return {
      setViewNames,
      setTopViewName,
      setBottomViewName,
      addViewToHistory,
      removeViewFromHistory,
      clearViewsHistory,
    };
  })
  .views((self) => ({
    getLastView(): string {
      if (self.viewsHistory.length > 1) {
        return self.viewsHistory[self.viewsHistory.length - 2];
      }
      return self.viewsHistory[self.viewsHistory.length - 1];
    },
  }));

export default Navigation;
