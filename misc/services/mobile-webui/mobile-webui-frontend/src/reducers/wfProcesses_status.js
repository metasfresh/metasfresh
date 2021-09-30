import { produce } from 'immer';
import * as types from '../constants/ActionTypes';

export const initialState = {
  'picking-7f42317d-0782-466c-a192-cb5ad7d3cce0': {
    isSentToBackend: false,
    activities: {
      1: {
        activityId: '1',
        isComplete: false,
        dataStored: {},
      },
      2: {
        activityId: '2',
        dataStored: {
          isComplete: false,
          isLinesListVisible: true,
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
      3: {
        activityId: '3',
        dataStored: {
          isComplete: false,
        },
      },
    },
  },
};

const reducer = produce((draftState, action) => {
  switch (action.type) {
    case types.ADD_WORKFLOW_STATUS: {
      const { id, headerProperties, activities } = action.payload;
      const formattedActivities = activities.reduce((acc, activity, idx) => {
        const tmpActivity = { activity };

        //tmp
        tmpActivity.dataStored = {
          isComplete: false,
          isLinesListVisible: true,
          lines: activity.componentProps.lines,
        };

        acc[idx + 1] = tmpActivity;

        return acc;
      }, {});

      draftState[id] = {
        headerProperties,
        isSentToBackend: false,
        activities: formattedActivities,
      };

      return draftState;
    }
    case types.SWITCHOFF_LINES_VISIBILITY: {
      const { wfProcessId, activityId } = action.payload;

      draftState[wfProcessId].activities[activityId].dataStored.isLinesListVisible = false;

      return draftState;
    }
    case types.UPDATE_PICKING_STEP_QTY: {
      const { wfProcessId, activityId, lineIndex, stepId, qty } = action.payload;

      draftState[wfProcessId].activities[activityId].dataStored.lines[lineIndex].steps[stepId].qtyPicked = qty;

      return draftState;
    }
    default:
      return draftState;
  }
}, initialState);

export default reducer;
