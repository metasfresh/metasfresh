import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import Container from '../components/Container';
import DraggableWrapper from '../components/widget/DraggableWrapper';
import Modal from '../components/app/Modal';

export class Dashboard extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {
            location, modal, selected, rawModal,
            indicator
        } = this.props;

        return (
            <Container
                siteName="Dashboard"
                noMargin={true}
            >
                {modal.visible &&
                    <Modal
                        windowType={modal.type}
                        data={modal.data}
                        layout={modal.layout}
                        rowData={modal.rowData}
                        tabId={modal.tabId}
                        rowId={modal.rowId}
                        modalTitle={modal.title}
                        modalType={modal.modalType}
                        modalViewId={modal.viewId}
                        selected={selected}
                        viewId={null}
                        rawModalVisible={rawModal.visible}
                        indicator={indicator}
                        isDocumentNotSaved={
                            (modal.saveStatus && !modal.saveStatus.saved) &&
                            (modal.validStatus &&
                                !modal.validStatus.initialValue)
                        }
                     />
                 }

                <div className="container-fluid dashboard-wrapper">
                    <DraggableWrapper
                        dashboard={location.pathname}
                    />
                </div>
            </Container>
        );
    }
}

Dashboard.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const {
        windowHandler
    } = state;

    const {
        modal,
        rawModal,
        selected,
        indicator
    } = windowHandler || {
        modal: false,
        rawModal: false,
        selected: [],
        indicator: ''
    }

    return {
        modal, selected, indicator, rawModal
    }
}

Dashboard = connect(mapStateToProps)(Dashboard);

export default Dashboard
