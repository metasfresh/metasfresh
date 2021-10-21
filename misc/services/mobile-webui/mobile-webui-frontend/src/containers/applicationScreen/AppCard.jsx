import React, { Component } from 'react';
import PropTypes from 'prop-types';

class AppCard extends Component {
  render() {
    const { caption } = this.props;
    return (
      <div className="card">
        <div className="card-content has-text-centered">
          <p className="is-size-4">{caption}</p>
        </div>
      </div>
    );
  }
}

AppCard.propTypes = {
  caption: PropTypes.string.isRequired,
};

export default AppCard;
