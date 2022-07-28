import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';

import { fetchTab } from '../../actions/WindowActions';

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
      if (singleRowView) {
        fetchTab({ tabId, windowId, docId, orderBy }).then((res) => {
          if (res.length) {
            onChange && onChange();
          }
        });
      } else {
        fetchTab({ tabId, windowId, docId, orderBy }).then(() => {
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
