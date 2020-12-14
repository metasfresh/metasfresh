import thunk from 'redux-thunk';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import { combineReducers } from 'redux';
import nock from 'nock';
import { getQuickActionsId } from '../../reducers/actionsHandler';
import { updateInlineTabItemFields } from '../../actions/InlineTabActions';
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
  it('should call UPDATE_INLINE_TAB_ITEM_FIELDS action with correct payload', () => {
    const { windowType, viewId } = gridProps.props1;
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
});
