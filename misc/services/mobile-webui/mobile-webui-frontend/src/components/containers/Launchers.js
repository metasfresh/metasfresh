import React, { Component } from 'react';
import { connect } from 'react-redux';
import { populateLaunchers } from '../../actions/LauncherActions';
import { getLaunchers } from '../../api/launchers';
class Launchers extends Component {

    componentDidMount() {
      const { populateLaunchers } = this.props;
      getLaunchers({ token: window.config.API_TOKEN }).then(() => {
        populateLaunchers();
      });
    }
    render() {
        return (
            <div>
                Launchers placeholder
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