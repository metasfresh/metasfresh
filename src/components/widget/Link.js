import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

export default class Link extends PureComponent {
  handleClick = () => {
    const { widgetData } = this.props;
    const url = widgetData[0].value;
    window.open(url, '_blank');
  };

  render() {
    const {
      getClassNames,
      isEdited,
      widgetProperties,
      icon,
      widgetData,
    } = this.props;
    return (
      <div className="input-inner-container">
        <div className={getClassNames() + (isEdited ? 'input-focused ' : '')}>
          <input {...widgetProperties} type="text" />
          {icon && <i className="meta-icon-edit input-icon-right" />}
        </div>
        <div
          onClick={this.handleClick}
          className={
            'btn btn-icon btn-meta-outline-secondary btn-inline ' +
            'pointer btn-distance-rev btn-sm ' +
            (!widgetData[0].validStatus.valid || widgetData[0].value === ''
              ? 'btn-disabled btn-meta-disabled'
              : '')
          }
        >
          <i className="meta-icon-link" />
        </div>
      </div>
    );
  }
}

Link.propTypes = {
  getClassNames: PropTypes.func,
  widgetData: PropTypes.array,
  isEdited: PropTypes.bool,
  widgetProperties: PropTypes.object,
  icon: PropTypes.any,
};
