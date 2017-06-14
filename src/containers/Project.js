import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import Container from '../components/Container';

class Project extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {modal, rawModal, breadcrumb, indicator} = this.props;
        return (
            <Container
                entity="project"
                {...{modal, rawModal, breadcrumb, indicator}}
            >
            </Container>
        );
    }
}

Project.propTypes = {
    modal: PropTypes.object.isRequired,
    breadcrumb: PropTypes.array.isRequired,
    dispatch: PropTypes.func.isRequired,
    rawModal: PropTypes.object.isRequired,
    indicator: PropTypes.string.isRequired
};

function mapStateToProps(state) {
    const { windowHandler, menuHandler } = state;
    
    const {
        modal,
        rawModal,
        indicator
    } = windowHandler || {
        modal: false,
        rawModal: {},
        indicator: ''
    }

    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: []
    }
    
    return {
        modal, rawModal, indicator, breadcrumb
    }
}

Project = connect(mapStateToProps)(Project)

export default Project;
