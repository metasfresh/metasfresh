import counterpart from 'counterpart';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import PropTypes from 'prop-types';

import Avatar from '../app/Avatar';
import Tooltips from '../tooltips/Tooltips';
import { openSelectCurrentWorkplaceModal } from '../../actions/WindowActions';
import { getSettingFromMEAsBoolean } from '../../utils/settings';
import { useWorkplaces } from '../../api/userSession';
import { useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';

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

  render() {
    const { open, me, plugins, shortcut, tooltipOpen, toggleTooltip } =
      this.props;

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
        <AvatarButton
          me={me}
          open={open}
          shortcut={shortcut}
          tooltipOpen={tooltipOpen}
          openDropdownPanel={this.openDropdownPanel}
        />
        {open && (
          <UserDropdownPanel
            me={me}
            plugins={plugins}
            closeDropdownPanel={this.closeDropdownPanel}
            handleKeyDown={this.handleKeyDown}
          />
        )}
      </div>
    );
  }
}

UserDropdown.propTypes = {
  open: PropTypes.bool.isRequired,
  handleUDOpen: PropTypes.func.isRequired,
  redirect: PropTypes.func.isRequired,
  shortcut: PropTypes.string,
  toggleTooltip: PropTypes.func.isRequired,
  tooltipOpen: PropTypes.string,
  me: PropTypes.object.isRequired,
  plugins: PropTypes.array,
};

// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------

const AvatarButton = ({
  me,
  open,
  shortcut,
  tooltipOpen,
  openDropdownPanel,
}) => {
  return (
    <>
      <div
        className="header-item avatar-container"
        onClick={() => openDropdownPanel()}
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
AvatarButton.propTypes = {
  me: PropTypes.object.isRequired,
  open: PropTypes.bool.isRequired,
  shortcut: PropTypes.string,
  tooltipOpen: PropTypes.string,
  openDropdownPanel: PropTypes.func.isRequired,
};

// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------

const UserDropdownPanel = ({
  me,
  plugins,
  closeDropdownPanel,
  handleKeyDown,
}) => {
  const history = useHistory();
  const dispatch = useDispatch();

  const { isWorkplacesEnabled, currentWorkplace } = useWorkplaces({
    includeAvailable: false,
  });

  const isShowOrg = getSettingFromMEAsBoolean(
    me,
    'userDropdown.showOrg',
    false
  );
  const isShowRole = getSettingFromMEAsBoolean(
    me,
    'userDropdown.showRole',
    true
  );

  return (
    <div className="user-dropdown-list" onKeyDown={handleKeyDown}>
      <div className="user-dropdown-item user-dropdown-header-item">
        <span className="meta-text-primary">{me.fullname}</span>
        {isShowOrg && me.orgname && <div>{me.orgname}</div>}
        {isShowRole && me.rolename && <div>{me.rolename}</div>}
        {isWorkplacesEnabled && currentWorkplace?.caption && (
          <div>{currentWorkplace.caption}</div>
        )}
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
      {isWorkplacesEnabled && (
        <UserDropdownItem
          caption={counterpart.translate(
            'userDropdown.changeWorkplace.caption'
          )}
          icon={'meta-icon-settings'}
          onClick={() => {
            dispatch(openSelectCurrentWorkplaceModal());
            closeDropdownPanel();
          }}
        />
      )}
      <UserDropdownItem
        caption={counterpart.translate('window.settings.caption')}
        icon="meta-icon-settings"
        onClick={() => {
          history.push(
            '/window/' + me.userProfileWindowId + '/' + me.userProfileId
          );
          closeDropdownPanel();
        }}
      />
      <UserDropdownPluginItems
        plugins={plugins}
        closeDropdownPanel={closeDropdownPanel}
      />
      <UserDropdownItem
        caption={counterpart.translate('window.logOut.caption')}
        icon="meta-icon-logout"
        onClick={() => {
          history.push('/logout');
          closeDropdownPanel();
        }}
      />
    </div>
  );
};
UserDropdownPanel.propTypes = {
  me: PropTypes.object.isRequired,
  plugins: PropTypes.array,
  closeDropdownPanel: PropTypes.func.isRequired,
  handleKeyDown: PropTypes.func.isRequired,
};

// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------

const UserDropdownPluginItems = ({ plugins, closeDropdownPanel }) => {
  const history = useHistory();

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
            history.push(plugin.userDropdownLink.url);
            closeDropdownPanel();
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
UserDropdownPluginItems.propTypes = {
  plugins: PropTypes.array,
  closeDropdownPanel: PropTypes.func.isRequired,
};

// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------

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

// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------

export default onClickOutside(UserDropdown);
