import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
class Header extends Component {
  render() {
    const { appName, networkStatus } = this.props;
    return (
      <header className="p-4 navbar is-fixed-top is-flex is-align-items-center is-justify-content-space-around header">
        <div className="header-title">
          <h4 className="title is-4">{appName}</h4>
          <div className="subtitle">network: {networkStatus ? 'online' : 'offline'} </div>
        </div>
      </header>
    );
  }
}

Header.propTypes = {
  appName: PropTypes.string.isRequired,
  networkStatus: PropTypes.bool,
};

const mapStateToProps = (state) => {
  return {
    networkStatus: state.appHandler.network,
  };
};

export default connect(mapStateToProps, null)(Header);
