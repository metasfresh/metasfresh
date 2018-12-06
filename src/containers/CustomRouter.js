import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { Router } from 'react-router';
import { connect } from 'react-redux';

import { getRoutes } from '../routes.js';

class CustomRouter extends PureComponent {
  UNSAFE_componentWillMount() {
    const { plugins, store, auth } = this.props;

    this.routes = getRoutes(store, auth, plugins);
  }

  render() {
    const { history } = this.props;

    return <Router history={history} routes={this.routes} />;
  }
}

CustomRouter.propTypes = {
  store: PropTypes.object.isRequired,
  auth: PropTypes.object.isRequired,
  plugins: PropTypes.array,
};

const mapStateToProps = state => ({
  plugins: state.pluginsHandler.files,
});

export default connect(mapStateToProps)(CustomRouter);
