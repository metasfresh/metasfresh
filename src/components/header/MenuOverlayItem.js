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
        const {handleClickOnFolder, handleRedirect, handleNewRedirect} = this.props;

        if(type === 'newRecord'){
            handleNewRedirect(elementId);
        } else if (type === 'window') {
            this.handleClick(elementId)
        } else if (type === 'group') {
            handleClickOnFolder(e, nodeId)
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

    render() {
        const {
            dispatch,
            nodeId,
            type,
            elementId,
            caption,
            children,
            handleClickOnFolder,
            handleRedirect,
            handleNewRedirect,
            handlePath,
            query,
            printChildren
        } = this.props;

        console.log(children)

        return (
            <span
                className={
                    "menu-overlay-expanded-link " +
                    (!printChildren ? "menu-overlay-expanded-link-spaced " : "")
                }
            >

            { !query &&
                <span
                    className={
                        (children ? "menu-overlay-expand" : "menu-overlay-link")
                    }
                    onClick={e => children ? handleClickOnFolder(e, nodeId) : (type==='newRecord' ? handleNewRedirect(elementId) : this.handleClick(elementId))}
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
