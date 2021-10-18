import React from 'react';
import axios from 'axios';

import { useAuth } from './hooks/useAuth';
import useConstructor from './hooks/useConstructor';
import Routes from './routes';
import { history } from './store/store';

import './App.css';
import UpdateCheck from './components/UpdateCheck';
import { UPDATE_CHECK_INTERVAL } from './constants/index';

function App() {
  const auth = useAuth();

  useConstructor(() => {
    axios.interceptors.response.use(undefined, function (error) {
      /*
       * Authorization error
       */
      if (error.response && error.response.status === 401) {
        auth.logout().finally(() => {
          history.push('/login');
        });
      } else {
        return Promise.reject(error);
      }
    });
  });

  return (
    <div className="application">
      <Routes />
      <UpdateCheck updateInterval={UPDATE_CHECK_INTERVAL} />
    </div>
  );
}

export default App;
