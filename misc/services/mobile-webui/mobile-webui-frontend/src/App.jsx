import React from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';

import { useAuth } from './hooks/useAuth';
import useConstructor from './hooks/useConstructor';
import Routes from './routes';

import './App.css';

function App() {
  const auth = useAuth();

  useConstructor(() => {
    axios.interceptors.response.use(
      function (response) {
        return response;
      },
      function (error) {
        /*
         * Authorization error
         */
        if (error.response.status == 401) {
          const history = useHistory();

          auth.logout().finally(() => {
            history.push('/login');
          });
        } else {
          return Promise.reject(error);
        }
      }
    );
  });

  return (
    <div className="application">
      <Routes />
    </div>
  );
}

export default App;
