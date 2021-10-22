import React, { Component } from 'react';
import AppCard from './AppCard';
import { getApplications } from '../../api/applications';
import { populateApplications } from '../../actions/ApplicationsActions';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
class ApplicationsScreen extends Component {
  componentDidMount() {
    const { populateApplications } = this.props;

    getApplications().then(({ applications }) => {
      populateApplications({ applications });
    });
  }
  render() {
    const { applications } = this.props;
    const apps = Object.keys(applications);
    return (
      <div className="application-container">
        {apps.length &&
          apps.map((appId) => {
            return <AppCard key={applications[appId].id} captionKey={applications[appId].caption} />;
          })}
      </div>
    );
  }
}
const mapStateToProps = (state) => {
  return {
    applications: state.applications,
  };
};

ApplicationsScreen.propTypes = {
  // Props
  applications: PropTypes.object.isRequired,
  // Actions
  populateApplications: PropTypes.func.isRequired,
};

export default connect(mapStateToProps, { populateApplications })(ApplicationsScreen);
