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

    it('Should handle updating rows with UPDATE_TAB_ROWS_DATA', () => {
      const initialStateData = createState({
        master: {
          rowData: Map({ 'Tab-1': List([{ rowId: '10000', value: '1' }, { rowId: '10001', value: '2' }]) }),
        }
      });
      const localReducer = (acc, action) => reducer(initialStateData, action);
      const data = {
        'Tab-1': {
          changed: {
            '10000': {
              rowId: '10000',
              value: '99',
            },
          },
        },
      };
      let expected = Map();
      expected = expected.set('Tab-1', List([{ rowId: '10000', value: '99' }, { rowId: '10001', value: '2' }]));

      const actions = [{}, updateTabRowsData('master', 'Tab-1', data['Tab-1'])];
      const state = actions.reduce(localReducer, undefined);

      expect(state.master.rowData).toEqual(expected);
    });

    it('Should handle removing/creating rows with UPDATE_TAB_ROWS_DATA', () => {
      const initialStateData = createState({
        master: {
          rowData: Map({ 'Tab-1': List([{ rowId: '10000', value: '1' }, { rowId: '10001', value: '2' }]) }),
        }
      });
      const localReducer = (acc, action) => reducer(initialStateData, action);
      const data = {
        'Tab-1': {
          removed: {
            '10001': true,
          },
          changed: {
            '10002': {
              rowId: '10099',
              value: '199',
            },
          },
        },
      };
      let expected = Map();
      expected = expected.set('Tab-1', List([
        { rowId: '10000', value: '1' },
        { rowId: '10099', value: '199' }
      ]));

      const actions = [{}, updateTabRowsData('master', 'Tab-1', data['Tab-1'])];
      const state = actions.reduce(localReducer, undefined);

      expect(state.master.rowData).toEqual(expected);
    });

    // @TODO: Add tests for FETCH_TOP_ACTIONS
});
