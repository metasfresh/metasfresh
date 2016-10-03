import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    getWindowBreadcrumb
 } from '../../actions/MenuActions';

class MenuOverlayItem extends Component {
    constructor(props){
        super(props);
    }
    showQueriedChildren = (children) => {
        // console.log("aaaa");
        // console.log(query);
        // caption +' > '+children[0].caption
        // {query ? (children ? this.showQueriedChildren(caption, children) : caption ) : caption}

        //onClick={ children ? '' : (e => type === 'group' ? handleClickOnFolder(e, nodeId) : (e => type === 'newRecord' ? handleNewRedirect(e, elementId) : handleRedirect(elementId)) ) }

        console.log(children);

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

    onMouseDown = (type, nodeId) => {
        const {handlePath} = this.props;

        if(type === 'group'){
            handlePath(nodeId)
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
        const {dispatch, nodeId, type, elementId, caption, children, handleClickOnFolder, handleRedirect, handleNewRedirect, handlePath, query} = this.props;

        return (
            <div
                className={
                    "menu-overlay-expanded-link "
                }
            >

            { !query &&
                <span
                    className={
                        (children ? "menu-overlay-expand" : "menu-overlay-link")
                    }
                    onClick={query? '' : e => children ? handleClickOnFolder(e, nodeId) : (type==='newRecord' ? handleNewRedirect(elementId) : this.handleClick(elementId) )}
                    onMouseDown={ e => children ? handlePath(nodeId) : ''}
                >
                {caption}
                </span>

            }

            { query &&
               <span className={children ? "" : (type === 'group'? "query-clickable-group" : "query-clickable-link")}
                onClick={ children ? '' : e => this.clickedItem(e, elementId, nodeId, type)  }
                onMouseDown={ e => this.onMouseDown(type, nodeId)}
               >
                    {children ? children.map(
                        (item, id) =>


                        <div key={id} className="query-results" >
                            <div className="query-caption">{caption +' / '}</div>
                            <MenuOverlayItem
                                handleClickOnFolder={handleClickOnFolder}
                                handleRedirect={handleRedirect}
                                handleNewRedirect={handleNewRedirect}
                                handlePath={handlePath}
                                query={true}
                                {...item}
                            />

                        </div>


                        ) : caption}


               </span>
            }
            </div>
        )
    }
}

MenuOverlayItem.propTypes = {
    dispatch: PropTypes.func.isRequired
};

MenuOverlayItem = connect()(MenuOverlayItem);

export default MenuOverlayItem
