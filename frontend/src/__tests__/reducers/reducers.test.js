import appHandler, { initialState as appHandlerState } from '../../reducers/appHandler';
import windowHandler, { initialState as windowHandlerState } from '../../reducers/windowHandler';
import menuHandler, { initialState as menuHandlerState } from '../../reducers/menuHandler';
import listHandler, { initialState as listHandlerState } from '../../reducers/listHandler';
import pluginsHandler, { initialState as pluginsHandlerState } from '../../reducers/pluginsHandler';
import viewHandler, { initialState as viewHandlerState } from '../../reducers/viewHandler';

describe.skip('Composed project reducer', () => {
  const initialComposedState = {
    appHandler: { ...appHandlerState },
    windowHandler: { ...windowHandlerState },
    listHandler: { ...listHandlerState },
    menuHandler: { ...menuHandlerState },
    pluginsHandler: { ...pluginsHandlerState },
    viewHandler: { ...viewHandlerState },
  };

  const reducerState = {
    appHandler: appHandler(undefined, {}),
    windowHandler: windowHandler(undefined, {}),
    listHandler: listHandler(undefined, {}),
    menuHandler: menuHandler(undefined, {}),
    pluginsHandler: pluginsHandler(undefined, {}),
    viewHandler: viewHandler(undefined, {}),
  }

  it('should return the initial state', () => {
    expect(reducerState).toEqual(initialComposedState);
  });
});
