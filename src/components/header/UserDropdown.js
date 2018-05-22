import counterpart from 'counterpart';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';

import Avatar from '../app/Avatar';
import Tooltips from '../tooltips/Tooltips';

class UserDropdown extends Component {
  constructor(props) {
    super(props);
  }

  handleClickOutside = () => this.props.onUDOpen(false);

  handleKeyDown = e => {
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

  render() {
    const {
      open,
      onUDOpen,
      redirect,
      shortcut,
      toggleTooltip,
      tooltipOpen,
      me,
    } = this.props;
    return (
      <div
        className={
          'header-item-container ' +
          'pointer user-dropdown-container tooltip-parent ' +
          (open ? 'header-item-open ' : '')
        }
        onMouseEnter={() => toggleTooltip(shortcut)}
        onMouseLeave={() => toggleTooltip('')}
      >
        <div
          className="header-item avatar-container"
          onClick={() => onUDOpen(true)}
        >
          <Avatar id={me.avatarId} />
        </div>

        {open && (
          <div className="user-dropdown-list" onKeyDown={this.handleKeyDown}>
            <div className="user-dropdown-item user-dropdown-header-item meta-text-primary">
              {me.fullname}
            </div>
            <hr className="context-menu-separator" />
            {
              // Placeholder, empty place, to keep focus when it is
              // not needed (e.g when mouse is in use)
              // It is always returning back there due to ref action
            }
            <div
              ref={c => c && c.focus()}
              tabIndex={0}
              className="js-selection-placeholder"
            />
            <div
              className="user-dropdown-item"
              onClick={() => {
                redirect(
                  '/window/' + me.userProfileWindowId + '/' + me.userProfileId
                );
                onUDOpen(false);
                toggleTooltip('');
              }}
              tabIndex={0}
            >
              <i className="meta-icon-settings" />
              {counterpart.translate('window.settings.caption')}
            </div>
            <div
              className="user-dropdown-item"
              onClick={() => {
                redirect('/logout');
                onUDOpen(false);
              }}
              tabIndex={0}
            >
              <i className="meta-icon-logout" />
              {counterpart.translate('window.logOut.caption')}
            </div>
          </div>
        )}
        {tooltipOpen === shortcut &&
          !open && (
            <Tooltips
              name={shortcut}
              action={counterpart.translate('mainScreen.userMenu.tooltip')}
              type={''}
            />
          )}
      </div>
    );
  }
}

export default onClickOutside(UserDropdown);
