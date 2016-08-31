import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    findRowByPropName
} from '../actions/WindowActions';

import Window from '../components/Window';
import Modal from '../components/app/Modal';
import Header from '../components/app/Header';
import OrderList from '../components/app/OrderList';
import ErrorScreen from '../components/app/ErrorScreen';


class MasterWindow extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {master, connectionError, modal} = this.props;
        const {documentNoElement, docActionElement, type} = master.layout;

        const dataId = findRowByPropName(master.data, "ID").value;
        const docNoData = findRowByPropName(master.data, "DocumentNo");
        const docStatusData = {
            "status": findRowByPropName(master.data, "DocStatus"),
            "action": findRowByPropName(master.data, "DocAction"),
            "displayed": true
        };

        return (
            <div>
                <Header
                    docStatus = {docActionElement}
                    docStatusData = {docStatusData}
                    docNo = {documentNoElement}
                    docNoData = {docNoData}
                    dataId={dataId}
                    windowType={type}
                />
                {connectionError && <ErrorScreen />}
                {modal.visible &&
                    <Modal
                        windowType={140}
                        dataId={dataId}
                        data={modal.data}
                        layout={modal.layout}
                     />
                 }
                <Window
                    data={master.data}
                    layout={master.layout}
                />
            </div>
        );
    }
}

MasterWindow.propTypes = {
    connectionError: PropTypes.bool.isRequired,
    modal: PropTypes.object.isRequired,
    master: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { windowHandler } = state;
    const {
        master,
        connectionError,
        modal
    } = windowHandler || {
        master: {},
        connectionError: false,
        modal: false
    }
    return {
        master,
        connectionError,
        modal
    }
}

MasterWindow = connect(mapStateToProps)(MasterWindow)

export default MasterWindow;
