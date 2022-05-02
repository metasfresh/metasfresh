import PropTypes from 'prop-types';
import React, { useEffect } from 'react';
import { connect } from 'react-redux';

import { fetchTab } from '../../actions/WindowActions';
import { toOrderBysCommaSeparatedString } from '../../utils/windowHelpers';

const Tab = ({
  children,
  onChange,
  singleRowView,
  windowId,
  tabId,
  docId,
  queryOnActivate,
  orderBy,
  fetchTab,
}) => {
  useEffect(() => {
    if (docId && queryOnActivate) {
      const query = toOrderBysCommaSeparatedString(orderBy);

      if (singleRowView) {
        fetchTab({ tabId, windowId, docId, query }).then((res) => {
          if (res.length) {
            onChange && onChange();
          }
        });
      } else {
        fetchTab({ tabId, windowId, docId, query }).then(() => {
          onChange && onChange();
        });
      }
    }
  }, [
    docId,
    queryOnActivate,
    singleRowView,
    windowId,
    tabId,
    orderBy,
    onChange,
  ]);

  return <div className="row table-wrapper">{children}</div>;
};

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
};

export { Tab };
export default connect(null, {
  fetchTab,
})(Tab);
