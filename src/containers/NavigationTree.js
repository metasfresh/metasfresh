import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import { Link } from 'react-router';

import Header from '../components/app/Header';
import MenuOverlayContainer from '../components/app/MenuOverlayContainer';
import NavigationTreeItem from '../components/app/NavigationTreeItem';
import {push} from 'react-router-redux';

import {
    rootRequest,
    nodePathsRequest,
    getWindowBreadcrumb
 } from '../actions/MenuActions';


class NavigationTree extends Component {
    constructor(props){
        super(props);
        this.state = {
            rootResults: {
              caption: "",
              children: [],
              query: ""
          },
          deepNode: null
        };
    }

    componentDidMount = () => {
        const {dispatch, windowType} = this.props;

        this.getData();
        // dispatch(getRootMenu());
        dispatch(getWindowBreadcrumb("143"));
    }

    getData = () => {
        const {dispatch} = this.props;
        dispatch(rootRequest()).then(response => {
            this.setState(Object.assign({}, this.state, {
                rootResults: response.data
            }), 
                ()=>{
                console.log(this.state.rootResults);}
            )
        });


    }

    handleClear = (e) => {
        e.preventDefault();

        this.setState(Object.assign({}, this.state, {
            query: ""
        }));
    }

    renderTree = (res) => {
      const {dispatch} = this.props;
      const {rootResults} = this.state;

      return(
        <div className="container">
            <div className="search-wrapper">
                <div className="input-flex input-primary">
                    <i className="input-icon meta-icon-preview"/>
                    <input type="text" className="input-field" placeholder="Type phrase here" value={this.state.query} />
                    {this.state.query && <i className="input-icon meta-icon-close-alt pointer" onClick={e => this.handleClear(e) } />}
                </div>
            </div>
            <p className="menu-overlay-header">{rootResults.caption}</p>
            <div className="column-wrapper">
            {rootResults.children && rootResults.children.map((subitem, subindex) =>
                <NavigationTreeItem
                    key={subindex}
                    handleRedirect={this.handleRedirect}
                    handleClickOnFolder={this.handleDeeper}
                    {...subitem}
                />
            )}
            </div>
        </div>
      )

    }
    handleRedirect = (elementId) => {
        const {dispatch} = this.props;
        dispatch(push("/window/" + elementId));
    }
    handleDeeper = (e, nodeId) => {
        console.log('handleClickOnFolder');
        const {dispatch} = this.props;

        e.preventDefault();


        dispatch(nodePathsRequest(nodeId,4)).then(response => {
            this.setState(Object.assign({}, this.state, {
                deepNode: response.data
            }))
        })
    }
    handleClickBack = (e) => {
        e.preventDefault();

        this.setState(Object.assign({}, this.state, {
            deepNode: null
        }))
    }

    render() {
        const {master, connectionError, modal, breadcrumb} = this.props;
        const {nodeId, node} = this.props;
        const {rootResults, deepNode} = this.state;

        return (
          <div className="map-tree-wrapper">
              <Header
                  breadcrumb={breadcrumb.slice(0,1)}
                  siteName = {"Sitemap"}
              />
              {connectionError && <ErrorScreen />}

              <div className="container">
                <div className="row">
                {this.renderTree(rootResults)}
                </div>
              </div>

          </div>
        );
    }
}

function mapStateToProps(state) {
    const { windowHandler, menuHandler } = state;
    const {
        master,
        connectionError,
        modal
    } = windowHandler || {
        master: {},
        connectionError: false,
        modal: false
    }


    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: {}
    }

    return {
        master,
        connectionError,
        breadcrumb,
        modal
    }
}

NavigationTree.propTypes = {
    dispatch: PropTypes.func.isRequired
};

NavigationTree = connect(mapStateToProps)(NavigationTree);

export default NavigationTree
