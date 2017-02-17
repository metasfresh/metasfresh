import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    findRowByPropName,
    attachFileAction
} from '../actions/WindowActions';

import {
    addNotification
} from '../actions/AppActions'

import Window from '../components/Window';
import Modal from '../components/app/Modal';
import RawModal from '../components/app/RawModal';
import DocumentList from '../components/app/DocumentList';
import Container from '../components/Container';

class MasterWindow extends Component {
    constructor(props){
        super(props);

        this.state = {
            newRow: false,
            modalTitle: null
        }
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

    handleDropFile(file){
        file = file instanceof Array ? file[0] : file;

        if (!file instanceof File){
            return Promise.reject();
        }

        const { dispatch, master } = this.props;
        const dataId = findRowByPropName(master.data, 'ID').value;
        const { type } = master.layout;

        let fd = new FormData();
        fd.append('file', file);

        return dispatch(attachFileAction(type, dataId, fd));
    }

    setModalTitle = (title) => {
        this.setState({
            modalTitle: title
        })
    }

    render() {
        const {
            master, modal, breadcrumb, references, actions, attachments, rawModal,
            selected, dispatch
        } = this.props;
        const {newRow, modalTitle} = this.state;
        const {documentNoElement, docActionElement, documentSummaryElement, type} = master.layout;
        const dataId = master.docId;
        const docNoData = findRowByPropName(master.data, documentNoElement && documentNoElement.fields[0].field);

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
                docActionElem = {docActionElement}
                docStatusData = {docStatusData}
                docNoElement = {documentNoElement}
                docNoData = {docNoData}
                docSummaryData = {docSummaryData}
                dataId={dataId}
                windowType={type}
                breadcrumb={breadcrumb}
                references={references}
                actions={actions}
                attachments={attachments}
                showSidelist={true}
            >
            {/*TO REMOVE, ONLY DEV PURPOSE*/}
            <div onClick={() => {dispatch(addNotification('test', 'test', 5000))}}>NOTIFICATION</div>
                {modal.visible &&
                    <Modal
                        relativeType={type}
                        windowType={modal.type}
                        dataId={dataId}
                        data={modal.data}
                        layout={modal.layout}
                        rowData={modal.rowData}
                        tabId={modal.tabId}
                        rowId={modal.rowId}
                        modalTitle={modal.title}
                        modalType={modal.modalType}
                        isAdvanced={modal.isAdvanced}
                        viewId={null}
                        closeCallback={(isNew) => this.closeModalCallback(
                            modal.modalType, isNew, modal.layout.pinstanceId
                        )}
                     />
                 }
                 {rawModal.visible &&
                     <RawModal
                         modalTitle={modalTitle}
                     >
                         <DocumentList
                             type="grid"
                             windowType={parseInt(rawModal.type)}
                             defaultViewId={rawModal.viewId}
                             selected={selected}
                             setModalTitle={this.setModalTitle}
                         />
                     </RawModal>
                 }
                <Window
                    data={master.data}
                    layout={master.layout}
                    rowData={master.rowData}
                    dataId={dataId}
                    isModal={false}
                    newRow={newRow}
                    handleDropFile={(accepted, rejected) => this.handleDropFile(accepted, rejected)}
                />
            </Container>
        );
    }
}

MasterWindow.propTypes = {
    modal: PropTypes.object.isRequired,
    master: PropTypes.object.isRequired,
    breadcrumb: PropTypes.array.isRequired,
    references: PropTypes.array.isRequired,
    actions: PropTypes.array.isRequired,
    attachments: PropTypes.array.isRequired,
    dispatch: PropTypes.func.isRequired,
    selected: PropTypes.array,
    rawModal: PropTypes.object.isRequired
};

function mapStateToProps(state) {
    const { windowHandler, menuHandler } = state;
    const {
        master,
        modal,
        rawModal,
        selected
    } = windowHandler || {
        master: {},
        modal: false,
        rawModal: {},
        selected: []
    }

    const {
        breadcrumb,
        references,
        actions,
        attachments
    } = menuHandler || {
        references: [],
        breadcrumb: [],
        actions: [],
        attachments: []
    }
    
    return {
        master,
        breadcrumb,
        references,
        modal,
        actions,
        attachments,
        selected,
        rawModal
    }
}

MasterWindow = connect(mapStateToProps)(MasterWindow)

export default MasterWindow;
