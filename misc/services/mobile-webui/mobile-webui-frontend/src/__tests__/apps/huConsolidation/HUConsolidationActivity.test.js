import React from 'react';
import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';

import HUConsolidationActivity from '../../../apps/huConsolidation/activities/HUConsolidationActivity';

const mockActivity = { current: null };

jest.mock('../../../hooks/useMobileNavigation', () => ({ useMobileNavigation: () => ({ push: jest.fn() }) }));
jest.mock('../../../utils/translations', () => ({ trl: (key) => key }));
jest.mock('../../../utils/ui_trace', () => ({ trace: jest.fn(), traceFunction: (func) => func }));
jest.mock('../../../reducers/wfProcesses', () => ({ useWFActivity: () => mockActivity.current }));
jest.mock('../../../apps/huConsolidation/actions/useCurrentTarget', () => ({
  useCurrentTarget: () => ({ currentTarget: { caption: 'LU1' } }),
}));
jest.mock('../../../apps/huConsolidation/actions/usePickingSlots', () => ({
  usePickingSlots: () => ({ pickingSlots: [] }),
}));

const activityOf = ({ graiScanEnabled, graiScanButtonShown }) => ({
  dataStored: { isUserEditable: true, graiScanButtonShown, job: { graiScanEnabled } },
});

const renderActivity = (activity) => {
  mockActivity.current = activity;
  return render(
    <HUConsolidationActivity applicationId="huConsolidation" wfProcessId="huConsolidation-1" activityId="a" />
  );
};

describe('HUConsolidationActivity: Scan (GRAI) button slot', () => {
  it('GRAI scan enabled: the button is shown, no placeholder slot', () => {
    renderActivity(activityOf({ graiScanEnabled: true, graiScanButtonShown: true }));
    expect(screen.getByTestId('scanGRAI-button')).toBeInTheDocument();
    expect(screen.queryByTestId('scanGRAI-slot')).not.toBeInTheDocument();
  });

  it('button was shown, GRAI scan now disabled: the button is gone, an invisible same-size slot keeps its place', () => {
    renderActivity(activityOf({ graiScanEnabled: false, graiScanButtonShown: true }));
    expect(screen.queryByTestId('scanGRAI-button')).not.toBeInTheDocument();
    const slot = screen.getByTestId('scanGRAI-slot');
    expect(slot).toHaveStyle({ visibility: 'hidden' });
  });

  it('button never shown (job without GRAI crates): no button and no slot, so no gap', () => {
    renderActivity(activityOf({ graiScanEnabled: false, graiScanButtonShown: undefined }));
    expect(screen.queryByTestId('scanGRAI-button')).not.toBeInTheDocument();
    expect(screen.queryByTestId('scanGRAI-slot')).not.toBeInTheDocument();
  });
});
