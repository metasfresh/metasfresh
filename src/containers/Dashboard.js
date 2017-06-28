import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import Container from '../components/Container';
import DraggableWrapper from '../components/dashboard/DraggableWrapper';

export class Dashboard extends Component {
    constructor(props){
        super(props);
        
        this.state = {
            editmode: false
        }
    }

    toggleEditMode = () => this.setState(prev => ({editmode: !prev.editmode}));

    render() {
        const {
            location, modal, selected, rawModal, indicator, processStatus,
            includedView
        } = this.props;
        
        const {editmode} = this.state;

        return (
            <Container
                siteName="Dashboard"
                noMargin={true}
                handleEditModeToggle={this.toggleEditMode}
                {...{modal, rawModal, selected, indicator, processStatus,
                    includedView, editmode}}
            >
                <div className="container-fluid dashboard-wrapper">
                    <DraggableWrapper
                        {...{editmode}}
                        toggleEditMode={this.toggleEditMode}
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
        windowHandler, listHandler, appHandler
    } = state;

    const {
        modal,
        rawModal,
        selected,
        indicator,
    } = windowHandler || {
        modal: false,
        rawModal: false,
        selected: [],
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

    return {
        modal, selected, indicator, rawModal, processStatus, includedView
    }
}

Dashboard = connect(mapStateToProps)(Dashboard);

export default Dashboard
