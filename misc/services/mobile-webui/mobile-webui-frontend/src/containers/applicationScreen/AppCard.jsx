import React, { Component } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
class AppCard extends Component {
  render() {
    const { captionKey } = this.props;
    return (
      <div className="card">
        <div className="card-content has-text-centered">
          <p className="is-size-4">{counterpart.translate(captionKey)}</p>
        </div>
      </div>
    );
  }
}

AppCard.propTypes = {
  captionKey: PropTypes.string.isRequired,
};

export default AppCard;
