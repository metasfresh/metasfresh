import React from 'react';
import { Link } from 'react-router-dom';

import Header from '../components/Header';
import Launchers from '../containers/Launchers';
import BarcodeScanner from './containers/BarcodeScanner';
import ConfirmActivity from './containers/ConfirmActivity';

const barcodeMockProps = {
  activityId: '1',
  caption: 'Scan picking slot',
  componentType: 'common/scanBarcode',
  readonly: true,
  componentProps: {
    barcodeCaption: null,
  },
};

const confirmActivityProps = {
  activityId: '3',
  caption: 'Complete picking',
  componentType: 'common/confirmButton',
  readonly: true,
  componentProps: {
    question: 'Are you sure?',
  },
};

const Dashboard = () => {
  return (
    <>
      <Header appName="webUI app" />
      <Launchers />
      <BarcodeScanner {...barcodeMockProps} />
      <ConfirmActivity {...confirmActivityProps} />
      <Link to="/test">Go to test</Link>
    </>
  );
};

export default Dashboard;
