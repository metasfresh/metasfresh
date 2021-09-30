import * as types from '../constants/ActionTypes';
import { produce } from 'immer';

export const initialState = {
  'picking-7f42317d-0782-466c-a192-cb5ad7d3cce0': {
    id: 'picking-7f42317d-0782-466c-a192-cb5ad7d3cce0',
    headerProperties: {
      entries: [],
    },
    activities: [
      {
        activityId: '1',
        caption: 'Scan picking slot',
        componentType: 'common/scanBarcode',
        readonly: true,
        componentProps: {
          barcodeCaption: null,
        },
      },
      {
        activityId: '2',
        caption: 'Pick',
        componentType: 'picking/pickProducts',
        readonly: true,
        componentProps: {
          lines: [
            {
              caption: 'TestProduct',
              steps: [
                {
                  productName: 'TestProduct',
                  locatorName: 'Hauptlager',
                  huBarcode: '1000001',
                  uom: 'Stk',
                  qtyToPick: 7,
                  qtyPicked: 0,
                },
              ],
            },
          ],
        },
      },
      {
        activityId: '3',
        caption: 'Complete picking',
        componentType: 'common/confirmButton',
        readonly: true,
        componentProps: {
          question: 'Are you sure?',
        },
      },
    ],
  },
};

const reducer = produce((draftState, action) => {
  switch (action.type) {
    case types.START_WORKFLOW_PROCESS: {
      const { id, headerProperties, activities } = action.payload;

      draftState[id] = { headerProperties, activities };

      return draftState;
    }
    case types.CONTINUE_WORKFLOW_PROCESS: {
      const { id, headerProperties, activities } = action.payload;

      draftState[id] = { headerProperties, activities };

      return draftState;
    }

    default:
      return draftState;
  }
}, initialState);

export default reducer;
