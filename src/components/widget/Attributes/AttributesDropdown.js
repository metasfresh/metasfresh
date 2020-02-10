import PropTypes from 'prop-types';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import { Map } from 'immutable';

import RawWidget from '../RawWidget';

/**
 * @file Class based component.
 * @module AttributesDropdown
 * @extends Component
 */
class AttributesDropdown extends Component {
  constructor(props) {
    super(props);

    this.state = {
      patchCallbacks: Map(),
    };
  }

  /**
   * @method handleClickOutside
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleClickOutside = () => {
    const { onClickOutside } = this.props;

    // we need to wait for fetching all of PATCH fields on blur
    // to complete on updated instance
    // TODO: Figure out if it would be possible to rewrite this
    // using Promise.all somehow
    const requestsInterval = window.setInterval(() => {
      const intervalsLeft = this.state.patchCallbacks.size;

      if (intervalsLeft === 0) {
        window.clearInterval(requestsInterval);
        onClickOutside();
      }
    }, 10);
  };

  /**
   * @method handlePatch
   * @summary ToDo: Describe the method
   * @param {*} prop
   * @param {*} value
   * @param {*} id
   * @todo Write the documentation
   */
  handlePatch = (prop, value, id) => {
    const { handlePatch, attrId } = this.props;
    const { patchCallbacks } = this.state;
    const updatedCallbacks = patchCallbacks.set(id, true);

    return new Promise((res) => {
      this.setState(
        {
          patchCallbacks: updatedCallbacks,
        },
        () => {
          return handlePatch(prop, value, attrId, () => {
            const resolvedCallbacks = this.state.patchCallbacks.delete(id);

            res();

            this.setState({
              patchCallbacks: resolvedCallbacks,
            });
          });
        }
      );
    });
  };

  /**
   * @method renderFields
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  renderFields = () => {
    const {
      tabIndex,
      layout,
      data,
      attributeType,
      handleChange,
      attrId,
      disableOnClickOutside,
      enableOnClickOutside,
      isModal,
    } = this.props;

    if (layout) {
      return layout.map((item, idx) => {
        const widgetData = item.fields.map((elem) => data[elem.field] || -1);
        return (
          <RawWidget
            entity={attributeType}
            widgetType={item.widgetType}
            fields={item.fields}
            dataId={attrId}
            widgetData={widgetData}
            gridAlign={item.gridAlign}
            key={idx}
            type={item.type}
            caption={item.caption}
            handlePatch={(prop, value) => this.handlePatch(prop, value, idx)}
            handleChange={handleChange}
            disableOnClickOutside={disableOnClickOutside}
            enableOnClickOutside={enableOnClickOutside}
            tabIndex={tabIndex}
            isModal={isModal}
            attributeWidget={true}
          />
        );
      });
    }
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    return (
      <div className="attributes-dropdown panel-shadowed panel-primary panel-bordered panel-spaced">
        {this.renderFields()}
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {number} [tabIndex]
 * @prop {bool} [isModal]
 * @prop {object} data
 * @prop {string} attributeType
 * @prop {func} handleChange
 * @prop {*} attrId
 * @prop {array} layout
 * @prop {func} [onClickOutside]
 * @prop {func} handlePatch
 * @prop {func} disableOnClickOutside
 * @prop {func} enableOnClickOutside
 * @todo Check props. Which proptype? Required or optional?
 */
AttributesDropdown.propTypes = {
  tabIndex: PropTypes.number,
  isModal: PropTypes.bool,
  data: PropTypes.object.isRequired,
  attributeType: PropTypes.string.isRequired,
  handleChange: PropTypes.func.isRequired,
  attrId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  layout: PropTypes.array,
  onClickOutside: PropTypes.func,
  handlePatch: PropTypes.func.isRequired,
  disableOnClickOutside: PropTypes.func.isRequired,
  enableOnClickOutside: PropTypes.func.isRequired,
};

export default onClickOutside(AttributesDropdown);
