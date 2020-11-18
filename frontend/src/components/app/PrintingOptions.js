import React, { PureComponent } from 'react';
class PrintingOptions extends PureComponent {
  render() {
    return (
      <div className="panel-modal-content js-panel-modal-content container-fluid">
        <div className="window-wrapper">
          <div className="document-file-dropzone">
            <div className="sections-wrapper">
              <div className="panel panel-spaced panel-distance panel-bordered panel-primary">
                <div>&nbsp;</div>

                <div className="row">
                  <div className="col-lg-6 col-md-6">
                    <label className="checkbox-inline">
                      <input type="checkbox" value="option1" />
                      Print logo
                    </label>
                  </div>
                </div>
                <div>&nbsp;</div>
                <div className="row">
                  <div className="col-lg-6 col-md-6">
                    <label className="checkbox-inline">
                      <input type="checkbox" value="option2" />
                      Print totals
                    </label>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default PrintingOptions;
