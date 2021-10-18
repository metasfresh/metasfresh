import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { getServerVersion } from '../api/update';
import { connect } from 'react-redux';
import { setVersion } from '../actions/UpdateActions';
import { networkStatusOffline } from '../actions/NetworkActions';

class UpdateCheck extends Component {
  constructor(props) {
    super(props);
    this.state = {
      intervalId: null,
      serverVersion: null,
    };
  }

  checkServerVersion = async () => {
    const { currentVersion, setVersion, networkStatusOffline } = this.props;
    getServerVersion()
      .then((version) => {
        if (currentVersion === null) {
          setVersion(version);
          return;
        }
        if (currentVersion !== version) {
          caches.keys().then((keys) => {
            for (const key of keys) {
              caches.delete(key);
            }
          });

          setVersion(version);
          window.location.reload();
        }
      })
      .catch(() => {
        networkStatusOffline();
      });
  };

  componentDidMount() {
    const { updateInterval } = this.props;
    let intervalId = setInterval(this.checkServerVersion, updateInterval);
    this.setState({ intervalId: intervalId });
  }

  componentWillUnmount() {
    clearInterval(this.state.intervalId);
  }

  render() {
    return <div></div>;
  }
}

const mapStateToProps = (state) => {
  return {
    currentVersion: state.update.version,
  };
};

UpdateCheck.propTypes = {
  updateInterval: PropTypes.number.isRequired,
  setVersion: PropTypes.func.isRequired,
  networkStatusOffline: PropTypes.func.isRequired,
  currentVersion: PropTypes.oneOfType([() => null, PropTypes.string]).isRequired,
};

export default connect(mapStateToProps, { setVersion, networkStatusOffline })(UpdateCheck);
