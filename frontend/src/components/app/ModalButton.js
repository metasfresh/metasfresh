import React from 'react';
import PropTypes from 'prop-types';

/**
 * @file Function based component.
 * @module ModalButton
 * @param {object} props
 */
const ModalButton = (props) => {
  const {
    name,
    onShowTooltip,
    onHideTooltip,
    children,
    onClick,
    tabIndex,
    disabled,
  } = props;

  /**
   * @func handleClick
   * @summary Call the `onClick` on mouse click
   */
  const handleClick = () => onClick(name);

  /**
   * @func handleShowTooltip
   * @summary Call the `onShowTooltip` on mouse enter
   */
  const handleShowTooltip = () => onShowTooltip(name);

  /**
   * @method handleHideTooltip
   * @summary Call the `onHideTooltip` on mouse leave
   */
  const handleHideTooltip = () => onHideTooltip(name);

  return (
    <button
      key={`rawmodal-button-${name}`}
      name={name}
      className="btn btn-meta-outline-secondary btn-distance-3 btn-md"
      onClick={handleClick}
      tabIndex={tabIndex}
      onMouseEnter={handleShowTooltip}
      onMouseLeave={handleHideTooltip}
      disabled={disabled}
    >
      {children}
    </button>
  );
};

/**
 * @typedef {object} Props Component props
 * @prop {node} [children]
 * @prop {*} [name]
 * @prop {*} [onShowTooltip]
 * @prop {*} [onHideTooltip]
 * @prop {*} [onClick]
 * @prop {*} [tabIndex]
 */
ModalButton.propTypes = {
  children: PropTypes.node,
  name: PropTypes.any,
  onShowTooltip: PropTypes.any,
  onHideTooltip: PropTypes.any,
  onClick: PropTypes.any,
  tabIndex: PropTypes.any,
  disabled: PropTypes.bool,
};

export default ModalButton;
