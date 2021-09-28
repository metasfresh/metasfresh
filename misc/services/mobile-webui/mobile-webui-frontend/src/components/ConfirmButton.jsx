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
    const { onConfirmExec, wfProcessId, activityId: wfActivityId } = this.props;
    onConfirmExec({ token: window.config.API_TOKEN, wfProcessId, wfActivityId });
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
            <article className="message confirm-box is-dark">
              <div className="message-header">
                <p>{btnCaption}</p>
                <button className="delete" aria-label="delete" onClick={this.cancelConfirmDialog}></button>
              </div>
              <div className="message-body">
                <strong>{promptQuestion}</strong>
                <div>&nbsp;</div>
                <div className="buttons is-centered">
                  <button className="button is-success confirm-button" onClick={this.onDialogYes}>
                    Yes
                  </button>
                  <button className="button is-danger confirm-button" onClick={this.cancelConfirmDialog}>
                    No
                  </button>
                </div>
              </div>
            </article>
          </div>
        )}
        {/*  Confirm Initiator  */}
        <div className="ml-3 mr-3 is-light launcher" onClick={this.showConfirmDialog}>
          <div className="box">
            <div className="columns is-mobile">
              <div className="column is-12">
                <div className="columns">
                  <div className="column is-size-4-mobile no-p">{btnCaption}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
  1;
}

ConfirmButton.propTypes = {
  caption: PropTypes.string,
  componentProps: PropTypes.object,
  onConfirmExec: PropTypes.func,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
};

export default ConfirmButton;
