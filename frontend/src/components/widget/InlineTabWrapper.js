import React, { Component } from 'react';
import InlineTab from './InlineTab';
import PropTypes from 'prop-types';
import { fetchTab } from '../../actions/WindowActions';
import { connect } from 'react-redux';

class InlineTabWrapper extends Component {
  constructor(props) {
    super(props);
    const query = '';
    const {
      inlineTab: { windowId, tabId },
      dataId: docId,
      fetchTab,
    } = props;
    fetchTab({ tabId, windowId, docId, query });
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
  inlineTab: PropTypes.object.isRequired,
  dataId: PropTypes.string.isRequired,
  fetchTab: PropTypes.func.isRequired,
};

export default connect(
  null,
  {
    fetchTab,
  }
)(InlineTabWrapper);
