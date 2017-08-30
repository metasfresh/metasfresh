import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import Container from '../components/Container';
import DraggableWrapper from '../components/dashboard/DraggableWrapper';

import { Steps, Hints } from 'intro.js-react';
import { introSteps, introHints } from '../components/intro/intro';

export class Dashboard extends Component {
    constructor(props){
        super(props);
        
        this.state = {
            editmode: false,
            hintsEnabled: null,
            introHints: null
        };
    }

    componentDidUpdate() {
        const {me} = this.props;

        if (me) {
            let docIntroHints;

            if (Array.isArray(introHints['default'])) {
                docIntroHints = introHints['default'];
            }

            this.setState({
                hintsEnabled: (docIntroHints && (docIntroHints.length > 0)),
                introHints: docIntroHints
            });
        }
    }

    toggleEditMode = () => this.setState(prev => ({editmode: !prev.editmode}));

    render() {
        const {
            location, modal, selected, rawModal, indicator, processStatus,
            includedView, enableTutorial
    } = this.props;

        const { editmode, hintsEnabled, introHints } = this.state;

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

                { (enableTutorial && introHints && (introHints.length > 0)) && (
                    <Hints
                        enabled={hintsEnabled}
                        hints={introHints}
                    />
                )}
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
        enableTutorial,
        processStatus,
        me
    } = appHandler || {
        enableTutorial: false,
        processStatus: '',
        me: {}
    }

    return {
        modal,
        selected,
        indicator,
        rawModal,
        processStatus,
        includedView,
        enableTutorial,
        me
    }
}

Dashboard = connect(mapStateToProps)(Dashboard);

export default Dashboard
