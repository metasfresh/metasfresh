import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import onClickOutside from 'react-onclickoutside';
import { Manager, Popper, Reference } from 'react-popper';

import iconHelp from '../../assets/images/tooltip-help.png';
import iconText from '../../assets/images/tooltip-text.png';

class WidgetTooltip extends PureComponent {
  handleClick = () => {
    const { onToggle } = this.props;
    onToggle(null); // toggle popup
  };

  handleClickOutside() {
    const { onToggle, isToggled } = this.props;

    if (isToggled) {
      onToggle(false);
    }
  }

  render() {
    const { isToggled, iconName } = this.props;

    return (
      <Manager>
        <div className="widget-tooltip" tabIndex={-1}>
          <Reference>
            {({ ref }) => (
              <img
                src={iconName === 'text' ? iconText : iconHelp}
                onClick={this.handleClick}
                ref={ref}
                alt="icon"
              />
            )}
          </Reference>
          {isToggled && this.renderPopper()}
        </div>
      </Manager>
    );
  }

  renderPopper = () => {
    const { text } = this.props;

    return (
      <Popper
        placement="right-start"
        modifiers={[
          {
            name: 'flip',
            enabled: true,
            options: {
              rootBoundary: 'viewport',
            },
          },
        ]}
        outOfBoundaries={true}
      >
        {({ ref, style, placement }) => (
          <div
            ref={ref}
            style={style}
            data-placement={placement}
            className="tooltip-content"
          >
            <span>{text || ''}</span>
          </div>
        )}
      </Popper>
    );
  };
}

WidgetTooltip.propTypes = {
  iconName: PropTypes.string,
  text: PropTypes.string,
  isToggled: PropTypes.bool.isRequired,
  onToggle: PropTypes.func.isRequired,
};

export default onClickOutside(WidgetTooltip);
