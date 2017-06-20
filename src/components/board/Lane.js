import React, { Component } from 'react';
import Card from './Card';
import { DropTarget } from 'react-dnd';
import ItemTypes from '../../constants/ItemTypes';

class Lane extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {
            caption, cards, laneId, onHover, onDrop, targetIndicator, onReject
        } = this.props;
        
        return (
            <div className="board-lane">
                <div className="board-lane-header">{caption}</div>
                <div 
                    className={
                        'board-draggable-wrapper ' +
                        (!cards.length ? 'board-draggable-placeholder ' : '')
                    }
                >
                    {!cards.length && <Card
                        index={0}
                        {...{laneId, onHover, onDrop, targetIndicator}}
                        placeholder={true} />
                    }
                    {cards.map((card, i) => <Card
                        key={i}
                        index={i}
                        {...{
                            laneId, onHover, onDrop, onReject, targetIndicator
                        }}
                        {...card} />
                    )}
                </div>
            </div>
        );
    }
}

export default Lane;
