import rawstateJson from '../fixtures/rawstate.json';
import { produce } from 'immer';
import { generateAlternativeSteps } from '../../reducers/wfProcesses_status/picking';

describe('picking unit tests', () => {
  describe('generate alternative steps function', () => {
    it('should generate no steps when called with zero quantity to allocate', () => {
      const wfProcessId = 'picking-1000001';
      const activityId = 'A2';
      const lineId = 0;
      const stepId = '1000001';

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

      const { genSteps } =
        initialState[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId].altSteps;
      expect(genSteps).toMatchObject({});
    });

    it('should generate no steps when called with quantity to allocate greater than zero', () => {
      const wfProcessId = 'picking-1000001';
      const activityId = 'A2';
      const lineId = 0;
      const stepId = '1000001';

      const initialState = produce(rawstateJson, (draftState) => {
        generateAlternativeSteps({
          draftState: draftState['wfProcesses_status'],
          wfProcessId,
          activityId,
          lineId,
          stepId,
          qtyToAllocate: 30,
        });

        return draftState;
      });

      const { pickFromAlternatives } = initialState['wfProcesses_status'][wfProcessId].activities[activityId].dataStored;
      const { genSteps } =
        initialState['wfProcesses_status'][wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId]
          .altSteps;

      // check the allocatedQtys object within the pickFromAlternatives item (poolItem)
      expect(pickFromAlternatives[0].allocatedQtys[stepId]).toEqual(30);
  
      // check the quantity available within the corresponding generated step
      expect(genSteps['1000019'].qtyAvailable).toEqual(30);
    });
  });
});
