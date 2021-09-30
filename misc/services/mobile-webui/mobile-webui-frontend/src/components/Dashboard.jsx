import React from 'react';
import { Link } from 'react-router-dom';

import Launchers from '../containers/Launchers';

const Dashboard = () => {
  return (
    <>
      <Launchers />
      <Link to="/test">Go to test</Link>
    </>
  );
};

export default Dashboard;
