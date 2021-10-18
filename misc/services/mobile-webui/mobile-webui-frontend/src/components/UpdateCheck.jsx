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

  checkServerVersion = () => {
    const { currentVersion, setVersion } = this.props;
    getServerVersion().then((version) => {
      if (currentVersion === null) {
        setVersion(version);
        return;
      }
      if (currentVersion !== version) {
        setVersion(version);
        window.location.reload();
      }
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
  currentVersion: PropTypes.oneOfType([() => null, PropTypes.string]).isRequired,
};

export default connect(mapStateToProps, { setVersion })(UpdateCheck);
