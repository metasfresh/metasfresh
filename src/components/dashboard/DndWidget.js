import React, { Component } from 'react';
import { DragSource, DropTarget } from 'react-dnd';
import classnames from 'classnames';

const cardSource = {
  beginDrag(props) {
    return {
      id: props.id,
      index: props.index,
      isNew: props.isNew,
    };
  },
};

const cardTarget = {
  hover(props, monitor) {
    const dragIndex = monitor.getItem().index;
    const hoverIndex = props.index;

    if (dragIndex === hoverIndex || monitor.getItem().id === props.id) {
      return;
    }

    props.moveCard &&
      props.moveCard(props.entity, dragIndex, hoverIndex, monitor.getItem());
    monitor.getItem().index = hoverIndex;
  },
  drop(props, monitor) {
    if (monitor.getItem().isNew && props.addCard) {
      props.addCard(props.entity, monitor.getItem().id);
    } else {
      props.onDrop && props.onDrop(props.entity, monitor.getItem().id);
    }
  },
};

function collect(connect, monitor) {
  return {
    connectDragSource: connect.dragSource(),
    isDragging: monitor.isDragging(),
  };
}

function connect(connect) {
  return {
    connectDropTarget: connect.dropTarget(),
  };
}

export class DndWidget extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const {
      children,
      connectDragSource,
      connectDropTarget,
      isDragging,
      className,
      transparent,
      removeCard,
      entity,
      id,
      placeholder,
      index,
    } = this.props;

    if (transparent) return <div {...{ className }}>{children}</div>;

    return connectDragSource(
      connectDropTarget(
        <div
          className={classnames(className, 'dnd-widget', {
            dragging: isDragging,
            'dnd-placeholder': placeholder,
          })}
        >
          {!placeholder &&
            removeCard && (
              <i
                className="meta-icon-trash draggable-icon-remove pointer"
                onClick={() => removeCard(entity, index, id)}
              />
            )}
          {children}
        </div>
      )
    );
  }
}

export default DragSource(props => props.entity, cardSource, collect)(
  DropTarget(props => props.entity, cardTarget, connect)(DndWidget)
);
