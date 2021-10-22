import React, { Component } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
class AppCard extends Component {
  handleAppClick = () => {
    const { push, appId } = this.props;
    push(`/launchers/${appId}`);
  };

  render() {
    const { appId, captionKey } = this.props;
    return (
      <div className="card" onClick={() => this.handleAppClick(appId)}>
        <div className="card-content has-text-centered">
          <p className="is-size-4">{counterpart.translate(captionKey)}</p>
        </div>
      </div>
    );
  }
}

AppCard.propTypes = {
  captionKey: PropTypes.string.isRequired,
  appId: PropTypes.string.isRequired,
  push: PropTypes.func.isRequired,
};

export default connect(null, { push })(AppCard);
