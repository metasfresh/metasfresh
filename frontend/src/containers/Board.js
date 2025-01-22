import update from 'immutability-helper';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import { connect } from 'react-redux';

import { getData, deleteRequest, patchRequest } from '../api';
import { addCard } from '../actions/BoardActions';
import { getRequest } from '../actions/GenericActions';
import { connectWS, disconnectWS } from '../utils/websockets';

import { BlankPage } from '../components/BlankPage';
import Container from '../components/Container';
import Lanes from '../components/board/Lanes';
import Sidenav from '../components/board/Sidenav';

/**
 * @file Class based component.
 * @module Board
 * @extends Component
 */
class Board extends Component {
  constructor(props) {
    super(props);

    this.state = {
      sidenav: false,
      board: props.board || null,
      targetIndicator: {
        laneId: null,
        index: null,
      },
      sidenavViewId: null,
    };
  }

  UNSAFE_componentWillMount = () => {
    this.init();
  };

  componentWillUnmount = () => {
    disconnectWS.call(this);
  };

  /**
   * @method laneCardsChanged
   * @summary ToDo: Describe the method
   * @param {object} event
   */
  laneCardsChanged = (event) => {
    const { board } = this.state;
    const { laneId, cardIds } = event;
    const laneIndex = board.lanes.findIndex((lane) => lane.laneId === laneId);

    const prom = Promise.all(
      cardIds.map((id) => getRequest('board', board.boardId, 'card', id))
    );

    prom.then((res) => {
      const cards = res.map((item) => item.data);
      this.addCards(laneIndex, cards);
    });

    // refresh the view once the ws event is received
    this.sideNav && this.sideNav.instanceRef.refreshView(board.boardId);
  };

  /**
   * @method init
   * @summary ToDo: Describe the method
   */
  init = () => {
    const { boardId } = this.props;
    // in case it's a 404 page because we couldn't match any url
    const { board } = this.state;

    if (!board) {
      getData({
        entity: 'board',
        docType: boardId,
      })
        .then((res) => {
          this.setState(
            {
              board: res.data,
            },
            () => {
              connectWS.call(this, res.data.websocketEndpoint, (msg) => {
                msg.events.map((event) => {
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
            board: '404',
          });
        });
    }
  };

  /**
   * @method handleDrop
   * @summary ToDo: Describe the method
   * @param {*} card
   * @param {*} targetLaneId
   */
  handleDrop = (card, targetLaneId) => {
    const { board } = this.state;
    this.clearTargetIndicator();

    if (card.initLaneId === 0) {
      // Adding card
      addCard(board.boardId, targetLaneId, card.id, card.index);
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

  /**
   * @method handleHover
   * @summary ToDo: Describe the method
   * @param {*} card
   * @param {*} targetLaneId
   * @param {*} targetIndex
   */
  handleHover = (card, targetLaneId, targetIndex) => {
    this.setState({
      targetIndicator: {
        laneId: targetLaneId,
        index: targetIndex,
      },
    });
  };

  /**
   * @method clearTargetIndicator
   * @summary ToDo: Describe the method
   */
  clearTargetIndicator = () => {
    this.setState({
      targetIndicator: {
        laneId: undefined,
        index: undefined,
      },
    });
  };

  /**
   * @method removeCard
   * @summary ToDo: Describe the method
   * @param {*} laneIndex
   * @param {*} cardIndex
   */
  removeCard = (laneIndex, cardIndex) => {
    this.setState((prev) =>
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

  /**
   * @method addCards
   * @summary ToDo: Describe the method
   * @param {*} laneIndex
   * @param {*} cards
   */
  addCards = (laneIndex, cards) => {
    this.setState((prev) =>
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

  /**
   * @method handleCaptionClick
   * @summary ToDo: Describe the method
   * @param {*} docPath
   */
  handleCaptionClick = (docPath) => {
    if (!docPath) return;

    const url =
      '/window/' +
      docPath.windowId +
      (docPath.documentId ? '/' + docPath.documentId : '');

    window.open(url, '_blank');
  };

  /**
   * @method handleDelete
   * @summary ToDo: Describe the method
   * @param {*} laneId
   * @param {*} cardId
   */
  handleDelete = (laneId, cardId) => {
    const { board } = this.state;

    const laneIndex = board.lanes.findIndex((l) => l.laneId === laneId);

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
        board.lanes[laneIndex].cards.findIndex((c) => c.cardId === cardId)
      );
    });
  };

  /**
   * @method setSidenavViewId
   * @summary ToDo: Describe the method
   * @param {*} id
   */
  setSidenavViewId = (id) => {
    this.setState({ sidenavViewId: id });
  };

  render() {
    const { modal, rawModal, pluginModal, breadcrumb, indicator } = this.props;
    const { board, targetIndicator, sidenav, sidenavViewId } = this.state;

    return (
      <DndProvider backend={HTML5Backend}>
        <Container
          entity="board"
          siteName={board && board.caption}
          windowId={board && board.boardId && String(board.boardId)}
          {...{ modal, rawModal, pluginModal, breadcrumb, indicator }}
        >
          {sidenav && (
            <Sidenav
              ref={(c) => (this.sideNav = c)}
              boardId={board.boardId}
              viewId={sidenavViewId}
              onClickOutside={() => this.setState({ sidenav: false })}
              setViewId={this.setSidenavViewId}
            />
          )}
          {board === '404' ? (
            <BlankPage what="Board" />
          ) : (
            <div className="board">
              <div key="board-header" className="board-header clearfix">
                <button
                  className="btn btn-meta-outline-secondary btn-sm float-right"
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
      </DndProvider>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {object} modal
 * @prop {array} breadcrumb
 * @prop {func} dispatch
 * @prop {object} rawModal
 * @prop {string} indicator
 * @prop {object} [pluginModal]
 * @prop {*} [updateDocList]
 * @prop {*} [boardId]
 */
Board.propTypes = {
  modal: PropTypes.object.isRequired,
  breadcrumb: PropTypes.array.isRequired,
  dispatch: PropTypes.func.isRequired,
  rawModal: PropTypes.object.isRequired,
  indicator: PropTypes.string.isRequired,
  pluginModal: PropTypes.object,
  boardId: PropTypes.any,
  board: PropTypes.string,
};

/**
 * @method mapStateToProps
 * @summary ToDo: Describe the method
 * @param {object} state
 */
function mapStateToProps(state) {
  const { windowHandler, menuHandler } = state;

  const { modal, rawModal, pluginModal, indicator } = windowHandler || {
    modal: false,
    rawModal: {},
    pluginModal: {},
    indicator: '',
  };

  const { breadcrumb } = menuHandler || {
    breadcrumb: [],
  };

  return {
    modal,
    rawModal,
    pluginModal,
    indicator,
    breadcrumb,
  };
}

export default connect(mapStateToProps)(Board);
