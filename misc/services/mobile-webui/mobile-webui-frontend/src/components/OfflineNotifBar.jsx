import React, { Component } from 'react';
import counterpart from 'counterpart';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

class OfflineNotifBar extends Component {
  render() {
    const { network } = this.props;

    return (
      <div>
        {!network && (
          <article className="message is-warning">
            <div className="message-header">
              <p>{counterpart.translate('login.offlineMsgHeader')}</p>
            </div>
            <div className="message-body">{counterpart.translate('login.offlineMsgContent')}</div>
          </article>
        )}
      </div>
    );
  }
}

OfflineNotifBar.propTypes = {
  network: PropTypes.bool.isRequired,
};

const mapStateToProps = (state) => {
  return {
    network: state.appHandler.network,
  };
};

export default connect(mapStateToProps, null)(OfflineNotifBar);
