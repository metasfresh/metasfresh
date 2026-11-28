import reducer, { getActivityById } from '../../../../reducers/wfProcesses';
import { getCUEntry } from '../../../../reducers/wfProcesses/picking';
import {
  forgetCUEntry,
  forgetPendingScan,
  rememberCUEntry,
  rememberPendingScan,
} from '../../../../apps/picking/redux/PickingActions';
import { updateWFProcess } from '../../../../actions/WorkflowActions';

const wfProcessId = 'wf1';
const activityId = 'act1';

const state0 = () => ({
  [wfProcessId]: {
    id: wfProcessId,
    activities: { [activityId]: { activityId, componentType: 'picking/pickProducts', dataStored: { lines: {} } } },
  },
});

const activityOf = (state) => getActivityById({ wfProcesses: state }, wfProcessId, activityId);

describe('picking reducer: count remembered per line', () => {
  it('remembers the last typed count of the line (no label)', () => {
    const state = reducer(state0(), rememberCUEntry({ wfProcessId, activityId, lineId: 'L1', lastTypedQty: 5 }));
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L1' })).toEqual({
      lineId: 'L1',
      lastTypedQty: 5,
    });
  });

  it('forgetting (a new round on the line: "HU verpacken") removes the count', () => {
    let state = reducer(state0(), rememberCUEntry({ wfProcessId, activityId, lineId: 'L1', lastTypedQty: 5 }));
    state = reducer(state, forgetCUEntry({ wfProcessId, activityId }));
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L1' })).toBeNull();
  });

  it('forgetting with nothing remembered, or for an unknown process, changes nothing', () => {
    expect(reducer(state0(), forgetCUEntry({ wfProcessId, activityId }))).toEqual(state0());
    expect(reducer(state0(), forgetCUEntry({ wfProcessId: 'other', activityId }))).toEqual(state0());
  });

  it('another line (also one of the same product) sees nothing', () => {
    const state = reducer(state0(), rememberCUEntry({ wfProcessId, activityId, lineId: 'L1', lastTypedQty: 5 }));
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L2' })).toBeNull();
  });

  it('remembering on another line replaces the memory of the first one', () => {
    let state = reducer(state0(), rememberCUEntry({ wfProcessId, activityId, lineId: 'L1', lastTypedQty: 5 }));
    state = reducer(state, rememberCUEntry({ wfProcessId, activityId, lineId: 'L2', lastTypedQty: 3 }));
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
      rememberCUEntry({ wfProcessId: 'other', activityId, lineId: 'L1', lastTypedQty: 5 })
    );
    expect(state).toEqual(state0());
  });

  it('survives the update of the process from the server', () => {
    let state = reducer(state0(), rememberCUEntry({ wfProcessId, activityId, lineId: 'L1', lastTypedQty: 5 }));
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

describe('picking reducer: scan kept while the picker chooses the TU type ("Weiteres TU" on a line without TU type)', () => {
  const pending = { wfProcessId, activityId, lineId: 'L1', scannedBarcode: 'LABEL#1', qtyEntered: 4 };

  it('keeps the scanned label and the entered count of the line, and the last typed count next to them', () => {
    let state = reducer(state0(), rememberCUEntry({ wfProcessId, activityId, lineId: 'L1', lastTypedQty: 5 }));
    state = reducer(state, rememberPendingScan(pending));
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L1' })).toEqual({
      lineId: 'L1',
      lastTypedQty: 5,
      pendingScan: { scannedBarcode: 'LABEL#1', qtyEntered: 4 },
    });
  });

  it('works without any earlier memory', () => {
    const state = reducer(state0(), rememberPendingScan(pending));
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L1' }).pendingScan).toEqual({
      scannedBarcode: 'LABEL#1',
      qtyEntered: 4,
    });
  });

  it('another line sees nothing', () => {
    const state = reducer(state0(), rememberPendingScan(pending));
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L2' })).toBeNull();
  });

  it('forgetting it keeps the last typed count', () => {
    let state = reducer(state0(), rememberCUEntry({ wfProcessId, activityId, lineId: 'L1', lastTypedQty: 5 }));
    state = reducer(state, rememberPendingScan(pending));
    state = reducer(state, forgetPendingScan({ wfProcessId, activityId }));
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L1' })).toEqual({ lineId: 'L1', lastTypedQty: 5 });
  });

  it('a booking (remembering the count) drops the kept scan', () => {
    let state = reducer(state0(), rememberPendingScan(pending));
    state = reducer(state, rememberCUEntry({ wfProcessId, activityId, lineId: 'L1', lastTypedQty: 4 }));
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L1' }).pendingScan).toBeUndefined();
  });

  it('a new round ("HU verpacken") drops the kept scan', () => {
    let state = reducer(state0(), rememberPendingScan(pending));
    state = reducer(state, forgetCUEntry({ wfProcessId, activityId }));
    expect(getCUEntry({ activity: activityOf(state), lineId: 'L1' })).toBeNull();
  });

  it('ignores an unknown process, and forgetting with nothing kept changes nothing', () => {
    expect(reducer(state0(), rememberPendingScan({ ...pending, wfProcessId: 'other' }))).toEqual(state0());
    expect(reducer(state0(), forgetPendingScan({ wfProcessId, activityId }))).toEqual(state0());
  });
});
