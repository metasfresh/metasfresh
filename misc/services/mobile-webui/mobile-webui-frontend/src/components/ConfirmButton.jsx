import React, { Component } from 'react';
import PropTypes from 'prop-types';

class ConfirmButton extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isPromptDialogOpen: false,
    };
  }

  showConfirmDialog = () => this.setState((prevState) => ({ isPromptDialogOpen: !prevState.isPromptDialogOpen }));

  cancelConfirmDialog = () => this.setState({ isPromptDialogOpen: false });

  /**
   * Execute any function passed as onConfirm prop
   */
  onDialogYes = () => {
    const { onConfirmExec } = this.props;
    onConfirmExec({ token: window.config.API_TOKEN });
    // TODO !!! : deal with the response
  };

  render() {
    const { caption, componentProps } = this.props;
    const { isPromptDialogOpen } = this.state;
    const btnCaption = caption || 'Confirm';
    const promptQuestion = componentProps.promptQuestion || 'Are you sure?';

    return (
      <div>
        {/*  Full sconfirm creen dialog  */}
        {isPromptDialogOpen && (
          <div className="prompt-dialog-screen">
            <article className="message is-dark">
              <div className="message-header">
                <p>{btnCaption}</p>
                <button className="delete" aria-label="delete" onClick={this.cancelConfirmDialog}></button>
              </div>
              <div className="message-body">
                <strong>{promptQuestion}</strong>
                <div className="buttons">
                  <button className="button is-success" onClick={this.onDialogYes}>
                    Yes
                  </button>
                  <button className="button is-danger" onClick={this.cancelConfirmDialog}>
                    No
                  </button>
                </div>
              </div>
            </article>
          </div>
        )}
        {/*  Confirm Initiator  */}
        <button className="button confirm-btn-green" onClick={this.showConfirmDialog}>
          {btnCaption}
        </button>
      </div>
    );
  }
  1;
}

ConfirmButton.propTypes = {
  caption: PropTypes.string,
  componentProps: PropTypes.object,
  onConfirmExec: PropTypes.func,
};

export default ConfirmButton;
