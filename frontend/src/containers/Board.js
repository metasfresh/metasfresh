import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import { shallowEqual, useSelector } from 'react-redux';

import { deleteRequest, getData, patchRequest } from '../api';
import { addCard } from '../actions/BoardActions';
import { getRequest } from '../actions/GenericActions';

import { BlankPage } from '../components/BlankPage';
import Container from '../components/Container';
import Lanes from '../components/board/Lanes';
import Sidenav from '../components/board/Sidenav';
import { useWebsocket } from '../hooks/useWebsocket';

const Board = ({ boardId }) => {
  const sideNavRef = useRef();
  const [isSideNavDisplayed, setIsSideNavDisplayed] = useState(false);
  const { board, setBoard } = useBoardData({ boardId });
  const [targetIndicator, setTargetIndicator] = useState({
    laneId: null,
    index: null,
  });
  const [sidenavViewId, setSidenavViewId] = useState(null);

  const { modal, rawModal, pluginModal, indicator, breadcrumb } = useSelector(
    (state) => mapStateToProps(state),
    shallowEqual
  );

  useWebsocket({
    topic: board?.websocketEndpoint,
    onMessage: (msg) => {
      msg.event.events.map((event) => {
        switch (event.changeType) {
          case 'laneCardsChanged':
            laneCardsChanged(event);
            break;
        }
      });
    },
  });

  const laneCardsChanged = (event) => {
    const { laneId, cardIds } = event;
    const laneIndex = board.lanes.findIndex((lane) => lane.laneId === laneId);

    const prom = Promise.all(
      cardIds.map((id) => getRequest('board', board.boardId, 'card', id))
    );

    prom.then((res) => {
      const cards = res.map((item) => item.data);
      addCards(laneIndex, cards);
    });

    // refresh the view once the ws event is received
    sideNavRef.current?.instanceRef?.refreshView?.(board.boardId);
  };

  const handleDrop = (card, targetLaneId) => {
    clearTargetIndicator();

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

  const handleHover = (card, targetLaneId, targetIndex) => {
    setTargetIndicator({
      laneId: targetLaneId,
      index: targetIndex,
    });
  };

  const clearTargetIndicator = () => {
    setTargetIndicator({
      laneId: undefined,
      index: undefined,
    });
  };

  const removeCard = (laneIndex, cardIndex) => {
    setBoard((prevBoard) => ({
      ...prevBoard,
      lanes: prevBoard.lanes.map((lane, lIndex) => {
        return lIndex !== laneIndex
          ? lane
          : {
              ...lane,
              cards: lane.cards.filter((_, cIndex) => cIndex !== cardIndex),
            };
      }),
    }));
  };

  const addCards = (laneIndex, cards) => {
    setBoard((prevBoard) => ({
      ...prevBoard,
      lanes: prevBoard.lanes.map((lane, lIndex) => {
        return lIndex !== laneIndex ? lane : { ...lane, cards: cards };
      }),
    }));
  };

  const handleCaptionClick = (docPath) => {
    if (!docPath) return;

    const url =
      '/window/' +
      docPath.windowId +
      (docPath.documentId ? '/' + docPath.documentId : '');

    window.open(url, '_blank');
  };

  const handleDelete = (laneId, cardId) => {
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
      removeCard(
        laneIndex,
        board.lanes[laneIndex].cards.findIndex((c) => c.cardId === cardId)
      );
    });
  };

  return (
    <DndProvider backend={HTML5Backend}>
      <Container
        entity="board"
        siteName={board && board.caption}
        windowId={board && board.boardId && String(board.boardId)}
        {...{ modal, rawModal, pluginModal, breadcrumb, indicator }}
      >
        {isSideNavDisplayed && (
          <Sidenav
            ref={sideNavRef}
            boardId={board.boardId}
            viewId={sidenavViewId}
            onClickOutside={() => setIsSideNavDisplayed(false)}
            setViewId={setSidenavViewId}
          />
        )}
        {board === '404' ? (
          <BlankPage what="Board" />
        ) : (
          <div className="board">
            <div key="board-header" className="board-header clearfix">
              <button
                className="btn btn-meta-outline-secondary btn-sm float-right"
                onClick={() => setIsSideNavDisplayed(true)}
              >
                Add new
              </button>
            </div>
            <Lanes
              {...{ targetIndicator }}
              key="board-lanes"
              onDrop={handleDrop}
              onHover={handleHover}
              onReject={clearTargetIndicator}
              onDelete={handleDelete}
              onCaptionClick={handleCaptionClick}
              lanes={board && board.lanes}
            />
          </div>
        )}
      </Container>
    </DndProvider>
  );
};

Board.propTypes = {
  boardId: PropTypes.any,
};

export default Board;

//
//
//
//
//

const mapStateToProps = (state) => {
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
};

//
//
//
//
//

const useBoardData = ({ boardId }) => {
  const [board, setBoard] = useState(null);

  useEffect(() => {
    if (!boardId) {
      return;
    }

    getData({
      entity: 'board',
      docType: boardId,
    })
      .then((res) => setBoard(res.data))
      .catch(() => setBoard('404'));
  }, [boardId]);

  return { board, setBoard };
};
