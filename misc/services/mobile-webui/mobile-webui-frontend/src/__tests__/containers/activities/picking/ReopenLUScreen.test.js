import React from 'react';
import { render, screen } from '@testing-library/react';

// the app reads `config` (a runtime global of the hosting page) while its modules load
global.config = { SERVER_URL: '' };
const { ReopenLUScreen } = require('../../../../containers/activities/picking/ReopenLUScreen');
const { useClosedHUs } = require('../../../../containers/activities/picking/useClosedHUs');

jest.mock('../../../../hooks/useScreenDefinition', () => ({
  useScreenDefinition: () => ({ history: { goBack: jest.fn() }, wfProcessId: 'wf1', lineId: 'line1' }),
}));
// real German strings, without pulling the whole app (stores, styles) in through utils/translations
let mockEmptyTranslations = false;
jest.mock('../../../../utils/translations', () => {
  const de = require('../../../../utils/translations_de').default;
  return { trl: (key) => (mockEmptyTranslations ? '' : key.split('.').reduce((o, k) => o && o[k], de)) };
});
jest.mock('../../../../routes/picking', () => ({ pickingJobOrLineLocation: () => '/' }));
jest.mock('../../../../containers/activities/picking/useClosedHUs', () => ({ useClosedHUs: jest.fn() }));

const lu = (id, huStatus) => ({
  id,
  jsonHUType: 'LU',
  huStatus,
  displayName: `LU-${id}`,
  products: [],
  attributes2: { list: [{ code: 'Lot-Nummer', caption: 'Lot', value: `L${id}` }] },
});

describe('ReopenLUScreen status labels', () => {
  beforeEach(() => {
    mockEmptyTranslations = false;
    useClosedHUs.mockReturnValue({
      isWorking: false,
      closedLUs: [lu('1', 'S'), lu('2', 'E'), lu('3', 'D')],
      reopenLU: jest.fn(),
    });
  });

  it('greys out and labels shipped (E) and destroyed (D) LUs, leaves the selected (S) LU tappable and unlabelled', () => {
    const { container } = render(<ReopenLUScreen />);

    const buttons = screen.getAllByRole('button');
    expect(buttons).toHaveLength(3);
    expect(buttons[0].disabled).toBe(false);
    expect(buttons[1].disabled).toBe(true);
    expect(buttons[2].disabled).toBe(true);

    const labels = [...container.querySelectorAll('.hu-button-status')].map((e) => e.textContent);
    expect(labels).toEqual(['Versendet', 'Aufgelöst']);
    expect(buttons[0].querySelector('.hu-button-status')).toBeNull();
  });

  it('keeps shipped (E) and destroyed (D) LUs not tappable even when their status label text is empty', () => {
    mockEmptyTranslations = true;
    render(<ReopenLUScreen />);

    const buttons = screen.getAllByRole('button');
    expect(buttons.map((b) => b.disabled)).toEqual([false, true, true]);
  });
});
