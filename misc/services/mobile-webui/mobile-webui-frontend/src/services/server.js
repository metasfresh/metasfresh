import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

// This sets the mock adapter on the default instance
const mockServer = new MockAdapter(axios);

mockServer.onPost('/app/api/v2/aut').reply(200, {
  token: '4bed7f8ec882465e80eb9d23f0f063cf'
});

export { mockServer };
