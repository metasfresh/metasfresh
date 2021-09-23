import React from 'react';
import { Link } from 'react-router-dom';

import Header from '../components/Header';
import Launchers from '../containers/Launchers';

const Dashboard = () => {
  return (
    <>
      <Header appName="webUI app" />
      <Launchers />
      <Link to="/test">Go to test</Link>
    </>
  );
}

export default Dashboard;
