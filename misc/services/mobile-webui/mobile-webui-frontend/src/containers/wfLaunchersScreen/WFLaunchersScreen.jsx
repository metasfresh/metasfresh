import React, { Component } from 'react';
import { connect } from 'react-redux';
import { v4 as uuidv4 } from 'uuid';
import PropTypes from 'prop-types';

import { populateLaunchers } from '../../actions/LauncherActions';
import { getLaunchers } from '../../api';
import Launcher from './WFLauncherItem';

class WFLaunchersScreen extends Component {
  componentDidMount() {
    const { populateLaunchers } = this.props;

    getLaunchers().then((response) => {
      populateLaunchers(response.data.endpointResponse.launchers);
    });
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
  //
  // Props
  launchers: PropTypes.object.isRequired,
  //
  // Actions
  populateLaunchers: PropTypes.func.isRequired,
};

const mapStateToProps = (state) => {
  return {
    launchers: state.launchers,
  };
};

export default connect(mapStateToProps, { populateLaunchers })(WFLaunchersScreen);
