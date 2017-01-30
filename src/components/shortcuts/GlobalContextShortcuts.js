import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';

class GlobalContextShortcuts extends Component {
    constructor(props) {
        super(props);

    }
    handleShortcuts = (action, event) => {
        const { 
          handleMenuOverlay, openModal, 
          handlePrint, handleDelete, handleSideList, handleInboxOpen, 
          redirect, handleDocStatusToggle, closeOverlays 
        } = this.props;
        switch (action) {
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
                closeOverlays('', (e)=> handleInboxOpen(true));
                
                break;
            case 'OPEN_SIDEBAR_MENU':
                event.preventDefault();
                if (handleSideList) {
                    closeOverlays('isSideListShow');
                }
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
        }
    }

    render() {
        return (
            <Shortcuts
                name = { "GLOBAL_CONTEXT" }
                handler = { this.handleShortcuts }
                targetNodeSelector = { "body" }
                isolate = { true }
                preventDefault = { true }
                stopPropagation = { true }
                global = {true}
            />
        )
    }
}

export default GlobalContextShortcuts
