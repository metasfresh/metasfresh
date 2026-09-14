import { merge } from 'merge-anything';

import masterWindowData from '../../../test_setup/fixtures/master_window/data.json';
import masterWindowLayout from '../../../test_setup/fixtures/master_window/layout.json';
import modalFixtures from '../../../test_setup/fixtures/window/modal.json';
import { initDataSuccess } from '../../actions/WindowActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import reducer, {
  initialState,
  getData,
  getElementWidgetData,
  getElementWidgetFields,
  getMasterDocStatus,
  getProcessWidgetData,
  getProcessWidgetFields,
  isRelevantSaveError,
  computeSaveStatusFlags,
} from '../../reducers/windowHandler';
import * as IndicatorState from '../../constants/IndicatorState';

const createState = function (state = {}) {
  return merge(
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
    const fieldName =
      layout.sections[0].columns[0].elementGroups[0].elementsLine[2].elements[0]
        .fields[0].field;
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
    const fieldName =
      layout.sections[0].columns[0].elementGroups[0].elementsLine[2].elements[0]
        .fields[0].field;
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
    const selectorPath = `0_0_0_2_0`;
    const fieldsData = getElementWidgetFields(state, false, selectorPath);
    const layoutFields =
      layout.sections[0].columns[0].elementGroups[0].elementsLine[2].elements[0]
        .fields;

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
    const selectorPath = `0_0_0_2_0`;
    const fieldsData = getElementWidgetFields(state, true, selectorPath);
    const layoutFields =
      layout.sections[0].columns[0].elementGroups[0].elementsLine[2].elements[0]
        .fields;

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

describe('isRelevantSaveError', () => {
  // A server-side business rejection of a complete, individually-valid document
  // (e.g. a unique-index collision) carries a real save exception flagged
  // userFriendlyError -> the reason must be surfaced.
  it('is true for a userFriendly save exception (business rejection)', () => {
    const saveStatus = {
      saved: false,
      error: true,
      reason: 'The date is already used in another version of this price list.',
      exception: { message: 'duplicate date', userFriendlyError: true },
    };
    expect(isRelevantSaveError(saveStatus)).toBe(true);
  });

  // A mandatory-missing / incomplete new record ALSO sets error=true, but with NO
  // exception (a pure validation state) -> must stay quiet (field cues already signal it).
  it('is false for a mandatory-missing state (error but no exception)', () => {
    const saveStatus = {
      saved: false,
      error: true,
      reason: 'Fill mandatory fields:  Price List Version',
    };
    expect(isRelevantSaveError(saveStatus)).toBe(false);
  });

  it('is false when an exception is present but not userFriendly', () => {
    const saveStatus = {
      error: true,
      exception: { message: 'NPE somewhere', userFriendlyError: false },
    };
    expect(isRelevantSaveError(saveStatus)).toBe(false);
  });

  it('is false when there is no error', () => {
    expect(isRelevantSaveError({ error: false, exception: { userFriendlyError: true } })).toBe(false);
    expect(isRelevantSaveError({ saved: true })).toBe(false);
  });

  it('is null-safe', () => {
    expect(isRelevantSaveError(undefined)).toBe(false);
    expect(isRelevantSaveError(null)).toBe(false);
    expect(isRelevantSaveError({})).toBe(false);
    expect(isRelevantSaveError({ error: true })).toBe(false);
  });
});

describe('computeSaveStatusFlags — relevant save error surfacing', () => {
  // A complete, individually-valid document rejected by a server-side business rule /
  // unique-index collision: error=true + userFriendly exception. For a NOT-yet-persisted
  // (new) record the server reports presentInDatabase=false, so the legacy ERROR gate
  // (isDocumentNotSaved && presentInDatabase) stays quiet and the reason is swallowed.
  const relevantNewRecordError = {
    saved: false,
    error: true,
    presentInDatabase: false,
    reason: 'The date is already used in another version of this price list.',
    exception: { userFriendlyError: true },
  };

  // Mandatory-missing / incomplete new record: error=true, presentInDatabase=false, but NO
  // exception (a pure validation state). Must stay quiet — field-level cues already signal it.
  const mandatoryMissingNewRecord = {
    saved: false,
    error: true,
    presentInDatabase: false,
    reason: 'Fill mandatory fields:  Price List Version',
  };

  it('surfaces (ERROR) a relevant save error on a NEW main-window (master) record', () => {
    const { indicator } = computeSaveStatusFlags({
      master: {
        saveStatus: relevantNewRecordError,
        indicator: IndicatorState.SAVED,
        layout: { windowId: '143' },
        docId: 'NEW',
      },
    });
    expect(indicator).toBe(IndicatorState.ERROR);
  });

  it('does NOT surface (keeps base) a mandatory-missing state on a NEW main-window record', () => {
    const { indicator } = computeSaveStatusFlags({
      master: {
        saveStatus: mandatoryMissingNewRecord,
        indicator: IndicatorState.SAVED,
        layout: { windowId: '143' },
        docId: 'NEW',
      },
    });
    expect(indicator).toBe(IndicatorState.SAVED);
  });

  it('surfaces (ERROR) a relevant save error on a NEW window modal via the shared core', () => {
    const { indicator } = computeSaveStatusFlags({
      modal: {
        visible: true,
        modalType: 'window',
        windowId: '143',
        docId: 'NEW',
        saveStatus: relevantNewRecordError,
        indicator: IndicatorState.SAVED,
      },
    });
    expect(indicator).toBe(IndicatorState.ERROR);
  });

  it('does NOT surface (keeps base) a mandatory-missing state on a NEW window modal', () => {
    const { indicator } = computeSaveStatusFlags({
      modal: {
        visible: true,
        modalType: 'window',
        windowId: '143',
        docId: 'NEW',
        saveStatus: mandatoryMissingNewRecord,
        indicator: IndicatorState.SAVED,
      },
    });
    expect(indicator).toBe(IndicatorState.SAVED);
  });

  it('does NOT promote a process modal to ERROR on a relevant save error (Start button stays enabled)', () => {
    const { indicator } = computeSaveStatusFlags({
      modal: {
        visible: true,
        modalType: 'process',
        saveStatus: relevantNewRecordError,
        indicator: IndicatorState.PENDING,
      },
    });
    expect(indicator).toBe(IndicatorState.PENDING);
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
      tabs: [{ windowId: '1' }],
      windowId: '1',
    };

    expect(
      reducer(undefined, {
        type: ACTION_TYPES.INIT_LAYOUT_SUCCESS,
        scope: 'master',
        layout,
      })
    ).toEqual(
      expect.objectContaining({
        master: expect.objectContaining({ layout: { ...layout } }),
      })
    );
  });

  it('Should handle INIT_DATA_SUCCESS', () => {
    const data = {
      data: {
        ID: {
          field: 'ID',
          value: 1000000,
          widgetType: 'Integer',
        },
      },
      docId: '1000',
      saveStatus: {},
      scope: 'master',
      standardActions: [],
      validStatus: {},
      includedTabsInfo: {},
      websocketEndpoint: 'test/url',
    };

    const actions = [{}, initDataSuccess({ ...data })];
    const state = actions.reduce(reducer, undefined);

    expect(state).toEqual(
      expect.objectContaining({
        master: expect.objectContaining({ data: { ...data.data } }),
      })
    );
  });

  //@TODO: Add tests for FETCH_TOP_ACTIONS
});
