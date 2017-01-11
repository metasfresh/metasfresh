import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Container from '../components/Container';
import DraggableWrapper from '../components/widget/DraggableWrapper';
import BarChart from '../components/charts/BarChart';

export class D3 extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {breadcrumb} = this.props;
        return (
            // <Container
            //     breadcrumb={breadcrumb}
            //     siteName = {"Dashboard"}
            //     noMargin = {true}
            // >
            //     <div className="container-fluid dashboard-wrapper">
            //         <DraggableWrapper/>
            //     </div>
            // </Container>
            <div>
                 <BarChart/>
            </div>
           
        );
    }
}

function mapStateToProps(state) {
    const {menuHandler } = state;
    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: []
    }

    return {
        breadcrumb
    }
}

D3.propTypes = {
    dispatch: PropTypes.func.isRequired,
    breadcrumb: PropTypes.array.isRequired
};

D3 = connect(mapStateToProps)(D3);

export default D3
