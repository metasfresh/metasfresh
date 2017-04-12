import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import Actions from './Actions';

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
            const child = active.childNodes;
            if(child[0] && child[0].classList.contains('js-spacer')){
                return child[0];
            }else{
                return child[1];
            }
        } else {
             return active;
        }
    }

    renderNavColumn = () => {
        const {
            dataId, windowType, openModal, closeSubheader, handlePrint,
            handleDelete, handleClone, docNo, redirect
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
                <i className="meta-icon-edit" /> Advanced Edit
                <span className="tooltip-inline">
                    {keymap.GLOBAL_CONTEXT.OPEN_ADVANCED_EDIT}
                </span>
            </div>,
            <div
                key={1}
                className="subheader-item js-subheader-item"
                tabIndex={0}
                onClick={() => {
                    handlePrint(windowType, dataId, docNo); closeSubheader()
                }}
            >
                <i className="meta-icon-print" /> Print
                <span className="tooltip-inline">
                    {keymap.GLOBAL_CONTEXT.OPEN_PRINT_RAPORT}
                </span>
            </div>,
            <div
                key={2}
                className="subheader-item js-subheader-item"
                tabIndex={0}
                onClick={() => handleClone(windowType, dataId)}
            >
                <i className="meta-icon-duplicate" /> Clone
            </div>,
            <div
                key={3}
                className="subheader-item js-subheader-item"
                tabIndex={0}
                onClick={() => handleDelete()}
            >
                <i className="meta-icon-delete" /> Delete
                <span className="tooltip-inline">
                    {keymap.GLOBAL_CONTEXT.DELETE_DOCUMENT}
                </span>
            </div>
        ]

        return (
            <div
                className="subheader-column js-subheader-column"
                tabIndex={0}
            >
                <div className="js-spacer"/>

                {windowType && <div
                    className="subheader-item js-subheader-item"
                    tabIndex={0}
                    onClick={() => { redirect(
                        '/window/'+ windowType + '/new'
                    ); closeSubheader()}
                }>
                    <i className="meta-icon-report-1" /> New
                    <span className="tooltip-inline">
                        {keymap.GLOBAL_CONTEXT.NEW_DOCUMENT}
                    </span>
                </div>}
                {docLinks}
                <div
                    className="subheader-item js-subheader-item"
                    tabIndex={0}
                    onClick={()=> redirect('/settings')}
                >
                    <i className="meta-icon-settings" /> Settings
                </div>
                <div
                    className="subheader-item js-subheader-item"
                    tabIndex={0}
                    onClick={()=> redirect('/logout')}
                >
                    <i className="meta-icon-logout" /> Log out
                </div>
            </div>
        )
    }

    renderActionsColumn = () => {
        const {
            windowType, dataId, selected, selectedWindowType, entity, query,
            openModal, closeSubheader
        } = this.props;

        return (
            <div
                className="subheader-column js-subheader-column"
                tabIndex={0}
            >
                <div className="subheader-header">Actions</div>
                <div className="subheader-break" />
                <Actions
                    {...{windowType, entity, openModal, closeSubheader}}
                    docId={dataId ? dataId : query && query.viewId}
                    rowId={selectedWindowType === windowType ? selected : []}
                />
            </div>
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
                <div className="container-fluid">
                    <div className="row">
                        <div className="subheader-row">
                            {this.renderNavColumn()}
                            {this.renderActionsColumn()}
                        </div>
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
