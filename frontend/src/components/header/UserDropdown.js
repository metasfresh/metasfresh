import counterpart from 'counterpart';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import PropTypes from 'prop-types';

import Avatar from '../app/Avatar';
import Tooltips from '../tooltips/Tooltips';
import { getSettingFromMEAsBoolean } from '../../utils/settings';

/**
 * @file Class based component.
 * @module UserDropdown
 * @extends Component
 */
class UserDropdown extends Component {
  handleClickOutside = () => this.closeDropdownPanel();

  openDropdownPanel = () => {
    const { handleUDOpen } = this.props;
    handleUDOpen(true);
  };

  closeDropdownPanel = () => {
    const { handleUDOpen, toggleTooltip } = this.props;
    handleUDOpen(false);
    toggleTooltip('');
  };

  handleKeyDown = (e) => {
    switch (e.key) {
      case 'ArrowDown': {
        e.preventDefault();
        const activeElem = document.activeElement;
        if (activeElem.nextSibling) {
          activeElem.nextSibling.focus();
        }
        break;
      }
      case 'ArrowUp': {
        e.preventDefault();
        const activeElem = document.activeElement;
        // When focus pulled out once, do not allow to get there
        if (
          activeElem.previousSibling.classList.contains(
            'js-selection-placeholder'
          )
        ) {
          return;
        }
        if (activeElem.previousSibling) {
          activeElem.previousSibling.focus();
        }
        break;
      }
      case 'Enter':
        e.preventDefault();
        document.activeElement.click();
        break;
      case 'Escape':
        e.preventDefault();
        this.handleClickOutside();
        break;
    }
  };

  renderPlugins = () => {
    const { plugins, redirect } = this.props;

    if (!plugins || plugins.length <= 0) return null;

    const menuOptions = [];
    plugins.forEach((plugin, i) => {
      if (plugin.userDropdownLink) {
        menuOptions.push(
          <UserDropdownItem
            key={`menu-item-${i}`}
            caption={plugin.userDropdownLink.text}
            icon="meta-icon-settings"
            onClick={() => {
              redirect(plugin.userDropdownLink.url);
              this.closeDropdownPanel();
            }}
          />
        );
      }
    });

    if (menuOptions.length <= 0) return null;

    return (
      <>
        <hr className="context-menu-separator" />
        {menuOptions}
        <hr className="context-menu-separator" />
      </>
    );
  };

  renderDropdownPanel = () => {
    const { me, redirect } = this.props;

    const isShowOrg = getSettingFromMEAsBoolean(
      me,
      'userDropdown.showOrg',
      false
    );

    return (
      <div className="user-dropdown-list" onKeyDown={this.handleKeyDown}>
        <div className="user-dropdown-item user-dropdown-header-item">
          <span className="meta-text-primary">{me.fullname}</span>
          <br />
          {isShowOrg && me.orgname && <span>({me.orgname})</span>}
        </div>
        <hr className="context-menu-separator" />
        {
          // Placeholder, empty place, to keep focus when it is
          // not needed (e.g when mouse is in use)
          // It is always returning back there due to ref action
        }
        <div
          ref={(c) => c && c.focus()}
          tabIndex={0}
          className="js-selection-placeholder"
        />
        <UserDropdownItem
          caption={counterpart.translate('window.settings.caption')}
          icon="meta-icon-settings"
          onClick={() => {
            redirect(
              '/window/' + me.userProfileWindowId + '/' + me.userProfileId
            );
            this.closeDropdownPanel();
          }}
        />
        {this.renderPlugins()}
        <UserDropdownItem
          caption={counterpart.translate('window.logOut.caption')}
          icon="meta-icon-logout"
          onClick={() => {
            redirect('/logout');
            this.closeDropdownPanel();
          }}
        />
      </div>
    );
  };

  renderHeaderButton = () => {
    const { open, shortcut, tooltipOpen, me } = this.props;
    return (
      <>
        <div
          className="header-item avatar-container"
          onClick={() => this.openDropdownPanel()}
        >
          <Avatar id={me.avatarId} />
        </div>
        {tooltipOpen === shortcut && !open && (
          <Tooltips
            name={shortcut}
            action={counterpart.translate('mainScreen.userMenu.tooltip')}
            type={''}
          />
        )}
      </>
    );
  };

  render() {
    const { open, shortcut, toggleTooltip } = this.props;

    return (
      <div
        className={
          'header-item-container ' +
          'js-not-unselect ' +
          'pointer user-dropdown-container tooltip-parent ' +
          (open ? 'header-item-open ' : '')
        }
        onMouseEnter={() => toggleTooltip(shortcut)}
        onMouseLeave={() => toggleTooltip('')}
      >
        {this.renderHeaderButton()}
        {open && this.renderDropdownPanel()}
      </div>
    );
  }
}

UserDropdown.propTypes = {
  open: PropTypes.bool,
  handleUDOpen: PropTypes.func,
  redirect: PropTypes.func,
  shortcut: PropTypes.string,
  toggleTooltip: PropTypes.func,
  tooltipOpen: PropTypes.string,
  me: PropTypes.object,
  plugins: PropTypes.array,
};

const UserDropdownItem = ({ icon, caption, onClick }) => {
  return (
    <div className="user-dropdown-item" onClick={onClick} tabIndex={0}>
      <i className={icon} />
      {caption}
    </div>
  );
};
UserDropdownItem.propTypes = {
  icon: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default onClickOutside(UserDropdown);
