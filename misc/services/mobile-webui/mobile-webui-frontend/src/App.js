import React from 'react';
// import logo from './logo.svg';
import './App.css';
import { connect } from 'react-redux';
import Header from './components/Header';
import Launchers from './components/containers/Launchers';

function App(props) {
  return (
      <>
      <Header appName="webUI app" />
      <div></div>
      <Launchers />
      </>
  );
}

const mapStateToProps = (state) => {
  return state;
};

export default connect(mapStateToProps, null)(App);
