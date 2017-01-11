import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Container from '../components/Container';
import DraggableWrapper from '../components/widget/DraggableWrapper';

import BarChart from '../components/charts/BarChart';
import PieChart from '../components/charts/PieChart';

export class D3 extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount(){

    }

    render() {
        const {breadcrumb} = this.props; 
        return (
            <Container
                breadcrumb={breadcrumb}
                siteName = {"Charts"}
                noMargin = {true}
            >
                <div className="container-fluid dashboard-wrapper">
                       
                    <div className="col-xs-4">
                          <BarChart/>
                          <PieChart/>
                    </div>
                    
                </div>
            </Container>
           
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
