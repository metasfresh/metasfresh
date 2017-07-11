import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import MenuOverlayContainer from '../components/header/MenuOverlayContainer';
import {push} from 'react-router-redux';
import DebounceInput from 'react-debounce-input';
import Container from '../components/Container';
import counterpart from 'counterpart';

import {
    rootRequest,
    nodePathsRequest,
    queryPathsRequest
 } from '../actions/MenuActions';

import {
    openModal
} from '../actions/WindowActions';

class NavigationTree extends Component {
    constructor(props){
        super(props);
        this.state = {
            rootResults: {
                caption: '',
                children: []
            },
            query: '',
            queriedResults: [],
            deepNode: null
        };
    }

    componentDidMount = () => {
        this.getData();
        document.getElementById('search-input').focus();
    }

    getData = (callback, doNotResetState) => {
        const {query} = this.state;

        if(doNotResetState && query){
            this.queryRequest(query);
        } else {
            rootRequest().then(response => {
                this.setState(Object.assign({}, this.state, {
                    rootResults: response.data,
                    queriedResults: response.data.children,
                    query: ''
                }), () => {
                    callback();
                })
            }).catch((err) => {
                if(err.response && err.response.status === 404) {
                    this.setState(Object.assign({}, this.state, {
                        queriedResults: [],
                        rootResults: {},
                        query: ''
                    }), () => {
                        callback();
                    })
                }
            });
        }
    }


    updateData = () => {
        this.getData(false, true);
    }

    openModal = (windowType, type, caption, isAdvanced) => {
        const {dispatch} = this.props;
        dispatch(openModal(caption, windowType, type, null, null, isAdvanced));
    }

    handleQuery = (e) => {
        e.preventDefault();
        if(e.target.value){
            this.setState({
                query: e.target.value
            });

            this.queryRequest(e.target.value);

        }else{
            this.getData(this.clearValue);
        }
    }

    queryRequest = (value) => {
        queryPathsRequest(value, '', true)
            .then(response => {
                this.setState({
                    queriedResults: response.data.children
                })
            }).catch((err) => {
                if(err.response && err.response.status === 404) {
                    this.setState({
                        queriedResults: [],
                        rootResults: {}
                    })
                }
            });
    }

    clearValue = () => {
        document.getElementById('search-input').value=''
    }

    handleClear = (e) => {
        e.preventDefault();
        this.getData(this.clearValue);
    }

    handleKeyDown = (e) => {
        const input = document.getElementById('search-input');
        const firstMenuItem =
            document.getElementsByClassName('js-menu-item')[0];

        switch(e.key) {
            case 'ArrowDown':
                if(document.activeElement === input) {
                    firstMenuItem.focus();
                }
                break;
            case 'Tab':
                e.preventDefault();
                if(document.activeElement === input) {
                    firstMenuItem.focus();
                } else {
                    input.focus();
                }
                break;
            case 'Enter':
                e.preventDefault();
                document.activeElement.childNodes[0].childNodes[0].click();
                break;
        }
    }

    handleRedirect = (elementId, isNew, type) => {
        const {dispatch} = this.props;
        dispatch(push(
            '/' + (type ? type : 'window') + '/' + elementId
        ));
    }

    handleNewRedirect = (elementId) => {
        const {dispatch} = this.props;
        dispatch(push('/window/' + elementId + '/new'));
    }

    handleDeeper = (e, nodeId) => {
        e.preventDefault();

        nodePathsRequest(nodeId, 4).then(response => {
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

    renderTree = () => {
        const {rootResults, queriedResults, query} = this.state;

        return(
            <div className="sitemap">
                <div className="search-wrapper">
                    <div className="input-flex input-primary">
                        <i className="input-icon meta-icon-preview"/>

                        <DebounceInput
                            debounceTimeout={250}
                            type="text" id="search-input"
                            className="input-field"
                            placeholder={counterpart.translate(
                                'window.type.placeholder'
                            )}
                            onChange={this.handleQuery}
                            onKeyDown={this.handleKeyDown}
                        />

                        {query && (
                            <i
                                className="input-icon meta-icon-close-alt pointer"
                                onClick={this.handleClear}
                            />
                        )}
                    </div>
                </div>

                <p className="menu-overlay-header menu-overlay-header-main menu-overlay-header-spaced">
                    {rootResults.caption}
                </p>

                <div className="column-wrapper">
                    {queriedResults && queriedResults.map((subitem, subindex) =>
                        <MenuOverlayContainer
                            key={subindex}
                            printChildren={true}
                            showBookmarks={true}
                            openModal={this.openModal}
                            updateData={this.updateData}
                            onKeyDown={this.handleKeyDown}
                            handleClickOnFolder={this.handleDeeper}
                            handleRedirect={this.handleRedirect}
                            handleNewRedirect={this.handleNewRedirect}
                            {...subitem}
                        />
                    )}

                    {(queriedResults.length === 0) && (query !== '') && (
                        <span>There are no results</span>
                    )}
                </div>
            </div>
        )
    }

    render() {
        const {rawModal, modal} = this.props;
        const {rootResults} = this.state;

        return (
            <Container
                siteName = "Sitemap"
                {...{modal, rawModal}}
            >
                {this.renderTree(rootResults)}
            </Container>
        );
    }
}

function mapStateToProps(state) {
    const { windowHandler } = state;

    const {
        modal,
        rawModal
    } = windowHandler || {
        modal: {},
        rawModal: {}
    }

    return {
        modal, rawModal
    }
}

NavigationTree.propTypes = {
    dispatch: PropTypes.func.isRequired,
    modal: PropTypes.object.isRequired,
    rawModal: PropTypes.object.isRequired,
};

NavigationTree = connect(mapStateToProps)(NavigationTree);

export default NavigationTree
