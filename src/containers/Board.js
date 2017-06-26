import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import update from 'immutability-helper';
import {push} from 'react-router-redux';

import {getData, patchRequest, deleteRequest} from '../actions/GenericActions';
import {connectWS, disconnectWS} from '../actions/WindowActions';
import {addCard} from '../actions/BoardActions';

import { DragDropContext } from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import Container from '../components/Container';
import BlankPage from '../components/BlankPage';
import Lanes from '../components/board/Lanes';
import Sidenav from '../components/board/Sidenav';

class Board extends Component {
    constructor(props){
        super(props);

        this.state = {
            sidenav: false,
            board: null,
            targetIndicator: {
                laneId: null,
                index: null
            },
            sidenavViewId: null
        }
    }

    componentWillMount = () => {
        this.init();
    }

    componentWillUnmount = () => {
        disconnectWS.call(this);
    }

    init = () => {
        const {boardId} = this.props;

        getData('board', boardId).then(res => {
            this.setState({
                board: res.data
            }, () => {
                connectWS.call(this, res.data.websocketEndpoint, msg => {
                    //TODO
                })
            })
        }).catch(err => {
            this.setState({
                board: 404
            })
        })
    }

    handleDrop = (card, targetLaneId) => {
        const {board} = this.state;

        this.clearTargetIndicator();

        const laneIndex = board.lanes.findIndex(l => l.laneId === targetLaneId);

        if(card.initLaneId === 0) {
            // Adding card
            addCard(
                board.boardId, targetLaneId, card.id, card.index
            ).then(res => {
                this.addCard(laneIndex, res.data);
            });
        }else{
            // Moving card
            const staleLaneIndex =
                board.lanes.findIndex(l => l.laneId === card.initLaneId);

            const staleCardIndex = board.lanes[staleLaneIndex].cards
                .findIndex(c =>
                    c.cardId === card.id
                );

            patchRequest(
                'board', board.boardId, null, null, null, 'laneId',
                targetLaneId, 'card', card.id
            ).then(res => {
                this.removeCard(staleLaneIndex, staleCardIndex);
                this.addCard(laneIndex, res.data);
            });
        }
    }

    handleHover = (card, targetLaneId, targetIndex) => {
        this.setState({
            targetIndicator: {
                laneId: targetLaneId,
                index: targetIndex
            }
        });
    }

    clearTargetIndicator = () => {
        this.setState({
            targetIndicator: {
                laneId: undefined,
                index: undefined
            }
        });
    }

    removeCard = (laneIndex, cardIndex) => {
        this.setState(prev => update(prev, {
            board: {
                lanes: {
                    [laneIndex]: {
                        cards: {$splice: [[cardIndex, 1]]}
                    }
                }
            }
        }))
    }

    addCard = (laneIndex, card) => {
        this.setState(prev => update(prev, {
            board: {
                lanes: {
                    [laneIndex]: {
                        cards: {$push: [card]}
                    }
                }
            }
        }));
    }

    handleCaptionClick = (docPath) => {
        const {dispatch} = this.props;
        if(!docPath) return;

        dispatch(push(
            '/window/' + docPath.windowId +
            (docPath.documentId ? '/' + docPath.documentId : '')
        ))
    }

    handleDelete = (laneId, cardId) => {
        const {board} = this.state;

        const laneIndex = board.lanes.findIndex(l => l.laneId === laneId);

        deleteRequest(
            'board', board.boardId, null, null, null, 'card', cardId
        ).then(() => {
            this.removeCard(
                laneIndex,
                board.lanes[laneIndex].cards
                    .findIndex(c => c.cardId === cardId));
        });
    }

    setSidenavViewId = (id) => {this.setState({sidenavViewId: id})}

    render() {
        const {
            modal, rawModal, breadcrumb, indicator, dispatch
        } = this.props;

        const {
            board, targetIndicator, sidenav, sidenavViewId
        } = this.state;

        return (
            <Container
                entity="board"
                siteName={board && board.caption}
                {...{modal, rawModal, breadcrumb, indicator}}
            >
                {sidenav && (
                    <Sidenav
                        boardId={board.boardId}
                        viewId={sidenavViewId}
                        onClickOutside={() => this.setState({sidenav: false})}
                        setViewId={this.setSidenavViewId}
                    />
                )}
                {board === 404 ?
                    <BlankPage what="Board" /> :
                    <div className="board">
                        <div
                            key="board-header"
                            className="board-header clearfix"
                        >
                            <button
                                className="btn btn-meta-outline-secondary btn-sm float-xs-right"
                                onClick={() => this.setState({sidenav: true})}
                            >
                                Add new
                            </button>
                        </div>
                        <Lanes
                            {...{targetIndicator}}
                            key="board-lanes"
                            onDrop={this.handleDrop}
                            onHover={this.handleHover}
                            onReject={this.clearTargetIndicator}
                            onDelete={this.handleDelete}
                            onCaptionClick={this.handleCaptionClick}
                            lanes={board && board.lanes}
                        />
                    </div>
                }
            </Container>
        );
    }
}

Board.propTypes = {
    modal: PropTypes.object.isRequired,
    breadcrumb: PropTypes.array.isRequired,
    dispatch: PropTypes.func.isRequired,
    rawModal: PropTypes.object.isRequired,
    indicator: PropTypes.string.isRequired
};

function mapStateToProps(state) {
    const { windowHandler, menuHandler } = state;

    const {
        modal,
        rawModal,
        indicator
    } = windowHandler || {
        modal: false,
        rawModal: {},
        indicator: ''
    }

    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: []
    }

    return {
        modal, rawModal, indicator, breadcrumb
    }
}

Board = connect(mapStateToProps)(DragDropContext(HTML5Backend)(Board))

export default Board;
