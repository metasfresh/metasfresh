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
    const { onToggle, fieldName } = this.props;

    onToggle(fieldName);
  };

  handleClickOutside() {
    const { fieldName, onToggle, isToggled } = this.props;

    if (isToggled) {
      onToggle(fieldName, false);
    }
  }

  render() {
    const { isToggled, widget, data } = this.props;
    const modifiers = {
      preventOverflow: {
        enabled: false,
        boundariesElement: 'viewport',
        escapeWithReference: true,
      },
      flip: {
        enabled: true,
        boundariesElement: 'viewport',
      },
    };

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
            <Popper
              placement="right-start"
              modifiers={modifiers}
              outOfBoundaries={true}
            >
              {({ ref, style, placement }) => (
                <div
                  ref={ref}
                  style={style}
                  data-placement={placement}
                  className="tooltip-content"
                  modifiers={modifiers}
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
  fieldName: PropTypes.string.isRequired,
  isToggled: PropTypes.bool.isRequired,
  onToggle: PropTypes.func,
};

export default onClickOutside(WidgetTooltip);
