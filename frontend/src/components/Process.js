import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { omit } from 'lodash';

import WidgetWrapper from '../containers/WidgetWrapper';
import Loader from './app/Loader';

/**
 * @file Class based component.
 * @module Process
 * @extends PureComponent
 */
class Process extends PureComponent {
  /**
   * @method renderElements
   * @summary ToDo: Describe the method
   * @param {*} layout
   * @param {*} data
   * @param {*} type
   * @todo Write the documentation
   */
  renderElements = () => {
    const { layout, type, disabled } = this.props;
    const elements = layout.elements;

    return elements.map((elem, idx) => {
      const element = omit(elem, ['fields']);

      return (
        <div key={`${idx}-${layout.pinstanceId}`}>
          <WidgetWrapper
            renderMaster={true}
            dataSource="process"
            layoutId={`${idx}`}
            entity="process"
            key={`element${idx}`}
            windowId={type}
            dataId={layout.pinstanceId}
            isModal={true}
            disabled={disabled}
            autoFocus={idx === 0}
            {...element}
          />
        </div>
      );
    });
  };

  render() {
    const { disabled, layout } = this.props;
    return (
      <div key="window" className="window-wrapper process-wrapper">
        {disabled && <Loader loaderType="bootstrap" />}
        {!disabled && layout && layout.elements && this.renderElements()}
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
 */
Process.propTypes = {
  disabled: PropTypes.bool,
  data: PropTypes.any,
  layout: PropTypes.any,
  type: PropTypes.any,
};

export default Process;
