import rawstateJson from '../fixtures/rawstate.json';
import { produce } from 'immer';
// import generateAlternativeSteps from '../../reducers/wfProcesses_status/picking';

describe('picking unit tests', () => {
  describe('generate alternative steps function', () => {
    it('should generate no steps when ...', () => {
      const initialState = produce(rawstateJson, (draftState) => {
        // draftState = generateAlternativeSteps({
        //     draftState,
        //     wfProcessId: 'picking-1000001',
        //     activityId: 'A2',
        //     lineId: '0',
        //     stepId: '1000001',
        //     qtyToAllocate: 20,
        // });
        return draftState;
      });
      console.log('initialState:', initialState);
    });
  });
});
