import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ItemTypes from '../../constants/ItemTypes';
import { DragSource, DropTarget } from 'react-dnd';

const cardSource = {
    beginDrag(props) {
        return {
            id: props.id,
            index: props.index
        };
    }
};

const cardTarget = {
    hover(props, monitor) {
        const dragIndex = monitor.getItem().index;
        const hoverIndex = props.index;

        // Don't replace items with themselves
        if (dragIndex === hoverIndex) {
            return;
        }

        // Time to actually perform the action
        props.moveCard(props.entity, dragIndex, hoverIndex);

        // Note: we're mutating the monitor item here!
        // Generally it's better to avoid mutations,
        // but it's good here for the sake of performance
        // to avoid expensive index searches.
        monitor.getItem().index = hoverIndex;
    }
};

function collect(connect, monitor) {
    return {
        connectDragSource: connect.dragSource(),
        isDragging: monitor.isDragging()
    };
}

function connect(connect) {
    return {
        connectDropTarget: connect.dropTarget()
    };
}

export class DndWidget extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {
            children, connectDragSource, connectDropTarget, isDragging,
            className, transparent, removeCard, entity, id, placeholder
        } = this.props;
        
        if(transparent) return <div {...{className}}>{children}</div>;

        return connectDragSource(connectDropTarget(
            <div
                className={
                    className + ' dnd-widget ' + 
                    (isDragging ? 'dragging ' : '') +
                    (placeholder ? 'dnd-placeholder ' : '')
                }
            >
                {!placeholder && <i
                    className="meta-icon-trash draggable-icon-remove pointer"
                    onClick={() => removeCard(entity, id)}
                />}
                {children}
            </div>
        ));
    }
}

DndWidget =
    DragSource(props => props.entity, cardSource, collect)(
        DropTarget(props => props.entity, cardTarget, connect)(
            DndWidget
        )
    );

export default DndWidget;
