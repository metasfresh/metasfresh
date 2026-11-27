import React from 'react';
import '@testing-library/jest-dom';
import { act, render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import { combineReducers, createStore } from 'redux';

import { ApplicationLayout } from '../../../containers/applicationRoot/ApplicationLayout';
import headers from '../../../reducers/headers';
import wfProcesses from '../../../reducers/wfProcesses/index';
import { pushHeaderEntry, updateHeaderEntry } from '../../../actions/HeaderActions';

// A screen can put data-* attributes on the root element of the page (the one with id = screenId): the line screen of the
// label modes sets data-cu-label-required to get its smaller header font. The attributes travel through the real header
// store entry of the screen, up to the DOM.

jest.mock('../../../hooks/useMobileNavigation', () => ({ useMobileNavigation: () => ({ goHome: jest.fn() }) }));
jest.mock('../../../hooks/useMobileLocation', () => ({ useMobileLocation: () => ({}) }));
jest.mock('../../../reducers/applications', () => ({
  useApplicationInfo: () => ({ caption: 'Picking', iconClassNames: 'fas fa-box' }),
}));
jest.mock('../../../apps', () => ({
  isApplicationFullScreen: () => false,
  // ApplicationLayout also mounts apps/picking/ShelfLifeConfirmDialogHost, whose selector reads getApplicationState
  // from this module: undefined state = no pending confirmation, the host renders nothing.
  getApplicationState: () => undefined,
}));
jest.mock('../../../utils/ui_trace/useUITraceLocationChange', () => ({ useUITraceLocationChange: jest.fn() }));
jest.mock('../../../utils/ui_trace', () => ({ traceFunction: (fn) => fn }));
jest.mock('../../../containers/ViewHeader', () => ({ ViewHeader: () => null }));
jest.mock('../../../components/ScreenToaster', () => () => null);

const SCREEN_LOCATION = '/line/1';
const SCREEN_ID = 'PickLineScanScreen';

const renderScreen = ({ rootDataAttributes }) => {
  const store = createStore(combineReducers({ headers, wfProcesses }));
  store.dispatch(pushHeaderEntry({ location: SCREEN_LOCATION, screenId: SCREEN_ID }));
  if (rootDataAttributes) {
    store.dispatch(updateHeaderEntry({ location: SCREEN_LOCATION, rootDataAttributes }));
  }

  render(
    <Provider store={store}>
      <MemoryRouter initialEntries={[SCREEN_LOCATION]}>
        <ApplicationLayout applicationId="picking" Component={() => <div>screen</div>} />
      </MemoryRouter>
    </Provider>
  );
  return store;
};

describe('ApplicationLayout: root data attributes of the screen', () => {
  it('are on the root element of the page', () => {
    renderScreen({
      rootDataAttributes: { 'data-cu-qty-entry-mode': 'SCAN_ONCE_ENTER_QTY', 'data-cu-label-required': 'true' },
    });

    const root = document.getElementById(SCREEN_ID);
    expect(root).toHaveAttribute('data-cu-qty-entry-mode', 'SCAN_ONCE_ENTER_QTY');
    expect(root).toHaveAttribute('data-cu-label-required', 'true');
  });

  it('a screen that sets none gets none: every other screen of every app is unchanged', () => {
    renderScreen({});

    const root = document.getElementById(SCREEN_ID);
    expect(root).not.toBeNull();
    expect(root.getAttributeNames().sort()).toEqual(['class', 'id']);
  });

  it('follow a later header update of the screen', () => {
    const store = renderScreen({ rootDataAttributes: { 'data-cu-label-required': 'false' } });

    act(() => {
      store.dispatch(
        updateHeaderEntry({ location: SCREEN_LOCATION, rootDataAttributes: { 'data-cu-label-required': 'true' } })
      );
    });

    expect(document.getElementById(SCREEN_ID)).toHaveAttribute('data-cu-label-required', 'true');
  });
});
