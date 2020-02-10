import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { Router } from 'react-router';
import { connect, ReactReduxContext } from 'react-redux';

import { getRoutes } from '../routes.js';

class CustomRouter extends PureComponent {
  static contextType = ReactReduxContext;

  UNSAFE_componentWillMount() {
    const { plugins, auth } = this.props;
    const store = this.context.store;

    this.routes = getRoutes(store, auth, plugins);
  }

  render() {
    const { history } = this.props;

    return <Router history={history} routes={this.routes} />;
  }
}

CustomRouter.propTypes = {
  auth: PropTypes.object.isRequired,
  plugins: PropTypes.array,
  history: PropTypes.any,
};

const mapStateToProps = (state) => ({
  plugins: state.pluginsHandler.files,
});

export default connect(mapStateToProps)(CustomRouter);
