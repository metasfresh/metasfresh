import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';

class GlobalContextShortcuts extends Component {
    constructor(props) {
        super(props);

    }
    handleShortcuts = (action, event) => {
        const { 
          handleSubheaderOpen, handleMenuOverlay, closeMenuOverlay, openModal, 
          handlePrint, handleDelete, handleSideListToggle, handleInboxOpen, 
          closeInbox, redirect, handleDocStatusToggle 
        } = this.props;
        switch (action) {
            case 'OPEN_ACTIONS_MENU':
                event.preventDefault();
                closeMenuOverlay();
                closeInbox();
                if(handleDocStatusToggle){
                    handleDocStatusToggle(true);
                }
                handleSubheaderOpen();
                break;
            case 'OPEN_NAVIGATION_MENU':
                event.preventDefault();
                closeInbox();
                if(handleDocStatusToggle){
                    handleDocStatusToggle(true);
                }
                handleMenuOverlay();
                break;
            case 'OPEN_INBOX_MENU':
                event.preventDefault();
                closeMenuOverlay();
                if(handleDocStatusToggle){
                    handleDocStatusToggle(true);
                }
                handleInboxOpen(true);
                break;
            case 'OPEN_SIDEBAR_MENU':
                event.preventDefault();
                closeMenuOverlay();
                closeInbox();
                if(handleDocStatusToggle){
                    handleDocStatusToggle(true);
                }
                if (handleSideListToggle) {
                    handleSideListToggle();
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
                closeMenuOverlay();
                closeInbox();
                if(handleDocStatusToggle) {
                    handleDocStatusToggle();
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
            />
        )
    }
}

export default GlobalContextShortcuts
