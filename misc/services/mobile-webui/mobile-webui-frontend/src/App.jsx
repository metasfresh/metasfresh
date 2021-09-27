import React from 'react';
import axios from 'axios';
import { useStore } from 'react-redux';

import { ProvideAuth } from './hooks/useAuth';
import Routes from './routes';

// import logo from './logo.svg';
import './App.css';

function App() {
  const store = useStore();

  (function () {
    const token = store.getState().appHandler.token;

    if (token) {
      axios.defaults.headers.common['Authorization'] = token;
    } else {
      axios.defaults.headers.common['Authorization'] = null;
      /*if setting null does not remove `Authorization` header then try     
        delete axios.defaults.headers.common['Authorization'];
      */
    }
  })();

  return (
    <ProvideAuth>
      <Routes />
    </ProvideAuth>
  );
}

export default App;
