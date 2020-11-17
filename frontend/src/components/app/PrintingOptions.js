import React, { PureComponent } from 'react';

class PrintingOptions extends PureComponent {
  render() {
    return (
      <div className="panel-modal-content js-panel-modal-content container-fluid">
        <div className="window-wrapper">
          <div className="document-file-dropzone">
            <div className="sections-wrapper">
              <div className="panel panel-spaced panel-distance panel-bordered panel-primary">
                <div className="row">Options</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default PrintingOptions;
