import merge from 'merge';

import * as ACTION_TYPES from '../../constants/ActionTypes';
import reducer, {
  initialState,
  viewState
} from '../../reducers/viewHandler';

// limited data sets as we don't need everything for those tests
import fixtures from '../../../test_setup/fixtures/grid/reducers.json';

const createState = function(state = {}) {
  return merge.recursive(
    true,
    {
      ...initialState,
    },
    state
  );
};

const formatData = function(data) {
  const {
    firstRow,
    headerProperties,
    pageLength,
    type,
    viewId,
    windowId,
    orderBy,
    staticFilters,
    queryLimit,
    queryLimitHit,
  } = data;

  return {
    firstRow,
    headerProperties,
    pageLength,
    type,
    viewId,
    windowId,
    orderBy,
    staticFilters,
    queryLimit,
    queryLimitHit,
  };
};

describe('Views reducer for `view` type', () => {
  const layoutData = fixtures.viewLayout1;
  const documentData = fixtures.basicViewData1;

  it('should return the initial state', () => {
    expect(reducer(undefined, {})).toEqual(initialState);
  });

  it('Should handle FETCH_LAYOUT', () => {
    const id = layoutData.windowId;
    const actions = [
      {
        type: ACTION_TYPES.FETCH_LAYOUT_PENDING,
        payload: {
          id,
          isModal: false,
        },
      },
      {
        type: ACTION_TYPES.FETCH_LAYOUT_SUCCESS,
        payload: {
          id,
          layout: layoutData,
          isModal: false,
        },
      }
    ];
    const state = actions.reduce(reducer, initialState);

    expect(state).toEqual(expect.objectContaining({
      views: expect.objectContaining({
        [id]: expect.objectContaining({ layout: expect.objectContaining({ ...layoutData }) }),
      }),
      modals: {},
    }));
  });

  it('Should handle FETCH_LAYOUT_ERROR', () => {
    const id = layoutData.windowId;
    const error = 'Error';
    const actions = [
      {
        type: ACTION_TYPES.FETCH_LAYOUT_PENDING,
        payload: {
          id,
          isModal: false,
        },
      },
      {
        type: ACTION_TYPES.FETCH_LAYOUT_ERROR,
        payload: {
          id,
          error,
          isModal: false,
        },
      }
    ];
    const state = actions.reduce(reducer, initialState);

    expect(state).toEqual(expect.objectContaining({
      views: expect.objectContaining({
        [id]: expect.objectContaining({ layoutPending: false, layoutError: error, layoutNotFound: true }),
      }),
      modals: {},
    }));
  });

  it('Should handle FETCH_DOCUMENT', () => {
    const id = layoutData.windowId;
    const actions = [
      {
        type: ACTION_TYPES.FETCH_DOCUMENT_PENDING,
        payload: {
          id,
          isModal: false,
        },
      },
      {
        type: ACTION_TYPES.FETCH_DOCUMENT_SUCCESS,
        payload: {
          id,
          data: documentData,
          isModal: false,
        },
      }
    ];
    const state = actions.reduce(reducer, initialState);
    const formattedData = formatData(documentData);

    expect(state).toEqual(expect.objectContaining({
      views: expect.objectContaining({
        [id]: expect.objectContaining({ ...formattedData }),
      }),
      modals: {},
    }));
  });
});

describe('Views reducer for `modals` type', () => {
  const layoutData = fixtures.modalLayout1;

  it('Should handle FETCH_LAYOUT', () => {
    const id = layoutData.windowId;
    const actions = [
      {
        type: ACTION_TYPES.FETCH_LAYOUT_PENDING,
        payload: {
          id,
          isModal: true,
        },
      },
      {
        type: ACTION_TYPES.FETCH_LAYOUT_SUCCESS,
        payload: {
          id,
          layout: layoutData,
          isModal: true,
        },
      }
    ];
    const state = actions.reduce(reducer, initialState);

    expect(state).toEqual(expect.objectContaining({
      modals: expect.objectContaining({
        [id]: expect.objectContaining({ layout: expect.objectContaining({ ...layoutData }) }),
      }),
      views: {},
    }));
  });
});
