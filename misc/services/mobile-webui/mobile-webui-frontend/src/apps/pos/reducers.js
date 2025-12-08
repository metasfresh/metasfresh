import { ordersReducer } from './reducers/orders';
import { posTerminalReducer } from './reducers/posTerminal';
import { uiReducer } from './reducers/ui';

const initialState = {
  terminal: null,
  orders: {
    current_uuid: null,
    byUUID: {},
  },
};

export function posReducer(applicationState = initialState, action) {
  applicationState = ordersReducer(applicationState, action);
  applicationState = posTerminalReducer(applicationState, action);
  applicationState = uiReducer(applicationState, action);
  return applicationState;
}
