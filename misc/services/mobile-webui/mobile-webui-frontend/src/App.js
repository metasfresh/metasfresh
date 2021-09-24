import React from 'react';
import './App.css';
import { connect } from 'react-redux';
import Header from './components/Header';
import Launchers from './components/containers/Launchers';

function App() {
  return (
    <>
      <Header appName="webUI app" />
      <Launchers />
    </>
  );
}

const mapStateToProps = (state) => {
  return state;
};

export default connect(mapStateToProps, null)(App);
