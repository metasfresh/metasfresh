import React from 'react';
import { useStore } from 'react-redux';
import { Link } from 'react-router-dom';

import Header from '../components/Header';
import Launchers from '../containers/Launchers';
import WFProcess from '../components/containers/WFProcess';

const Dashboard = () => {
  const state = useStore().getState();
  const { wfProcesses, wfProcesses_status } = state;

  return (
    <>
      <Header appName="webUI app" />
      <Launchers />
      <WFProcess
        wfProcessId="picking-7f42317d-0782-466c-a192-cb5ad7d3cce0"
        {...wfProcesses['picking-7f42317d-0782-466c-a192-cb5ad7d3cce0']}
        status={wfProcesses_status['picking-7f42317d-0782-466c-a192-cb5ad7d3cce0']}
      />
      <Link to="/test">Go to test</Link>
    </>
  );
};

export default Dashboard;
