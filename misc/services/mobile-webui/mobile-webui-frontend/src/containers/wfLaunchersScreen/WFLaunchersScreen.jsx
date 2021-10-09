import React, { Component } from 'react';
import { connect } from 'react-redux';
import { v4 as uuidv4 } from 'uuid';
import PropTypes from 'prop-types';

import { populateLaunchers } from '../../actions/LauncherActions';
import { getLaunchers } from '../../api/launchers';
import Launcher from './WFLauncherItem';

class WFLaunchersScreen extends Component {
  componentDidMount() {
    const { populateLaunchers, token } = this.props;

    if (token) {
      // TODO: fetch on each visit
      getLaunchers({ token }).then((response) => {
        populateLaunchers(response.data.endpointResponse.launchers);
      });
    }
  }

  render() {
    const { launchers: launchersMap } = this.props;
    const launchers = Object.values(launchersMap);

    return (
      <div className="container launchers-container">
        {launchers.length > 0 &&
          launchers.map((launcher) => {
            let key = launcher.startedWFProcessId ? 'started-' + launcher.startedWFProcessId : 'new-' + uuidv4();
            return <Launcher key={key} {...launcher} />;
          })}
      </div>
    );
  }
}

WFLaunchersScreen.propTypes = {
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

export default connect(mapStateToProps, { populateLaunchers })(WFLaunchersScreen);
