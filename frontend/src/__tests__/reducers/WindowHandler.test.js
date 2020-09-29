import nock from 'nock';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import { Map, List } from 'immutable';

import masterWindowData from '../../../test_setup/fixtures/master_window/data.json';
import masterWindowLayout from '../../../test_setup/fixtures/master_window/layout.json';
import modalFixtures from '../../../test_setup/fixtures/window/modal.json';
import {
  updateTabRowsData,
  initDataSuccess,
  initLayoutSuccess,
} from '../../actions/WindowActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import reducer, {
  initialState,
  getData,
  getElementWidgetData,
  getElementWidgetFields,
  getMasterDocStatus,
  getProcessWidgetData,
  getProcessWidgetFields,
} from '../../reducers/windowHandler';

const createState = function(state = {}) {
  return merge.recursive(
    true,
    {
      windowHandler: {
        ...initialState,
      },
    },
    state
  );
};

describe('WindowHandler helper functions', () => {
  it('getData should return state.windowHandler.master.data', () => {
    const state = createState({
      windowHandler: {
        master: {
          data: masterWindowData.data1[0].fieldsByName,
        },
      },
    });
    const masterData = getData(state, false);

    expect(masterData).toEqual(state.windowHandler.master.data);
  });

  it('getData should return state.windowHandler.modal.data', () => {
    const state = createState({
      windowHandler: {
        modal: {
          data: masterWindowData.data1[0].fieldsByName,
        },
      },
    });
    const masterData = getData(state, true);

    expect(masterData).toEqual(state.windowHandler.modal.data);
  });

  it('getMasterDocStatus should return values from state.windowHandler.master.data[DocStatus/DocAction]', () => {
    const data = masterWindowData.data1[0].fieldsByName;
    const state = createState({
      windowHandler: {
        master: {
          data,
        }, 
      },
    });

    const mockStatusData = [
      {
        status: data.DocStatus,
        action: data.DocAction,
        displayed: true,
      },
    ];
    const statusData = getMasterDocStatus(state);

    expect(statusData).toEqual(mockStatusData);
  });

  it('getElementWidgetData should return state.windowHandler.master.data[fieldName]', () => {
    const layout = masterWindowLayout.layout1;
    const state = createState({
      windowHandler: {
        master: {
          data: masterWindowData.data1[0].fieldsByName,
          layout,
        }, 
      },
    });
    const selectorPath = '0_0_0_2_0';
    const widgetData = getElementWidgetData(state, false, selectorPath);
    const fieldName = layout.sections[0].columns[0].elementGroups[0].elementsLine[2].elements[0].fields[0].field;
    const fieldData = masterWindowData.data1[0].fieldsByName[fieldName];

    expect(widgetData[0]).toEqual(fieldData);
  });

  it('getElementWidgetData should return state.windowHandler.modal.data[fieldName]', () => {
    const layout = masterWindowLayout.layout1;
    const state = createState({
      windowHandler: {
        modal: {
          data: masterWindowData.data1[0].fieldsByName,
          layout,
        }, 
      },
    });
    const selectorPath = '0_0_0_2_0';
    const widgetData = getElementWidgetData(state, true, selectorPath);
    const fieldName = layout.sections[0].columns[0].elementGroups[0].elementsLine[2].elements[0].fields[0].field;
    const fieldData = masterWindowData.data1[0].fieldsByName[fieldName];

    expect(widgetData[0]).toEqual(fieldData);
  });

  it('getProcessWidgetData should return state.windowHandler.modal.data[fieldName]', () => {
    const data = modalFixtures.process_data1;
    const layout = modalFixtures.process_layout1;
    const state = createState({
      windowHandler: {
        modal: {
          data: data.fieldsByName,
          layout,
        }, 
      },
    });

    const elementIndex = '0';
    const widgetData = getProcessWidgetData(state, true, elementIndex);
    const fieldName = layout.elements[elementIndex].fields[0].field;
    const fieldData = data.fieldsByName[fieldName];

    expect(widgetData[0]).toEqual(fieldData);
  });

  it('getElementWidgetFields should return state.master.layout[path].fields', () => {
    const layout = masterWindowLayout.layout1;
    const state = createState({
      windowHandler: {
        master: {
          data: masterWindowData.data1[0].fieldsByName,
          layout,
        },
      },
    });
    const selectorPath = `0_0_0_2_0`
    const fieldsData = getElementWidgetFields(state, false, selectorPath);
    const layoutFields = layout.sections[0].columns[0].elementGroups[0].elementsLine[2].elements[0].fields;

    expect(fieldsData).toEqual(layoutFields);
  });

  it('getElementWidgetFields should return state.modal.layout[path].fields', () => {
    const layout = masterWindowLayout.layout1;
    const state = createState({
      windowHandler: {
        modal: {
          data: masterWindowData.data1[0].fieldsByName,
          layout,
        },
      },
    });
    const selectorPath = `0_0_0_2_0`
    const fieldsData = getElementWidgetFields(state, true, selectorPath);
    const layoutFields = layout.sections[0].columns[0].elementGroups[0].elementsLine[2].elements[0].fields;

    expect(fieldsData).toEqual(layoutFields);
  });

  it('getProcessWidgetFields should return state.modal.layout[path].fields', () => {
    const data = modalFixtures.process_data1;
    const layout = modalFixtures.process_layout1;
    const state = createState({
      windowHandler: {
        modal: {
          data: data.fieldsByName,
          layout,
        }, 
      },
    });

    const elementIndex = '0';
    const fieldsData = getProcessWidgetFields(state, true, elementIndex);
    const layoutFields = layout.elements[elementIndex].fields;

    expect(fieldsData).toEqual(layoutFields);    
  });
});

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
