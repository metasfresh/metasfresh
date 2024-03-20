import PropTypes from 'prop-types';
import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';

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
}) => {
  const dispatch = useDispatch();
  useEffect(() => {
    let fetchTabRequest = null;
    if (docId && queryOnActivate && onChange) {
      const query = toOrderBysCommaSeparatedString(orderBy);

      fetchTabRequest = dispatch(fetchTab({ tabId, windowId, docId, query }));
      if (singleRowView) {
        fetchTabRequest.then((res) => {
          if (res.length) {
            onChange();
          }
        });
      } else {
        fetchTabRequest.then(() => {
          onChange();
        });
      }
    }

    return () => {
      fetchTabRequest?.cancel?.();
    };
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
};

export default Tab;
