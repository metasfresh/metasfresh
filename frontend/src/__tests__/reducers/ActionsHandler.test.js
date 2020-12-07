import merge from 'merge';

import * as ACTION_TYPES from '../../constants/ActionTypes';
import reducer, {
  initialState,
  initialSingleActionsState,
  getQuickActionsId,
} from '../../reducers/actionsHandler';

import gridDataFixtures from '../../../test_setup/fixtures/grid/data.json';
import quickActionsFixtures from '../../../test_setup/fixtures/grid/quick_actions.json';

const createState = function(state = {}) {
  const res = merge.recursive(
    true,
    {
      ...initialState,
    },
    state
  );

  return res;
};

describe('Actions reducer', () => {
  const { windowId, viewId } = gridDataFixtures.data1;
  const id = getQuickActionsId({ windowId, viewId });
  const basicData = {
    ...initialSingleActionsState
  };

  it('should return the initial state', () => {
    expect(reducer(undefined, {})).toEqual(initialState);
  });

  it('Should handle FETCH_QUICK_ACTIONS', () => {
    expect(
      reducer(undefined, {
        type: ACTION_TYPES.FETCH_QUICK_ACTIONS,
        payload: {
          id,
        },
      })
    ).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({ ...basicData, pending: true }),
      })
    );
  });

  it('Should handle FETCH_QUICK_ACTIONS_SUCCESS', () => {
    const { actions } = quickActionsFixtures;

    const initialStateData = createState({
      [id]: { ...basicData, pending: true, },
    });
    const successAction = {
      type: ACTION_TYPES.FETCH_QUICK_ACTIONS_SUCCESS,
      payload: {
        id,
        actions,
      },
    };

    const actionsList = [successAction];
    const state = actionsList.reduce(reducer, initialStateData);

    expect(state).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({ ...basicData, actions }),
      })
    );
  });

  it('Should handle FETCH_QUICK_ACTIONS_SUCCESS and delete actions marked with `toDelete`', () => {
    const { actions } = quickActionsFixtures;

    const initialStateData = createState({
      [id]: { ...basicData, pending: true, toDelete: true },
    });
    const successAction = {
      type: ACTION_TYPES.FETCH_QUICK_ACTIONS_SUCCESS,
      payload: {
        id,
        actions,
      },
    };

    const actionsList = [successAction];
    const state = actionsList.reduce(reducer, initialStateData);

    expect(state).toEqual({});
  });

  it('Should handle FETCH_QUICK_ACTIONS_SUCCESS', () => {
    const { actions } = quickActionsFixtures;

    const initialStateData = createState({
      [id]: { ...basicData, pending: true, },
    });
    const failedAction = {
      type: ACTION_TYPES.FETCH_QUICK_ACTIONS_FAILURE,
      payload: {
        id,
      },
    };

    const actionsList = [failedAction];
    const state = actionsList.reduce(reducer, initialStateData);

    expect(state).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({ ...basicData, error: true }),
      })
    );
  });

  it('Should handle DELETE_QUICK_ACTIONS', () => {
    const { actions } = quickActionsFixtures;

    const initialStateData = createState({
      [id]: { ...basicData, actions },
    });
    const deleteAction = {
      type: ACTION_TYPES.DELETE_QUICK_ACTIONS,
      payload: {
        id,
      },
    };

    const actionsList = [deleteAction];
    const state = actionsList.reduce(reducer, initialStateData);

    expect(state).toEqual({});
  });

  it('Should handle DELETE_QUICK_ACTIONS and mark pending QA as `toDelete`', () => {
    const { actions } = quickActionsFixtures;

    const initialStateData = createState({
      [id]: { ...basicData, pending: true },
    });
    const deleteAction = {
      type: ACTION_TYPES.DELETE_QUICK_ACTIONS,
      payload: {
        id,
      },
    };

    const actionsList = [deleteAction];
    const state = actionsList.reduce(reducer, initialStateData);

    expect(state).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({ ...basicData, pending: true, toDelete: true }),
      })
    );
  });
});
