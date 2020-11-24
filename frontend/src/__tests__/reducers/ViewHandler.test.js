import merge from 'merge';

import * as ACTION_TYPES from '../../constants/ActionTypes';
import reducer, {
  initialState,
  viewState
} from '../../reducers/viewHandler';

// limited data sets as we don't need everything for those tests
import fixtures from '../../../test_setup/fixtures/grid/reducers.json';
import generalData from '../../../test_setup/fixtures/grid/data.json';


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

  it('Should handle FETCH_DOCUMENT_ERROR', () => {
    const id = layoutData.windowId;
    const error = 'Error';
    const localState = createState({ pending: true });
    const actions = [
      {
        type: ACTION_TYPES.FETCH_DOCUMENT_ERROR,
        payload: {
          id,
          error,
          isModal: false,
        },
      }
    ];
    const state = actions.reduce(reducer, localState);

    expect(state).toEqual(expect.objectContaining({
      views: expect.objectContaining({
        [id]: expect.objectContaining({ pending: false, error: false, notFound: true, error }),
      }),
      modals: {},
    }));
  });

  it('Should handle CREATE_VIEW', () => {
    const id = documentData.windowId;
    const viewId = documentData.viewId;
    const actions = [
      {
        type: ACTION_TYPES.CREATE_VIEW_PENDING,
        payload: {
          id,
          isModal: false,
        },
      },
      {
        type: ACTION_TYPES.CREATE_VIEW_SUCCESS,
        payload: {
          id,
          viewId,
          isModal: false,
        },
      }
    ];
    const state = actions.reduce(reducer, initialState);

    expect(state.views).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({ pending: false, notFound: false, viewId }),
      }),
    );
  });

  it('Should handle DELETE_VIEW', () => {
    const id = documentData.windowId;
    const viewId = documentData.viewId;
    const actions = [
      {
        type: ACTION_TYPES.CREATE_VIEW_SUCCESS,
        payload: {
          id,
          viewId,
          isModal: false,
        },
      },
      {
        type: ACTION_TYPES.DELETE_VIEW,
        payload: {
          id,
          isModal: false,
        },
      }
    ];
    const state = actions.reduce(reducer, initialState);

    expect(state.views[id]).toBeFalsy();
  });

  it('Should handle RESET_VIEW', () => {
    const id = documentData.windowId;
    const viewId = documentData.viewId;
    const actions = [
      {
        type: ACTION_TYPES.CREATE_VIEW_SUCCESS,
        payload: {
          id,
          viewId,
          isModal: false,
        },
      },
      {
        type: ACTION_TYPES.RESET_VIEW,
        payload: {
          id,
          isModal: false,
        },
      }
    ];
    const state = actions.reduce(reducer, initialState);

    expect(state.views).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({ viewId: null }),
      }),
    );
  });
});

describe('Views reducer for `modals` type', () => {
  const layoutData = fixtures.modalLayout1;
  const documentData = fixtures.basicModalData1;

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

  it('Should handle CREATE_VIEW_ERROR', () => {
    const id = documentData.windowId;
    const error = 'Error';
    const actions = [
      {
        type: ACTION_TYPES.CREATE_VIEW_PENDING,
        payload: {
          id,
          isModal: true,
        },
      },
      {
        type: ACTION_TYPES.CREATE_VIEW_ERROR,
        payload: {
          id,
          error,
          isModal: true,
        },
      }
    ];
    const state = actions.reduce(reducer, initialState);

    expect(state.modals).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({ pending: false, notFound: true, error }),
      }),
    );
  });

  it('Should handle FILTER_VIEW', () => {
    const id = documentData.windowId;
    const filterData = fixtures.basicModalFilters1;
    const { viewId, filters, size } = filterData;
    const actions = [
      {
        type: ACTION_TYPES.FILTER_VIEW_PENDING,
        payload: {
          id,
          isModal: true,
        },
      },
      {
        type: ACTION_TYPES.FILTER_VIEW_SUCCESS,
        payload: {
          id,
          data: { filters, viewId, size },
          isModal: true,
        },
      }
    ];
    const state = actions.reduce(reducer, initialState);

    expect(state.modals).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({ pending: false, notFound: false, viewId, filters }),
      }),
    );
  });

  it('Should handle UPDATE_VIEW_DATA_SUCCESS', () => {
    const id = documentData.windowId;
    const headersData = generalData.headerProperties1;
    const actions = [
      {
        type: ACTION_TYPES.UPDATE_VIEW_DATA_SUCCESS,
        payload: {
          id,
          data: { headerProperties: headersData },
          isModal: false,
        },
      }
    ];

    const state = actions.reduce(reducer, initialState);

    expect(state.views).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({ headerProperties: headersData }),
      }),
    );
  });
});
