import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    getWindowBreadcrumb
} from '../../actions/MenuActions';

class MenuOverlayItem extends Component {
    constructor(props){
        super(props);
    }

    clickedItem = (e, elementId, nodeId, type ) => {
        const {
            handleClickOnFolder, handleRedirect, handleNewRedirect, openModal,
            caption
        } = this.props;

        if(type === 'newRecord'){
            handleNewRedirect(elementId);
        } else if (type === 'window') {
            this.handleClick(elementId)
        } else if (type === 'group') {
            handleClickOnFolder(e, nodeId)
        } else if (type === 'report') {
            openModal(elementId + "", "process", caption)
        }
    }

    renderBreadcrumb = (elementId) => {
        const {dispatch} = this.props;

        dispatch(getWindowBreadcrumb(elementId));
    }

    handleClick = (elementId) => {
        const {handleRedirect} = this.props;
        handleRedirect(elementId);
        this.renderBreadcrumb(elementId)
    }

    componentDidMount() {
        const menuOverlay = document.getElementsByClassName('js-menu-overlay')[0];
        menuOverlay && menuOverlay.focus();
    }

    handleKeyDown = (e) => {
        const {back, handleMenuOverlay} = this.props;

        switch(e.key){
            case "ArrowDown":
                e.preventDefault();
                this.handleArrowDown();
                break;
            case "ArrowUp":
                e.preventDefault();
                this.handeArrowUp();
                break;
            case "Backspace":
                e.preventDefault();
                back(e);
                document.getElementsByClassName('js-menu-overlay')[0].focus();
                break;
            case "Enter":
                e.preventDefault();
                document.activeElement.childNodes[0].click();
                document.getElementsByClassName('js-menu-overlay')[0].focus();
                break;
            case "Escape":
                e.preventDefault();
                handleMenuOverlay("","");
        }
    }

    handeArrowUp() {
        let prevSiblings = document.activeElement.previousSibling;
        if (prevSiblings && prevSiblings.classList.contains('js-menu-item')) {
            document.activeElement.previousSibling.focus();
        } else {
            if (document.activeElement.parentElement.previousSibling) {
                const listChildren = document.activeElement.parentElement.previousSibling.childNodes;

                if(listChildren.length == 1){
                    document.activeElement.parentElement.previousSibling.childNodes[0].focus();
                }else{
                    listChildren[listChildren.length - 1].focus();
                }
            }
        }
    }

    handleArrowDown() {

        if (document.activeElement.nextSibling) {
            document.activeElement.nextSibling.focus();
        } else {
            if (document.activeElement.parentElement.nextSibling) {
                const listChildren = document.activeElement.parentElement.nextSibling.childNodes;
                if(listChildren.length == 1){
                    listChildren[0].focus();
                }else{
                    listChildren[1].focus();
                }
            }
        }
    }

    render() {
        const {
            dispatch, nodeId, type, elementId, caption, children, handleClickOnFolder,
            handleRedirect, handleNewRedirect, handlePath, query, printChildren
        } = this.props;

        return (
            <span
                tabIndex={0}
                onKeyDown={this.handleKeyDown}
                className={
                    "menu-overlay-expanded-link js-menu-item " +
                    (!printChildren ? "menu-overlay-expanded-link-spaced " : "")
                }
            >

            { !query &&
                <span
                    className={
                        (children ? "menu-overlay-expand" : "menu-overlay-link")
                    }
                    onClick={e => {
                        children ?
                            handleClickOnFolder(e, nodeId) :
                            this.clickedItem(e, elementId, nodeId, type)
                    }}
                >
                    {caption}
                </span>

            }

            { query &&
                <span className={children ? "" : (type === 'group'? "query-clickable-group" : "query-clickable-link")}
                    onClick={ children ? '' : e => this.clickedItem(e, elementId, nodeId, type)  }
                >
                    {children ? children.map(
                        (item, id) =>
                            <span key={id} className="query-results" >
                                <span className="query-caption">{id===0 ? caption +' / ': '/'}</span>
                                <span title={item.caption} className={type === 'group' ? "query-clickable-group" : "query-clickable-link"}
                                     onClick={e => this.clickedItem(e, item.elementId, item.nodeId, item.type)}
                                >
                                    {item.caption}
                                </span>
                            </span>
                        ) : caption}
               </span>
            }
            </span>
        )
    }
}

MenuOverlayItem.propTypes = {
    dispatch: PropTypes.func.isRequired
};

MenuOverlayItem = connect()(MenuOverlayItem);

export default MenuOverlayItem
