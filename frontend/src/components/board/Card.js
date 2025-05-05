import React, { PureComponent } from 'react';
import { DragSource, DropTarget } from 'react-dnd';
import PropTypes from 'prop-types';
import Avatar from '../app/Avatar';

const cardTarget = {
  drop(props, monitor) {
    props.onDrop && props.onDrop(monitor.getItem(), props.laneId);
  },
  hover(props, monitor) {
    if (!props.onHover) {
      return;
    }

    if (
      monitor.getItem().index === props.index &&
      props.laneId === monitor.getItem().laneId
    ) {
      return;
    }

    props.onHover(monitor.getItem(), props.laneId, props.index);

    monitor.getItem().index = props.index;
    monitor.getItem().laneId = props.laneId;
  },
};

function connect(connect) {
  return {
    connectDropTarget: connect.dropTarget(),
  };
}

const cardSource = {
  beginDrag(props) {
    return {
      id: props.cardId,
      index: props.index,
      initLaneId: props.laneId,
      laneId: props.laneId,
    };
  },
  endDrag(props) {
    props.onReject && props.onReject();
  },
};

function collect(connect, monitor) {
  return {
    connectDragSource: connect.dragSource(),
    isDragging: monitor.isDragging(),
  };
}

const TargetIndicator = (props) => {
  const { index, laneId, parentIndex, parentLaneId } = props;
  if (laneId !== parentLaneId || index !== parentIndex) return false;
  return <div className="lane-card-placeholder" />;
};

TargetIndicator.propTypes = {
  index: PropTypes.number,
  laneId: PropTypes.number,
  parentIndex: PropTypes.number,
  parentLaneId: PropTypes.number,
};

class Card extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      mouseOn: false,
    };
  }

  renderCard = () => {
    const {
      caption,
      description,
      users,
      placeholder,
      connectDragSource,
      connectDropTarget,
      onDelete,
      cardId,
      laneId,
      onCaptionClick,
      documentPath,
    } = this.props;

    const { mouseOn } = this.state;

    if (placeholder) {
      return connectDropTarget(<div className="card-placeholder" />);
    } else {
      return connectDragSource(
        connectDropTarget(
          <div className="card">
            {mouseOn && onDelete && (
              <i
                className="pointer meta-icon-close-1 float-right"
                onClick={() => onDelete(laneId, cardId)}
              />
            )}
            <b className="pointer" onClick={() => onCaptionClick(documentPath)}>
              {caption}
            </b>
            <p className="card-description">{description}</p>
            {users.map((user, i) => (
              <Avatar
                key={i}
                id={user.avatarId}
                className="float-right"
                size="sm"
                title={user.fullname}
              />
            ))}
            <span className="clearfix" />
          </div>
        )
      );
    }
  };

  render() {
    const { targetIndicator, index, laneId } = this.props;

    return (
      <div
        onMouseEnter={() => this.setState({ mouseOn: true })}
        onMouseLeave={() => this.setState({ mouseOn: false })}
      >
        {targetIndicator && (
          <TargetIndicator
            {...targetIndicator}
            parentIndex={index}
            parentLaneId={laneId}
          />
        )}
        {this.renderCard()}
      </div>
    );
  }
}

Card.propTypes = {
  index: PropTypes.number,
  laneId: PropTypes.number,
  parentIndex: PropTypes.number,
  parentLaneId: PropTypes.number,
  caption: PropTypes.string,
  description: PropTypes.string,
  users: PropTypes.any,
  connectDragSource: PropTypes.func,
  connectDropTarget: PropTypes.func,
  onDelete: PropTypes.func,
  cardId: PropTypes.number,
  documentPath: PropTypes.object, // this has the form { documentId: xxx, windowId: yyy }
  onCaptionClick: PropTypes.func,
  targetIndicator: PropTypes.any,
  placeholder: PropTypes.bool,
};

export default DragSource(
  'CARD',
  cardSource,
  collect
)(DropTarget('CARD', cardTarget, connect)(Card));
