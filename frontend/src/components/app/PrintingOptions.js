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
                  <div key={printOptItem.internalName}>
                    <div>&nbsp;</div>

                    <div className="row">
                      <div className="col-lg-6 col-md-6">
                        <label className="input-checkbox">
                          <input
                            type="checkbox"
                            value="printOptItem.value"
                            checked={printOptItem.value}
                          />
                          &nbsp;&nbsp; {printOptItem.caption}
                          <span className="input-checkbox-tick" />
                        </label>
                      </div>
                    </div>
                  </div>
                ))}
                <div>&nbsp;</div>
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
