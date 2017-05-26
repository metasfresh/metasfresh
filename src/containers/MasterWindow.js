import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {push} from 'react-router-redux';

import {
    attachFileAction,
    clearMasterData,
    getTab,
    addRowData,
    sortTab
} from '../actions/WindowActions';

import {
    addNotification
} from '../actions/AppActions';

import Window from '../components/Window';
import BlankPage from '../components/BlankPage';
import Modal from '../components/app/Modal';
import RawModal from '../components/app/RawModal';
import DocumentList from '../components/app/DocumentList';
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
            window.addEventListener('beforeunload', this.confirm);
        }
    }

    componentDidUpdate(prevProps) {
        const {master, modal, params, dispatch} = this.props;
        const isDocumentNotSaved = !master.saveStatus.saved;
        const isDocumentSaved = master.saveStatus.saved;

        if(prevProps.master.saveStatus.saved && isDocumentNotSaved) {
            window.addEventListener('beforeunload', this.confirm)
        }
        if (!prevProps.master.saveStatus.saved && isDocumentSaved) {
            window.removeEventListener('beforeunload', this.confirm)
        }

        // When closing modal, we need to update the stale tabs
        if(!modal.visible && modal.visible !== prevProps.modal.visible){
            Object.keys(master.includedTabsInfo).map(tabId => {
                dispatch(getTab(tabId, params.windowType, master.docId))
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

        window.removeEventListener('beforeunload', this.confirm);

        if(isDocumentNotSaved && !isDeleted){
            const result = window.confirm('Do you really want to leave?');

            if(!result){
                dispatch(push(pathname));
            }
        }

        dispatch(clearMasterData());
    }

    confirm = (e) => {
        e.returnValue = '';
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
        dispatch(getTab(tabId, windowType, dataId, orderBy)).then(res => {
            dispatch(addRowData({[tabId]: res}, 'master'));
        });
    }

    renderBody = () => {
        const {
            master, modal, rawModal, selected, indicator, selectedWindowType,
            includedView, processStatus
        } = this.props;
        const {newRow, modalTitle} = this.state;
        const {type} = master.layout;
        const dataId = master.docId;

        let body = [];

        if(modal.visible){
            body.push(
                <Modal
                    key="modal"
                    relativeType={type}
                    relativeDataId={dataId}
                    triggerField={modal.triggerField}
                    windowType={modal.type}
                    dataId={modal.dataId ? modal.dataId : dataId}
                    data={modal.data}
                    layout={modal.layout}
                    rowData={modal.rowData}
                    tabId={modal.tabId}
                    rowId={modal.rowId}
                    modalTitle={modal.title}
                    modalType={modal.modalType}
                    modalViewId={modal.viewId}
                    isAdvanced={modal.isAdvanced}
                    viewId={null}
                    modalViewDocumentIds={modal.viewDocumentIds}
                    closeCallback={this.closeModalCallback}
                    rawModalVisible={rawModal.visible}
                    indicator={indicator}
                    modalSaveStatus={
                        modal.saveStatus &&
                        modal.saveStatus.saved !== undefined ?
                            modal.saveStatus.saved : true
                    }
                    isDocumentNotSaved={modal.saveStatus ?
                        !modal.saveStatus.saved &&
                        !modal.validStatus.initialValue : false
                    }
                 />
            )
        }

        if(rawModal.visible){
            body.push(
                <RawModal
                    key="rawModal"
                    modalTitle={modalTitle}
                >
                    <div className="document-lists-wrapper">
                        <DocumentList
                            type="grid"
                            windowType={rawModal.type}
                            defaultViewId={rawModal.viewId}
                            selected={selected}
                            selectedWindowType={selectedWindowType}
                            isModal={true}
                            setModalTitle={this.setModalTitle}
                            processStatus={processStatus}
                            includedView={includedView}
                            inBackground={
                                includedView.windowType && includedView.viewId
                            }
                        >
                        </DocumentList>
                        {includedView.windowType && includedView.viewId &&
                            <DocumentList
                                type="includedView"
                                selected={selected}
                                selectedWindowType={selectedWindowType}
                                windowType={includedView.windowType}
                                defaultViewId={includedView.viewId}
                                isIncluded={true}
                            />
                        }
                    </div>
                </RawModal>
            )
        }

        body.push(
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
                handleDropFile={accepted => this.handleDropFile(accepted)}
                handleRejectDropped={
                    rejected => this.handleRejectDropped(rejected)
                }
            />
        )

        return body;
    }

    render() {
        const {
            master, modal, breadcrumb, params
        } = this.props;

        const {
            dropzoneFocused
        } = this.state;

        const {
            docActionElement, documentSummaryElement
        } = master.layout;

        const dataId = master.docId;
        const docNoData = master.data.DocumentNo;

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
                {...{dropzoneFocused, docStatusData, docSummaryData,
                    dataId, breadcrumb, docNoData, isDocumentNotSaved}}
                docActionElem = {docActionElement}
                windowType={params.windowType}
                docId={params.docId}
                showSidelist={true}
                showIndicator={!modal.visible}
                handleDeletedStatus={this.handleDeletedStatus}
            >
                {dataId === 'notfound' ?
                    <BlankPage what="Document" /> : this.renderBody()
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
        processStatus
    } = appHandler || {
        processStatus: ''
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
        processStatus
    }
}

MasterWindow.contextTypes = {
    router: PropTypes.object.isRequired
}

MasterWindow = connect(mapStateToProps)(MasterWindow)

export default MasterWindow;
