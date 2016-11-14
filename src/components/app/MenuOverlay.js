import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import MenuOverlayContainer from './MenuOverlayContainer';
import MenuOverlayItem from './MenuOverlayItem';
import {push} from 'react-router-redux';
import DebounceInput from 'react-debounce-input';
import {
    nodePathsRequest,
    queryPathsRequest,
    pathRequest,
    getWindowBreadcrumb
 } from '../../actions/MenuActions';
class MenuOverlay extends Component {
    constructor(props){
        super(props);
        this.state = {
            queriedResults: [],
            query: "",
            deepNode: null,
            deepSubNode: null,
            path: "",
            subPath: ""
        };
    }

    browseWholeTree = () => {
      const {dispatch} = this.props;
      dispatch(push("/sitemap"));
    }

    handleClickOutside = (e) => {
        const {onClickOutside} = this.props;
        onClickOutside(e);
    }

    handleQuery = (e) => {
        const {dispatch} = this.props;
        e.preventDefault();
        if(!!e.target.value){
            this.setState(Object.assign({}, this.state, {
                query: e.target.value
            }));
            dispatch(queryPathsRequest(e.target.value, 9)).then(response => {
                this.setState(Object.assign({}, this.state, {
                    queriedResults: response.data.children
                }))
            });
        }else{
            this.setState(Object.assign({}, this.state, {
                queriedResults: [],
                query: ""
            }))
        }
    }

    handleClear = (e) => {
        e.preventDefault();
        this.setState(Object.assign({}, this.state, {
            query: "",
            queriedResults: []
        }));
    }

    handleDeeper = (e, nodeId) => {
        const {dispatch} = this.props;
        e.preventDefault();
        dispatch(nodePathsRequest(nodeId,8)).then(response => {
            this.setState(Object.assign({}, this.state, {
                deepNode: response.data
            }))

            this.handlePath(nodeId);
        })
    }

    handleSubDeeper = (nodeId) => {
        const {dispatch} = this.props;
        dispatch(nodePathsRequest(nodeId,8)).then(response => {
            this.setState(Object.assign({}, this.state, {
                deepSubNode: response.data
            }))
        })
    }

    handleClickBack = (e) => {
        e.preventDefault();
        this.setState(Object.assign({}, this.state, {
            deepNode: null
        }))
    }

    handleSubClickBack = (e) => {
        e.preventDefault();
        this.setState(Object.assign({}, this.state, {
            deepSubNode: null
        }))
    }

    handleRedirect = (elementId) => {
        const {dispatch} = this.props;
        this.handleClickOutside();
        dispatch(push("/window/" + elementId));
    }

    handleNewRedirect = (elementId) => {
        const {dispatch} = this.props;
        this.handleClickOutside();
        dispatch(push("/window/" + elementId + "/new"));
    }


    handlePath = (nodeId) => {
        const {dispatch} = this.props;
        dispatch(pathRequest(nodeId)).then(response => {
            let pathArray = [];
            let node = response.data;

            do{
                const children = node.children && node.children[0];
                node.children = undefined;

                pathArray.push(node);
                node = children;
            }while(!!node);

            //remove first MENU element
            pathArray.shift();

            this.setState(Object.assign({}, this.state, {
                path: pathArray
            }))
        });
    }

    handleSubPath = (nodeId) => {
        const {dispatch} = this.props;
        dispatch(pathRequest(nodeId)).then(response => {
            let pathArray = [];
            let node = response.data;

            do{
                const children = node.children && node.children[0];
                node.children = undefined;

                pathArray.push(node);
                node = children;
            }while(!!node);

            //remove first MENU element
            pathArray.shift();

            this.setState(Object.assign({}, this.state, {
                subPath: pathArray
            }))
        });
    }

    renderPath = (path) => {
        return (
            <span>
                {path && path.map((item, index) =>
                    <span key={index}>
                        {item.nodeId > 0 ? ((index > 0 ? ' / ' : '') + item.captionBreadcrumb) : item.captionBreadcrumb}
                    </span>
                )}
            </span>
        )
    }

    renderNaviagtion = (node) => {
    	const {path} = this.state;
        return (
             <div className="menu-overlay-container-column-wrapper">
                {node.nodeId != 0 &&
                    <p className="menu-overlay-header menu-overlay-header-main menu-overlay-header-spaced group-header">
                        {this.renderPath(path)}
                    </p>
                }
                <div className="column-wrapper">

                    {node && node.children.map((item,index) =>
                        <MenuOverlayContainer
                            key={index}
                            handleClickOnFolder={this.handleDeeper}
                            handleRedirect={this.handleRedirect}
                            handleNewRedirect={this.handleNewRedirect}
                            handlePath={this.handlePath}
                            parent={node}
                            {...item}
                        />
                    )}
                </div>
            </div>
        )
    }

    renderSubnavigation = (nodeData) => {
        return(
            <div>
                {nodeData && nodeData.children.map((item, index) =>
                    <span
                        className="menu-overlay-expanded-link"
                        key={index}
                    >
                        <span
                            className={item.elementId ? 'menu-overlay-link' : 'menu-overlay-expand'}
                            onClick={ e => this.linkClick(item) }>
                                {item.caption}
                            </span>
                    </span>
                )}
            </div>
        )
    }

    linkClick = (item) => {
        const {dispatch} = this.props;
        if(item.elementId && item.type == "newRecord") {
            this.handleNewRedirect(item.elementId)
        } else if (item.elementId && item.type == "window"){
            this.handleRedirect(item.elementId)
            dispatch(getWindowBreadcrumb(item.elementId));
        } else if (item.type == "group"){
            this.handleSubDeeper(item.nodeId);
            this.handleSubPath(item.nodeId);
        }
    }

    render() {
        const {queriedResults, deepNode, deepSubNode, subPath} = this.state;
        const {dispatch, nodeId, node, siteName, index} = this.props;
        const nodeData = node.children;

        return (
            <div className="menu-overlay menu-overlay-primary">
                <div className="menu-overlay-caption">{ (index === 0) ? <span className="ico-home"><i className="meta-icon-menu" /></span> : nodeData && nodeData.captionBreadcrumb}</div>
                <div className="menu-overlay-body breadcrumbs-shadow">
                    {nodeId == 0 ?
                        //ROOT
                        <div>
                            {deepNode ?
                                <div>
                                    <span className="menu-overlay-link" onClick={e => this.handleClickBack(e)}>&lt; Back</span>
                                </div>
                                :
                                <span
                                    className="menu-overlay-link"
                                    onClick={() => dispatch(push("/"))}
                                >
                                    Home
                                </span>
                            }
                            <div className="menu-overlay-root-body">

                                {this.renderNaviagtion(deepNode ? deepNode : nodeData)}

                                <div className="menu-overlay-query hidden-sm-down">
                                    <div className="input-flex input-primary">
                                        <i className="input-icon meta-icon-preview"/>
                                        <DebounceInput debounceTimeout={250} type="text" className="input-field" placeholder="Type phrase here" value={this.state.query} onChange={e => this.handleQuery(e) } />
                                        {this.state.query && <i className="input-icon meta-icon-close-alt pointer" onClick={e => this.handleClear(e) } />}
                                    </div>
                                    {queriedResults && queriedResults.map((result, index) =>
                                        <MenuOverlayItem
                                            key={index}
                                            handleClickOnFolder={this.handleDeeper}
                                            handleRedirect={this.handleRedirect}
                                            handleNewRedirect={this.handleNewRedirect}
                                            query={true}
                                            handlePath={this.handlePath}
                                            {...result}
                                        />
                                    )}
                                </div>
                            </div>
                        </div> :
                        //NOT ROOT
                        <div className="menu-overlay-node-container">

                            {deepSubNode ?
                                [
                                    <div key="back">
                                        <span className="menu-overlay-link" onClick={e => this.handleSubClickBack(e)}>&lt; Back</span>
                                    </div>,
                                    <p key="path" className="menu-overlay-header">{this.renderPath(subPath)}</p>
                                ]:
                                    <p className="menu-overlay-header">{nodeData && nodeData.caption}</p>
                            }

                            {this.renderSubnavigation(deepSubNode ? deepSubNode : nodeData)}

                        </div>
                    }
                    {index === 0 && siteName !== "Sitemap" &&
                        <div className="text-xs-right">
                            <span className="menu-overlay-link tree-link" onClick={this.browseWholeTree}>Browse whole tree &gt;&gt; </span>
                        </div>
                    }
                </div>
            </div>
        )
    }
}

MenuOverlay.propTypes = {
    dispatch: PropTypes.func.isRequired
};

MenuOverlay = connect()(onClickOutside(MenuOverlay));

export default MenuOverlay;
