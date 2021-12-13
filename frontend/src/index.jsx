import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';

import App from './containers/App';
import { ProvideAuth } from './hooks/useAuth';
import { historyDoubleBackOnPopstate } from './utils';

import store from './store/store';

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

// If there is a view generated in case one doesn't exist that view and the params are added to the URI and recorded to the history
// as a consequence when the user hits the Back button has the impression that it is on the same page as the browser is visiting the same view
// to deal with this case we added a `popstate` listener that will go to the correct page in history skipping
// the case when the URL and the view are the same when the back button is pressed
window.addEventListener('popstate', () => {
  historyDoubleBackOnPopstate(store);
});
