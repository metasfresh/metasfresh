import reducer, { getActivityById } from '../../../reducers/wfProcesses';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { isQuickPackAllAllowed } from '../../../utils/picking';

const wfProcessId = 'wf1';
const activityId = 'act1';

const stateWithJob = (pickingJobProps) =>
  reducer(
    {},
    updateWFProcess({
      wfProcess: {
        id: wfProcessId,
        activities: [
          {
            activityId,
            componentType: 'picking/pickProducts',
            componentProps: {
              customQRCodeFormats: [],
              pickingJob: { lines: [], pickFromAlternatives: [], aggregationType: 'SALES_ORDER', ...pickingJobProps },
            },
          },
        ],
      },
    })
  );

const activityOf = (state) => getActivityById({ wfProcesses: state }, wfProcessId, activityId);

describe('isQuickPackAllAllowed: the backend decides, the client reads', () => {
  it('is allowed when the job says so', () => {
    expect(isQuickPackAllAllowed({ activity: activityOf(stateWithJob({ quickPackAllAllowed: true })) })).toBe(true);
  });
  it('is refused when the job says so (profile switch off, or a line requires a label)', () => {
    expect(isQuickPackAllAllowed({ activity: activityOf(stateWithJob({ quickPackAllAllowed: false })) })).toBe(false);
  });
  it('is refused when the field is absent or the activity is missing', () => {
    expect(isQuickPackAllAllowed({ activity: activityOf(stateWithJob({})) })).toBe(false);
    expect(isQuickPackAllAllowed({ activity: undefined })).toBe(false);
  });
});
