import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import onClickOutside from 'react-onclickoutside';
import { Manager, Reference, Popper } from 'react-popper';

import iconHelp from '../../assets/images/tooltip-help.png';
import iconText from '../../assets/images/tooltip-text.png';

class WidgetTooltip extends PureComponent {
  /*
   * Alternative method to open dropdown, in case of disabled opening
   * on focus.
   */
  handleClick = () => {
    const { onToggle } = this.props;

    onToggle();
  };

  handleClickOutside() {
    const { onToggle } = this.props;

    onToggle(false);
  }

  render() {
    const { isToggled, widget, data } = this.props;

    return (
      <Manager>
        <div className="widget-tooltip" tabIndex={-1}>
          <Reference>
            {({ ref }) => (
              <img
                src={widget.tooltipIconName === 'text' ? iconText : iconHelp}
                onClick={this.handleClick}
                ref={ref}
              />
            )}
          </Reference>
          {isToggled && (
            <Popper placement="auto-start">
              {({ ref, style, placement }) => (
                <div
                  ref={ref}
                  style={style}
                  data-placement={placement}
                  className="tooltip-wrapp tooltip-content"
                >
                  <span>{data.value}</span>
                </div>
              )}
            </Popper>
          )}
        </div>
      </Manager>
    );
  }
}

WidgetTooltip.propTypes = {
  widget: PropTypes.any.isRequired,
  data: PropTypes.any.isRequired,
  isToggled: PropTypes.bool.isRequired,
  onToggle: PropTypes.func,
};

export default onClickOutside(WidgetTooltip);
