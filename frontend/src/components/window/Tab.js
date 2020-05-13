import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import {
  addRowData,
  updateMasterData,
  fetchTab,
} from '../../actions/WindowActions';

/*
 * @TODO: I think this can safely be rewritten to a functional component
 *
 */
class Tab extends Component {
  constructor(props) {
    super(props);

    const {
      fetchTab,
      updateMasterData,
      addRowData,
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
        fetchTab({ tabId, windowType: windowId, docId, query }).then((res) => {
          if (res.length) {
            updateMasterData(res[0]);
            addRowData({ [tabId]: res }, 'master');
            onChange && onChange();
          }
        });
      } else {
        fetchTab({ tabId, windowType: windowId, docId, query }).then((res) => {
          addRowData({ [tabId]: res }, 'master');
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
  onChange: PropTypes.func,
  singleRowView: PropTypes.bool,
  windowId: PropTypes.string,
  tabId: PropTypes.string,
  docId: PropTypes.string,
  queryOnActivate: PropTypes.bool,
  orderBy: PropTypes.array,
  fetchTab: PropTypes.func.isRequired,
  addRowData: PropTypes.func.isRequired,
  updateMasterData: PropTypes.func.isRequired,
};

export { Tab };
export default connect(
  null,
  {
    fetchTab,
    updateMasterData,
    addRowData,
  }
)(Tab);
