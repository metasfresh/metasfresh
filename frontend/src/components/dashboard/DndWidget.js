import React from 'react';
import { useDrop, useDrag } from 'react-dnd';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import ItemTypes from '../../constants/ItemTypes';

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

const DndWidget = (props) => {
  const {
    children,
    className,
    transparent,
    removeCard,
    entity,
    id,
    placeholder,
    index,
    isNew,
  } = props;

  const [{ isDragging }, connectDragSource] = useDrag({
    type: ItemTypes.WIDGET,
    item: { id, index, isNew },
  });
  const [, connectDropTarget] = useDrop({
    ...cardTarget,
    accept: ItemTypes.WIDGET,
  });

  if (transparent) return <div {...{ className }}>{children}</div>;

  return connectDragSource(
    connectDropTarget(
      <div
        className={classnames(className, 'dnd-widget', {
          dragging: isDragging,
          'dnd-placeholder': placeholder,
        })}
      >
        {!placeholder && removeCard && (
          <i
            className="meta-icon-trash draggable-icon-remove pointer"
            onClick={() => removeCard(entity, index, id)}
          />
        )}
        {children}
      </div>
    )
  );
};

DndWidget.propTypes = {
  children: PropTypes.any,
  className: PropTypes.string,
  transparent: PropTypes.bool,
  removeCard: PropTypes.func,
  entity: PropTypes.any,
  id: PropTypes.number,
  placeholder: PropTypes.string,
  index: PropTypes.number,
  isDragging: PropTypes.any,
  isNew: PropTypes.bool,
};

export default DndWidget;
