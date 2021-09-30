import React, { Component } from 'react';
import { connect } from 'react-redux';
import { v4 as uuidv4 } from 'uuid';
import PropTypes from 'prop-types';

import { populateLaunchers } from '../actions/LauncherActions';
import { getLaunchers } from '../api/launchers';
import Launcher from '../components/Launcher';

class Launchers extends Component {
  componentDidMount() {
    const { populateLaunchers, token } = this.props;

    if (token) {
      getLaunchers({ token }).then((response) => {
        populateLaunchers(response.data.endpointResponse.launchers);
      });
    }
  }

  render() {
    const { launchers } = this.props;
    const launchersKeys = Object.keys(launchers);

    return (
      <div className="launchers-container">
        {launchersKeys.length > 0 &&
          launchersKeys.map((keyName) => {
            let uniqueId = uuidv4();
            return <Launcher key={uniqueId} {...launchers[keyName]} />;
          })}
      </div>
    );
  }
}

Launchers.propTypes = {
  populateLaunchers: PropTypes.func.isRequired,
  launchers: PropTypes.object.isRequired,
  token: PropTypes.string,
};

const mapStateToProps = (state) => {
  return {
    launchers: state.launchers,
    token: state.appHandler.token,
  };
};

export default connect(mapStateToProps, { populateLaunchers })(Launchers);
