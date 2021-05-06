import React, { PureComponent } from 'react';
import qs from 'qs';

import { createWindow } from '../actions/WindowActions';

import Board from '../containers/Board.js';
import DocList from '../containers/DocList.js';
import MasterWindow from '../containers/MasterWindow.js';

function DocListRoute({ location, match }) {
  const query = qs.parse(location.search);

  console.log('DocListRoute: ', query, location, match);

  return <DocList query={query} windowId={match.params.windowId} />;
}
function BoardRoute({ location, match }) {
  const query = qs.parse(location.search);

  return <Board query={query} boardId={match.params.boardId} />;
}

// TODO: PASS PARAMS
class MasterWindowRoute extends PureComponent {
  constructor(props) {
    super(props);

    const {
      match: { params },
      dispatch,
    } = props;

    dispatch(createWindow(params.windowId, params.docId));
  }

  render() {
    return <MasterWindow {...this.props} params={this.props.match.params} />;
  }
}

export { DocListRoute, BoardRoute, MasterWindowRoute };
