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
    }

    renderCancelButton = () => {
      return(
        <span className="btn btn-meta-outline-secondary btn-distance-3 btn-sm" onClick={(e) => this.props.onCancelClick(e)}>
            {this.props.buttons.cancel}
        </span>
      )
    }
    renderSubmitButton = () => {
      return(
        <span className="btn btn-meta-primary btn-sm btn-submit" onClick={(e) => this.props.onSubmitClick(e)}>
            {this.props.buttons.submit}
        </span>
      )
    }
    render() {
        const {onCancelClick, onSubmitClick} = this.props;
        const {cancel, submit} = this.props.buttons;
        return (
          <div className="screen-freeze screen-prompt-freeze">
              <div className="panel panel-modal-primary panel-prompt prompt-shadow">
                  <div className="panel-modal-header panel-prompt-header">
                      <span className="panel-modal-header-title">{this.props.title}</span>
                      <i className="meta-icon-close-1" onClick={(e) => this.props.onCancelClick(e)}></i>
                  </div>
                  <div className="panel-modal-content panel-prompt-content">
                    <p>{this.props.text}</p>
                  </div>
                  <div className="panel-modal-header panel-prompt-header panel-prompt-footer">
                    <div className="prompt-button-wrapper">
                      {cancel ? this.renderCancelButton() : ""}
                      {submit ? this.renderSubmitButton() : ""}
                    </div>
                  </div>
              </div>
          </div>
        )
    }
}

Prompt.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Prompt = connect()(Prompt)

export default Prompt
