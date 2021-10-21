import React, { Component } from 'react';
import AppCard from './AppCard';
import { getApplications } from '../../api/applications';

class ApplicationsScreen extends Component {
  componentDidMount() {
    // const { populateLaunchers } = this.props;

    getApplications().then((applications) => {
      console.log('APPS:', applications);
      // populateApplications(application);
    });
  }
  render() {
    return (
      <div className="application-container">
        <AppCard captionKey="mobileui.distribution.appName" />
        <AppCard captionKey="mobileui.manufacturing.appName" />
        <AppCard captionKey="mobileui.picking.appName" />
      </div>
    );
  }
}

export default ApplicationsScreen;
