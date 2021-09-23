import React, { Component } from 'react';
import { connect } from 'react-redux';
import { v4 as uuidv4 } from 'uuid';

import { populateLaunchers } from '../actions/LauncherActions';
import { getLaunchers } from '../api/launchers';
import Launcher from '../components/Launcher';

class Launchers extends Component {

    componentDidMount() {
      const { populateLaunchers } = this.props;
      getLaunchers({ token: window.config.API_TOKEN }).then((response) => {
        populateLaunchers(response.data.endpointResponse.launchers);
      });
    }

    render() {
      const { launchers } = this.props;
      const launchersKeys = Object.keys(launchers);
        return (
            <div className="launchers-container">
                {launchersKeys.length > 0 && launchersKeys.map((keyName) => {
                  let uniqueId = uuidv4();
                  return <Launcher key={uniqueId} id={uniqueId} {...launchers[keyName]} />
                })}
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        launchers: state.launchers,
      };
};

export default connect(mapStateToProps, { populateLaunchers })(Launchers);