import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import { Link } from 'react-router';

import Header from '../components/header/Header';
import MenuOverlayContainer from '../components/header/MenuOverlayContainer';
import {push} from 'react-router-redux';
import DebounceInput from 'react-debounce-input';
import Container from '../components/Container';


import {
    rootRequest,
    nodePathsRequest,
    queryPathsRequest,
    getWindowBreadcrumb
 } from '../actions/MenuActions';

import {
    openModal
} from '../actions/WindowActions';


class NavigationTree extends Component {
    constructor(props){
        super(props);
        this.state = {
            rootResults: {
                caption: "",
                children: []
            },
            query: "",
            queriedResults: [],
            deepNode: null
        };
    }

    componentDidMount = () => {
        const {dispatch, windowType} = this.props;

        this.getData();
        dispatch(getWindowBreadcrumb("143"));
    }

    getData = (callback) => {
        const {dispatch} = this.props;
        dispatch(rootRequest()).then(response => {
            this.setState(Object.assign({}, this.state, {
                rootResults: response.data,
                queriedResults: response.data.children,
                query: ""
            }), () => {
                callback();
            })
        }).catch((err) => {
            if(err.response && err.response.status === 404) {
                this.setState(Object.assign({}, this.state, {
                    queriedResults: [],
                    rootResults: {},
                    query: ""
                }), () => {
                    callback();
                })
            }
        });
    }

    openModal = (windowType, type, caption, isAdvanced) => {
        const {dispatch} = this.props;
        dispatch(openModal(caption, windowType, type, null, null, isAdvanced));
    }

    handleQuery = (e) => {
        const {dispatch} = this.props;
        const {rootResults} = this.state;

        e.preventDefault();
        if(!!e.target.value){
            this.setState(Object.assign({}, this.state, {
                query: e.target.value
            }));

            dispatch(queryPathsRequest(e.target.value, 9, true)).then(response => {

                this.setState(Object.assign({}, this.state, {
                    queriedResults: response.data.children
                }))
            }).catch((err) => {
                if(err.response && err.response.status === 404) {
                    this.setState(Object.assign({}, this.state, {
                        queriedResults: [],
                        rootResults: {}
                    }))
                }
            });
        }else{
            this.getData(this.clearValue);
        }

    }

    clearValue = () => {
        document.getElementById('search-input').value=""
    }

    handleClear = (e) => {
        e.preventDefault();
        this.getData(this.clearValue);
    }

    renderTree = (res) => {
      const {dispatch} = this.props;
      const {rootResults, queriedResults, query} = this.state;

      return(
          <div>
              <div className="search-wrapper">
                  <div className="input-flex input-primary">
                    <i className="input-icon meta-icon-preview"/>
                    <DebounceInput 
                        debounceTimeout={250} 
                        type="text" id="search-input" 
                        className="input-field" 
                        placeholder="Type phrase here" 
                        onChange={e => this.handleQuery(e) }
                    />
                    {this.state.query && <i className="input-icon meta-icon-close-alt pointer" onClick={e => this.handleClear(e) } />}
                  </div>
              </div>
              <p className="menu-overlay-header menu-overlay-header-main menu-overlay-header-spaced">{rootResults.caption}</p>
              <div className="column-wrapper">
                    {queriedResults && queriedResults.map((subitem, subindex) =>
                        <MenuOverlayContainer
                            key={subindex}
                            printChildren={true}
                            handleClickOnFolder={this.handleDeeper}
                            handleRedirect={this.handleRedirect}
                            handleNewRedirect={this.handleNewRedirect}
                            openModal={this.openModal}
                            {...subitem}
                        />
                    )}
                    { queriedResults.length === 0 && query!="" &&
                        <span>There are no results</span>
                    }
              </div>
          </div>
      )

  }
    handleRedirect = (elementId) => {
        const {dispatch} = this.props;
        dispatch(push("/window/" + elementId));
    }

    handleNewRedirect = (elementId) => {
        const {dispatch} = this.props;
        dispatch(push("/window/" + elementId + "/new"));
    }

    handleDeeper = (e, nodeId) => {
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
            <Container
                breadcrumb={breadcrumb.slice(0,1)}
                siteName = {"Sitemap"}
            >
                {this.renderTree(rootResults)}
            </Container>
        );
    }
}

function mapStateToProps(state) {
    const { windowHandler, menuHandler } = state;
    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: []
    }

    return {
        breadcrumb
    }
}

NavigationTree.propTypes = {
    dispatch: PropTypes.func.isRequired,
    breadcrumb: PropTypes.array.isRequired
};

NavigationTree = connect(mapStateToProps)(NavigationTree);

export default NavigationTree
