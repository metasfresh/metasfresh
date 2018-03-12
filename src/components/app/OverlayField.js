import React, { Component } from 'react';

import MasterWidget from '../widget/MasterWidget';
import RawWidget from '../widget/RawWidget';
import BarcodeScanner, { BarcodeScannerResult } from './BarcodeScanner';
import Result from './BarcodeScanner'

class OverlayField extends Component {
  constructor(props) {
    super(props);

    this.state = {
      scanning: false,
      results: [],
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

  _scan = () => {
    this.setState({
      scanning: !this.state.scanning,
    });
  };

  _onDetected = result => {
    this.setState({
      results: this.state.results.concat([result]),
    });
  };

  renderElements = (layout, data, type) => {
    const { disabled } = this.props;
    const elements = layout.elements;
    return elements.map((elem, id) => {
      const widgetData = elem.fields.map(item => data[item.field] || -1);

      if (elem.caption === 'Barcode') {
        console.log('barcodescanner !')
        return (
          <div key={id}>
            <button onClick={this._scan}>{this.state.scanning ? 'Stop' : 'Start'}</button>
            <ul className="results">
              {this.state.results.map(result => (
                <BarcodeScannerResult key={result.codeResult.code} result={result} />
              ))}
            </ul>
            {this.state.scanning ? (
              <BarcodeScanner onDetected={this._onDetected} />
            ) : null}
          </div>
        );
      } else {
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
            {...elem}
          />
        );
      }
    });
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

    return (
      <div
        className="overlay-field js-not-unselect"
        onKeyDown={e => this.handleKeyDown(e)}
        tabIndex={-1}
      >
        {filter
          ? this.renderParameters(layout)
          : layout &&
            layout.elements &&
            this.renderElements(layout, data, type)}
      </div>
    );
  }
}

export default OverlayField;
