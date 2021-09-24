import React from 'react';
import './App.css';
import { connect } from 'react-redux';
import Header from './components/Header';
import Launchers from './components/containers/Launchers';
import BarcodeScanner from './components/containers/BarcodeScanner';

function App() {
  const barcodeMockProps = {
    activityId: '1',
    caption: 'Scan picking slot',
    componentType: 'common/scanBarcode',
    readonly: true,
    componentProps: {
      barcodeCaption: null,
    },
  };

  return (
    <>
      <Header appName="webUI app" />
      <Launchers />
      <BarcodeScanner {...barcodeMockProps} />
    </>
  );
}

const mapStateToProps = (state) => {
  return state;
};

export default connect(mapStateToProps, null)(App);
