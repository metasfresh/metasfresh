import React, { useRef, useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

/**
 * @file Function based component.
 * @module Checkbox
 */
const Checkbox = (props) => {
  const rawWidget = useRef(null);
  const {
    widgetData,
    disabled,
    fullScreen,
    tabIndex,
    handlePatch,
    widgetField,
    id,
    filterWidget,
    isFilterActive,
    updateItems,
  } = props;
  let { value, defaultValue } = props.widgetData[0];

  const [isChanged, setChanged] = useState(false);

  let initialValue =
    updateItems && !isFilterActive && !isChanged ? defaultValue : value;
  initialValue = typeof initialValue === 'undefined' ? null : initialValue;
  const [checkedState, setCheckedState] = useState(initialValue);
  const [checkedValue, setCheckedValue] = useState(!!initialValue);

  useEffect(() => {
    defaultValue &&
      !isFilterActive &&
      !isChanged &&
      handlePatch(widgetField, defaultValue, id);

    const initialValue = updateItems ? checkedState : initialValue;
    setCheckedValue(isChanged && value === '' ? false : !!initialValue);
  }, [checkedState]);

  /**
   * @method handleClear
   * @summary clears the widget field by calling the method handlePatch from its parent with an empty value
   */
  const handleClear = () => {
    const { handlePatch, widgetField, id } = props;

    setChanged(true);
    setCheckedState(null);
    handlePatch(widgetField, '', id);
    // here we should call a method that would clear the filter item for the case when there is no active filter
    !isFilterActive && updateItems && updateItems({ widgetField, value: '' });
  };

  /**
   * @method updateCheckedState
   * @summary toggles local checked state and in the same time it performs a patch to update
   *          the widgetField with the current checked value of the element
   * @param {object} e
   */
  const updateCheckedState = () => {
    const newCheckedState = !checkedState;

    setChanged(true);
    setCheckedState(newCheckedState);
    !isFilterActive &&
      updateItems &&
      updateItems({ widgetField, value: !checkedState });
    handlePatch(widgetField, newCheckedState, id);
  };

  initialValue = updateItems ? checkedState : initialValue;

  return (
    <div>
      <label
        className={classnames('input-checkbox', {
          'input-disabled': widgetData[0].readonly || disabled,
        })}
        tabIndex={fullScreen ? -1 : tabIndex}
        onKeyDown={(e) => {
          if (e.key === ' ') {
            e.preventDefault();
            rawWidget.current && rawWidget.current.click();
          }
        }}
      >
        <input
          ref={rawWidget}
          type="checkbox"
          className={classnames({ 'is-checked': initialValue })}
          checked={checkedValue}
          disabled={widgetData[0].readonly || disabled}
          onChange={updateCheckedState}
          tabIndex="-1"
        />
        <div
          className={classnames('input-checkbox-tick', {
            'input-state-false': checkedState === false && filterWidget,
            checked: checkedState,
          })}
        />
      </label>
      {filterWidget &&
      !disabled &&
      !widgetData[0].readonly &&
      (checkedState === false || checkedState === true) ? (
        <small className="input-side" onClick={handleClear}>
          (clear)
        </small>
      ) : (
        ''
      )}
    </div>
  );
};

/**
 * @typedef {object} Props Component props
 * @prop {array} widgetData
 * @prop {bool} [disabled]
 * @prop {bool} [fullScreen]
 * @prop {number} [tabIndex]
 * @prop {bool} [filterWidget]
 * @prop {func} [handlePatch]
 * @prop {string} [widgetField]
 * @prop {string|number} [id]
 * @todo Check props. Which proptype? Required or optional?
 */
Checkbox.propTypes = {
  widgetData: PropTypes.array.isRequired,
  disabled: PropTypes.bool,
  fullScreen: PropTypes.bool,
  tabIndex: PropTypes.number,
  filterWidget: PropTypes.bool,
  handlePatch: PropTypes.func,
  widgetField: PropTypes.string,
  isFilterActive: PropTypes.bool,
  id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  updateItems: PropTypes.func, // function used for updating the filter items before having an active filter
};

export default Checkbox;
