import React from 'react';
import { shallow } from 'enzyme';
import nock from 'nock';

import App from '../../containers/App';
import { ShortcutProvider } from '../../components/keyshortcuts/ShortcutProvider';

import hotkeys from '../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../test_setup/fixtures/keymap.json';

describe('Application', () => {
  let spy;
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
      <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
        <App  />
      </ShortcutProvider>
    );
  });
});