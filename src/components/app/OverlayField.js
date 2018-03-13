import React, { Component } from 'react';
import counterpart from 'counterpart';

import MasterWidget from '../widget/MasterWidget';
import RawWidget from '../widget/RawWidget';
import BarcodeScanner, { BarcodeScannerResult } from './BarcodeScanner';

class OverlayField extends Component {
  constructor(props) {
    super(props);

    this.state = {
      scanning: false,
      result: null,
      barcodeSelected: null,
    };
  }

  handleKeyDown = e => {
    const { handleSubmit, closeOverlay } = this.props;
    switch (e.key) {
      case 'Enter':
        document.activeElement.blur();
        handleSubmit();
        break;
      case 'Escape':
        closeOverlay();
        break;
    }
  };

  _scanBarcode = val => {
    this.setState({
      scanning: typeof val !== 'undefined' ? val : !this.state.scanning,
      barcodeSelected: null,
    });
  };

  _onBarcodeDetected = result => {
    this.setState({
      result,
    });
  };

  selectBarcode = (result) => {
    const barcode = result || null;

    this.setState({
      barcodeSelected: result,
    });
  };

  renderElements = (layout, data, type) => {
    const { disabled } = this.props;
    const { barcodeSelected } = this.state;
    const elements = layout.elements;

    return elements.map((elem, id) => {
      const widgetData = elem.fields.map(item => data[item.field] || -1);
      let captionElement = null;

      if (elem.caption === 'Barcode') {
        captionElement = (
          <button
            className="btn btn-sm btn-block btn-meta-success"
            onClick={this._scanBarcode}
          >
            {/*{counterpart.translate('barcodescan.caption')}*/}
            Scan using camera
          </button>
        );

        if (barcodeSelected) {

        }
      }

      return (
        <MasterWidget
          entity="process"
          key={'element' + id}
          windowType={type}
          dataId={layout.pinstanceId}
          widgetData={widgetData}
          isModal={true}
          disabled={disabled}
          autoFocus={id === 0}
          captionElement={captionElement}
          {...elem}
        />
      );
    });
  };

  renderScanner = () => {
    const { result } = this.state;

    return (
      <div className="barcode-scanner-widget">
        <BarcodeScanner
          onDetected={this._onBarcodeDetected}
          onClose={() => this._scanBarcode(false)}
          onReset={this.selectBarcode}
        />
        <div className="scanning-result">
          <span className="form-control-label col-sm-3 ">
            Barcode
          </span>
          <BarcodeScannerResult
            result={result}
            onSelect={(result) => this.selectBarcode(result)}
          />
        </div>
      </div>
    );
  }

  renderParameters = layout => {
    const {
      windowType,
      viewId,
      onShow,
      onHide,
      handlePatch,
      handleChange,
      captionValue,
    } = this.props;
    const parameters = layout.parameters;
    return parameters.map((item, index) => {
      return (
        <RawWidget
          defaultValue={captionValue}
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
          }}
        />
      );
    });
  };

  render() {
    const { data, layout, type, filter } = this.props;
    const { scanning } = this.state;
    let renderedContent = null;

    if (scanning) {
      renderedContent = this.renderScanner();
    } else {
      if (filter) {
        renderedContent = this.renderParameters(layout);
      } else if (!filter && layout && layout.elements) {
        renderedContent = this.renderElements(layout, data, type);
      }
    }

    return (
      <div
        className="overlay-field js-not-unselect"
        onKeyDown={e => this.handleKeyDown(e)}
        tabIndex={-1}
      >
        {renderedContent}
      </div>
    );
  }
}

export default OverlayField;
