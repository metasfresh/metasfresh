import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import update from 'react-addons-update';

import {getData, patchRequest} from '../actions/GenericActions';
import {connectWS, disconnectWS} from '../actions/WindowActions';

import { DragDropContext } from 'react-dnd';
import HTML5Backend, { NativeTypes } from 'react-dnd-html5-backend';

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
            }
        }
    }
    
    componentWillMount = () => {
        this.init();
    }
    
    init = () => {
        const {boardId} = this.props;
        
        getData('board', boardId).then(res => {
            this.setState({
                board: res.data
            }, () => {
                connectWS.call(this, res.data.websocketEndpoint, msg => {
                    //TODO
                    console.log(msg);
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
        this.setState({
            targetIndicator: {
                laneId: null,
                index: null
            }
        });
        
        patchRequest(
            'board', board.boardId, null, null, null, 'laneId', targetLaneId,
            'card', card.id
        );
    }
    
    handleHover = (card, targetLaneId, targetIndex) => {
        this.setState({
            targetIndicator: {
                laneId: targetLaneId,
                index: targetIndex
            }
        });
    }

    render() {
        const {
            modal, rawModal, breadcrumb, indicator
        } = this.props;
        
        const {
            board, targetIndicator, sidenav
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
                        onClickOutside={() => this.setState({sidenav: false})}
                    />
                )}
                {board === 404 ? 
                    <BlankPage what='Board' /> : 
                    <div className='board'>
                        <div 
                            key='board-header'
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
                            key='board-lanes'
                            onDrop={this.handleDrop}
                            onHover={this.handleHover}
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
