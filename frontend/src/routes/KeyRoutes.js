import React, { useEffect } from 'react';
import queryString from 'query-string';
import _ from 'lodash';
import { useDispatch } from 'react-redux';

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

/**
 * @file Functional component.
 * @module KeyRoutes/RawDocListRoute
 * @description Route for the document's lists
 */
const RawDocListRoute = ({ location, match }) => {
  const { search } = location;
  const { params } = match;
  const query = queryString.parse(search);

  return <DocList query={query} windowId={params.windowId} />;
};
const DocListRoute = React.memo(RawDocListRoute, propsAreEqual);

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

/**
 * @file Functional component.
 * @module KeyRoutes/MasterWindowRoute
 * @description Route for the details view
 */
const RawMasterWindowRoute = (props) => {
  const dispatch = useDispatch();
  const { match, location } = props;

  useEffect(() => {
    const {
      match: { params },
    } = props;

    dispatch(createWindow({ windowId: params.windowId, docId: params.docId }));
  }, [match]);

  return <MasterWindow {...props} params={match.params} />;
};
const MasterWindowRoute = React.memo(RawMasterWindowRoute, propsAreEqual);

export { DocListRoute, BoardRoute, MasterWindowRoute };
