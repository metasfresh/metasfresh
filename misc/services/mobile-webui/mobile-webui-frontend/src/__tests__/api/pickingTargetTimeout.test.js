import axios from 'axios';

import {
  closePickingTarget,
  PICKING_TARGET_TIMEOUT_MILLIS,
  setLUPickingTarget,
  setTUPickingTargetFromGrai,
} from '../../api/picking';
import { PickingTargetType } from '../../constants/PickingTargetType';

jest.mock('axios');

// A hung connection (handheld roaming between WLAN cells) must end in an error instead of hanging the TU opening
// (and the booking that waits for it, see useOpenNextTU bookAfterTUOpened).
describe('picking target calls have a request timeout', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    axios.post.mockResolvedValue({ data: {} });
  });

  const lastConfig = () => axios.post.mock.calls[0].slice(-1)[0];

  it('the timeout is a finite positive number', () => {
    expect(Number.isFinite(PICKING_TARGET_TIMEOUT_MILLIS)).toBe(true);
    expect(PICKING_TARGET_TIMEOUT_MILLIS).toBeGreaterThan(0);
  });

  it('open TU from a GRAI', async () => {
    await setTUPickingTargetFromGrai({ wfProcessId: 'wf', lineId: 'l', graiString: 'g' });
    expect(lastConfig()).toEqual({ timeout: PICKING_TARGET_TIMEOUT_MILLIS });
  });

  it('open LU target', async () => {
    await setLUPickingTarget({ wfProcessId: 'wf', lineId: 'l', target: {} });
    expect(lastConfig()).toEqual({ timeout: PICKING_TARGET_TIMEOUT_MILLIS });
  });

  it.each([PickingTargetType.TU, PickingTargetType.LU])('close target %s', async (type) => {
    await closePickingTarget({ wfProcessId: 'wf', lineId: 'l', type });
    expect(lastConfig()).toEqual({ timeout: PICKING_TARGET_TIMEOUT_MILLIS });
  });
});
