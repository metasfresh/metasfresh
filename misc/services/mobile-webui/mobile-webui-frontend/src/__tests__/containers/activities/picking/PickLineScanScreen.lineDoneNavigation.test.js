import React from 'react';
import { render } from '@testing-library/react';
import { useOnClose } from '../../../../containers/activities/picking/PickLineScanScreen';

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

describe('PickLineScanScreen: where a finished line leaves to', () => {
  beforeEach(() => {
    mockHistory.replace.mockClear();
    mockHistory.goBack.mockClear();
    mockGotoPickingJobOnClose = false; // the shipped sysconfig value (N)
  });

  it('a scan-once line done via "Pack HU" (screen opened from the line screen) goes to the job screen', () => {
    renderOnClose({ cuQtyEntryMode: 'SCAN_ONCE_ENTER_QTY', isLineDone: true })();
    expect(mockHistory.goBack).not.toHaveBeenCalled();
    expect(mockHistory.replace).toHaveBeenCalledTimes(1);
  });

  it('a scan-each line done goes to the job screen too', () => {
    renderOnClose({ cuQtyEntryMode: 'SCAN_EACH_CU', isLineDone: true })();
    expect(mockHistory.goBack).not.toHaveBeenCalled();
    expect(mockHistory.replace).toHaveBeenCalledTimes(1);
  });

  it('a manual line keeps today: the sysconfig decides (N = back to the line screen)', () => {
    renderOnClose({ cuQtyEntryMode: 'MANUAL', isLineDone: true })();
    expect(mockHistory.goBack).toHaveBeenCalledTimes(1);
    expect(mockHistory.replace).not.toHaveBeenCalled();
  });

  it('closing early (not done) on a scan-once line keeps today: the sysconfig decides', () => {
    renderOnClose({ cuQtyEntryMode: 'SCAN_ONCE_ENTER_QTY' })();
    expect(mockHistory.goBack).toHaveBeenCalledTimes(1);
  });
});
