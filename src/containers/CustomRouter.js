import React from 'react';
import { Router } from 'react-router';

import { connect } from 'react-redux';
import { getRoutes } from '../routes.js';

const CustomRouter = ({ history, plugins, pluginsLoading, store, auth }) => {
  const routes = getRoutes(store, auth, plugins);

  if (pluginsLoading) {
    return null;
  }

  return <Router history={history} routes={routes} />;
};

const mapStateToProps = state => ({
  plugins: state.pluginsHandler.files,
});

export default connect(mapStateToProps)(CustomRouter);
