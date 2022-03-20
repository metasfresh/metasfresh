import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import onClickOutsideHOC from 'react-onclickoutside';
import classnames from 'classnames';
import FocusTrap from 'focus-trap-react';

import { DROPUP_START } from '../../../constants/Constants';

import WidgetWrapper from '../../../containers/WidgetWrapper';

class AttributesDropdown extends PureComponent {
  handleClickOutside = () => {
    const { onCompletion } = this.props;
    onCompletion();
  };

  handleWidgetPatch = (fieldName, value) => {
    const { onFieldPatch, editingInstanceId } = this.props;
    onFieldPatch(fieldName, value, editingInstanceId);
  };

  handleKeyDown = (e) => {
    if ((e.key === 'Enter' && e.altKey) || e.key === 'Escape') {
      const { onCompletion } = this.props;

      e.stopPropagation();
      e.preventDefault();
      onCompletion();
    }
  };

  renderFieldsPanel = () => {
    const { layout, rowIndex } = this.props;

    if (layout) {
      return (
        <div
          className={classnames(
            'attributes-dropdown panel-shadowed panel-primary panel-bordered panel-spaced',
            { 'attributes-dropup': rowIndex > DROPUP_START }
          )}
          onKeyDown={this.handleKeyDown}
        >
          {layout.map(this.renderField)}
        </div>
      );
    }
  };

  renderField = (elementLayout, elementIndex) => {
    const {
      tabIndex,
      fieldsByName,
      attributeType,
      editingInstanceId,
      isModal,
      onFieldChange,
      disableOnClickOutside,
      enableOnClickOutside,
    } = this.props;

    const widgetData = elementLayout.fields.map(
      (fieldLayout) => fieldsByName[fieldLayout.field] || -1
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
        dataId={editingInstanceId}
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
  };

  render() {
    return (
      <FocusTrap
        focusTrapOptions={{
          // NOTE: we have to allowOutsideClick=true
          // because else clicking on date picker calendar navigation buttons won't work
          allowOutsideClick: true,
          escapeDeactivates: false,
        }}
      >
        {this.renderFieldsPanel()}
      </FocusTrap>
    );
  }
}

/**
 * @typedef {object} Props Component props
 *
 * @prop {string} [attributeType] i.e. pattribute, address
 * @prop {string|number} [editingInstanceId] the ID of the temporary editing instance
 * @prop {array} [layout] array of element layouts
 * @prop {number} [rowIndex] row index within the table
 * @prop {number} [tabIndex]
 * @prop {object} [fieldsByName]
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
  editingInstanceId: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
    .isRequired,
  layout: PropTypes.array,
  rowIndex: PropTypes.number,
  tabIndex: PropTypes.number,
  fieldsByName: PropTypes.object.isRequired,
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
