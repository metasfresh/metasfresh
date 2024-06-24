import * as CompleteStatus from '../../../constants/CompleteStatus';
import { mergeActivitiesToState, updateUserEditable } from '../../../reducers/wfProcesses/utils';

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
                      "componentProps": Object {},
                      "componentType": undefined,
                      "dataStored": Object {
                        "completeStatus": "NOT_STARTED",
                        "isUserEditable": false,
                      },
                      "userInstructions": undefined,
                    },
                    "A3": Object {
                      "activityId": "A3",
                      "caption": "Activity 3 updated",
                      "componentProps": Object {},
                      "componentType": undefined,
                      "dataStored": Object {
                        "completeStatus": "NOT_STARTED",
                        "isUserEditable": false,
                      },
                      "userInstructions": undefined,
                    },
                    "A4": Object {
                      "activityId": "A4",
                      "caption": "Activity 4 updated",
                      "componentProps": Object {},
                      "componentType": undefined,
                      "dataStored": Object {
                        "completeStatus": "NOT_STARTED",
                        "isUserEditable": false,
                      },
                      "userInstructions": undefined,
                    },
                    "A5": Object {
                      "activityId": "A5",
                      "caption": "Activity 5 updated",
                      "componentProps": Object {},
                      "componentType": undefined,
                      "dataStored": Object {
                        "completeStatus": "NOT_STARTED",
                        "isUserEditable": false,
                      },
                      "userInstructions": undefined,
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

  describe('updateUserEditable', () => {
    it('no activities started', () => {
      const draftWFProcess = {
        activityIdsInOrder: ['A1', 'A2', 'A3'],
        activities: {
          A1: { dataStored: { completeStatus: CompleteStatus.NOT_STARTED } },
          A2: { dataStored: { completeStatus: CompleteStatus.NOT_STARTED } },
          A3: { dataStored: { completeStatus: CompleteStatus.NOT_STARTED } },
        },
      };
      updateUserEditable({ draftWFProcess });

      expect(draftWFProcess.activities).toMatchInlineSnapshot(`
                Object {
                  "A1": Object {
                    "dataStored": Object {
                      "completeStatus": "NOT_STARTED",
                      "isUserEditable": true,
                    },
                  },
                  "A2": Object {
                    "dataStored": Object {
                      "completeStatus": "NOT_STARTED",
                      "isUserEditable": false,
                    },
                  },
                  "A3": Object {
                    "dataStored": Object {
                      "completeStatus": "NOT_STARTED",
                      "isUserEditable": false,
                    },
                  },
                }
            `);
    });

    it('1st activity completed, 2nd one not started', () => {
      let draftWFProcess = {
        activityIdsInOrder: ['A1', 'A2'],
        activities: {
          A1: { dataStored: { completeStatus: CompleteStatus.COMPLETED } },
          A2: { dataStored: { completeStatus: CompleteStatus.NOT_STARTED } },
        },
      };
      updateUserEditable({ draftWFProcess });

      expect(draftWFProcess.activities).toMatchInlineSnapshot(`
                Object {
                  "A1": Object {
                    "dataStored": Object {
                      "completeStatus": "COMPLETED",
                      "isUserEditable": true,
                    },
                  },
                  "A2": Object {
                    "dataStored": Object {
                      "completeStatus": "NOT_STARTED",
                      "isUserEditable": true,
                    },
                  },
                }
            `);
    });

    it('1st activity completed, 2nd one in progress', () => {
      let draftWFProcess = {
        activityIdsInOrder: ['A1', 'A2'],
        activities: {
          A1: { dataStored: { completeStatus: CompleteStatus.COMPLETED } },
          A2: { dataStored: { completeStatus: CompleteStatus.IN_PROGRESS } },
        },
      };
      updateUserEditable({ draftWFProcess });

      expect(draftWFProcess.activities).toMatchInlineSnapshot(`
                Object {
                  "A1": Object {
                    "dataStored": Object {
                      "completeStatus": "COMPLETED",
                      "isUserEditable": false,
                    },
                  },
                  "A2": Object {
                    "dataStored": Object {
                      "completeStatus": "IN_PROGRESS",
                      "isUserEditable": true,
                    },
                  },
                }
            `);
    });

    it('1st activity not started, 2nd one in progress, 3rd not started', () => {
      let draftWFProcess = {
        activityIdsInOrder: ['A1', 'A2', 'A3'],
        activities: {
          A1: { dataStored: { completeStatus: CompleteStatus.NOT_STARTED } },
          A2: { dataStored: { completeStatus: CompleteStatus.IN_PROGRESS } },
          A3: { dataStored: { completeStatus: CompleteStatus.NOT_STARTED } },
        },
      };
      updateUserEditable({ draftWFProcess });

      expect(draftWFProcess.activities).toMatchInlineSnapshot(`
                Object {
                  "A1": Object {
                    "dataStored": Object {
                      "completeStatus": "NOT_STARTED",
                      "isUserEditable": true,
                    },
                  },
                  "A2": Object {
                    "dataStored": Object {
                      "completeStatus": "IN_PROGRESS",
                      "isUserEditable": true,
                    },
                  },
                  "A3": Object {
                    "dataStored": Object {
                      "completeStatus": "NOT_STARTED",
                      "isUserEditable": false,
                    },
                  },
                }
            `);
    });
  });
});
