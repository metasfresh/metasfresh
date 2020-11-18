import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
class PrintingOptions extends PureComponent {
  render() {
    const { options } = this.props.printingOptions;
    console.log(options);
    return (
      <div className="panel-modal-content js-panel-modal-content container-fluid">
        <div className="window-wrapper">
          <div className="document-file-dropzone">
            <div className="sections-wrapper">
              <div className="panel panel-spaced panel-distance panel-bordered panel-primary">
                {options.map((printOptItem) => (
                  <div key={printOptItem.caption}>
                    <div>&nbsp;</div>

                    <div className="row">
                      <div className="col-lg-6 col-md-6">
                        <label className="checkbox-inline">
                          <input type="checkbox" value="option1" />
                          {printOptItem.caption}
                        </label>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

PrintingOptions.propTypes = {
  printingOptions: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => {
  return {
    printingOptions: state.windowHandler.printingOptions,
  };
};

export default connect(mapStateToProps)(PrintingOptions);
