import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';

import MasterWidget from '../components/widget/MasterWidget';
import RawWidget from '../components/widget/RawWidget';

/**
 * @file Class based component.
 * @module WidgetWrapper
 * @extends PureComponent
 */
class WidgetWrapper extends PureComponent {
  render() {
    const { renderMaster } = this.props;

    if (renderMaster) {
      return <MasterWidget {...this.props} />;
    } else {
      return <RawWidget {...this.props} />;
    }
  }
}

const mapStateToProps = (state, props) => {
  const { windowHandler } = state;
  const { dataSource, fields } = props;
  const { data } = windowHandler.master;

  let widgetData = null;

  switch (dataSource) {
    case 'doc-status':
      widgetData = [
        {
          status: data.DocStatus || null,
          action: data.DocAction || null,
          displayed: true,
        },
      ];

      break;

    case 'element':
      widgetData = fields.reduce((result, item) => {
        data[item.field] && result.push(data[item.field]);

        return result;
      }, []);
      break;
  }

  return {
    widgetData: widgetData,
  };
};

WidgetWrapper.props = {
  renderMaster: PropTypes.bool,
  dataSource: PropTypes.string.isRequired,
};

export default connect(
  mapStateToProps,
  null
)(WidgetWrapper);
