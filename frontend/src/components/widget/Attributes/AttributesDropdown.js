import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import onClickOutsideHOC from 'react-onclickoutside';
import classnames from 'classnames';
import FocusTrap from 'focus-trap-react';

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
    const { onCompletion } = this.props;

    // we need to wait for fetching all of PATCH fields on blur
    // to complete on updated instance
    // TODO: Figure out if it would be possible to rewrite this
    // using Promise.all somehow
    const requestsInterval = window.setInterval(() => {
      const intervalsLeft = this.state.patchCallbacks.size;

      if (intervalsLeft === 0) {
        window.clearInterval(requestsInterval);
        onCompletion();
      }
    }, 10);
  };

  /**
   * @method handleWidgetPatch
   * @param {string} fieldName
   * @param {*} value
   * @param {number} idx
   */
  handleWidgetPatch = (fieldName, value, idx) => {
    const { onFieldPatch, attrId } = this.props;
    const { patchCallbacks } = this.state;
    const updatedCallbacks = patchCallbacks.set(idx, true);

    return new Promise((resolve) => {
      this.setState(
        {
          patchCallbacks: updatedCallbacks,
        },
        () => {
          return onFieldPatch(fieldName, value, attrId, () => {
            this.state.patchCallbacks.delete(idx);

            resolve();
          });
        }
      );
    });
  };

  renderFields = () => {
    const {
      tabIndex,
      layout,
      data,
      attributeType,
      attrId,
      isModal,
      onFieldChange,
      disableOnClickOutside,
      enableOnClickOutside,
    } = this.props;

    console.log('renderFields', { props: this.props });

    if (layout) {
      return layout.map((elementLayout, elementIndex) => {
        const widgetData = elementLayout.fields.map(
          (elem) => data[elem.field] || -1
        );

        return (
          <WidgetWrapper
            key={elementIndex}
            dataSource="attributes-dropdown"
            entity={attributeType}
            type={elementLayout.type}
            caption={elementLayout.caption}
            widgetType={elementLayout.widgetType}
            fields={elementLayout.fields}
            dataId={attrId}
            widgetData={widgetData}
            gridAlign={elementLayout.gridAlign}
            autoFocus={elementIndex === 0}
            handlePatch={(fieldName, value) =>
              this.handleWidgetPatch(fieldName, value, elementIndex)
            }
            handleChange={onFieldChange}
            enableOnClickOutside={enableOnClickOutside}
            disableOnClickOutside={disableOnClickOutside}
            attributeWidget={true}
            tabIndex={tabIndex}
            isModal={isModal}
          />
        );
      });
    }
  };

  render() {
    const { rowIndex } = this.props;

    return (
      <FocusTrap
        focusTrapOptions={{
          // NOTE: we have to allowOutsideClick=true
          // because else clicking on date picker calendar navigation buttons won't work
          allowOutsideClick: true,
          escapeDeactivates: false,
        }}
      >
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
      </FocusTrap>
    );
  }
}

/**
 * @typedef {object} Props Component props
 *
 * @prop {string} [attributeType] i.e. pattribute, address
 * @prop {*} [attrId] the ID of the temporary editing instance
 * @prop {array} [layout] array of element layouts
 * @prop {number} [rowIndex] row index within the table
 * @prop {number} [tabIndex]
 * @prop {object} [data]
 * @prop {bool} [isModal]
 *
 * @prop {func} [onFieldChange]
 * @prop {func} [onFieldPatch]
 * @prop {func} [onCompletion]
 *
 * @prop {func} [enableOnClickOutside]
 * @prop {func} [disableOnClickOutside]
 */
AttributesDropdown.propTypes = {
  attributeType: PropTypes.string.isRequired,
  attrId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
  layout: PropTypes.array,
  rowIndex: PropTypes.number,
  tabIndex: PropTypes.number,
  data: PropTypes.object.isRequired,
  isModal: PropTypes.bool,
  //
  onFieldChange: PropTypes.func.isRequired,
  onFieldPatch: PropTypes.func.isRequired,
  onCompletion: PropTypes.func.isRequired,

  // wired by onClickOutside:
  enableOnClickOutside: PropTypes.func.isRequired,
  disableOnClickOutside: PropTypes.func.isRequired,
};

export default onClickOutsideHOC(AttributesDropdown);
