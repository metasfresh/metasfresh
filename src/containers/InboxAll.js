import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Header from '../components/header/Header';
import {push} from 'react-router-redux';
import Container from '../components/Container';

import InboxItem from '../components/inbox/InboxItem';
import Inbox from '../components/inbox/Inbox';

class InboxAll extends Component {
    render() {
        const {inbox, breadcrumb} = this.props;

        return (
            <Container
                breadcrumb={breadcrumb.slice(0,1)}
                siteName = {"Inbox"}
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
    const { menuHandler, appHandler } = state;
    const {
        inbox
    } = appHandler || {
        inbox: {}
    }

    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: []
    }

    return {
        inbox,
        breadcrumb
    }
}

InboxAll.propTypes = {
    dispatch: PropTypes.func.isRequired,
    inbox: PropTypes.object.isRequired,
    breadcrumb: PropTypes.array.isRequired
};

InboxAll = connect(mapStateToProps)(InboxAll);

export default InboxAll
