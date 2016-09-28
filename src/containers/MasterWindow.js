import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    findRowByPropName,
    getData
} from '../actions/WindowActions';

import Window from '../components/Window';
import Modal from '../components/app/Modal';
import Header from '../components/app/Header';
import OrderList from '../components/app/OrderList';
import ErrorScreen from '../components/app/ErrorScreen';
import Widget from '../components/Widget';


class MasterWindow extends Component {
    constructor(props){
        super(props);
        this.state = {
            data: []
        };
    }

    handleUpdateClick =()=> {
        const {dispatch} = this.props;
        const {docId, windowType} = this.props.params;

        dispatch(getData(windowType, docId)).then(response => {
            this.setState(Object.assign({}, this.state, {
                data: response.data[0].fields
            }))
        });  
    }

    componentWillReceiveProps(props) {
        const {master} = this.props;
        this.setState(Object.assign({}, this.state, {
            data: master.data
        }))
    }

    render() {
        const {master, connectionError, modal, breadcrumb} = this.props;
        const {documentNoElement, docActionElement, documentSummaryElement, type} = master.layout;
        const{data} = this.state;
        const dataId = findRowByPropName(master.data, "ID").value;
        const docNoData = findRowByPropName(master.data, documentNoElement && documentNoElement.fields[0].field);
        
        const docStatusData = {
            "status": findRowByPropName(master.data, "DocStatus"),
            "action": findRowByPropName(master.data, "DocAction"),
            "displayed": true
        };
        const docSummaryData =  findRowByPropName(master.data, documentSummaryElement && documentSummaryElement.fields[0].field);
        return (
            <div>
                <Header
                    docStatus = {docActionElement}
                    docStatusData = {docStatusData}
                    docNo = {documentNoElement}
                    docNoData = {docNoData}
                    docSummaryData = {docSummaryData}
                    dataId={dataId}
                    windowType={type}
                    breadcrumb={breadcrumb}
                />
                {connectionError && <ErrorScreen />}
                {modal.visible &&
                    <Modal
                        windowType={modal.type}
                        dataId={dataId}
                        data={modal.data}
                        layout={modal.layout}
                        rowData={modal.rowData}
                        tabId={modal.tabId}
                        rowId={modal.rowId}
                        modalTitle={modal.title}
                        handleUpdateClick={e => this.handleUpdateClick()}
                     />
                 }
                <Window
                    data={data}
                    layout={master.layout}
                    rowData={master.rowData}
                    dataId={dataId}
                    isModal={false}
                />
            </div>
        );
    }
}

MasterWindow.propTypes = {
    connectionError: PropTypes.bool.isRequired,
    modal: PropTypes.object.isRequired,
    master: PropTypes.object.isRequired,
    breadcrumb: PropTypes.array.isRequired,
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

MasterWindow = connect(mapStateToProps)(MasterWindow)

export default MasterWindow;
