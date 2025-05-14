import React from 'react';
import { mount, shallow } from 'enzyme';
import nock from 'nock';
import { omit } from 'lodash';
import { Provider } from 'react-redux';
import thunk from 'redux-thunk';
import configureStore from 'redux-mock-store';
import { merge } from 'merge-anything';

import {
  initialState as tableQuickInputHandlerState
} from '../../../reducers/tableQuickInputHandler';
import { parseToDisplay } from '../../../utils/documentListHelper';

import quickInputData
  from '../../../../test_setup/fixtures/table/table_quickinput.json';
import ConnectedTableQuickInput, {
  TableQuickInput
} from '../../../components/table/TableQuickInput';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);
const createStore = (state = {}) => {
  return merge(
    {
      tableQuickInputHandler: tableQuickInputHandlerState,
    },
    state
  );
};
const promiseMock = jest.fn(() => Promise.resolve(true));
const initialProps = {
  ...quickInputData.basicProps,
  addNotification: jest.fn(),

  //
  // mapStateToProps
  data: quickInputData.data1.fieldsByName,
  layout: quickInputData.layout.elements,
  inProgress: false,
  id: quickInputData.data1.id,

  //
  // mapDispatchToProps
  fetchQuickInputData: promiseMock,
  fetchQuickInputLayout: promiseMock,
  deleteQuickInput: jest.fn(),
  updateQuickinputData: jest.fn(),
  patchQuickInput: promiseMock,
  closeBatchEntry: jest.fn(),
};

// leaving this so that I won't have to look it up again in case we need it
// nock.recorder.rec({
//   output_objects: true,
// })

describe('TableQuickInput', () => {
  beforeEach(() => {
    const { windowId, docId, tabId } = initialProps;

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/window/${windowId}/${docId}/${tabId}/quickInput`)
      .reply(200, quickInputData.data1);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowId}/${docId}/${tabId}/quickInput/layout`)
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
    const moduleMock = jest.requireActual('../../../actions/TableQuickInputActions');

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

    expect(wrapper.find('.hint').text()).toBe(`(Press 'Enter' to add)`);
  });
});
