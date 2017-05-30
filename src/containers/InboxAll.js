import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import Container from '../components/Container';

import Inbox from '../components/inbox/Inbox';

class InboxAll extends Component {
    render() {
        const {
            inbox, modal, rawModal, processStatus, indicator, selected, 
            includedView
        } = this.props;

        return (
            <Container
                siteName = "Inbox"
                {...{modal, rawModal, processStatus, indicator, selected,
                    includedView}}
            >
                <Inbox
                    all={true}
                    inbox={inbox}
                />
            </Container>
        );
    }
}

function mapStateToProps(state) {
    const { appHandler, windowHandler, listHandler  } = state;
    
    const {
        inbox,
        processStatus
    } = appHandler || {
        inbox: {},
        processStatus: ''
    }
    
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

    return {
        inbox, modal, rawModal, selected, indicator, includedView, processStatus
    }
}

InboxAll.propTypes = {
    dispatch: PropTypes.func.isRequired,
    inbox: PropTypes.object.isRequired
};

InboxAll = connect(mapStateToProps)(InboxAll);

export default InboxAll
