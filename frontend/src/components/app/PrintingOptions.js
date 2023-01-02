import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { togglePrintingOption } from '../../actions/WindowActions';

class PrintingOptions extends PureComponent {
  handleClick = (e) => {
    const { togglePrintingOption } = this.props;
    // update the store with the toggled printing value
    togglePrintingOption(e.target.value);
  };
  handleChange = () => false; // just a dummy placeholder that prevents any warnings in the console

  render() {
    const { options } = this.props.printingOptions;

    return (
      <div className="panel-modal-content js-panel-modal-content container-fluid">
        <div className="window-wrapper">
          <div className="document-file-dropzone">
            <div className="sections-wrapper">
              <div className="panel panel-spaced panel-distance panel-bordered panel-primary">
                {options &&
                  options.map((printOptItem) => (
                    <div key={printOptItem.internalName}>
                      <div>&nbsp;</div>

                      <div className="row">
                        <div className="col-lg-6 col-md-6">
                          <label className="input-checkbox">
                            <input
                              type="checkbox"
                              value={printOptItem.internalName}
                              checked={printOptItem.value}
                              onClick={this.handleClick}
                              onChange={this.handleChange}
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
  togglePrintingOption: PropTypes.func.isRequired,
};

const mapStateToProps = (state) => {
  return {
    printingOptions: state.windowHandler.printingOptions,
  };
};

export default connect(mapStateToProps, { togglePrintingOption })(
  PrintingOptions
);
