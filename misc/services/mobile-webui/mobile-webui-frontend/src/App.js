import React from 'react';
// import logo from './logo.svg';
import './App.css';
import { connect } from 'react-redux';
import Header from './components/Header';

function App(props) {
  return (
      <>
      <Header appName="webUI app" />
      </>
  );
}

const mapStateToProps = (state) => {
  return state;
};

export default connect(mapStateToProps, null)(App);
