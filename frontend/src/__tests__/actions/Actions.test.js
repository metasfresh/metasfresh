import thunk from 'redux-thunk';
import configureStore from 'redux-mock-store';
import nock from 'nock';
import merge from 'merge';
import { combineReducers } from 'redux';

import actionsHandler, {
  initialState as initialActionsState,
  getQuickActionsId,
  getQuickActions
} from '../../reducers/actionsHandler';
import viewHandler, { initialState as initialViewsState } from '../../reducers/viewHandler';
import { getView } from '../../reducers/viewHandler';

import {
  fetchQuickActions,
  getTableActions,
  deleteQuickActions
} from '../../actions/Actions';
import * as ACTION_TYPES from '../../constants/ActionTypes';

import gridDataFixtures from '../../../test_setup/fixtures/grid/data.json';
import gridLayoutFixtures from '../../../test_setup/fixtures/grid/layout.json';
import gridRowFixtures from '../../../test_setup/fixtures/grid/row_data.json';
import quickActionsFixtures from '../../../test_setup/fixtures/grid/quick_actions.json';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);

const createState = function(state = {}) {
  const res = merge.recursive(
    true,
    {
      viewHandler: initialViewsState,
      actions: { ...actionsHandler(undefined, {}) },
    },
    state
  );

  return res;
};

describe('QuickActions', () => {
  it(`call 'FETCH_QUICK_ACTIONS_SUCCESS' on fetching actions`, () => {
    const { windowId, viewId } = gridDataFixtures.data1;
    const { actions } = quickActionsFixtures;
    const state = createState();
    const store = mockStore(state);

    const id = getQuickActionsId({ windowId, viewId });
    const payload1 = { id };
    const payload2 = { id, actions };
    const expectedActions = [
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS, payload: payload1 },
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS_SUCCESS, payload: payload2 },
    ];

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/documentView/${windowId}/${viewId}/quickActions`)
      .reply(200, { actions });

    return store
      .dispatch(
        fetchQuickActions({
          windowId,
          viewId,
        })
      )
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining(expectedActions)
        );
      }); 
  }); 
});