import React from 'react';
import { Link, useHistory } from 'react-router-dom';

import { useAuth } from '../hooks/useAuth';

const Dashboard = () => {
  const auth = useAuth();
  const history = useHistory();

  return (
    <div>
      <h1>Authenticated !!</h1>
      <ul>
        <li>
          <Link to="/test">Go to test</Link>
        </li>
        <li>
          <button
            onClick={() => {
              auth.logout().then(() => history.push("/"));
            }}
          >
          Log out
          </button>
        </li>
      </ul>
      
    </div>
  )
}

export default Dashboard;
