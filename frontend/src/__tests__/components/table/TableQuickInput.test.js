import React from 'react';
import { shallow, render, mount } from 'enzyme';
import renderer from 'react-test-renderer';
import nock from 'nock';
import { omit } from 'lodash';
import { Provider } from 'react-redux';
import thunk from 'redux-thunk';
import configureStore from 'redux-mock-store';
import { merge } from 'merge-anything';

import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import { initialState as widgetHandlerState } from '../../../reducers/widgetHandler';
import { parseToDisplay } from '../../../utils/documentListHelper';

import quickInputData from '../../../../test_setup/fixtures/table/table_quickinput.json';
import ConnectedTableQuickInput, { TableQuickInput } from '../../../components/table/TableQuickInput';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);
const createStore = function(state = {}) {
  const res = merge(
    {
      widgetHandler: widgetHandlerState,
    },
    state
  );

  return res;
};
const promiseMock = jest.fn(() => Promise.resolve(true));
const initialProps = {
  ...quickInputData.basicProps,
  data: quickInputData.data1.fieldsByName,
  layout: quickInputData.layout.elements,
  inProgress: false,
  id: quickInputData.data1.id,
  addNotification: jest.fn(),
  fetchQuickInputData: promiseMock,
  fetchQuickInputLayout: promiseMock,
  deleteQuickInput: jest.fn(),
  setQuickinputData: jest.fn(),
  patchQuickInput: promiseMock,
  closeBatchEntry: jest.fn(),
};

// leaving this so that I won't have to look it up again in case we need it
// nock.recorder.rec({
//   output_objects: true,
// })

describe('TableQuickInput', () => {
  beforeEach(() => {
    const { docType, docId, tabId } = initialProps;

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/window/${docType}/${docId}/${tabId}/quickInput`)
      .reply(200, quickInputData.data1);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${docType}/${docId}/${tabId}/quickInput/layout`)
      .reply(200, quickInputData.layout1);
  });

  it('renders without errors', () => {
    const wrapperTableCMenu = shallow(<TableQuickInput {...initialProps} />);

    expect(wrapperTableCMenu.find('.quick-input-container').length).toBe(1);

    // const nockCalls = nock.recorder.play() // "play" the recording into a variable
    // console.log(`Nock Captured Calls: \n${JSON.stringify(nockCalls,null,2)}`) // inspect calls that Nock recorder

    // nock.restore();
  });

  it('renders without error 22s', (done) => {
    const moduleMock = jest.requireActual('../../../actions/IndependentWidgetsActions');

    const initialState = createStore({
      widgetHandler: {
        layout: quickInputData.layout.elements,
        data: parseToDisplay(quickInputData.data1.fieldsByName),
      }
    });

    const localProps = omit(initialProps, ['layout', 'data']);
    const store = mockStore(initialState);
    const fetchQuickInputLayoutMock = (args, dispatch) => {
      return dispatch(moduleMock.fetchQuickInputLayout({ ...args })).then(done);
    };
    const wrapper = mount(
      <Provider store={store}>
        <ConnectedTableQuickInput {...localProps} fetchQuickInputLayout={fetchQuickInputLayoutMock} />
      </Provider>
    );

    expect(wrapper.find('.hint').text()).toBe(
      `(Press 'Enter' to add)`
    );
  });
});
