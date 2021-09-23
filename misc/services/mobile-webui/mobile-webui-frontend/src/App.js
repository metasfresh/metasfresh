import React from 'react';
import { connect, useStore } from 'react-redux';

import { ProvideAuth } from './hooks/useAuth';
import Routes from './routes';
import { mockServer } from './services/server';

// import logo from './logo.svg';
import './App.css';

function App(props) {
  const store = useStore();

  // (function() {
  //      const token = store.getState().session.token;
  //      if (token) {
  //          axios.defaults.headers.common['Authorization'] = token;
  //      } else {
  //          axios.defaults.headers.common['Authorization'] = null;
  //          /*if setting null does not remove `Authorization` header then try     
  //            delete axios.defaults.headers.common['Authorization'];
  //          */
  //      }
  // })();

  return (
    <ProvideAuth>
      <Routes />
    </ProvideAuth>
  );
}

export default App;
