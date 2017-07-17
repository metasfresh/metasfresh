import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import counterpart from 'counterpart';

import {updateBreadcrumb} from '../../actions/MenuActions';

import Actions from './Actions';

import BookmarkButton from './BookmarkButton';

import keymap from '../../keymap.js';

class Subheader extends Component {
    constructor(props){
        super(props);

        this.state = {
            pdfSrc: null
        }
    }

    componentDidMount() {
        document.getElementsByClassName('js-subheader-column')[0].focus();
    }

    handleKeyDown = (e) => {
        const {closeSubheader} = this.props;

        switch(e.key){
            case 'ArrowDown': {
                e.preventDefault();
                const activeElem = this.getItemActiveElem();
                if(activeElem.nextSibling) {
                    activeElem.nextSibling.focus();
                }
                break;
            }
            case 'ArrowUp': {
                e.preventDefault();
                const activeEl = this.getItemActiveElem();
                if(activeEl.previousSibling) {
                    activeEl.previousSibling.focus();
                }
                break;
            }
            case 'ArrowLeft': {
                e.preventDefault();
                const activeColumn = this.getColumnActiveElem();
                if(activeColumn.previousSibling) {
                    activeColumn.previousSibling.focus();
                    if(this.getItemActiveElem().nextSibling) {
                         this.getItemActiveElem().nextSibling.focus();
                    }
                }
                break;
            }
            case 'ArrowRight': {
                e.preventDefault();
                const activeCol = this.getColumnActiveElem();
                if(activeCol.nextSibling) {
                    activeCol.nextSibling.focus();
                    if(this.getItemActiveElem().nextSibling){
                        this.getItemActiveElem().nextSibling.focus();
                    }
                }
                break;
            }
            case 'Enter':
                e.preventDefault();
                document.activeElement.click();
                break;
            case 'Escape':
                e.preventDefault();
                closeSubheader();
                break;
        }
    }

    handleClickOutside = () => {
        const { closeSubheader } = this.props;
        closeSubheader();
    }

    toggleAttachmentDelete = (value) => {
        this.setState({
            attachmentHovered: value
        })
    }

    getColumnActiveElem = () => {
        const active = document.activeElement;
        if(active.classList.contains('js-subheader-item')) {
            return active.parentNode;
        } else {
             return active;
        }
    }

    getItemActiveElem = () => {
        const active = document.activeElement;
        if(
            active.classList.contains('js-subheader-column')
        ) {
            return active.childNodes[1];
        } else {
            return active;
        }
    }

    handleUpdateBreadcrumb = (nodes) => {
        const {dispatch} = this.props;
        nodes.map(node => dispatch(updateBreadcrumb(node)));
    }

    renderNavColumn = () => {
        const {
            dataId, windowType, openModal, closeSubheader, handlePrint,
            handleDelete, docNo, redirect, breadcrumb, siteName, editmode,
            handleEditModeToggle, handleEmail
        } = this.props;

        const docLinks = dataId && [
            <div
                key={0}
                className="subheader-item js-subheader-item"
                tabIndex={0}
                onClick={() => {
                    openModal(windowType, 'window', 'Advanced edit', true);
                    closeSubheader();
                }}
            >
                <i className="meta-icon-edit" />
                {counterpart.translate('window.advancedEdit.caption')}
                <span className="tooltip-inline">
                    {keymap.GLOBAL_CONTEXT.OPEN_ADVANCED_EDIT}
                </span>
            </div>,
            <div
                key={1}
                className="subheader-item js-subheader-item"
                tabIndex={0}
                onClick={() => {handleEmail(); closeSubheader()}}
            >
                <i className="meta-icon-mail" />
                {counterpart.translate('window.email.caption')}
                <span className="tooltip-inline">
                    {keymap.GLOBAL_CONTEXT.OPEN_EMAIL}
                </span>
            </div>,
            <div
                key={2}
                className="subheader-item js-subheader-item"
                tabIndex={0}
                onClick={() => {
                    handlePrint(windowType, dataId, docNo); closeSubheader()
                }}
            >
                <i className="meta-icon-print" />
                {counterpart.translate('window.Print.caption')}
                <span className="tooltip-inline">
                    {keymap.GLOBAL_CONTEXT.OPEN_PRINT_RAPORT}
                </span>
            </div>,
            <div
                key={3}
                className="subheader-item js-subheader-item"
                tabIndex={0}
                onClick={() => handleDelete()}
            >
                <i className="meta-icon-delete" />
                {counterpart.translate('window.Delete.caption')}
                <span className="tooltip-inline">
                    {keymap.GLOBAL_CONTEXT.DELETE_DOCUMENT}
                </span>
            </div>
        ]

        const currentNode = breadcrumb &&
            breadcrumb[breadcrumb.length - 1] &&
            breadcrumb[breadcrumb.length - 1].children;

        return (
            <div
                className="subheader-column js-subheader-column"
                tabIndex={0}
            >
                <div
                    className="subheader-header"
                >
                    <BookmarkButton
                        isBookmark={currentNode && currentNode.favorite}
                        nodeId={currentNode && currentNode.nodeId}
                        transparentBookmarks={!!siteName}
                        updateData={this.handleUpdateBreadcrumb}
                    >
                        <span className="subheader-title">
                            {currentNode ? currentNode.caption : siteName}
                        </span>
                    </BookmarkButton>
                </div>
                <div className="subheader-break" />

                {windowType && <div
                    className="subheader-item js-subheader-item"
                    tabIndex={0}
                    onClick={() => { redirect(
                        '/window/'+ windowType + '/new'
                    ); closeSubheader()}
                }>
                    <i className="meta-icon-report-1" />
                    {counterpart.translate('window.new.caption')}
                    <span className="tooltip-inline">
                        {keymap.GLOBAL_CONTEXT.NEW_DOCUMENT}
                    </span>
                </div>}
                {docLinks}
                {editmode !== undefined && <div
                    key={editmode}
                    className="subheader-item js-subheader-item"
                    tabIndex={0}
                    onClick={() => {handleEditModeToggle(); closeSubheader()}}
                >
                    <i className="meta-icon-settings" />
                    {editmode ?
                        counterpart.translate('window.closeEditMode') :
                        counterpart.translate('window.openEditMode')
                    }
                    <span className="tooltip-inline">
                        {keymap.GLOBAL_CONTEXT.TOGGLE_EDIT_MODE}
                    </span>
                </div>}
            </div>
        )
    }

    renderActionsColumn = () => {
        const {
            windowType, dataId, selected, selectedWindowType, entity, query,
            openModal, closeSubheader, notfound
        } = this.props;

        return (
            <Actions
                {...{windowType, entity, openModal, closeSubheader, notfound}}
                docId={dataId ? dataId : query && query.viewId}
                rowId={selectedWindowType === windowType ? selected : []}
            />
        )
    }

    render() {

        return (
            <div
                className="subheader-container overlay-shadow subheader-open js-not-unselect"
                tabIndex={0}
                onKeyDown={this.handleKeyDown}
                ref={(c)=> this.subHeader = c}
            >
                <div className="container-fluid-subheader container-fluid">
                    <div className="subheader-row">
                        {this.renderNavColumn()}
                        {this.renderActionsColumn()}
                    </div>
                </div>
            </div>
        )
    }
}

Subheader.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Subheader = connect()(onClickOutside(Subheader));

export default Subheader;
