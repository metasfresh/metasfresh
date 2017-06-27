import React, { Component } from 'react';
import { Shortcuts } from 'react-shortcuts';

class GlobalContextShortcuts extends Component {
    constructor(props) {
        super(props);

    }
    handleShortcuts = (action, event) => {
        const {
          handleMenuOverlay, openModal, handleSidelistToggle, handleUDOpen,
          handlePrint, handleDelete, handleInboxOpen,
          redirect, handleDocStatusToggle, closeOverlays, handleEditModeToggle
        } = this.props;

        switch (action) {
            case 'OPEN_AVATAR_MENU':
                event.preventDefault();
                closeOverlays(null, () => {
                    handleUDOpen();
                });
                break;
            case 'OPEN_ACTIONS_MENU':
                event.preventDefault();
                closeOverlays('isSubheaderShow');
                break;
            case 'OPEN_NAVIGATION_MENU':
                event.preventDefault();
                handleMenuOverlay();
                break;
            case 'OPEN_INBOX_MENU':
                event.preventDefault();
                closeOverlays('', ()=> handleInboxOpen(true));
                break;
            case 'OPEN_SIDEBAR_MENU_0':
                event.preventDefault();
                closeOverlays(null, () => {
                    handleSidelistToggle(0);
                });
                break;
            case 'OPEN_SIDEBAR_MENU_1':
                event.preventDefault();
                closeOverlays(null, () => {
                    handleSidelistToggle(1);
                });
                break;
            case 'OPEN_SIDEBAR_MENU_2':
                event.preventDefault();
                closeOverlays(null, () => {
                    handleSidelistToggle(2);
                });
                break;
            case 'DELETE_DOCUMENT':
                event.preventDefault();
                if (handleDelete) {
                    handleDelete();
                }
                break;
            case 'OPEN_ADVANCED_EDIT':
                event.preventDefault();
                if (openModal) {
                    openModal();
                }
                break;
            case 'OPEN_PRINT_RAPORT':
                event.preventDefault();
                if (handlePrint) {
                    handlePrint();
                }
                break;
            case 'NEW_DOCUMENT':
                event.preventDefault();
                if (redirect) {
                    redirect();
                }
                break;
            case 'DOC_STATUS':
                event.preventDefault();
                if(handleDocStatusToggle) {
                    closeOverlays('dropdown', handleDocStatusToggle);
                }
                break;
            case 'TOGGLE_EDIT_MODE':
                event.preventDefault();
                if(handleEditModeToggle){
                    handleEditModeToggle();
                }
        }
    }

    render() {
        return (
            <Shortcuts
                name = "GLOBAL_CONTEXT"
                handler = { this.handleShortcuts }
                targetNodeSelector = "body"
                isolate = { true }
                preventDefault = { true }
                stopPropagation = { true }
                global = {true}
                alwaysFireHandler = { true }
            />
        )
    }
}

export default GlobalContextShortcuts
