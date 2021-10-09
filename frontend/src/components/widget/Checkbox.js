import React, { useRef, useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import usePrevious from '../../hooks/usePrevious';

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
  let { value, defaultValue } = widgetData;
  const prevValue = usePrevious(value);

  const [isChanged, setChanged] = useState(false);

  let initialValue =
    updateItems && !isFilterActive && !isChanged ? defaultValue : value;

  initialValue = typeof initialValue === 'undefined' ? null : initialValue;
  const [initialRender, setInitialRender] = useState(false);
  const [checkedState, setCheckedState] = useState(initialValue);
  const [checkedValue, setCheckedValue] = useState(!!initialValue);

  useEffect(() => {
    defaultValue &&
      !isFilterActive &&
      !isChanged &&
      handlePatch(widgetField, defaultValue, id);

    // we're not setting the initialRender flag for filters, as we don't have a case where
    // they might be rendered already AND their value could be modified in another part of the
    // application (ex modal)
    if (!initialRender && !updateItems) {
      setInitialRender(true);
    }

    // if widget's value changed without user triggering it, update the local state as it
    // was due to a change in a modal or other part of the app
    if (!isChanged && value !== prevValue && initialRender) {
      setCheckedState(!checkedState);
    }

    setCheckedValue(isChanged && value === '' ? false : !!checkedState);
  }, [checkedState, value]);

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
    handlePatch(widgetField, newCheckedState, id).then(() => {
      setChanged(false);
    });
  };

  return (
    <div>
      <label
        className={classnames('input-checkbox', {
          'input-disabled': widgetData.readonly || disabled,
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
          disabled={widgetData.readonly || disabled}
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
      !widgetData.readonly &&
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
 * @prop {object} widgetData
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
  widgetData: PropTypes.object.isRequired,
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
