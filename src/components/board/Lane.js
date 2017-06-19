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
            caption, cards, laneId, onHover, onDrop, targetIndicator
        } = this.props;
        
        return (
            <div className="board-lane">
                <div className="board-lane-header">{caption}</div>
                {cards.map((card, i) => <Card
                    key={i}
                    index={i}
                    {...{laneId, onHover, onDrop, targetIndicator}}
                    {...card} />
                )}
            </div>
        );
    }
}

export default Lane;
