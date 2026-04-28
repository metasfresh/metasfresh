import React from 'react';
import PropTypes from 'prop-types';
import queryString from 'query-string';

import Board from '../containers/Board.js';

function BoardRoute({ location, match }) {
  const { search } = location;
  const { params } = match;
  const query = queryString.parse(search);

  return <Board query={query} boardId={params.boardId} />;
}

BoardRoute.propTypes = {
  location: PropTypes.object,
  match: PropTypes.object,
};

export default BoardRoute;
