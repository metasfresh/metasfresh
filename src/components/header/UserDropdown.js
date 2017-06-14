import React, { Component } from 'react';
import defaultAvatar from '../../assets/images/default-avatar.png';
import Tooltips from '../tooltips/Tooltips';
import counterpart from 'counterpart';

import onClickOutside from 'react-onclickoutside';

class UserDropdown extends Component {
    constructor(props) {
        super(props);
    }

    handleClickOutside = () => this.props.handleUDOpen(false);

    handleKeyDown = (e) => {
        switch(e.key){
            case 'ArrowDown': {
                e.preventDefault();
                const activeElem = document.activeElement;
                if(activeElem.nextSibling) {
                    activeElem.nextSibling.focus();
                }
                break;
            }
            case 'ArrowUp': {
                e.preventDefault();
                const activeElem = document.activeElement;
                // When focus pulled out once, do not allow to get there
                if(
                    activeElem.previousSibling.classList
                        .contains('js-selection-placeholder')
                ){
                    return;
                }
                if(activeElem.previousSibling) {
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
    }

    render() {
        const {
            open, handleUDOpen, redirect, shortcut, toggleTooltip, tooltipOpen
        } = this.props;

        return (
            <div
                className={
                    'header-item-container header-item-container-static ' +
                    'pointer user-dropdown-container tooltip-parent ' +
                    (open ? 'header-item-open ' : '')
                }
                onMouseEnter={() => toggleTooltip(shortcut)}
                onMouseLeave={() => toggleTooltip('')}
            >
                <div
                    className="header-item avatar-container"
                    onClick={() => handleUDOpen(true)}>
                    <img
                        src={defaultAvatar}
                        className="avatar img-fluid rounded-circle"
                    />
                </div>

                {open &&
                    <div
                        className="user-dropdown-list"
                        onKeyDown={this.handleKeyDown}
                    >
                        <div
                            className="user-dropdown-item user-dropdown-header-item meta-text-primary"
                        >
                            Profile
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
                            onClick={() => {redirect('/settings');
                                handleUDOpen(false)}}
                            tabIndex={0}
                        >
                            <i className="meta-icon-settings" /> Settings
                        </div>
                        <div
                            className="user-dropdown-item"
                            onClick={() => {redirect('/logout');
                                handleUDOpen(false)}}
                            tabIndex={0}
                        >
                            <i className="meta-icon-logout" /> Log out
                        </div>
                    </div>
                }
                {tooltipOpen === shortcut && !open && <Tooltips
                    name={shortcut}
                    action= {
                        counterpart.translate('mainScreen.userMenu.tooltip')
                    }
                    type={''}
                />}
            </div>
        );
    }
}

export default onClickOutside(UserDropdown);
