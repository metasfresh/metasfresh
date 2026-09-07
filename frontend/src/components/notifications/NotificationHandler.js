import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';

import Notification from './Notification';
import Prompt from '../app/Prompt';
import { hideAcknowledgeDialog } from '../../actions/AppActions';

const EMPTY_OBJECT = { notifications: {} };

class NotificationHandler extends PureComponent {
  handleAcknowledgeDialogClose = () => {
    this.props.dispatch(hideAcknowledgeDialog());
  };

  render() {
    const { notifications, acknowledgeDialog, children } = this.props;

    return (
      <div>
        <div className="notification-handler">
          {Object.keys(notifications).map((key) => (
            <Notification key={key} item={notifications[key]} />
          ))}
        </div>
        <div className="root-children">{children}</div>
        {/*
          Rendered after {children} on purpose. A process modal carries .screen-freeze, which shares
          z-index $z-index-screen-freeze with .screen-prompt-freeze, so at equal z-index the later DOM
          node wins. Emitted before {children} this dialog would be painted behind any open modal —
          invisible and swallowing clicks — for exactly the case this presentation mode exists to serve:
          a user-friendly exception raised from inside a modal.
        */}
        {acknowledgeDialog && (
          <Prompt
            title={acknowledgeDialog.title}
            text={acknowledgeDialog.text}
            buttons={{ submit: 'OK' }}
            onSubmitClick={this.handleAcknowledgeDialogClose}
            onCancelClick={this.handleAcknowledgeDialogClose}
          />
        )}
      </div>
    );
  }
}

NotificationHandler.propTypes = {
  notifications: PropTypes.object.isRequired,
  acknowledgeDialog: PropTypes.object,
  dispatch: PropTypes.func.isRequired,
  children: PropTypes.element,
};

function mapStateToProps(state) {
  const { appHandler } = state;
  const { notifications, acknowledgeDialog } = appHandler || EMPTY_OBJECT;

  return {
    notifications,
    acknowledgeDialog,
  };
}

export default connect(mapStateToProps)(NotificationHandler);
