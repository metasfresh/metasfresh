import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';

import { fetchTab } from '../../actions/WindowActions';
<<<<<<< HEAD
import { toOrderBysCommaSeparatedString } from '../../utils/windowHelpers';
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

class Tab extends PureComponent {
  constructor(props) {
    super(props);

    const {
      fetchTab,
      tabId,
      windowId,
      onChange,
      queryOnActivate,
      singleRowView,
      docId,
      orderBy,
    } = this.props;

    if (docId && queryOnActivate) {
<<<<<<< HEAD
      const query = toOrderBysCommaSeparatedString(orderBy);

      if (singleRowView) {
        fetchTab({ tabId, windowId, docId, query }).then((res) => {
          if (res.length) {
=======
      if (singleRowView) {
        fetchTab({ tabId, windowId, docId, orderBy }).then((rows) => {
          if (rows.length) {
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
            onChange && onChange();
          }
        });
      } else {
<<<<<<< HEAD
        fetchTab({ tabId, windowId, docId, query }).then(() => {
=======
        fetchTab({ tabId, windowId, docId, orderBy }).then(() => {
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
};

export { Tab };
export default connect(null, {
  fetchTab,
})(Tab);
