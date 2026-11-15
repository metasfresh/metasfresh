import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

export default class Password extends PureComponent {
  /**
   * @method handleFocus
   * @summary Runs the widget's own focus handler first (it marks the widget focused, so the
   * first keystroke is not lost), then selects the content: typing then replaces the shown
   * mask `********` instead of appending to it.
   */
  handleFocus = (e) => {
    const { widgetProperties } = this.props;

    widgetProperties.onFocus && widgetProperties.onFocus(e);
    e.target.select();
  };

  render() {
    const {
      getClassNames,
      widgetProperties,
      icon,
      allowShowPassword,
      onSetWidgetType,
    } = this.props;

    return (
      <div className="input-inner-container">
        <div
          className={classnames(
            getClassNames({
              icon: true,
            })
          )}
        >
          <input
            {...widgetProperties}
            type="password"
            onFocus={this.handleFocus}
          />
          {icon && <i className="meta-icon-edit input-icon-right" />}
        </div>
        {allowShowPassword && (
          <div
            onMouseDown={() => onSetWidgetType('text')}
            onMouseUp={() => onSetWidgetType('password')}
            className="btn btn-icon btn-meta-outline-secondary btn-inline pointer btn-distance-rev btn-sm"
          >
            <i className="meta-icon-show" />
          </div>
        )}
      </div>
    );
  }
}

Password.propTypes = {
  icon: PropTypes.string,
  allowShowPassword: PropTypes.bool,
  widgetProperties: PropTypes.object.isRequired,
  getClassNames: PropTypes.func.isRequired,
  onSetWidgetType: PropTypes.func.isRequired,
};
