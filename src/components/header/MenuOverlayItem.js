import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import {
    getWindowBreadcrumb
} from '../../actions/MenuActions';

import BookmarkButton from './BookmarkButton';

class MenuOverlayItem extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount() {
        const {query} = this.props;
        if(!query &&  document.getElementsByClassName('js-menu-overlay')[0]) {
             document.getElementsByClassName('js-menu-overlay')[0].focus();
        }
    }

    clickedItem = (e, elementId, nodeId, type ) => {
        const {
            handleClickOnFolder, handleNewRedirect, openModal, caption
        } = this.props;

        if(type === 'newRecord'){
            handleNewRedirect(elementId);
        } else if (type === 'window') {
            this.handleClick(elementId)
        } else if (type === 'group') {
            handleClickOnFolder(e, nodeId)
        } else if (type === 'report' || type === 'process') {
            openModal(elementId + '', 'process', caption)
        }
    }

    handleClick = (elementId) => {
        const {handleRedirect} = this.props;
        handleRedirect(elementId);
        this.renderBreadcrumb(elementId)
    }

    handleKeyDown = (e) => {
        const {back, handleMenuOverlay} = this.props;
        const overlay = document.getElementsByClassName('js-menu-overlay')[0];

        switch(e.key){
            case 'ArrowDown':
                e.preventDefault();
                this.handleArrowDown();
                break;
            case 'ArrowUp':
                e.preventDefault();
                this.handeArrowUp();
                break;
            case 'Tab':
                e.preventDefault();
                document.getElementsByClassName('js-menu-item')[0].focus();
                break;
            case 'Backspace':
                e.preventDefault();
                back(e);
                overlay.focus();
                break;
            case 'Enter':
                e.preventDefault();
                document.activeElement.childNodes[0].click();
                overlay && overlay.focus();
                break;
            case 'Escape':
                e.preventDefault();
                handleMenuOverlay('', '');
        }
    }

    handeArrowUp() {
        let prevSiblings = document.activeElement.previousSibling;
        if(prevSiblings && prevSiblings.classList.contains('input-primary')) {
            document.getElementById('search-input-query').focus();
        } else if (
            prevSiblings && prevSiblings.classList.contains('js-menu-item')
        ) {
            document.activeElement.previousSibling.focus();
        } else {
            this.handleGroupUp();
        }
    }

    handleGroupUp() {
        const previousGroup =
            document.activeElement.parentElement.previousSibling;
        const browseItem = document.getElementsByClassName('js-browse-item')[0];

        if(previousGroup && previousGroup.classList.contains('js-menu-item')){
            previousGroup.focus();
        } else {
            if (previousGroup) {
                const listChildren = previousGroup.childNodes;
                const lastChildren = listChildren[listChildren.length - 1];
                if(listChildren.length == 1){
                    listChildren[0].focus && listChildren[0].focus();
                }else{
                    if(lastChildren.classList.contains('js-menu-item')) {
                        lastChildren.focus();
                    } else {
                        lastChildren.children[lastChildren.children.length - 1]
                            .focus();
                    }

                }
            } else {
                browseItem && browseItem.focus()
            }
        }

    }

    handleArrowDown() {
        const nextElem = document.activeElement.nextSibling;
        const parentElem = document.activeElement.parentElement;
        if (nextElem) {
            if(nextElem.classList.contains('js-menu-item')) {
                nextElem.focus();
            } else {
                nextElem.getElementsByClassName('js-menu-item')[0] &&
                nextElem.getElementsByClassName('js-menu-item')[0].focus();
            }

        } else {
            if (parentElem.nextSibling) {
                const listChildren =
                    parentElem.nextSibling.childNodes;
                if(listChildren.length == 1){
                    listChildren[0].focus();
                }else{
                    listChildren[1].focus();
                }
            } else if(parentElem.parentElement.nextSibling) {
                parentElem.parentElement.nextSibling.childNodes[1].focus();
            }
        }
    }

    renderBreadcrumb = (elementId) => {
        const {dispatch} = this.props;

        dispatch(getWindowBreadcrumb(elementId));
    }

    render() {
        const {
            nodeId, type, elementId, caption, children, handleClickOnFolder,
            query, printChildren, favorite, updateData, transparentBookmarks
        } = this.props;

        return (
            <span
                tabIndex={0}
                onKeyDown={this.handleKeyDown}
                className={
                    'menu-overlay-expanded-link js-menu-item ' +
                    (!printChildren ? 'menu-overlay-expanded-link-spaced ' : '')
                }
            >

            { !query &&
                <BookmarkButton
                    isBookmark={favorite}
                    {...{updateData, nodeId, transparentBookmarks}}
                >
                    <span
                        className={
                            (children ?
                                'menu-overlay-expand' : 'menu-overlay-link')
                        }
                        onClick={e => {
                            children ?
                                handleClickOnFolder(e, nodeId) :
                                this.clickedItem(e, elementId, nodeId, type)
                        }}
                    >
                        {caption}
                    </span>
                </BookmarkButton>
            }

            { query &&
                <span
                    className={children ? '' : (type === 'group' ?
                        'query-clickable-group' : 'query-clickable-link')
                    }
                    onClick={ children ? '' :
                        e => this.clickedItem(e, elementId, nodeId, type)
                    }
                >
                    {children ? children.map(
                        (item, id) =>
                            <span key={id} className="query-results" >
                                <span
                                    className="query-caption"
                                >
                                    {id === 0 ? caption + ' / ': '/'}
                                </span>
                                <span
                                    title={item.caption}
                                    className={type === 'group' ?
                                        'query-clickable-group' :
                                        'query-clickable-link'
                                    }
                                    onClick={
                                        e => this.clickedItem(
                                            e, item.elementId, item.nodeId,
                                            item.type
                                        )
                                    }
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
