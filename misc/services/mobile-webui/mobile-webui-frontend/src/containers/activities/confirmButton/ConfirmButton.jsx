import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import { selectWFProcessState } from '../../../reducers/wfProcesses_status/index';

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
    onConfirmExec({ wfProcessId, wfActivityId });
    // TODO !!! : deal with the response
  };

  render() {
    console.log(this.props);
    const {
      caption,
      componentProps,
      dataStored: { isUserEditable },
    } = this.props;
    const { isPromptDialogOpen } = this.state;

    const btnCaption = caption || 'Confirm';
    const promptQuestion = componentProps.promptQuestion || 'Are you sure?';

    return (
      <div>
        {/*  Full screen confirm dialog  */}
        {isPromptDialogOpen && (
          <div className="prompt-dialog-screen">
            <article className="message confirm-box is-dark">
              <div className="message-header">
                <p>{btnCaption}</p>
                <button className="delete" aria-label="delete" onClick={this.cancelConfirmDialog} />
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
        <div>
          <button
            className="button is-outlined complete-btn"
            onClick={this.showConfirmDialog}
            disabled={!isUserEditable}
          >
            {btnCaption}
          </button>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { wfProcessId, activityId } = ownProps;
  const workflowStatus = selectWFProcessState(state, wfProcessId);
  const dataStored = workflowStatus.activities[activityId] ? workflowStatus.activities[activityId].dataStored : {};

  return {
    dataStored,
  };
};

ConfirmButton.propTypes = {
  caption: PropTypes.string,
  componentProps: PropTypes.object,
  onConfirmExec: PropTypes.func,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  dataStored: PropTypes.object,
};

export default connect(mapStateToProps, null)(ConfirmButton);
