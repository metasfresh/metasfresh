import nock from 'nock';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import { Map, List } from 'immutable';

import {
  updateTabRowsData,
  initDataSuccess,
  initLayoutSuccess,
} from '../../actions/WindowActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import reducer, { initialState } from '../../reducers/windowHandler';

const createState = function(state = {}) {
  return merge.recursive(
    true,
    {
      ...initialState,
    },
    state
  );
}

describe('WindowHandler reducer', () => {
    it('should return the initial state', () => {
      expect(reducer(undefined, {})).toEqual(initialState);
    });

    it('Should handle INIT_LAYOUT_SUCCESS', () => {
      const layout = {
        activeTab: '1',
        sections: [],
        tabs: [ { windowId: '1' }],
        windowId: '1'
      };

      expect(
        reducer(undefined, {
          type: ACTION_TYPES.INIT_LAYOUT_SUCCESS,
          scope: 'master',
          layout,
        })
      ).toEqual(expect.objectContaining({ master: expect.objectContaining({ layout: { ...layout } }) }));
    });

    it('Should handle INIT_DATA_SUCCESS', () => {
      const data = {
        data: {
          "ID": {
            "field": "ID",
            "value": 1000000,
            "widgetType": "Integer"
          }
        },
        docId: '1000',
        saveStatus: {},
        scope: 'master',
        standardActions: [],
        validStatus: {},
        includedTabsInfo: {},
        websocketEndpoint: 'test/url',
      };

      const actions = [{}, initDataSuccess({ ...data})];
      const state = actions.reduce(reducer, undefined);

      expect(state).toEqual(expect.objectContaining({ master: expect.objectContaining({ data: { ...data.data } }) }));
    });

    // @TODO: Add tests for FETCH_TOP_ACTIONS
});
