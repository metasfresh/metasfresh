import React from 'react';
import ReactDOM from 'react-dom';

import App from './containers/App';

// if (process.env.NODE_ENV !== 'production') {
//   const whyDidYouRender = require('@welldone-software/why-did-you-render');
//   whyDidYouRender(React, { include: [/DocList/] });
// }

/* eslint-disable */
console.info(`%c
    metasfresh-webui-frontend build ${COMMIT_HASH}
    https://github.com/metasfresh/metasfresh/commit/${COMMIT_HASH}
`, "color: blue;");
/* eslint-enable */

ReactDOM.render(<App />, document.getElementById('root'));
