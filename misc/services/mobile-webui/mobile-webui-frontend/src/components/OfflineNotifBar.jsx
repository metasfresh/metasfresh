import React, { Component } from 'react';
import counterpart from 'counterpart';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

class OfflineNotifBar extends Component {
  render() {
    const { network, headerKey, captionKey } = this.props;

    return (
      <div>
        {!network && (
          <article className="message is-warning">
            <div className="message-header">
              <p>{counterpart.translate(`${headerKey}`)}</p>
            </div>
            <div className="message-body">{counterpart.translate(`${captionKey}`)}</div>
          </article>
        )}
      </div>
    );
  }
}

OfflineNotifBar.propTypes = {
  network: PropTypes.bool.isRequired,
  headerKey: PropTypes.string.isRequired,
  captionKey: PropTypes.string.isRequired,
};

const mapStateToProps = (state) => {
  return {
    network: state.appHandler.network,
  };
};

export default connect(mapStateToProps, null)(OfflineNotifBar);
