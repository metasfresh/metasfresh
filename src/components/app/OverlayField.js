import React, { Component } from 'react';

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
      default:
        this.setState({
          barcodeSelected: null,
        });
        break;
    }
  };

  _scanBarcode = val => {
    this.setState({
      scanning: typeof val !== 'undefined' ? val : !this.state.scanning,
      result: null,
      barcodeSelected: null,
    });
  };

  _onBarcodeDetected = result => {
    this.setState({
      result,
    });
  };

  selectBarcode = result => {
    this.setState({
      barcodeSelected: result.codeResult.code,
      scanning: false,
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
            className="btn btn-sm btn-meta-success"
            onClick={() => this._scanBarcode(true)}
          >
            Scan using camera
          </button>
        );
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
          data={barcodeSelected || undefined}
          {...elem}
        />
      );
    });
  };

  renderScanner = () => {
    const { result } = this.state;

    return (
      <div className="row barcode-scanner-widget">
        <div className="col-sm-12">
          <BarcodeScanner
            result={result}
            onDetected={this._onBarcodeDetected}
            onClose={() => this._scanBarcode(false)}
            onReset={() => this._scanBarcode(true)}
          />
          <div className="row scanning-result">
            <span className="label col-xs-3 col-sm-2">Barcode</span>
            <BarcodeScannerResult
              result={result}
              onSelect={result => this.selectBarcode(result)}
            />
          </div>
        </div>
      </div>
    );
  };

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
