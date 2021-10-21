import React, { Component } from 'react';
import AppCard from './AppCard';

class ApplicationsScreen extends Component {
  render() {
    return (
      <div className="application-container">
        <AppCard caption="Picking" />
        <AppCard caption="Production" />
        <AppCard caption="Move" />
      </div>
    );
  }
}

export default ApplicationsScreen;
