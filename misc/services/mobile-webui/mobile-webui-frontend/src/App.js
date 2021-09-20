import React from 'react';
// import logo from './logo.svg';
import './App.css';
import { connect } from 'react-redux';

function App(props) {
  return (
    <div className="App">
      <header className="App-header">
         WebUI for Mobile
      </header>
    </div>
  );
}

const mapStateToProps = (state) => {
  return state;
};

export default connect(mapStateToProps, null)(App);
