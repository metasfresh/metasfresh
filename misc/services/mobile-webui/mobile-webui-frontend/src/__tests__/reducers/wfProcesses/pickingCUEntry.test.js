import reducer, { getActivityById } from '../../../reducers/wfProcesses';
import { getCUEntry } from '../../../reducers/wfProcesses/picking';
import { rememberCUEntry } from '../../../actions/PickingActions';
import { updateWFProcess } from '../../../actions/WorkflowActions';

const wfProcessId = 'wf1';
const activityId = 'act1';

const state0 = () => ({
  [wfProcessId]: {
    id: wfProcessId,
    activities: { [activityId]: { activityId, componentType: 'picking/pickProducts', dataStored: { lines: {} } } },
  },
});

const activityOf = (state) => getActivityById({ wfProcesses: state }, wfProcessId, activityId);
const label = { cuLabel: 'RAW-1', lotNo: 'LOTA', bestBeforeDate: '2026-11-07' };

describe('picking reducer: count and label remembered per line', () => {
  it('remembers the last typed count and the label of the line', () => {
    const state = reducer(state0(), rememberCUEntry({ wfProcessId, activityId, lineId: 'L1', lastTypedQty: 5, label }));
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L1' })).toEqual({
      lineId: 'L1',
      lastTypedQty: 5,
      label,
    });
  });

  it('another line (also one of the same product) sees nothing', () => {
    const state = reducer(state0(), rememberCUEntry({ wfProcessId, activityId, lineId: 'L1', lastTypedQty: 5, label }));
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L2' })).toBeNull();
  });

  it('remembering on another line replaces the memory of the first one', () => {
    let state = reducer(state0(), rememberCUEntry({ wfProcessId, activityId, lineId: 'L1', lastTypedQty: 5, label }));
    state = reducer(state, rememberCUEntry({ wfProcessId, activityId, lineId: 'L2', lastTypedQty: 3, label }));
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L1' })).toBeNull();
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L2' }).lastTypedQty).toBe(3);
  });

  it('is null without any memory and for a missing activity', () => {
    expect(getCUEntry({ activity: activityOf(state0()), lineId: 'L1' })).toBeNull();
    expect(getCUEntry({ activity: undefined, lineId: 'L1' })).toBeNull();
  });

  it('ignores an action for an unknown process', () => {
    const state = reducer(
      state0(),
      rememberCUEntry({ wfProcessId: 'other', activityId, lineId: 'L1', lastTypedQty: 5, label })
    );
    expect(state).toEqual(state0());
  });

  it('survives the update of the process from the server', () => {
    let state = reducer(state0(), rememberCUEntry({ wfProcessId, activityId, lineId: 'L1', lastTypedQty: 5, label }));
    state = reducer(
      state,
      updateWFProcess({
        wfProcess: {
          id: wfProcessId,
          activities: [
            {
              activityId,
              componentType: 'picking/pickProducts',
              componentProps: {
                customQRCodeFormats: [],
                pickingJob: { lines: [], pickFromAlternatives: [], aggregationType: 'SALES_ORDER' },
              },
            },
          ],
        },
      })
    );
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L1' })?.lastTypedQty).toBe(5);
  });
});
