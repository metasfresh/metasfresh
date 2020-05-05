import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import {
  addRowData,
  updateMasterData,
  fetchTab,
} from '../../actions/WindowActions';
import { getTab } from '../../api';

/*
 * @TODO: I think this can safely be rewritten to a functional component, and probably
 * also disconnected from the store.
 *
 */
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

      dispatch(fetchTab(tabId, windowId, docId));

      if (singleRowView) {
        getTab(tabId, windowId, docId).then((res) => {
          if (res.length) {
            dispatch(updateMasterData(res[0]));
            dispatch(addRowData({ [tabId]: res }, 'master'));
            onChange && onChange();
          }
        });
      } else {
        getTab(tabId, windowId, docId, query).then((res) => {
          dispatch(addRowData({ [tabId]: res }, 'master'));
          onChange && onChange();
        });
      }
    }
  }

  render() {
    const { children } = this.props;

    return <div className="row table-wrapper">{children}</div>;
  }
}

Tab.propTypes = {
  children: PropTypes.any,
  dispatch: PropTypes.func.isRequired,
  onChange: PropTypes.func,
  singleRowView: PropTypes.bool,
  windowId: PropTypes.string,
  tabId: PropTypes.string,
  docId: PropTypes.string,
  queryOnActivate: PropTypes.bool,
  orderBy: PropTypes.array,
};

export { Tab };
export default connect()(Tab);
