import React, { Component } from 'react';
import Avatar from '../app/Avatar';
import { DragSource, DropTarget } from 'react-dnd';
import ItemTypes from '../../constants/ItemTypes';


const cardTarget = {
    drop(props, monitor) {
        props.onDrop && props.onDrop(monitor.getItem(), props.laneId);
    },
    hover(props, monitor) {
        if(
            !props.onHover
        ){
            return;
        }
        
        if(
             monitor.getItem().index === props.index &&
             props.laneId === monitor.getItem().laneId
        ){
            return;
        }

        props.onHover(monitor.getItem(), props.laneId, props.index);
        
        monitor.getItem().index = props.index;
        monitor.getItem().laneId = props.laneId;
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
            id: props.cardId,
            index: props.index,
            initLaneId: props.laneId,
            laneId: props.laneId
        };
    },
    endDrag(props) {
        props.onReject && props.onReject();
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
    
    renderCard = () => {
        const {
            index, caption, description, users, placeholder, connectDragSource,
            connectDropTarget, onDelete
        } = this.props;
        
        if(placeholder){
            return connectDropTarget(<div className="card-placeholder" />);
        }else{
            return connectDragSource(connectDropTarget(
                <div className="card">
                    <i
                        className="meta-icon-close-1 float-xs-right"
                        onClick={onDelete}
                    />
                    <b>{caption}</b>
                    <p>{description}</p>
                    <span className="tag tag-primary">asd</span>
                    {users.map((user, i) =>
                        <Avatar 
                            key={i}
                            id={user.avatarId}
                            className="float-xs-right"
                            size="sm"
                            title={user.fullname}
                        />
                    )}
                </div>
            ));
        }
    } 

    render() {
        const {
            targetIndicator, index, laneId
        } = this.props;
                
        return (
            <div>
                {targetIndicator && <TargetIndicator
                    {...targetIndicator}
                    parentIndex={index}
                    parentLaneId={laneId}
                />}
                {this.renderCard()}
            </div>
        );
    }
}

Card = DragSource(ItemTypes.CARD, cardSource, collect)(
    DropTarget(ItemTypes.CARD, cardTarget, connect)(
        Card
    ));

export default Card;
