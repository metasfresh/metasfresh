import rawstateJson from '../fixtures/rawstate.json';
import { produce } from 'immer';
import { generateAlternativeSteps } from '../../reducers/wfProcesses_status/picking';

describe('picking unit tests', () => {
  describe('generate alternative steps function', () => {
    it('should generate no steps when called with zero quantity to allocate', () => {
      const initialState = produce(rawstateJson, (draftState) => {
        const draftStateWFProcesses = draftState['wfProcesses_status'];

        draftState = generateAlternativeSteps({
          draftState: draftStateWFProcesses,
          wfProcessId: 'picking-1000001',
          activityId: 'A2',
          lineId: '0',
          stepId: '1000001',
          qtyToAllocate: 0,
        });

        return draftState;
      });
      console.log('initialState:', initialState);
    });
  });
});
