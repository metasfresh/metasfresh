import React, { render } from 'react';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';

function wrapInRouter(
  component,
  { route = '/', history = createMemoryHistory({ initialEntries: [route] })} = {}
) {
  console.log('innerWithRouter: ', component)
  return <Router history={history}>{component}</Router>
}

export { wrapInRouter };
