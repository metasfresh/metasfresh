import React, { Component } from 'react';
import defaultAvatar from '../../assets/images/default-avatar.png';
import Tooltips from '../tooltips/Tooltips';

import onClickOutside from 'react-onclickoutside';

class UserDropdown extends Component {
    constructor(props) {
        super(props);
    }
    
    handleClickOutside = () => this.props.handleUDOpen(false);

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
                onClick={() => handleUDOpen(true)}
                onMouseEnter={() => toggleTooltip(shortcut)}
                onMouseLeave={() => toggleTooltip('')}
            >
                <img
                    src={defaultAvatar}
                    className="header-item avatar img-fluid rounded-circle"
                />
                
                {open && <div className="user-dropdown-list">
                    <div
                        className="user-dropdown-item user-dropdown-header-item meta-text-primary"
                    >
                        Marcus Gronholm
                    </div>
                    <hr className="context-menu-separator" />
                    <div
                        className="user-dropdown-item"
                        onClick={() => redirect('/settings')}
                    >
                        <i className="meta-icon-settings" /> Settings
                    </div>
                    <div
                        className="user-dropdown-item"
                        onClick={() => redirect('/logout')}
                    >
                        <i className="meta-icon-logout" /> Log out
                    </div>
                </div>}
                {tooltipOpen === shortcut && <Tooltips
                    name={shortcut}
                    action={'User menu'}
                    type={''}
                />}
            </div>
        );
    }
}

export default onClickOutside(UserDropdown);
