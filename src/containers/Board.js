import update from 'immutability-helper';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { DragDropContext } from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import { connect } from 'react-redux';

import { addCard } from '../actions/BoardActions';

import {
  deleteRequest,
  getData,
  getRequest,
  patchRequest,
} from '../actions/GenericActions';

import { connectWS, disconnectWS } from '../actions/WindowActions';
import BlankPage from '../components/BlankPage';
import Container from '../components/Container';
import Lanes from '../components/board/Lanes';
import Sidenav from '../components/board/Sidenav';

class Board extends Component {
  constructor(props) {
    super(props);

    this.state = {
      sidenav: false,
      board: null,
      targetIndicator: {
        laneId: null,
        index: null,
      },
      sidenavViewId: null,
    };
  }

  componentWillMount = () => {
    this.init();
  };

  componentWillUnmount = () => {
    disconnectWS.call(this);
  };

  laneCardsChanged = event => {
    const { board } = this.state;
    const { laneId, cardIds } = event;
    const laneIndex = board.lanes.findIndex(lane => lane.laneId === laneId);

    const prom = Promise.all(
      cardIds.map(id => getRequest('board', board.boardId, 'card', id))
    );

    prom.then(res => {
      const cards = res.map(item => item.data);
      this.addCards(laneIndex, cards);
    });
  };

  init = () => {
    const { boardId } = this.props;

    getData('board', boardId)
      .then(res => {
        this.setState(
          {
            board: res.data,
          },
          () => {
            connectWS.call(this, res.data.websocketEndpoint, msg => {
              msg.events.map(event => {
                switch (event.changeType) {
                  case 'laneCardsChanged':
                    this.laneCardsChanged(event);
                    break;
                }
              });
            });
          }
        );
      })
      .catch(() => {
        this.setState({
          board: 404,
        });
      });
  };

  handleDrop = (card, targetLaneId) => {
    const { board } = this.state;
    this.clearTargetIndicator();

    if (card.initLaneId === 0) {
      // Adding card
      addCard(board.boardId, targetLaneId, card.id, card.index);

      if (this.sideNav) {
        this.sideNav.removeCard(card.id);
      }
    } else {
      // Moving card
      if (card.initLaneId === targetLaneId) {
        //Changing position
        patchRequest({
          entity: 'board',
          docType: board.boardId,
          property: 'position',
          value: card.index,
          subentity: 'card',
          subentityId: card.id,
        });
      } else {
        //Changing lane at least
        patchRequest({
          entity: 'board',
          docType: board.boardId,
          property: ['laneId', 'position'],
          value: [targetLaneId, card.index],
          subentity: 'card',
          subentityId: card.id,
        });
      }
    }
  };

  handleHover = (card, targetLaneId, targetIndex) => {
    this.setState({
      targetIndicator: {
        laneId: targetLaneId,
        index: targetIndex,
      },
    });
  };

  clearTargetIndicator = () => {
    this.setState({
      targetIndicator: {
        laneId: undefined,
        index: undefined,
      },
    });
  };

  removeCard = (laneIndex, cardIndex) => {
    this.setState(prev =>
      update(prev, {
        board: {
          lanes: {
            [laneIndex]: {
              cards: { $splice: [[cardIndex, 1]] },
            },
          },
        },
      })
    );
  };

  addCards = (laneIndex, cards) => {
    this.setState(prev =>
      update(prev, {
        board: {
          lanes: {
            [laneIndex]: {
              cards: { $set: cards },
            },
          },
        },
      })
    );
  };

  handleCaptionClick = docPath => {
    if (!docPath) return;

    const url =
      '/window/' +
      docPath.windowId +
      (docPath.documentId ? '/' + docPath.documentId : '');

    window.open(url, '_blank');
  };

  handleDelete = (laneId, cardId) => {
    const { board } = this.state;

    const laneIndex = board.lanes.findIndex(l => l.laneId === laneId);

    deleteRequest(
      'board',
      board.boardId,
      null,
      null,
      null,
      'card',
      cardId
    ).then(() => {
      this.removeCard(
        laneIndex,
        board.lanes[laneIndex].cards.findIndex(c => c.cardId === cardId)
      );
    });
  };

  setSidenavViewId = id => {
    this.setState({ sidenavViewId: id });
  };

  render() {
    const { modal, rawModal, breadcrumb, indicator } = this.props;

    const { board, targetIndicator, sidenav, sidenavViewId } = this.state;

    return (
      <Container
        entity="board"
        siteName={board && board.caption}
        windowType={board && board.boardId && String(board.boardId)}
        {...{ modal, rawModal, breadcrumb, indicator }}
      >
        {sidenav && (
          <Sidenav
            ref={c => (this.sideNav = c && c.refs && c.refs.instance)}
            boardId={board.boardId}
            viewId={sidenavViewId}
            onClickOutside={() => this.setState({ sidenav: false })}
            setViewId={this.setSidenavViewId}
          />
        )}
        {board === 404 ? (
          <BlankPage what="Board" />
        ) : (
          <div className="board">
            <div key="board-header" className="board-header clearfix">
              <button
                className="btn btn-meta-outline-secondary btn-sm float-xs-right"
                onClick={() => this.setState({ sidenav: true })}
              >
                Add new
              </button>
            </div>
            <Lanes
              {...{ targetIndicator }}
              key="board-lanes"
              onDrop={this.handleDrop}
              onHover={this.handleHover}
              onReject={this.clearTargetIndicator}
              onDelete={this.handleDelete}
              onCaptionClick={this.handleCaptionClick}
              lanes={board && board.lanes}
            />
          </div>
        )}
      </Container>
    );
  }
}

Board.propTypes = {
  modal: PropTypes.object.isRequired,
  breadcrumb: PropTypes.array.isRequired,
  dispatch: PropTypes.func.isRequired,
  rawModal: PropTypes.object.isRequired,
  indicator: PropTypes.string.isRequired,
};

function mapStateToProps(state) {
  const { windowHandler, menuHandler } = state;

  const { modal, rawModal, indicator } = windowHandler || {
    modal: false,
    rawModal: {},
    indicator: '',
  };

  const { breadcrumb } = menuHandler || {
    breadcrumb: [],
  };

  return {
    modal,
    rawModal,
    indicator,
    breadcrumb,
  };
}

export default connect(mapStateToProps)(DragDropContext(HTML5Backend)(Board));
