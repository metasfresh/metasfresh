import React from 'react';
import { ConnectedRouter } from 'connected-react-router';
import { Route, Switch } from 'react-router';

// import App from '../App';
import LoginRoute from './LoginRoute';
import PrivateRoute from './PrivateRoute';
import { history } from '../store/store';

const Routes = () => {
  return (
    <ConnectedRouter history={history}>
      <>
        <Switch>
          <Route exact path="/login" component={LoginRoute} />
          <PrivateRoute path="/">
            <Route exact path="/test" render={() => (<><h1>test</h1></>)} />
          </PrivateRoute>
        </Switch>
      </>
    </ConnectedRouter>
  );
};

export default Routes;
