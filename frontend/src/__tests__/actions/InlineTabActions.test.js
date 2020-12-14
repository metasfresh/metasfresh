import thunk from 'redux-thunk';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import {
  updateInlineTabItemFields,
  updateInlineTabWrapperFields,
  setInlineTabWrapperData,
  setInlineTabShowMore,
} from '../../actions/InlineTabActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import gridProps from '../../../test_setup/fixtures/grid.json';
import gridLayoutFixtures from '../../../test_setup/fixtures/grid/layout.json';
import { initialState as initialViewsState } from '../../reducers/viewHandler';
import tablesHandler from '../../reducers/tables';
const middlewares = [thunk];
const mockStore = configureStore(middlewares);

const createState = function(state = {}) {
  const res = merge.recursive(
    true,
    {
      viewHandler: initialViewsState,
      tables: { ...tablesHandler(undefined, {}) },
    },
    state
  );

  return res;
};

describe('InlineTab - actions general', () => {
  /** UPDATE_INLINE_TAB_ITEM_FIELDS action */
  it('should call UPDATE_INLINE_TAB_ITEM_FIELDS action with correct payload', () => {
    const { windowType } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const initialInlineTabId = '123_AD_Tab-222_2205230';
    const initialFieldsByName = {
      BPartnerName: {
        field: 'BPartnerName',
        value: 'e',
        widgetType: 'Text',
        validStatus: {
          valid: true,
          initialValue: true,
          fieldName: 'BPartnerName',
        },
      },
    };
    const payload = {
      inlineTabId: initialInlineTabId,
      fieldsByName: initialFieldsByName,
    };
    const action = updateInlineTabItemFields({
      inlineTabId: initialInlineTabId,
      fieldsByName: initialFieldsByName,
    });

    expect(action.type).toEqual(ACTION_TYPES.UPDATE_INLINE_TAB_ITEM_FIELDS);
    expect(action.payload).toHaveProperty('inlineTabId', payload.inlineTabId);
    expect(action.payload).toHaveProperty('fieldsByName', payload.fieldsByName);

    const initialState = createState({
      viewHandler: {
        views: {
          [windowType]: {
            layout: { ...layoutResponse },
          },
        },
      },
    });
    const store = mockStore(initialState);
    const expectedActions = [
      { type: ACTION_TYPES.UPDATE_INLINE_TAB_ITEM_FIELDS, payload },
    ];

    store.dispatch(
      updateInlineTabItemFields({
        inlineTabId: initialInlineTabId,
        fieldsByName: initialFieldsByName,
      })
    );
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });

  /** UPDATE_INLINE_TAB_WRAPPER_FIELDS action */
  it('should call UPDATE_INLINE_TAB_WRAPPER_FIELDS action with correct payload', () => {
    const { windowType } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const initialInlineTabId = '123_AD_Tab-222_2205230';
    const rowId = '2205230';
    const payload = {
      inlineTabWrapperId: initialInlineTabId,
      rowId,
      response: {},
    };

    const action = updateInlineTabWrapperFields({
      inlineTabWrapperId: initialInlineTabId,
      rowId,
      response: {},
    });

    expect(action.type).toEqual(ACTION_TYPES.UPDATE_INLINE_TAB_WRAPPER_FIELDS);
    expect(action.payload).toHaveProperty(
      'inlineTabWrapperId',
      payload.inlineTabWrapperId
    );
    expect(action.payload).toHaveProperty('rowId', payload.rowId);
    expect(action.payload).toHaveProperty('response', payload.response);

    const initialState = createState({
      viewHandler: {
        views: {
          [windowType]: {
            layout: { ...layoutResponse },
          },
        },
      },
    });
    const store = mockStore(initialState);
    const expectedActions = [
      { type: ACTION_TYPES.UPDATE_INLINE_TAB_WRAPPER_FIELDS, payload },
    ];

    store.dispatch(
      updateInlineTabWrapperFields({
        inlineTabWrapperId: initialInlineTabId,
        rowId,
        response: {},
      })
    );
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });

  /** SET_INLINE_TAB_WRAPPER_DATA action */
  it('should call SET_INLINE_TAB_WRAPPER_DATA action with correct payload', () => {
    const { windowType } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const initialInlineTabId = '123_AD_Tab-222_2205230';
    const dummyProp = { dummyProp: 'test ' };
    const payload = {
      inlineTabWrapperId: initialInlineTabId,
      data: dummyProp,
    };

    const action = setInlineTabWrapperData({
      inlineTabWrapperId: initialInlineTabId,
      data: dummyProp,
    });

    expect(action.type).toEqual(ACTION_TYPES.SET_INLINE_TAB_WRAPPER_DATA);
    expect(action.payload).toHaveProperty(
      'inlineTabWrapperId',
      payload.inlineTabWrapperId
    );
    expect(action.payload).toHaveProperty('data', payload.data);

    const initialState = createState({
      viewHandler: {
        views: {
          [windowType]: {
            layout: { ...layoutResponse },
          },
        },
      },
    });
    const store = mockStore(initialState);
    const expectedActions = [
      { type: ACTION_TYPES.SET_INLINE_TAB_WRAPPER_DATA, payload },
    ];

    store.dispatch(
      setInlineTabWrapperData({
        inlineTabWrapperId: initialInlineTabId,
        data: dummyProp,
      })
    );
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });

  /** SET_INLINE_TAB_SHOW_MORE action */
  it('should call SET_INLINE_TAB_SHOW_MORE action with correct payload', () => {
    const { windowType } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const initialInlineTabId = '123_AD_Tab-222_2205230';
    const payload = {
      inlineTabWrapperId: initialInlineTabId,
      showMore: true,
    };

    const action = setInlineTabShowMore({
      inlineTabWrapperId: initialInlineTabId,
      showMore: true,
    });

    expect(action.type).toEqual(ACTION_TYPES.SET_INLINE_TAB_SHOW_MORE);
    expect(action.payload).toHaveProperty(
      'inlineTabWrapperId',
      payload.inlineTabWrapperId
    );
    expect(action.payload).toHaveProperty('showMore', payload.showMore);

    const initialState = createState({
      viewHandler: {
        views: {
          [windowType]: {
            layout: { ...layoutResponse },
          },
        },
      },
    });
    const store = mockStore(initialState);
    const expectedActions = [
      { type: ACTION_TYPES.SET_INLINE_TAB_SHOW_MORE, payload },
    ];

    store.dispatch(
      setInlineTabShowMore({
        inlineTabWrapperId: initialInlineTabId,
        showMore: true,
      })
    );
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });
});
