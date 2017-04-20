import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import Container from '../components/Container';
import DraggableWrapper from '../components/widget/DraggableWrapper';

export class Dashboard extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {location} = this.props;
        return (
            <Container
                siteName="Dashboard"
                noMargin={true}
            >
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

Dashboard = connect()(Dashboard);

export default Dashboard
