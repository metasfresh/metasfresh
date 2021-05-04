import React from 'react';
import ReactDOM from 'react-dom';

import { ProvideAuth } from './hooks/useAuth';
import App from './containers/App';

if (process.env.NODE_ENV !== 'production') {
  const whyDidYouRender = require('@welldone-software/why-did-you-render');
  // whyDidYouRender(React, { include: [/Routes/] });
}

  const {whyDidYouUpdate} = require('why-did-you-update')
  // whyDidYouUpdate(React);
  whyDidYouUpdate(React, { include: [/LoginRoute/] });

/* eslint-disable */
console.info(`%c
    metasfresh-webui-frontend build ${COMMIT_HASH}
    https://github.com/metasfresh/metasfresh/commit/${COMMIT_HASH}
`, "color: blue;");
/* eslint-enable */

ReactDOM.render(<ProvideAuth><App /></ProvideAuth>, document.getElementById('root'));
