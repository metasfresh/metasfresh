import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { getServerVersion } from '../api/update';
import { connect } from 'react-redux';
import { setVersion } from '../actions/UpdateActions';
class UpdateCheck extends Component {
  constructor(props) {
    super(props);
    this.state = {
      intervalId: null,
      serverVersion: null,
    };
  }

  checkServerVersion = async () => {
    const { currentVersion, setVersion } = this.props;
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

          /**
           * (!) if full path contains `/login` on refresh it will lead to a failed to load resource fetch (404) that would make the service worker
           *     redundant. Due to this we need to redirect to the root of the site when a version change is happaning instead of reloading the page
           */
          // let fullUrl = window.location.href;
          // if (fullUrl.includes('/login')) {
          //   window.location.href = '/mobile/';
          // } else {
          //   window.location.reload();
          // }
          window.location.reload();
        }
      })
      .catch((error) => {
        console.log('Error from vercheck:', error);
        // networkStatusOffline();
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
  currentVersion: PropTypes.oneOfType([() => null, PropTypes.string]),
};

export default connect(mapStateToProps, { setVersion })(UpdateCheck);
