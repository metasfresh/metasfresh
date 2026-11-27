import React from 'react';
import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';
import counterpart from 'counterpart';
import { Provider } from 'react-redux';
import { createStore } from 'redux';

import SelectCurrentLUTUButtons from '../../../../containers/activities/picking/SelectCurrentLUTUButtons';
import translations_de from '../../../../utils/translations_de';
import translations_en from '../../../../utils/translations_en';
import { PICKTO_STRUCTURE_TU } from '../../../../reducers/wfProcesses/picking/PickToStructure';

let mockInfo;
jest.mock('../../../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../../../hooks/useMobileNavigation', () => ({ useMobileNavigation: () => ({ push: jest.fn() }) }));
jest.mock('../../../../containers/activities/picking/useClosedHUs', () => ({
  useHasClosedHUs: () => ({ hasClosedLUs: false }),
}));
jest.mock('../../../../reducers/wfProcesses/picking/useCurrentPickTarget', () => ({
  useCurrentPickingTargetInfo: () => mockInfo,
}));

// the component dispatches the carrier advise result, so it needs a store
const renderButtons = () =>
  render(
    <Provider store={createStore(() => ({}))}>
      <SelectCurrentLUTUButtons applicationId="picking" wfProcessId="wf" activityId="a" lineId="l" />
    </Provider>
  );

describe('SelectCurrentLUTUButtons: the open TU button caption', () => {
  beforeAll(() => {
    counterpart.registerTranslations('en', translations_en);
    counterpart.registerTranslations('de', translations_de);
  });
  afterEach(() => counterpart.setLocale('en'));

  const info = (tuCaption) => ({
    luPickingTarget: null,
    tuPickingTarget: tuCaption ? { caption: tuCaption } : null,
    allowedPickToStructures: [PICKTO_STRUCTURE_TU],
    isAllowReopeningLU: false,
  });

  it('German: "Offenes TU: <HU>"', () => {
    counterpart.setLocale('de');
    mockInfo = info('TU x 4 #1234');
    renderButtons();
    expect(screen.getByTestId('targetTU-button')).toHaveTextContent('Offenes TU: TU x 4 #1234');
  });

  it('English: "Current: <HU>" stays unchanged', () => {
    counterpart.setLocale('en');
    mockInfo = info('TU x 4 #1234');
    renderButtons();
    expect(screen.getByTestId('targetTU-button')).toHaveTextContent('Current: TU x 4 #1234');
  });
});
