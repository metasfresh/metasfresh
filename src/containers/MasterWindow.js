import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    findRowByPropName,
    startProcess
} from '../actions/WindowActions';

import {
    getData
} from '../actions/GenericActions';

import {
    addNotification
} from '../actions/AppActions';

import Window from '../components/Window';
import Modal from '../components/app/Modal';
import MasterWidget from '../components/widget/MasterWidget';
import Container from '../components/Container';

class MasterWindow extends Component {
    constructor(props){
        super(props);

        this.state = {
            newRow: false
        }
    }

    componentDidUpdate() {
        const {master} = this.props;
        console.log('******* master *******');
        master.data.map((item)=> {
            if(item.field === 'C_BPartner_ID'){
                console.log(item);
            }
        });
    }

    closeModalCallback = (entity, isNew, dataId) => {
        const {dispatch} = this.props;

        if(isNew){
            this.setState(
                Object.assign({}, this.state, {
                    newRow: true
                }), () => {
                    setTimeout(() => {
                        this.setState(Object.assign({}, this.state, {
                            newRow: false
                        }))
                    }, 1000);
                }
            )
        }
    }

    render() {
        const {master, connectionError, modal, breadcrumb, references, actions} = this.props;
        const {newRow} = this.state;
        const {documentNoElement, docActionElement, documentSummaryElement, type} = master.layout;
        const dataId = findRowByPropName(master.data, "ID").value;
        const docNoData = findRowByPropName(master.data, documentNoElement && documentNoElement.fields[0].field);

        const docStatusData = {
            "status": findRowByPropName(master.data, "DocStatus"),
            "action": findRowByPropName(master.data, "DocAction"),
            "displayed": true
        };

        const docSummaryData =  findRowByPropName(
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
                showSidelist={true}
            >
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
                <Window
                    data={master.data}
                    layout={master.layout}
                    rowData={master.rowData}
                    dataId={dataId}
                    isModal={false}
                    newRow={newRow}
                />
            </Container>
        );
    }
}

MasterWindow.propTypes = {
    connectionError: PropTypes.bool.isRequired,
    modal: PropTypes.object.isRequired,
    master: PropTypes.object.isRequired,
    breadcrumb: PropTypes.array.isRequired,
    references: PropTypes.array.isRequired,
    actions: PropTypes.array.isRequired,
    dispatch: PropTypes.func.isRequired
};

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
        breadcrumb,
        references,
        actions
    } = menuHandler || {
        references: [],
        breadcrumb: [],
        actions: []
    }

    return {
        master,
        connectionError,
        breadcrumb,
        references,
        modal,
        actions
    }
}

MasterWindow = connect(mapStateToProps)(MasterWindow)

export default MasterWindow;
