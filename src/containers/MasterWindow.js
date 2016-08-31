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
        const {data, layout, connectionError, modal} = this.props;
        const {documentNoElement, docActionElement, type} = this.props.layout;
        const dataId = findRowByPropName(data, "ID").value;
        return (
            <div>
                <Header
                    docStatus = {docActionElement}
                    docNo = {documentNoElement}
                    dataId={dataId}
                    windowType={type}
                />
                {connectionError && <ErrorScreen />}
                {modal &&
                    <Modal
                        windowType={140}
                        data={data}
                        layout={layout}
                     />
                 }
                <Window
                    data={data}
                    layout={layout}
                />
            </div>
        );
    }
}

MasterWindow.propTypes = {
    connectionError: PropTypes.bool.isRequired,
    modal: PropTypes.bool.isRequired,
    layout: PropTypes.object.isRequired,
    data: PropTypes.array.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { windowHandler } = state;
    const {
        layout,
        data,
        connectionError,
        modal
    } = windowHandler || {
        layout: {},
        data:[],
        connectionError: false,
        modal: false
    }
    return {
        data,
        layout,
        connectionError,
        modal
    }
}

MasterWindow = connect(mapStateToProps)(MasterWindow)

export default MasterWindow;
