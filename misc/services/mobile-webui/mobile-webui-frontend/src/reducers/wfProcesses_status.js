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

export default function wfProcesses_status(state = initialState, action) {
  // const { payload } = action;
  switch (action.type) {
    // case types.SET_WFPROCESSES:
    //   return {
    //     ...state,
    //     ...payload.WFPROCESESS,
    //   };

    default:
      return state;
  }
}
