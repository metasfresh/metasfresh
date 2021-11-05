import React, { useEffect } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { push } from 'connected-react-router';

import { useAuth } from './hooks/useAuth';
import useConstructor from './hooks/useConstructor';
import Routes from './routes';
// import { history } from './store/store';

import './App.css';
import UpdateCheck from './components/UpdateCheck';
import { getApplications } from './api/applications';
import { populateApplications } from './actions/ApplicationsActions';

function App() {
  const auth = useAuth();
  const dispatch = useDispatch();
  const token = useSelector((state) => state.appHandler.token);

  useConstructor(() => {
    axios.interceptors.response.use(undefined, function (error) {
      /*
       * Authorization error
       */
      if (error.response && error.response.status === 401) {
        auth.logout().finally(() => {
          dispatch(push('/login'));
        });
      } else {
        return Promise.reject(error);
      }
    });
  });

  useEffect(() => {
    if (token) {
      getApplications().then(({ applications }) => {
        dispatch(populateApplications({ applications }));
      });
    }
  }, [token]);

  return (
    <div className="application">
      <Routes />
      <UpdateCheck />
    </div>
  );
}

export default App;
