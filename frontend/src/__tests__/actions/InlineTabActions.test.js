/*
 * #%L
 * metasfresh
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import thunk from 'redux-thunk';
import configureStore from 'redux-mock-store';
import { merge } from 'merge-anything';
import {
  setInlineTabAddNew,
  setInlineTabItemProp,
  setInlineTabLayoutAndData,
  setInlineTabShowMore,
  setInlineTabWrapperData,
  updateInlineTabItemFields,
  updateInlineTabWrapperFields,
} from '../../actions/InlineTabActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import gridProps from '../../../test_setup/fixtures/grid.json';
import gridLayoutFixtures from '../../../test_setup/fixtures/grid/layout.json';
import { initialState as initialViewsState } from '../../reducers/viewHandler';
import tablesHandler from '../../reducers/tables';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);

const createState = function(state = {}) {
  const res = merge(
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
    const initialInlineTabWrapperId = '123_AD_Tab-222_2205230';
    const dummyProp = { dummyProp: 'test ' };
    const payload = {
      inlineTabWrapperId: initialInlineTabWrapperId,
      data: dummyProp,
    };

    const action = setInlineTabWrapperData({
      inlineTabWrapperId: initialInlineTabWrapperId,
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
        inlineTabWrapperId: initialInlineTabWrapperId,
        data: dummyProp,
      })
    );
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });

  /** SET_INLINE_TAB_SHOW_MORE action */
  it('should call SET_INLINE_TAB_SHOW_MORE action with correct payload', () => {
    const { windowType } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const initialInlineTabWrapperId = '123_AD_Tab-222_2205230';
    const payload = {
      inlineTabWrapperId: initialInlineTabWrapperId,
      showMore: true,
    };

    const action = setInlineTabShowMore({
      inlineTabWrapperId: initialInlineTabWrapperId,
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
        inlineTabWrapperId: initialInlineTabWrapperId,
        showMore: true,
      })
    );
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });

  /** SET_INLINE_TAB_LAYOUT_AND_DATA action */
  it('should call SET_INLINE_TAB_LAYOUT_AND_DATA action with correct payload', () => {
    const { windowType } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const initialInlineTabId = '123_AD_Tab-222_2205230';
    const payload = {
      inlineTabId: initialInlineTabId,
      data: {},
    };

    const action = setInlineTabLayoutAndData({
      inlineTabId: initialInlineTabId,
      data: {},
    });

    expect(action.type).toEqual(ACTION_TYPES.SET_INLINE_TAB_LAYOUT_AND_DATA);
    expect(action.payload).toHaveProperty('inlineTabId', payload.inlineTabId);
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
      { type: ACTION_TYPES.SET_INLINE_TAB_LAYOUT_AND_DATA, payload },
    ];

    store.dispatch(
      setInlineTabLayoutAndData({
        inlineTabId: initialInlineTabId,
        data: {},
      })
    );
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });

  /** SET_INLINE_TAB_ADD_NEW action */
  it('should call SET_INLINE_TAB_ADD_NEW action with correct payload', () => {
    const { windowType } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const visible = true;
    const windowId = '123';
    const tabId = 'AD_Tab-222';
    const docId = '2205230';
    const rowId = '111111';
    const payload = {
      visible,
      windowId,
      tabId,
      rowId,
      docId,
    };

    const action = setInlineTabAddNew(payload);

    expect(action.type).toEqual(ACTION_TYPES.SET_INLINE_TAB_ADD_NEW);
    expect(action.payload).toHaveProperty('visible', payload.visible);
    expect(action.payload).toHaveProperty('windowId', payload.windowId);
    expect(action.payload).toHaveProperty('docId', payload.docId);
    expect(action.payload).toHaveProperty('rowId', payload.rowId);
    expect(action.payload).toHaveProperty('tabId', payload.tabId);

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
      { type: ACTION_TYPES.SET_INLINE_TAB_ADD_NEW, payload },
    ];

    store.dispatch(
      setInlineTabAddNew({
        visible,
        windowId,
        tabId,
        rowId,
        docId,
      })
    );
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });

  /** SET_INLINE_TAB_ITEM_PROP action */
  it('should call SET_INLINE_TAB_ITEM_PROP action with correct payload', () => {
    const { windowType } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const initialInlineTabId = '123_AD_Tab-222_2205230';
    const payload = {
      inlineTabId: initialInlineTabId,
      targetProp: 'promptOpen',
      targetValue: true,
    };

    const action = setInlineTabItemProp({
      inlineTabId: initialInlineTabId,
      targetProp: 'promptOpen',
      targetValue: true,
    });

    expect(action.type).toEqual(ACTION_TYPES.SET_INLINE_TAB_ITEM_PROP);
    expect(action.payload).toHaveProperty('inlineTabId', payload.inlineTabId);
    expect(action.payload).toHaveProperty('targetProp', payload.targetProp);
    expect(action.payload).toHaveProperty('targetValue', payload.targetValue);

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
      { type: ACTION_TYPES.SET_INLINE_TAB_ITEM_PROP, payload },
    ];

    store.dispatch(
      setInlineTabItemProp({
        inlineTabId: initialInlineTabId,
        targetProp: 'promptOpen',
        targetValue: true,
      })
    );
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });
});
