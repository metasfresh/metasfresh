import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

const Checkbox = props => {
  const {
    widgetData,
    disabled,
    fullScreen,
    tabIndex,
    handlePatch,
    widgetField,
    id,
    filterWidget,
  } = props;

  const handleClear = () => {
    const { handlePatch, widgetField, id } = props;
    handlePatch(widgetField, '', id);
  };

  return (
    <div>
      <label
        className={
          'input-checkbox ' +
          (widgetData[0].readonly || disabled ? 'input-disabled ' : '')
        }
        tabIndex={fullScreen ? -1 : tabIndex}
        ref={c => (this.rawWidget = c)}
        onKeyDown={e => {
          if (e.key === ' ') {
            e.preventDefault();
            this.rawWidget && this.rawWidget.click();
          }
        }}
      >
        <input
          ref={c => (this.rawWidget = c)}
          type="checkbox"
          checked={widgetData[0].value}
          disabled={widgetData[0].readonly || disabled}
          onChange={e => handlePatch(widgetField, e.target.checked, id)}
          tabIndex="-1"
        />
        <div
          className={classnames('input-checkbox-tick', {
            'input-state-false': widgetData[0].value === false && filterWidget,
            checked: widgetData[0].value,
          })}
        />
      </label>
      {filterWidget &&
      !disabled &&
      !widgetData[0].readonly &&
      (widgetData[0].value != null && widgetData[0].value !== '') ? (
        <small className="input-side" onClick={handleClear}>
          (clear)
        </small>
      ) : (
        ''
      )}
    </div>
  );
};

Checkbox.propTypes = {
  handlePatch: PropTypes.func,
  widgetField: PropTypes.string,
  id: PropTypes.string,
};

export default Checkbox;
