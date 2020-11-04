import React from 'react';
import { shallow, render } from 'enzyme';
import renderer from 'react-test-renderer';
import nock from 'nock';

import quickInputData from '../../../../test_setup/fixtures/table/table_quickinput.json';
import TableQuickInput from '../../../components/table/TableQuickInput';

const initialProps = {
  ...quickInputData.basicProps,
  addNotification: jest.fn(),
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
    const wrapperTableCMenu = shallow(
      <TableQuickInput {...initialProps} />
    );
    expect(wrapperTableCMenu.find('.quick-input-container').length).toBe(1);

    // const nockCalls = nock.recorder.play() // "play" the recording into a variable
    // console.log(`Nock Captured Calls: \n${JSON.stringify(nockCalls,null,2)}`) // inspect calls that Nock recorder

    // nock.restore();
  });

  it('renders without error 22s', () => {
    const wrapperTableCMenu = render(
      <TableQuickInput {...initialProps} />
    );
    expect(wrapperTableCMenu.find('.hint').text()).toBe(`(Press 'Enter' to add)`);
  });
});
