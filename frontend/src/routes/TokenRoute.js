import React from 'react';

import { useAuth } from '../hooks/useAuth';
import history from '../services/History';
// import { loginWithToken } from '../api';

/**
 * @file Function component.
 * @module TokenRoute
 * @summary - handles the case when we authenticate directly by using a `token` without the need to supply a `username` and a `password`
 *
 * @param {object} - tokenId prop given as match.param to the /token path i.e  /token/xxxxxxx   (`xxxxxxx` will be the value of the tokenId )
 */
const TokenRoute = ({ match }) => {
  const { tokenId } = match.params;
  const auth = useAuth();
  // const { loginRequest } = auth;

  // if (loginRequest) {
  //   return null;
  //   }

  auth
    .tokenLogin(tokenId)
    .then(() => history.push('/'))
    .catch(() => history.push('/login?redirect=true'));

  // loginWithToken(tokenId)
  //   .then(() => {
  //     auth.loginSuccess();
  //     history.push('/');
  //   })
  //   .catch(() => {
  //     history.push('/login?redirect=true');
  //   });
};

export default React.memo(TokenRoute);
