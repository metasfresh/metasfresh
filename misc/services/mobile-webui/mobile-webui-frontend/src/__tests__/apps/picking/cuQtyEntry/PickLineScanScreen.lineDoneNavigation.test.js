import { POLICY } from '../../../../__testSupport__/picking/testPolicies';
import React from 'react';
import { render } from '@testing-library/react';
import { useOnClose, NEXT_NextPickingLine } from '../../../../containers/activities/picking/PickLineScanScreen';
import { pickingLineScanScreenLocation } from '../../../../routes/picking';

const mockHistory = { replace: jest.fn(), goBack: jest.fn() };
let mockGotoPickingJobOnClose = false;

jest.mock('../../../../utils/ui_trace', () => ({ trace: jest.fn() }));
jest.mock('../../../../hooks/useMobileNavigation', () => ({ useMobileNavigation: () => mockHistory }));
jest.mock('../../../../reducers/settings', () => ({
  ...jest.requireActual('../../../../reducers/settings'),
  useBooleanSetting: () => mockGotoPickingJobOnClose,
}));

const renderOnClose = (params) => {
  let onClose;
  const Harness = () => {
    onClose = useOnClose({ applicationId: 'picking', wfProcessId: 'wf', activity: {}, lineId: 'L1', ...params });
    return null;
  };
  render(<Harness />);
  return onClose;
};

describe('PickLineScanScreen useOnClose: where a closed line leaves to (decided by the setting gotoPickingJobOnClose and the next param only)', () => {
  beforeEach(() => {
    mockHistory.replace.mockClear();
    mockHistory.goBack.mockClear();
    mockGotoPickingJobOnClose = false; // the shipped sysconfig value (N)
  });

  it('setting N: closing goes back to the previous screen', () => {
    renderOnClose({})();
    expect(mockHistory.goBack).toHaveBeenCalledTimes(1);
    expect(mockHistory.replace).not.toHaveBeenCalled();
  });

  it('setting Y: closing goes to the picking job screen', () => {
    mockGotoPickingJobOnClose = true;
    renderOnClose({})();
    expect(mockHistory.goBack).not.toHaveBeenCalled();
    expect(mockHistory.replace).toHaveBeenCalledTimes(1);
  });

  it('next=NEXT_NextPickingLine: closing goes to the next eligible line (setting N)', () => {
    const activity = {
      activityId: 'A1',
      dataStored: {
        lines: {
          L1: { pickingLineId: 'L1', allowPickingAnyHU: true, completeStatus: 'NOT_STARTED' },
          L2: { pickingLineId: 'L2', allowPickingAnyHU: true, completeStatus: 'NOT_STARTED' },
        },
      },
    };
    renderOnClose({ activity, next: NEXT_NextPickingLine })();
    expect(mockHistory.goBack).not.toHaveBeenCalled();
    expect(mockHistory.replace).toHaveBeenCalledWith(
      pickingLineScanScreenLocation({
        applicationId: 'picking',
        wfProcessId: 'wf',
        activityId: 'A1',
        lineId: 'L2',
        next: NEXT_NextPickingLine,
      })
    );
  });

  it('a scan-each line done: the sysconfig decides (N = back to the line screen)', () => {
    renderOnClose({ cuQtyEntryPolicy: POLICY.SCAN_EACH_CU, isLineDone: true })();
    expect(mockHistory.goBack).toHaveBeenCalledTimes(1);
    expect(mockHistory.replace).not.toHaveBeenCalled();
  });

  it('a manual line: the sysconfig decides (N = back to the line screen)', () => {
    renderOnClose({ cuQtyEntryPolicy: POLICY.MANUAL, isLineDone: true })();
    expect(mockHistory.goBack).toHaveBeenCalledTimes(1);
    expect(mockHistory.replace).not.toHaveBeenCalled();
  });

  it('closing early (not done) on a scan-once line: the sysconfig decides', () => {
    renderOnClose({ cuQtyEntryPolicy: POLICY.SCAN_ONCE_ENTER_QTY })();
    expect(mockHistory.goBack).toHaveBeenCalledTimes(1);
  });
});
