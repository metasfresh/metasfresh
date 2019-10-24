import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import {
  addRowData,
  updateMasterData,
  getTab,
} from '../../actions/WindowActions';

class Tab extends Component {
  constructor(props) {
    super(props);

    const {
      dispatch,
      tabId,
      windowId,
      onChange,
      queryOnActivate,
      singleRowView,
      docId,
      orderBy,
    } = this.props;

    if (docId && queryOnActivate) {
      const query = orderBy
        ? (orderBy[0].ascending ? '+' : '-') + orderBy[0].fieldName
        : '';

      if (singleRowView) {
        getTab(tabId, windowId, docId).then(res => {
          if (res.length) {
            dispatch(updateMasterData(res[0]));
            dispatch(addRowData({ [tabId]: res }, 'master'));
            onChange && onChange();
          }
        });
      } else {
        getTab(tabId, windowId, docId, query).then(res => {
          dispatch(addRowData({ [tabId]: res }, 'master'));
          onChange && onChange();
        });
      }
    }
  }

  render() {
    const { children } = this.props;

    return <div className="row">{children}</div>;
  }
}

Tab.propTypes = {
  dispatch: PropTypes.func.isRequired,
  onChange: PropTypes.func,
  children: PropTypes.any,
  singleRowView: PropTypes.bool,
  windowId: PropTypes.string,
  tabId: PropTypes.string,
  docId: PropTypes.string,
};

export default connect()(Tab);
