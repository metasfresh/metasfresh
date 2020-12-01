import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

export default class Switch extends PureComponent {
  render() {
    const {
      widgetField,
      readonly,
      rowId,
      isModal,
      tabIndex,
      forwardedRef,
      onPatch,
      id,
    } = this.props;
    const widgetData = this.props.widgetData[0];

    return (
      <label
        className={classnames('input-switch', {
          'input-disabled': readonly,
          'input-error':
            widgetData.validStatus && !widgetData.validStatus.valid,
          'input-table': rowId && !isModal,
        })}
        tabIndex={tabIndex}
        ref={forwardedRef}
        onKeyDown={(e) => {
          e.key === ' ' && onPatch(widgetField, !widgetData.value, id);
        }}
      >
        <input
          type="checkbox"
          checked={widgetData.value}
          disabled={readonly}
          tabIndex="-1"
          onChange={(e) => onPatch(widgetField, e.target.checked, id)}
        />
        <div className="input-slider" />
      </label>
    );
  }
}

Switch.propTypes = {
  readonly: PropTypes.bool,
  widgetData: PropTypes.array.isRequired,
  widgetField: PropTypes.string.isRequired,
  rowId: PropTypes.string,
  isModal: PropTypes.bool,
  onPatch: PropTypes.func.isRequired,
  tabIndex: PropTypes.number,
  forwardedRef: PropTypes.any,
  id: PropTypes.number,
};
