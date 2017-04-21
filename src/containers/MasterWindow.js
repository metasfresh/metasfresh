import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {push} from 'react-router-redux';

import {
    findRowByPropName,
    attachFileAction,
    clearMasterData
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
        const {master} = this.props;
        const isDocumentNotSaved = !master.saveStatus.saved;
        const isDocumentSaved = master.saveStatus.saved;

        if(prevProps.master.saveStatus.saved && isDocumentNotSaved) {
            window.addEventListener('beforeunload', this.confirm)
        }
        if (!prevProps.master.saveStatus.saved && isDocumentSaved) {
            window.removeEventListener('beforeunload', this.confirm)
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
        const dataId = findRowByPropName(master.data, 'ID').value;
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

    renderBody = () => {
        const {
            master, modal, rawModal, selected, indicator
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
                    <DocumentList
                        type="grid"
                        windowType={rawModal.type}
                        defaultViewId={rawModal.viewId}
                        selected={selected}
                        setModalTitle={this.setModalTitle}
                    />
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
            documentNoElement, docActionElement, documentSummaryElement
        } = master.layout;

        const dataId = master.docId;

        const docNoData = findRowByPropName(
            master.data, documentNoElement && documentNoElement.fields[0].field
        );

        const docStatusData = {
            'status': findRowByPropName(master.data, 'DocStatus'),
            'action': findRowByPropName(master.data, 'DocAction'),
            'displayed': true
        };

        const docSummaryData = findRowByPropName(
            master.data,
            documentSummaryElement && documentSummaryElement.fields[0].field
        );

        return (
            <Container
                entity="window"
                {...{dropzoneFocused, docStatusData, docNoData, docSummaryData,
                    dataId, breadcrumb}}
                docActionElem = {docActionElement}
                docNoElement = {documentNoElement}
                windowType={params.windowType}
                showSidelist={true}
                showIndicator={!modal.visible}
                handleDeletedStatus={this.handleDeletedStatus}
                isDocumentNotSaved={
                    dataId !== 'notfound' &&
                    !master.saveStatus.saved &&
                    !master.validStatus.initialValue
                }
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
    const { windowHandler, menuHandler } = state;
    const {
        master,
        modal,
        rawModal,
        selected,
        indicator
    } = windowHandler || {
        master: {},
        modal: false,
        rawModal: {},
        selected: [],
        indicator: ''
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
        indicator
    }
}

MasterWindow.contextTypes = {
    router: PropTypes.object.isRequired
}

MasterWindow = connect(mapStateToProps)(MasterWindow)

export default MasterWindow;
