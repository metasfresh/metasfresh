import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import { addRowData, getTab } from '../../actions/WindowActions';

class Tab extends Component {
  constructor(props) {
    super(props);

    const {
      dispatch,
      tabId,
      windowType,
      queryOnActivate,
      docId,
      orderBy,
    } = this.props;

    if (docId && queryOnActivate) {
      const query = orderBy
        ? (orderBy[0].ascending ? '+' : '-') + orderBy[0].fieldName
        : '';

      getTab(tabId, windowType, docId, query).then(res => {
        dispatch(addRowData({ [tabId]: res }, 'master'));
      });
    }
  }

  render() {
    const { children } = this.props;

    return <div className="table-flex-wrapper">{children}</div>;
  }
}

Tab.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

export default connect()(Tab);
