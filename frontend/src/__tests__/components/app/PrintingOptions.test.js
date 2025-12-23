import React from 'react';
import { render } from 'enzyme';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import { merge } from 'merge-anything';
import {
  ShortcutProvider
} from '../../../components/keyshortcuts/ShortcutProvider';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import {
  initialState as windowHandlerState
} from '../../../reducers/windowHandler';

import PrintingOptions from '../../../components/app/PrintingOptions';
import testModal from '../../../../test_setup/fixtures/modal/test_modal.json';
import printingOptions
  from '../../../../test_setup/fixtures/window/printingOptions.json';
import thunk from 'redux-thunk';

const mockStore = configureStore([thunk]);

const getInitialState = function(state = {}) {
  return merge(
    {
      appHandler: { ...appHandlerState },
      windowHandler: {
        ...windowHandlerState,
        modal: testModal,
      },
    },
    state
  );
};

describe('PrintingOptions test', () => {
  it('renders without errors', () => {
    let dummyProps = printingOptions;
    const initialState = getInitialState();
    const store = mockStore(initialState);

    const wrapper = render(
      <Provider store={store}>
        <ShortcutProvider>
          <PrintingOptions printingOptions={dummyProps} />
        </ShortcutProvider>
      </Provider>
    );

    const html = wrapper.html();
    expect(html).not.toBe(null);
  });
});
