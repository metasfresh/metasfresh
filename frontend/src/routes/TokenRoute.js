import React from 'react';
import { isEqual } from 'lodash';
import PropTypes from 'prop-types';

import { useAuth } from '../hooks/useAuth';
import history from '../services/History';

/**
 * @file Function component.
 * @module TokenRoute
 * @summary - handles the case when we authenticate directly by using a `token` without the need to supply a `username` and a `password`
 *
 * @param {string} - tokenId prop given as match.param to the /token path i.e  /token/xxxxxxx   (`xxxxxxx` will be the value of the tokenId )
 */
const TokenRoute = (props) => {
  const { tokenId } = props.match.params;
  const auth = useAuth();

  auth
    .tokenLogin(tokenId)
    .then(() => history.push('/'))
    .catch(() => history.push('/login?redirect=true'));

  return null;
};

function propsAreEqual(prevProps, nextProps) {
  const { match } = prevProps;
  const { match: nextMatch } = nextProps;

  if (isEqual(match, nextMatch)) {
    return true;
  }

  return false;
}

TokenRoute.propTypes = {
  tokenId: PropTypes.string,
};

export default React.memo(TokenRoute, propsAreEqual);
