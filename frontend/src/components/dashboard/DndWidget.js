import React from 'react';
import { useDrag, useDrop } from 'react-dnd';
import PropTypes from 'prop-types';
import classnames from 'classnames';

const DndWidget = (props) => {
  const {
    id,
    index,
    entity,
    isNew,
    transparent,
    placeholder,
    className,
    //
    children,
    //
    onDrop,
    onRemove,
  } = props;

  if (transparent) return <div {...{ className }}>{children}</div>;

  //
  // dragging
  let isDragging, connectDragSource;
  if (!placeholder) {
    [{ isDragging }, connectDragSource] = useDrag({
      type: entity,
      item: { id, index, entity, isNew: !!isNew },
    });
  } else {
    isDragging = false;
    connectDragSource = (element) => element;
  }

  //
  // dropping
  let connectDropTarget;
  if (!isNew && onDrop) {
    [, connectDropTarget] = useDrop({
      accept: entity,
      drop: (item) =>
        onDrop({
          entity: item.entity,
          id: item.id,
          isNew: item.isNew,
          droppedOverId: id,
        }),
    });
  } else {
    connectDropTarget = (element) => element;
  }

  return connectDragSource(
    connectDropTarget(
      <div
        className={classnames(className, 'dnd-widget', {
          dragging: isDragging,
          'dnd-placeholder': placeholder,
        })}
      >
        {!placeholder && onRemove && (
          <i
            className="meta-icon-trash draggable-icon-remove pointer"
            onClick={() => onRemove(entity, index, id)}
          />
        )}
        {children}
      </div>
    )
  );
};

DndWidget.propTypes = {
  id: PropTypes.oneOfType([PropTypes.number, PropTypes.string]).isRequired,
  index: PropTypes.number.isRequired,
  entity: PropTypes.string.isRequired,
  isNew: PropTypes.bool,
  placeholder: PropTypes.bool,
  transparent: PropTypes.bool,
  className: PropTypes.string,
  //
  children: PropTypes.any,
  //
  onDrop: PropTypes.func,
  onRemove: PropTypes.func,
};

export default DndWidget;
