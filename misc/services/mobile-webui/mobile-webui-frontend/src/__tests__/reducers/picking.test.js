import rawstateJson from '../fixtures/rawstate.json';
import { produce } from 'immer';
import { generateAlternativeSteps } from '../../reducers/wfProcesses_status/picking';

describe('picking unit tests', () => {
  describe('generate alternative steps function', () => {

    const wfProcessId = 'picking-1000001';
    const activityId = 'A2';
    const lineId = 0;
    const stepId = '1000001';

    it('should generate no steps when called with zero quantity to allocate', () => {
      const initialState = produce(rawstateJson, (draftState) => {
        const draftStateWFProcesses = draftState['wfProcesses_status'];

        draftState = generateAlternativeSteps({
          draftState: draftStateWFProcesses,
          wfProcessId,
          activityId,
          lineId,
          stepId,
          qtyToAllocate: 0,
        });

        return draftState;
      });

      const { genSteps } = initialState[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId].altSteps;
      expect(genSteps).toMatchObject({});
    });
  });
});
