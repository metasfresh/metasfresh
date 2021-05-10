import React, { PureComponent } from 'react';
import qs from 'qs';
import _ from 'lodash';

import { createWindow } from '../actions/WindowActions';

import Board from '../containers/Board.js';
import DocList from '../containers/DocList.js';
import MasterWindow from '../containers/MasterWindow.js';

function propsAreEqual(prevProps, nextProps) {
  const { match, location } = prevProps;
  const { match: nextMatch, location: nextLocation } = nextProps;

  if (_.isEqual(match, nextMatch) && _.isEqual(location, nextLocation)) {
    return true;
  }

  return false;
}

const RawDocListRoute = ({ location, match }) => {
  const { search } = location;
  const { params } = match;
  const query = qs.parse(search);

  return <DocList query={query} windowId={params.windowId} />;
};
const DocListRoute = React.memo(RawDocListRoute, propsAreEqual);

function BoardRoute({ location, match }) {
  const { search } = location;
  const { params } = match;
  const query = qs.parse(search);

  return <Board query={query} boardId={params.boardId} />;
}

class MasterWindowRoute extends PureComponent {
  constructor(props) {
    super(props);

    const {
      match: { params },
      dispatch,
    } = props;

    dispatch(createWindow({ windowId: params.windowId, docId: params.docId }));
  }

  render() {
    return <MasterWindow {...this.props} params={this.props.match.params} />;
  }
}

export { DocListRoute, BoardRoute, MasterWindowRoute };
