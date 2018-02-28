import React, { Component } from 'react';

import Card from './Card';

class Lane extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const {
      caption,
      cards,
      laneId,
      onHover,
      onDrop,
      targetIndicator,
      onReject,
      onDelete,
      placeholder,
      onCaptionClick,
    } = this.props;

    if (placeholder) {
      return <div className="board-lane-placeholder" />;
    }

    return (
      <div className="board-lane">
        <div className="board-lane-header">{caption}</div>
        <div
          className={
            'board-draggable-wrapper ' +
            (!cards.length ? 'board-draggable-placeholder ' : '')
          }
        >
          {!cards.length && (
            <Card
              index={0}
              {...{ laneId, onHover, onDrop, targetIndicator }}
              placeholder={true}
            />
          )}
          {cards.map((card, i) => (
            <Card
              key={i}
              index={i}
              {...{
                laneId,
                onHover,
                onDrop,
                onReject,
                targetIndicator,
                onDelete,
                onCaptionClick,
              }}
              {...card}
            />
          ))}
          {cards.length && (
            <Card
              index={cards.length}
              {...{ laneId, onHover, onDrop, targetIndicator }}
              placeholder={true}
            />
          )}
        </div>
      </div>
    );
  }
}

export default Lane;
