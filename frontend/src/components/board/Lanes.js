import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Lane from './Lane';

class Lanes extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const {
      lanes,
      onDrop,
      onHover,
      onReject,
      onDelete,
      targetIndicator,
      onCaptionClick,
    } = this.props;

    if (!lanes) return false;

    return (
      <div className="board-lanes">
        {lanes.map((lane, i) => (
          <Lane
            key={i}
            {...{
              onDrop,
              onHover,
              onDelete,
              onReject,
              targetIndicator,
              onCaptionClick,
            }}
            {...lane}
          />
        ))}
        <Lane placeholder={true} />
      </div>
    );
  }
}

Lanes.propTypes = {
  lanes: PropTypes.array,
  onDrop: PropTypes.func,
  onHover: PropTypes.func,
  onReject: PropTypes.func,
  onDelete: PropTypes.func,
  targetIndicator: PropTypes.any,
  onCaptionClick: PropTypes.func,
};

export default Lanes;
