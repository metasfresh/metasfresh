import React, { useEffect } from 'react';
import queryString from 'query-string';
import _ from 'lodash';
import { useDispatch } from 'react-redux';
import PropTypes from 'prop-types';
import { createWindow } from '../actions/WindowActions';

import Board from '../containers/Board.js';
import DocList from '../containers/DocList.js';
import MasterWindow from '../containers/MasterWindow.js';

/**
 * @file Module holding routes that required to be a bit more specific, than the
 * built-in react-router components.
 * @module routes/KeyRoutes
 */

function propsAreEqual(prevProps, nextProps) {
  const { match, location } = prevProps;
  const { match: nextMatch, location: nextLocation } = nextProps;

  if (_.isEqual(match, nextMatch) && _.isEqual(location, nextLocation)) {
    return true;
  }

  return false;
}

function queriesAreEqual(prevProps, nextProps) {
  const { query, windowId } = prevProps;
  const { query: nextQuery, windowId: nextWindowId } = nextProps;

  if (_.isEqual(query, nextQuery) && windowId === nextWindowId) {
    return true;
  }

  return false;
}

/**
 * @file Functional component.
 * @module KeyRoutes/RawDocListRoute
 * @description Route for the document's lists
 */
const RawDocListRoute = ({ location, match }) => {
  const { search, pathname } = location;
  const { params } = match;
  const query = queryString.parse(search, {
    ignoreQueryPrefix: true,
  });

  return (
    <DocList query={query} windowId={params.windowId} pathname={pathname} />
  );
};

RawDocListRoute.propTypes = {
  location: PropTypes.object,
  match: PropTypes.object,
};

const DocListRoute = React.memo(RawDocListRoute, queriesAreEqual);

/**
 * @file Functional component.
 * @module KeyRoutes/BoardRoute
 * @discription Dashboard route
 */
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

/**
 * @file Functional component.
 * @module KeyRoutes/MasterWindowRoute
 * @description Route for the details view
 */
const RawMasterWindowRoute = (props) => {
  const dispatch = useDispatch();
  const { match } = props;

  useEffect(() => {
    const {
      match: { params },
    } = props;

    dispatch(createWindow({ windowId: params.windowId, docId: params.docId }));
  }, [match]);

  return <MasterWindow {...props} params={match.params} />;
};

RawMasterWindowRoute.propTypes = {
  location: PropTypes.object,
  match: PropTypes.object,
};

const MasterWindowRoute = React.memo(RawMasterWindowRoute, propsAreEqual);

export { DocListRoute, BoardRoute, MasterWindowRoute };
