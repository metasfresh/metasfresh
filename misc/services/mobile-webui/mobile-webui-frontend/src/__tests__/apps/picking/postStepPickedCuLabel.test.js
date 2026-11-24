import { postStepPicked } from '../../../api/picking';
import axios from 'axios';

jest.mock('axios');

describe('postStepPicked: cuLabel', () => {
  beforeEach(() => {
    axios.post.mockResolvedValue({ data: { wfProcess: {} } });
  });

  it('sends the raw label in the PICK event and no whole-TU pick', async () => {
    await postStepPicked({
      wfProcessId: 'wf1',
      activityId: 'a1',
      lineId: 'l1',
      huQRCode: 'RAW-LABEL',
      qtyPicked: 4,
      pickWholeTU: false,
      cuLabel: 'RAW-LABEL',
    });
    const event = JSON.stringify(axios.post.mock.calls[0]);
    expect(event).toContain('"cuLabel":"RAW-LABEL"');
    expect(event).toContain('"pickWholeTU":false');
  });

  it('omits cuLabel when none is given', async () => {
    await postStepPicked({ wfProcessId: 'wf1', activityId: 'a1', lineId: 'l1', huQRCode: 'x', qtyPicked: 1 });
    expect(JSON.stringify(axios.post.mock.calls[0])).not.toContain('cuLabel');
  });
});
