import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Container from '../components/Container';
import DraggableWrapper from '../components/widget/DraggableWrapper';

var d3 = require('d3');

export class D3 extends Component {
    constructor(props){
        super(props);
    }
    
    componentDidMount() {
        d3.selectAll("p")
        .data([4, 8, 15, 16, 23, 42])
            .style("font-size", function(d) { return d + "px"; });
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
                <p>asasas</p>
                <p>sssdsd</p>
                <p>sdsd</p>
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
