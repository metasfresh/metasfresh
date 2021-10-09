import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import onClickOutside from 'react-onclickoutside';
import classnames from 'classnames';

import { DROPUP_START } from '../../../constants/Constants';

import WidgetWrapper from '../../../containers/WidgetWrapper';

/**
 * @file Class based component.
 * @module AttributesDropdown
 * @extends Component
 */
class AttributesDropdown extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      patchCallbacks: new Map(),
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
            this.state.patchCallbacks.delete(id);

            res();
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
          <WidgetWrapper
            dataSource="attributes-dropdown"
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
            attributeWidget={true}
            {...{
              tabIndex,
              isModal,
            }}
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
    const { rowIndex } = this.props;

    return (
      <div
        className={classnames(
          'attributes-dropdown panel-shadowed panel-primary panel-bordered panel-spaced',
          {
            'attributes-dropup': rowIndex > DROPUP_START,
          }
        )}
      >
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
 * @prop {func} allowShortcut
 * @prop {func} disableShortcut
 * @prop {bool} modalVisible
 * @prop {string} timeZone
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
  rowIndex: PropTypes.number, // used for knowing the row index within the Table (used on AttributesDropdown component)
};

export default onClickOutside(AttributesDropdown);
