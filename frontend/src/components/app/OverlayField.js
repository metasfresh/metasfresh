import React, { Component } from 'react';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import onClickOutside from 'react-onclickoutside';
import { connect } from 'react-redux';

import {
  openModal,
  patch,
  updatePropertyValue,
  allowShortcut,
  disableShortcut,
} from '../../actions/WindowActions';
import MasterWidget from '../widget/MasterWidget';
import RawWidget from '../widget/RawWidget';
import BarcodeScanner from '../widget/BarcodeScanner/BarcodeScannerWidget';

/**
 * @file Class based component.
 * @module OverlayField
 * @extends Component
 */
class OverlayField extends Component {
  handleClickOutside = () => {
    const { closeOverlay } = this.props;

    closeOverlay();
  };

  /**
   * @method handleKeyDown
   * @summary ToDo: Describe the method
   * @param {*} event
   * @todo Write the documentation
   */
  handleKeyDown = (e) => {
    const { handleSubmit, closeOverlay, onSelectBarcode } = this.props;

    switch (e.key) {
      case 'Enter':
        handleSubmit();
        break;
      case 'Escape':
        closeOverlay();
        break;
      case 'Tab':
        handleSubmit();
        break;
      default:
        onSelectBarcode(null);
        break;
    }
  };

  /**
   * @method renderBarcodeScanButton
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  renderBarcodeScanButton = () => {
    const { onScanBarcode } = this.props;

    return (
      <button
        className="btn btn-sm btn-meta-success"
        onClick={() => onScanBarcode(true)}
      >
        {counterpart.translate('widget.scanFromCamera.caption')}
      </button>
    );
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
    const {
      disabled,
      codeSelected,
      onChange,
      openModal,
      patch,
      updatePropertyValue,
      allowShortcut,
      disableShortcut,
    } = this.props;
    const elements = layout.elements;

    return elements.map((elem, id) => {
      const widgetData = elem.fields.map((item) => data[item.field] || -1);
      let captionElement = null;

      if (elem.barcodeScannerType) {
        captionElement = this.renderBarcodeScanButton();
      }

      return (
        <MasterWidget
          entity="process"
          key={'element' + id}
          windowId={type}
          dataId={layout.pinstanceId}
          widgetData={widgetData}
          isModal={true}
          disabled={disabled}
          autoFocus={id === 0}
          captionElement={captionElement}
          value={codeSelected || undefined}
          onChange={onChange}
          openModal={openModal}
          patch={patch}
          updatePropertyValue={updatePropertyValue}
          allowShortcut={allowShortcut}
          disableShortcut={disableShortcut}
          {...elem}
        />
      );
    });
  };

  /**
   * @method renderParameters
   * @summary ToDo: Describe the method
   * @param {*} layout
   * @todo Write the documentation
   */
  renderParameters = (layout) => {
    const {
      windowType,
      viewId,
      onShow,
      onHide,
      handlePatch,
      handleChange,
      captionValue,
      codeSelected,
      modalVisible,
      timeZone,
      allowShortcut,
      disableShortcut,
    } = this.props;
    const parameters = layout.parameters;
    return parameters.map((item, index) => {
      let captionElement = null;

      if (item.barcodeScannerType) {
        captionElement = this.renderBarcodeScanButton();
      }

      if (codeSelected) {
        item.value = codeSelected;
      }

      return (
        <RawWidget
          defaultValue={captionValue}
          captionElement={captionElement}
          entity="documentView"
          subentity="filter"
          subentityId={layout.filterId}
          widgetType={item.widgetType}
          fields={[item]}
          type={item.type}
          widgetData={[item]}
          key={index}
          id={index}
          range={item.range}
          caption={item.caption}
          noLabel={false}
          filterWidget={true}
          autoFocus={index === 0}
          textSelected={true}
          {...{
            handlePatch,
            handleChange,
            windowType,
            onShow,
            onHide,
            viewId,
            modalVisible,
            timeZone,
            allowShortcut,
            disableShortcut,
          }}
        />
      );
    });
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { data, layout, type, filter, scanning, scannerElement } = this.props;
    let renderedContent = null;

    if (scanning) {
      renderedContent = scannerElement;
    } else {
      // TODO: Why sometimes it's wrapped in MasterWidget, and other
      // times it's not ? Needs refactoring.
      if (filter) {
        renderedContent = this.renderParameters(layout);
      } else if (!filter && layout && layout.elements) {
        renderedContent = this.renderElements(layout, data, type);
      }
    }

    return (
      <div
        className="overlay-field"
        onKeyDown={this.handleKeyDown}
        tabIndex={-1}
      >
        {renderedContent}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  const { appHandler, windowHandler } = state;

  return {
    modalVisible: windowHandler.modal.visible,
    timeZone: appHandler.me.timeZone,
  };
};

/**
 * @typedef {object} OverlayField
 * @prop {func} onChange
 * @prop {func} closeOverlay
 * @prop {any} data
 * @prop {any} layout
 * @prop {any} type
 * @prop {any} filter
 * @prop {any} scanning
 * @prop {any} scannerElement
 * @prop {any} windowType
 * @prop {any} viewId
 * @prop {any} onShow
 * @prop {any} onHide
 * @prop {any} handlePatch
 * @prop {any} handleChange
 * @prop {any} captionValue
 * @prop {any} codeSelected
 * @prop {any} disabled
 * @prop {any} onScanBarcode
 * @prop {any} onSelectBarcode
 * @prop {any} handleSubmit
 * @prop {bool} modalVisible
 * @prop {string} timeZone
 * @prop {func} allowShortcut
 * @prop {func} disableShortcut
 * @prop {func} updatePropertyValue
 * @prop {func} openModal
 * @prop {func} patch
 */
OverlayField.propTypes = {
  onChange: PropTypes.func,
  closeOverlay: PropTypes.func,
  data: PropTypes.any,
  layout: PropTypes.any,
  type: PropTypes.any,
  filter: PropTypes.any,
  scanning: PropTypes.any,
  scannerElement: PropTypes.any,
  windowType: PropTypes.any,
  viewId: PropTypes.any,
  onShow: PropTypes.any,
  onHide: PropTypes.any,
  handlePatch: PropTypes.any,
  handleChange: PropTypes.any,
  captionValue: PropTypes.any,
  codeSelected: PropTypes.any,
  disabled: PropTypes.any,
  onScanBarcode: PropTypes.any,
  onSelectBarcode: PropTypes.any,
  handleSubmit: PropTypes.any,
  allowShortcut: PropTypes.func.isRequired,
  disableShortcut: PropTypes.func.isRequired,
  modalVisible: PropTypes.bool.isRequired,
  timeZone: PropTypes.string.isRequired,
  updatePropertyValue: PropTypes.func.isRequired,
  openModal: PropTypes.func.isRequired,
  patch: PropTypes.func.isRequired,
};

export default BarcodeScanner(
  connect(
    mapStateToProps,
    {
      allowShortcut,
      disableShortcut,
      openModal,
      patch,
      updatePropertyValue,
    }
  )(onClickOutside(OverlayField))
);
