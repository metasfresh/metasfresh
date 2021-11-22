import rawstateJson from '../fixtures/rawstate.json';
import draftActivityDataStored from '../fixtures/picking/activityDataStored.json';
import fromActivity from '../fixtures/picking/fromActivity.json';

import { produce } from 'immer';
import {
  allocatePickingAlternatives,
  mergeActivityDataStoredAndAllocateAlternatives,
} from '../../reducers/wfProcesses_status/picking';

const wfProcessId = 'picking-1000001';
const activityId = 'A2';
const lineId = 0;
const stepId = '1000001';

describe('picking unit tests', () => {
  describe('generate alternative steps function', () => {
    xit('should generate no steps when called with zero quantity to allocate', () => {
      const initialState = produce(rawstateJson, (draftState) => {
        const draftStateWFProcesses = draftState['wfProcesses_status'];
        const dataStored = draftStateWFProcesses[wfProcessId].activities[activityId].dataStored;

        allocatePickingAlternatives({
          draftDataStored: dataStored,
          wfProcessId,
          activityId,
          lineId,
          stepId,
          qtyToAllocate: 0,
        });

        return draftState;
      });

      const { genSteps } =
        initialState['wfProcesses_status'][wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId]
          .altSteps;
      expect(genSteps).toMatchObject({});
    });

    xit('should generate one step when called with quantity to allocate smaller than the qty available in the first available step from the pool', () => {
      const initialState = produce(rawstateJson, (draftState) => {
        const draftStateWFProcesses = draftState['wfProcesses_status'];
        const dataStored = draftStateWFProcesses[wfProcessId].activities[activityId].dataStored;

        allocatePickingAlternatives({
          draftDataStored: dataStored,
          wfProcessId,
          activityId,
          lineId,
          stepId,
          qtyToAllocate: 30,
        });

        return draftState;
      });

      const { pickFromAlternatives } =
        initialState['wfProcesses_status'][wfProcessId].activities[activityId].dataStored;
      const { genSteps } =
        initialState['wfProcesses_status'][wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId]
          .altSteps;

      // check the allocatedQtys object within the pickFromAlternatives item (poolItem)
      expect(pickFromAlternatives[0].allocatedQtys[stepId]).toEqual(30);

      // check the quantity available within the corresponding generated step
      expect(genSteps['1000019'].qtyAvailable).toEqual(30);
    });

    xit('should generate three steps when called with higher quantity to allocate (i.e. 500)', () => {
      const initialState = produce(rawstateJson, (draftState) => {
        const draftStateWFProcesses = draftState['wfProcesses_status'];
        const dataStored = draftStateWFProcesses[wfProcessId].activities[activityId].dataStored;

        allocatePickingAlternatives({
          draftDataStored: dataStored,
          wfProcessId,
          activityId,
          lineId,
          stepId,
          qtyToAllocate: 500,
        });

        return draftState;
      });

      const { pickFromAlternatives } =
        initialState['wfProcesses_status'][wfProcessId].activities[activityId].dataStored;
      const { genSteps } =
        initialState['wfProcesses_status'][wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId]
          .altSteps;

      // check the allocatedQtys objects within the pickFromAlternatives items
      expect(pickFromAlternatives[0].allocatedQtys[stepId]).toEqual(320);
      expect(pickFromAlternatives[1].allocatedQtys[stepId]).toEqual(20);
      expect(pickFromAlternatives[2].allocatedQtys[stepId]).toEqual(160);

      // check the quantity available within the corresponding generated steps
      expect(genSteps['1000019'].qtyAvailable).toEqual(320);
      expect(genSteps['1000020'].qtyAvailable).toEqual(20);
      expect(genSteps['1000021'].qtyAvailable).toEqual(160);
    });

    xit('should generate correct steps when receiving data fromActivity', () => {
      const resultedData = mergeActivityDataStoredAndAllocateAlternatives({ draftActivityDataStored, fromActivity });
      // console.log('Resulted data:', resultedData);

      const dataStored = resultedData.dataStored;
      const { lines } = dataStored;
      const { steps } = lines[0];

      const targetStep = steps['1000000'];

      expect(targetStep.qtyPicked).toEqual(8);
      expect(targetStep.mainPickFrom.qtyRejected).toEqual(40);

      const { genSteps } = targetStep.altSteps;
      // console.log('targetStep:', targetStep.altSteps.genSteps);

      // The generated steps for the ones flagged by the BE with already picked qtys should be present
      expect(genSteps['1000000'].qtyAvailable).toEqual(10);
      expect(genSteps['1000000'].qtyPicked).toEqual(8);

      // remaining dyn generated altsteps
      expect(genSteps['1000001'].qtyAvailable).toEqual(4);
      expect(genSteps['1000001'].qtyPicked).toEqual(0);

      expect(genSteps['1000002'].qtyAvailable).toEqual(12);
      expect(genSteps['1000002'].qtyPicked).toEqual(0);

      expect(genSteps['1000003'].qtyAvailable).toEqual(5);
      expect(genSteps['1000003'].qtyPicked).toEqual(0);

      expect(genSteps['1000004'].qtyAvailable).toEqual(5);
      expect(genSteps['1000004'].qtyPicked).toEqual(0);

      expect(genSteps['1000005'].qtyAvailable).toEqual(4);
      expect(genSteps['1000005'].qtyPicked).toEqual(0);

      // expect to have the alternative steps for which we already picked present

      // const { genSteps } =
      //   initialState['wfProcesses_status'][wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId]
      //     .altSteps;
      // expect(genSteps).toMatchObject({});
    });
  });
});
