import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import DevicesWidget from './Devices/DevicesWidget';

export default class Amount extends PureComponent {
  render() {
    const {
      getClassNames,
      widgetProperties,
      widgetField,
      subentity,
      fields,
      onPatch,
    } = this.props;
    const widgetData = this.props.widgetData[0];

    return (
      <div className={classnames(getClassNames(), 'number-field')}>
        <input
          {...widgetProperties}
          type="number"
          min={0}
          precision={widgetField === 'CableLength' ? 2 : 1}
          step={subentity === 'quickInput' ? 'any' : 1}
        />
        {widgetData.devices && (
          <div className="device-widget-wrapper">
            <DevicesWidget
              devices={widgetData.devices}
              tabIndex={1}
              handleChange={(value) =>
                onPatch && onPatch(fields[0].field, value)
              }
            />
          </div>
        )}
      </div>
    );
  }
}

Amount.propTypes = {
  widgetData: PropTypes.array.isRequired,
  widgetField: PropTypes.string.isRequired,
  fields: PropTypes.array.isRequired,
  subentity: PropTypes.string,
  widgetProperties: PropTypes.object.isRequired,
  getClassNames: PropTypes.func.isRequired,
  onPatch: PropTypes.func.isRequired,
};
