import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import Translate from 'react-translate-component';
import counterpart from 'counterpart';

import MenuOverlayContainer from '../components/header/MenuOverlayContainer';
import {push} from 'react-router-redux';
import DebounceInput from 'react-debounce-input';
import Container from '../components/Container';

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
            deepNode: null,
            en: {
                "window.dropZone.caption": "Drop files here",
                "window.advancedEdit.caption":	"Advanced Edit",
                "window.Print.caption":	"Print",
                "window.Delete.caption":	"Delete",
                "window.New.caption":	"New",
                "window.settings.caption":	"Settings",
                "window.logOut.caption":	"Log Out",
                "window.zoomInto.caption":	"Zoom into",
                "window.newTab.caption":	"Open in new tab",
                "window.delete.caption":	"Delete",
                "window.batchEntry.caption":	"Batch entry",
                "window.batchEntryClose.caption":	"Close batch entry",
                "window.addNew.caption":	"Add new",
                "window.sideList.documentList.tooltip":	"Document list",
                "window.sideList.referencedDocuments.tooltip":	"Referenced documents",
                "window.sideList.attachments.tooltip":	"Attachments",
                "window.sideList.attachments.empty":	"There is no attachment",
                "window.sideList.referenced.empty":	"There is no referenced document",
                "window.actionMenu.tooltip":	"Action menu",
                "window.navigation.tooltip":	"Navigation",
                "window.docStatus.tooltip":	"Doc status",
                "window.inbox.tooltip":	"Inbox",
                "window.userMenu.tooltip":	"User menu",
                "window.sideList.tooltip":	"Side list",
                "window.actions.caption":	"Actions",
                "window.elementToDisplay.caption":	"Select element to display its attributes",
                "window.errorLoadingData.caption":	"Error loading data...",
                "window.noData.caption":	"No data",
                "window.error.caption":	"Connection lost",
                "window.error.description":	"There are some connection issues. Check connection and try to refresh the page.",
                "window.connectionProblem.caption":	"Connection problem",
                "window.filters.caption":	"Filters",
                "window.activeFilter.caption":	"Active Filter",
                "window.noMandatory.caption":	"Mandatory filters are not filled!",
                "window.apply.caption":	"Apply",
                "window.type.placeholder":	"Type phrase here",
                "window.noResults.caption":	"There are no results",
                "window.browseTree.caption":	"Browse whole tree",
                "window.back.caption":	"Back",
                "window.inbox.caption":	"Inbox",
                "window.markAsRead.caption":	"Mark all as read",
                "window.inbox.empty":	"Inbox is empty",
                "window.allInbox.caption":	"Show all",
                "window.notification.caption":	"Notification",
                "window.goTo.caption":	"Go to page",
                "window.totalItems.caption":	"Total items",
                "window.selectAll.caption":	"Select all",
                "window.selectAllOnPage.caption":	"Select all on this page",
                "window.itemsSelected.caption":	"items selected",
                "window.noItemSelected.caption":	"No item selected",
                "window.login.caption":	"Login",
                "window.password.caption":	"Password",
                "window.selectRole.caption":	"Select role",
                "window.send.caption":	"Send",
                "window.browserError.description":	"Your browser might be not fully supported. Please try Chrome in case of any errors.",
                "window.uploadPhoto.caption":	"Upload a photo",
                "window.clearPhoto.caption":	"Clear",
                "window.takeFromCamera.caption":	"Take from camera"
            },
            test: {
                example: {
                    greeting: 'Hello %(name)s! How are you today?'
                }
            },
            de: {
                example: {
                    greeting: 'Hello %(name)s! Its a de translation!'
                }
            }

        };
    }

    componentDidMount = () => {
        this.getData();
        document.getElementById('search-input').focus();


        
        counterpart.registerTranslations('en', this.state.test);
        counterpart.registerTranslations('de', this.state.de);

        counterpart.setLocale('de');
        
        
        console.log(counterpart.translate('example.greeting',{ name: 'aaaa' }));
    }

    getData = (callback) => {
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

    updateData = () => {
        this.getData();
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

            queryPathsRequest(e.target.value, '', true)
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
        }else{
            this.getData(this.clearValue);
        }

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
        }
    }

    handleRedirect = (elementId) => {
        const {dispatch} = this.props;
        dispatch(push('/window/' + elementId));
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

        const _t = Translate.translate;
        return(
            <div>
                { _t('example.greeting') }
                <div className="search-wrapper">
                    <div className="input-flex input-primary">
                        <i className="input-icon meta-icon-preview"/>
                        <DebounceInput
                            debounceTimeout={250}
                            type="text" id="search-input"
                            className="input-field"
                            placeholder="Type phrase here"
                            onChange={e => this.handleQuery(e) }
                            onKeyDown={(e) =>
                                this.handleKeyDown(e)}
                        />
                        {query && <i
                            className="input-icon meta-icon-close-alt pointer"
                            onClick={e => this.handleClear(e) }
                            />}
                    </div>
                </div>
                <p
                    className="menu-overlay-header menu-overlay-header-main menu-overlay-header-spaced"
                    >
                    {rootResults.caption}
                </p>
                <div className="column-wrapper">
                    {queriedResults && queriedResults.map((subitem, subindex) =>
                        <MenuOverlayContainer
                            key={subindex}
                            printChildren={true}
                            showBookmarks={true}
                            handleClickOnFolder={this.handleDeeper}
                            handleRedirect={this.handleRedirect}
                            handleNewRedirect={this.handleNewRedirect}
                            openModal={this.openModal}
                            updateData={this.updateData}
                            onKeyDown={(e) => this.handleKeyDown(e)}
                            {...subitem}
                        />
                    )}
                    { queriedResults.length === 0 && query != '' &&
                        <span>There are no results</span>
                    }
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
