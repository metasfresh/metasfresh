import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { map } from 'lodash';

import AppCard from './AppCard';
import { getApplications } from '../../api/applications';
import { populateApplications } from '../../actions/ApplicationsActions';

class ApplicationsScreen extends Component {
  componentDidMount() {
    const { populateApplications } = this.props;

    getApplications().then(({ applications }) => {
      populateApplications({ applications });
    });
  }

  render() {
    const { applications } = this.props;

    return (
      <div className="application-container columns ml-0">
        {map(applications, (app) => {
          return <AppCard key={app.id} appId={app.id} captionKey={app.caption} />;
        })}
      </div>
    );
  }
}

const mapStateToProps = ({ applications }) => {
  return {
    applications,
  };
};

ApplicationsScreen.propTypes = {
  // Props
  applications: PropTypes.object.isRequired,
  // Actions
  populateApplications: PropTypes.func.isRequired,
};

export default connect(mapStateToProps, { populateApplications })(ApplicationsScreen);
