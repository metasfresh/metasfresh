import React from 'react';
import PropTypes from 'prop-types';

const ModalButton = ({
  name,
  onShowTooltip,
  onHideTooltip,
  children,
  onClick,
  tabIndex,
  disabled,
}) => {
  return (
    <button
      key={`rawmodal-button-${name}`}
      name={name}
      className="btn btn-meta-outline-secondary btn-distance-3 btn-md"
      tabIndex={tabIndex}
      onClick={() => onClick && onClick(name)}
      onMouseEnter={() => onShowTooltip && onShowTooltip(name)}
      onMouseLeave={() => onHideTooltip && onHideTooltip(name)}
      disabled={disabled}
    >
      {children}
    </button>
  );
};

/**
 * @typedef {object} Props Component props
 * @prop {node} [children]
 * @prop {string} [name] internal name of the button; used as param when functions like onClick are called
 * @prop {number} [tabIndex]
 * @prop {bool} [disabled]
 * @prop {function} [onShowTooltip]
 * @prop {function} [onHideTooltip]
 * @prop {function} [onClick]
 */
ModalButton.propTypes = {
  children: PropTypes.node.isRequired,
  name: PropTypes.string,
  tabIndex: PropTypes.number,
  disabled: PropTypes.bool,
  onShowTooltip: PropTypes.func,
  onHideTooltip: PropTypes.func,
  onClick: PropTypes.func.isRequired,
};

export default ModalButton;
