import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';

import MasterWidget from './widget/MasterWidget';

/**
 * @file Class based component.
 * @module Process
 * @extends Component
 */
class Process extends PureComponent {
  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  getWidgetData = item => {
    const widgetData = item.fields.reduce(result => result, []);

    if (widgetData.length) {
      return widgetData;
    }

    return [{}];
  };

  /**
   * @method renderElements
   * @summary ToDo: Describe the method
   * @param {*} layout
   * @param {*} data
   * @param {*} type
   * @todo Write the documentation
   */
  renderElements = (layout, data, type) => {
    const { disabled } = this.props;
    const elements = layout.elements;
    return elements.map((elem, id) => {
      return (
        <div key={`${id}-${layout.pinstanceId}`}>
          <MasterWidget
            entity="process"
            key={'element' + id}
            windowType={type}
            dataId={layout.pinstanceId}
            getWidgetData={this.getWidgetData}
            isModal={true}
            disabled={disabled}
            autoFocus={id === 0}
            {...elem}
            item={elem}
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

/**
 * @typedef {object} Props Component props
 * @prop {bool} [disabled]
 * @prop {*} data
 * @prop {*} layout
 * @prop {*} type
 * @todo Check title, buttons. Which proptype? Required or optional?
 */
Process.propTypes = {
  disabled: PropTypes.bool,
  data: PropTypes.any,
  layout: PropTypes.any,
  type: PropTypes.any,
};

export default Process;
