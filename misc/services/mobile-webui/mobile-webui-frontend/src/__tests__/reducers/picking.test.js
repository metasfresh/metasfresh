import rawstateJson from '../fixtures/rawstate.json';
import draftActivityDataStored from '../fixtures/picking/activityDataStored.json';
import fromActivity from '../fixtures/picking/fromActivity.json';

import { produce } from 'immer';
import {
  generateAlternativeSteps,
  mergeActivityDataStoredAndGenerateAltSteps,
} from '../../reducers/wfProcesses_status/picking';

const wfProcessId = 'picking-1000001';
const activityId = 'A2';
const lineId = 0;
const stepId = '1000001';

describe('picking unit tests', () => {
  describe('generate alternative steps function', () => {
    it.skip('should generate no steps when called with zero quantity to allocate', () => {
      const initialState = produce(rawstateJson, (draftState) => {
        const draftStateWFProcesses = draftState['wfProcesses_status'];
        const dataStored = draftStateWFProcesses[wfProcessId].activities[activityId].dataStored;

        draftState['wfProcesses_status'][wfProcessId].activities[activityId].dataStored = generateAlternativeSteps({
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

    it.skip('should generate one step when called with quantity to allocate smaller than the qty available in the first available step from the pool', () => {
      const initialState = produce(rawstateJson, (draftState) => {
        const draftStateWFProcesses = draftState['wfProcesses_status'];
        const dataStored = draftStateWFProcesses[wfProcessId].activities[activityId].dataStored;

        draftState['wfProcesses_status'][wfProcessId].activities[activityId].dataStored = generateAlternativeSteps({
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

    it.skip('should generate three steps when called with higher quantity to allocate (i.e. 500)', () => {
      const initialState = produce(rawstateJson, (draftState) => {
        const draftStateWFProcesses = draftState['wfProcesses_status'];
        const dataStored = draftStateWFProcesses[wfProcessId].activities[activityId].dataStored;

        draftState['wfProcesses_status'][wfProcessId].activities[activityId].dataStored = generateAlternativeSteps({
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

    it('should generate correct steps when receiving data fromActivity', () => {
      const resultedData = mergeActivityDataStoredAndGenerateAltSteps({ draftActivityDataStored, fromActivity });
      // console.log('Resulted data:', resultedData);

      const dataStored = resultedData.dataStored;
      const { lines } = dataStored;
      const { steps } = lines[0];

      const targetStep = steps['1000040'];

      expect(targetStep.qtyPicked).toEqual(2);
      expect(targetStep.mainPickFrom.qtyRejected).toEqual(53);

      const { genSteps } = targetStep.altSteps;
      console.log('targetStep:', targetStep.altSteps.genSteps);

      // The generated steps for the ones flagged by the BE with already picked qtys should be present
      expect(genSteps['1000488'].qtyAvailable).toEqual(14);
      expect(genSteps['1000488'].qtyPicked).toEqual(4);

      expect(genSteps['1000489'].qtyAvailable).toEqual(5);
      expect(genSteps['1000489'].qtyPicked).toEqual(1);

      // expect to have the alternative steps for which we already picked present

      // const { genSteps } =
      //   initialState['wfProcesses_status'][wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId]
      //     .altSteps;
      // expect(genSteps).toMatchObject({});
    });
  });
});
