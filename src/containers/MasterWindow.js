import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {push, replace} from 'react-router-redux';

import {
    attachFileAction,
    clearMasterData,
    getTab,
    addRowData,
    sortTab,
    connectWS,
    disconnectWS,
    fireUpdateData
} from '../actions/WindowActions';

import {
    addNotification
} from '../actions/AppActions';

import Window from '../components/Window';
import BlankPage from '../components/BlankPage';
import Container from '../components/Container';

class MasterWindow extends Component {
    constructor(props){
        super(props);

        this.state = {
            newRow: false,
            modalTitle: null,
            isDeleted: false,
            dropzoneFocused: false
        }
    }

    componentDidMount(){
        const {master} = this.props;
        const isDocumentNotSaved = !master.saveStatus.saved;

        if(isDocumentNotSaved){
            this.initEventListeners();
        }
    }

    componentDidUpdate(prevProps) {
        const {master, modal, params, dispatch} = this.props;
        const isDocumentNotSaved = !master.saveStatus.saved;
        const isDocumentSaved = master.saveStatus.saved;

        if(prevProps.master.websocket !== master.websocket && master.websocket){
            connectWS.call(this, master.websocket, msg => {
                const {includedTabsInfo, stale} = msg;
                const {master} = this.props;

                if(stale){
                    dispatch(
                        fireUpdateData('window', params.windowType, params.docId, null, null, null, null )
                    );
                    
                }

                includedTabsInfo && Object.keys(includedTabsInfo).map(tabId => {
                    const tabLayout = master.layout.tabs && master.layout.tabs.filter(tab =>
                        tab.tabId === tabId
                    )[0];
                    if(
                        tabLayout && tabLayout.queryOnActivate &&
                        master.layout.activeTab !== tabId
                    ){
                        return;
                    }
                    includedTabsInfo[tabId] &&
                        getTab(tabId, params.windowType, master.docId)
                            .then(tab => {
                                dispatch(
                                    addRowData({[tabId]: tab}, 'master')
                                );
                            });
                })
            });
        }

        if(prevProps.master.saveStatus.saved && isDocumentNotSaved) {
            this.initEventListeners();
        }
        if (!prevProps.master.saveStatus.saved && isDocumentSaved) {
            this.removeEventListeners();
        }

        // When closing modal, we need to update the stale tabs
        if(!modal.visible && modal.visible !== prevProps.modal.visible){
            master.includedTabsInfo &&
                Object.keys(master.includedTabsInfo).map(tabId => {
                    getTab(tabId, params.windowType, master.docId)
                        .then(tab => {
                            dispatch(addRowData({[tabId]: tab}, 'master'));
                        });
                })
        }
    }

    componentWillUnmount() {
        const { master, dispatch } = this.props;
        const { isDeleted } = this.state;
        const {pathname} = this.props.location;
        const isDocumentNotSaved =
            !master.saveStatus.saved && master.saveStatus.saved !== undefined;

        this.removeEventListeners();

        if(isDocumentNotSaved && !isDeleted){
            const result = window.confirm('Do you really want to leave?');

            if(!result){
                dispatch(push(pathname));
            }
        }

        dispatch(clearMasterData());
        disconnectWS.call(this);
    }

    confirm = (e) => {
        e.returnValue = '';
    }

    initEventListeners = () => {
        window.addEventListener('beforeunload', this.confirm);
    }

    removeEventListeners = () => {
        window.removeEventListener('beforeunload', this.confirm);
    }

    closeModalCallback = (isNew) => {
        if(isNew){
            this.setState({
                    newRow: true
                }, () => {
                    setTimeout(() => {
                        this.setState({
                            newRow: false
                        })
                    }, 1000);
                }
            )
        }
    }

    handleDropFile(files){
        const file = files instanceof Array ? files[0] : files;

        if (!(file instanceof File)){
            return Promise.reject();
        }

        const { dispatch, master } = this.props;
        const dataId = master.data ? master.data.ID.value : -1;
        const { type } = master.layout;

        let fd = new FormData();
        fd.append('file', file);

        return dispatch(attachFileAction(type, dataId, fd));
    }

    handleDragStart = () => {
        this.setState({
            dropzoneFocused: true
        }, () => {
            this.setState({
                dropzoneFocused: false
            })
        })
    }

    handleRejectDropped(droppedFiles){
        const { dispatch } = this.props;

        const dropped = droppedFiles instanceof Array ?
            droppedFiles[0] : droppedFiles;

        dispatch(addNotification(
            'Attachment', 'Dropped item [' +
            dropped.type +
            '] could not be attached', 5000, 'error'
        ))
    }

    setModalTitle = (title) => {
        this.setState({
            modalTitle: title
        })
    }

    handleDeletedStatus = (param) => {
        this.setState({
            isDeleted: param
        })
    }

    sort = (asc, field, startPage, page, tabId) => {
        const {dispatch, master} = this.props;
        const {windowType} = this.props.params;
        const orderBy = (asc ? '+' : '-') + field;
        const dataId = master.docId;

        dispatch(sortTab('master', tabId, field, asc));
        getTab(tabId, windowType, dataId, orderBy).then(res => {
            dispatch(addRowData({[tabId]: res}, 'master'));
        });
    }

    render() {
        const {
            master, modal, breadcrumb, params, rawModal, selected,
            selectedWindowType, includedView, processStatus
        } = this.props;

        const {
            dropzoneFocused, newRow, modalTitle
        } = this.state;

        const {
            docActionElement, documentSummaryElement
        } = master.layout;

        const dataId = master.docId;
        const docNoData = master.data.DocumentNo;
        let activeTab;
        if (master.layout) {
            activeTab = master.layout.activeTab
        }

        const docStatusData = {
            'status': master.data.DocStatus || -1,
            'action': master.data.DocAction || -1,
            'displayed': true
        };

        const docSummaryData = documentSummaryElement &&
            master.data[documentSummaryElement.fields[0].field];

        const isDocumentNotSaved = dataId !== 'notfound' &&
        (master.saveStatus.saved !== undefined && !master.saveStatus.saved) &&
        (master.validStatus.initialValue !== undefined) &&
        !master.validStatus.initialValue

        return (
            <Container
                entity="window"
                {...{dropzoneFocused, docStatusData, docSummaryData, modal,
                    dataId, breadcrumb, docNoData, isDocumentNotSaved, rawModal,
                    selected, selectedWindowType, modalTitle, includedView,
                    processStatus, activeTab
                }}
                closeModalCallback={this.closeModalCallback}
                setModalTitle={this.setModalTitle}
                docActionElem = {docActionElement}
                windowType={params.windowType}
                docId={params.docId}
                showSidelist={true}
                showIndicator={!modal.visible}
                handleDeletedStatus={this.handleDeletedStatus}
            >
                {dataId === 'notfound' ?
                    <BlankPage what="Document" /> :
                    <Window
                        key="window"
                        data={master.data}
                        layout={master.layout}
                        rowData={master.rowData}
                        tabsInfo={master.includedTabsInfo}
                        sort={this.sort}
                        dataId={dataId}
                        isModal={false}
                        newRow={newRow}
                        handleDragStart={this.handleDragStart}
                        handleDropFile={accepted =>
                            this.handleDropFile(accepted)}
                        handleRejectDropped={
                            rejected => this.handleRejectDropped(rejected)
                        }
                    />
                }
            </Container>
        );
    }
}

MasterWindow.propTypes = {
    modal: PropTypes.object.isRequired,
    master: PropTypes.object.isRequired,
    breadcrumb: PropTypes.array.isRequired,
    dispatch: PropTypes.func.isRequired,
    selected: PropTypes.array,
    rawModal: PropTypes.object.isRequired,
    indicator: PropTypes.string.isRequired,
    me: PropTypes.object.isRequired
};

function mapStateToProps(state) {
    const { windowHandler, menuHandler, listHandler, appHandler } = state;
    const {
        master,
        modal,
        rawModal,
        selected,
        selectedWindowType,
        indicator
    } = windowHandler || {
        master: {},
        modal: false,
        rawModal: {},
        selected: [],
        selectedWindowType: null,
        indicator: ''
    }

    const {
        includedView
    } = listHandler || {
        includedView: {}
    }

    const {
        processStatus,
        me
    } = appHandler || {
        processStatus: '',
        me: {}
    }

    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: []
    }

    return {
        master,
        breadcrumb,
        modal,
        selected,
        rawModal,
        indicator,
        selectedWindowType,
        includedView,
        processStatus,
        me
    }
}

MasterWindow.contextTypes = {
    router: PropTypes.object.isRequired
}

MasterWindow = connect(mapStateToProps)(MasterWindow)

export default MasterWindow;
