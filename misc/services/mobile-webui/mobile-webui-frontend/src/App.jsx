import React from 'react';
import './App.css';
import { connect } from 'react-redux';
import Header from './components/Header';
import Launchers from './components/containers/Launchers';
import WFProcess from './components/containers/WFProcess';
import PropTypes from 'prop-types';
function App(props) {
  return (
    <>
      <Header appName="webUI app" />
      <Launchers />
      <WFProcess
        wfProcessId="picking-7f42317d-0782-466c-a192-cb5ad7d3cce0"
        {...props.wfProcesses['picking-7f42317d-0782-466c-a192-cb5ad7d3cce0']}
        status={props.wfProcesses_status['picking-7f42317d-0782-466c-a192-cb5ad7d3cce0']}
      />
    </>
  );
}

App.propTypes = {
  wfProcesses: PropTypes.object,
  wfProcesses_status: PropTypes.object,
};

const mapStateToProps = (state) => {
  return state;
};

export default connect(mapStateToProps, null)(App);
