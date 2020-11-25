import React, { Component } from 'react';
import InlineTab from './InlineTab';
import PropTypes from 'prop-types';

class InlineTabWrapper extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { caption } = this.props;
    return (
      <div className="inline-tab-wrapper">
        <span>{caption}</span>
        <InlineTab {...this.props} />
      </div>
    );
  }
}

InlineTabWrapper.propTypes = {
  caption: PropTypes.string.isRequired,
};

export default InlineTabWrapper;
