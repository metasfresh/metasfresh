import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    closePrompt,
} from '../../actions/WindowActions';

class Prompt extends Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        // Dirty solution, but use only if you need to
        // there is no way to affect body
        // because body is out of react app range
        // and css dont affect parents
        // but we have to change scope of scrollbar
        document.body.style.overflow = "hidden";
    }
    handleClose = () => {       
        document.body.style.overflow = "auto";
    }
    render() {
        const {isOpen, onCancelClick, onOkClick} = this.props;
        return (
          !!isOpen && <div className="screen-freeze screen-prompt-freeze">
              <div className="panel panel-modal-primary panel-prompt">
                  <div className="panel-modal-header panel-prompt-header">
                      <span className="panel-modal-header-title">New CU-TU allocation</span>
                      <i className="meta-icon-close-1" onClick={this.handleClose}></i>
                  </div>
                  <div className="panel-modal-content panel-prompt-content">
                    <p>Are you sure?</p>
                  </div>
                  <div className="panel-modal-header panel-prompt-header panel-prompt-footer">
                    <div className="prompt-button-wrapper">
                      <span className="btn btn-meta-outline-secondary btn-distance-3 btn-sm" onClick={onCancelClick}>
                          Cancel
                      </span>
                      <span className="btn btn-meta-primary btn-sm btn-submit" onClick={onOkClick}>
                          Save
                      </span>
                    </div>

                  </div>
              </div>
          </div>
        )

    }
}

Prompt.propTypes = {
    onCancelClick: PropTypes.func.isRequired,
    onOkClick: PropTypes.func.isRequired
};

export default Prompt
