import { mergeActivitiesToState } from '../../../reducers/wfProcesses_status/utils';

describe('reducers: utils tests', () => {
  describe('mergeActivitiesToState', () => {
    it('complex case with missing activities and new activities', () => {
      let targetWFProcess;

      mergeActivitiesToState({
        targetWFProcess: (targetWFProcess = {
          activities: {
            A1: { activityId: 'A1', caption: 'Activity 1' },
            A2: { activityId: 'A2', caption: 'Activity 2' },
            A3: { activityId: 'A3', caption: 'Activity 3' },
            A4: { activityId: 'A4', caption: 'Activity 4' },
          },
        }),
        fromActivities: [
          { activityId: 'A5', caption: 'Activity 5 updated' },
          { activityId: 'A4', caption: 'Activity 4 updated' },
          { activityId: 'A3', caption: 'Activity 3 updated' },
          { activityId: 'A2', caption: 'Activity 2 updated' },
        ],
      });

      expect(targetWFProcess).toMatchInlineSnapshot(`
                Object {
                  "activities": Object {
                    "A2": Object {
                      "activityId": "A2",
                      "caption": "Activity 2 updated",
                      "componentProps": undefined,
                      "componentType": undefined,
                      "dataStored": Object {
                        "completeStatus": "NOT_STARTED",
                        "isUserEditable": false,
                      },
                    },
                    "A3": Object {
                      "activityId": "A3",
                      "caption": "Activity 3 updated",
                      "componentProps": undefined,
                      "componentType": undefined,
                      "dataStored": Object {
                        "completeStatus": "NOT_STARTED",
                        "isUserEditable": false,
                      },
                    },
                    "A4": Object {
                      "activityId": "A4",
                      "caption": "Activity 4 updated",
                      "componentProps": undefined,
                      "componentType": undefined,
                      "dataStored": Object {
                        "completeStatus": "NOT_STARTED",
                        "isUserEditable": false,
                      },
                    },
                    "A5": Object {
                      "activityId": "A5",
                      "caption": "Activity 5 updated",
                      "componentProps": undefined,
                      "componentType": undefined,
                      "dataStored": Object {
                        "completeStatus": "NOT_STARTED",
                        "isUserEditable": false,
                      },
                    },
                  },
                  "activityIdsInOrder": Array [
                    "A5",
                    "A4",
                    "A3",
                    "A2",
                  ],
                }
            `);
    });
  });
});
