import React, { Component } from 'react';

class Link extends Component {
  constructor(props) {
    super(props);
  }

  handleClick = url => {
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
          onClick={() => this.handleClick(widgetData[0].value)}
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

export default Link;
