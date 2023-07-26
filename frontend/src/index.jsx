import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';

import App from './containers/App';
import configureStore from './store/configureStore';
import { ProvideAuth } from './hooks/useAuth';

const store = configureStore();

if (window.Cypress) {
  window.store = store;
}

// if (process.env.NODE_ENV !== 'production') {
//   const whyDidYouRender = require('@welldone-software/why-did-you-render');
//   whyDidYouRender(React, { include: [/^MasterWindowContainer/], collapseGroups: true });
// }

// const { whyDidYouUpdate } = require('why-did-you-update')
// // whyDidYouUpdate(React);
// whyDidYouUpdate(React, { include: [/PrivateRoute/] });

/* eslint-disable */
console.info(`%c
    metasfresh-webui-frontend build ${COMMIT_HASH}
    https://github.com/metasfresh/metasfresh/commit/${COMMIT_HASH}
`, "color: blue;");
/* eslint-enable */

// render the application wrapped in Redux store and authentication providers
ReactDOM.render(
  <Provider store={store}>
    <ProvideAuth>
      <App />
    </ProvideAuth>
  </Provider>,
  document.getElementById('root')
);
