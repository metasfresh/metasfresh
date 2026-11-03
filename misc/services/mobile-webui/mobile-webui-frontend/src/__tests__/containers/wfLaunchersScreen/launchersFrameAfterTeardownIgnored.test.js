import React from 'react';
import { render } from '@testing-library/react';

import { useLaunchersWebsocket } from '../../../api/launchers';

// ws.disconnectClient is asynchronous, so a launchers frame can still be delivered AFTER the
// subscription's effect cleanup ran and the screen unmounted -- in the captured failure the STOMP
// DISCONNECT was logged BEFORE the deleting MESSAGE. An unmounted subscription must not write to the
// store at all, so the inner frame handler has to drop anything that arrives after teardown.
//
// The lifecycle lives in api/launchers.js (useLaunchersWebsocket's useEffect creates the client and its
// cleanup disconnects it), NOT in containers/wfLaunchersScreen/useLaunchers.js -- which only supplies the
// OUTER callback and cannot see a frame's arrival. So this test drives useLaunchersWebsocket and asserts
// on that outer callback: it is the boundary at which a frame would reach the store.

let capturedInnerOnWebsocketMessage = null;
// `mock`-prefixed on purpose: babel-plugin-jest-hoist hoists the jest.mock factory above these
// declarations and rejects a READ of any other out-of-scope variable from inside it.
let mockDisconnectedClients = [];

const FAKE_CLIENT = { id: 'fake-stomp-client' };

// NOTE: plain functions, not jest.fn(impl) -- create-react-app's jest preset sets `resetMocks: true`,
// which strips implementations passed to jest.fn().
jest.mock('../../../utils/websocket', () => ({
  connectAndSubscribe: ({ onWebsocketMessage }) => {
    capturedInnerOnWebsocketMessage = onWebsocketMessage;
    return { id: 'fake-stomp-client' };
  },
  disconnectClient: (client) => {
    mockDisconnectedClients.push(client);
  },
}));

// A realistic frame: the server serialises the launchers snapshot into message.body as JSON.
const LAUNCHERS_FRAME = { body: JSON.stringify({ launchers: [] }) };

const WebsocketProbe = ({ onWebsocketMessage }) => {
  useLaunchersWebsocket({
    enabled: true,
    userToken: 'test-user-token',
    applicationId: 'picking',
    filterByQRCode: null,
    filters: {},
    facets: null,
    onWebsocketMessage,
  });
  return null;
};

describe('useLaunchersWebsocket: a launchers frame delivered after teardown must not reach the store', () => {
  beforeEach(() => {
    capturedInnerOnWebsocketMessage = null;
    mockDisconnectedClients = [];
  });

  it('ignores a frame delivered after the subscription tore down', () => {
    const outerOnWebsocketMessage = jest.fn();

    const { unmount } = render(<WebsocketProbe onWebsocketMessage={outerOnWebsocketMessage} />);
    expect(typeof capturedInnerOnWebsocketMessage).toBe('function');

    unmount();
    expect(mockDisconnectedClients).toEqual([FAKE_CLIENT]);

    // ws.disconnectClient is async: the broker can still hand us this frame.
    capturedInnerOnWebsocketMessage(LAUNCHERS_FRAME);

    expect(outerOnWebsocketMessage).not.toHaveBeenCalled();
  });

  // CONTROL: the identical frame, delivered while still mounted, must reach the callback exactly once.
  // Without it the test above could pass by never wiring anything up.
  it('delivers the same frame while the subscription is still mounted', () => {
    const outerOnWebsocketMessage = jest.fn();

    render(<WebsocketProbe onWebsocketMessage={outerOnWebsocketMessage} />);
    expect(typeof capturedInnerOnWebsocketMessage).toBe('function');

    capturedInnerOnWebsocketMessage(LAUNCHERS_FRAME);

    expect(outerOnWebsocketMessage).toHaveBeenCalledTimes(1);
    expect(outerOnWebsocketMessage).toHaveBeenCalledWith({
      applicationId: 'picking',
      applicationLaunchers: { launchers: [] },
    });
  });
});
