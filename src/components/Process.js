import PropTypes from 'prop-types';
import React, { Component } from 'react';

import MasterWidget from './widget/MasterWidget';

export default class Process extends Component {
  constructor(props) {
    super(props);
  }

  renderElements = (layout, data, type) => {
    const { disabled } = this.props;
    const elements = layout.elements;
    return elements.map((elem, id) => {
      const widgetData = elem.fields.map(item => data[item.field] || -1);
      return (
        <div key={`${id}-${layout.pinstanceId}`}>
          <MasterWidget
            entity="process"
            key={'element' + id}
            windowType={type}
            dataId={layout.pinstanceId}
            widgetData={widgetData}
            isModal={true}
            disabled={disabled}
            autoFocus={id === 0}
            {...elem}
          />
        </div>
      );
    });
  };

  render() {
    const { data, layout, type } = this.props;
    return (
      <div key="window" className="window-wrapper process-wrapper">
        {layout && layout.elements && this.renderElements(layout, data, type)}
      </div>
    );
  }
}

Process.propTypes = {
  disabled: PropTypes.bool,
};
