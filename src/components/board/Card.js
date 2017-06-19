import React, { Component } from 'react';
import Avatar from '../app/Avatar';
import { DragSource, DropTarget } from 'react-dnd';
import ItemTypes from '../../constants/ItemTypes';


const cardTarget = {
    drop(props, monitor) {
        props.onDrop(monitor.getItem(), props.laneId);
    },
    hover(props, monitor) {
        const hoverIndex = props.index;
        const dragIndex = monitor.getItem().index;
        
        if(dragIndex === hoverIndex){
            return;
        }
        
        props.onHover(monitor.getItem(), props.laneId, props.index);
        
        monitor.getItem().index = hoverIndex;
    }
};

function connect(connect) {
    return {
        connectDropTarget: connect.dropTarget()
    };
}

const cardSource = {
    beginDrag(props) {
        return {
            id: props.id,
            index: props.index,
            laneId: props.laneId
        };
    }
};

function collect(connect, monitor) {
    return {
        connectDragSource: connect.dragSource(),
        isDragging: monitor.isDragging()
    };
}

const TargetIndicator = (props) => {
    const {index, laneId, parentIndex, parentLaneId} = props;
    if(laneId !== parentLaneId || index !== parentIndex) return false;
    return (<div className='lane-card-placeholder' />);
}

class Card extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {
            connectDragSource, connectDropTarget, targetIndicator, index, laneId
        } = this.props;
        
        return connectDragSource(connectDropTarget(
            <div>
                <TargetIndicator
                    {...targetIndicator}
                    parentIndex={index}
                    parentLaneId={laneId}
                />
                <div className='card'>
                    <b>Card</b>
                    <p>Type description</p>
                    <span className='tag tag-primary'>asd</span>
                    <Avatar className='float-xs-right' size="sm" />
                </div>
            </div>
        ));
    }
}

Card = DragSource(ItemTypes.CARD, cardSource, collect)(
    DropTarget(ItemTypes.CARD, cardTarget, connect)(
        Card
    ));

export default Card;
