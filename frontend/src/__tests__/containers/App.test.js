import React from 'react';
import { shallow } from 'enzyme';
import nock from 'nock';

import App from '../../containers/App';
import {
  ShortcutProvider
} from '../../components/keyshortcuts/ShortcutProvider';

describe('Application', () => {
  beforeAll(() => {
    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .persist()
      .get(uri => uri.includes('login'))
      .reply(200, { data: {} });
  })

  afterAll(() => {
    nock.cleanAll();
  })

  it('renders without errors', () => {
    shallow(
      <ShortcutProvider>
        <App  />
      </ShortcutProvider>
    );
  });
});
