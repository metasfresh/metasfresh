import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Container from '../components/Container';
import WidgetWrapper from '../components/widget/DraggableWrapper';


import Draggable from './Draggable';


import {
    getRootBreadcrumb,
    getDashboardLink
 } from '../actions/MenuActions';

/**
 * Implements the drag source contract.
 */
var cardSource = {
    beginDrag: function (props) {
        return {
            text: props.text
        };
    }
}

/**
 * Specifies the props to inject into your component.
 */
function collect(connect, monitor) {
    return {
        connectDragSource: connect.dragSource(),
        isDragging: monitor.isDragging()
    };
}



export class Dashboard extends Component {
    constructor(props){
        super(props);
        this.state = {
            link: ''
        };
    }

    componentDidMount = () => {
        const {dispatch} = this.props;
        dispatch(getRootBreadcrumb());
        this.getDashboardLink();
    }

    getDashboardLink = () => {
        const {dispatch} = this.props;
        dispatch(getDashboardLink()).then(response => {
            this.setState(Object.assign({}, this.state, {
                link: response.data
            }))
        });
    }










    render() {
        const {breadcrumb} = this.props;
        const {link} = this.state;
        return (
            <Container
                breadcrumb={breadcrumb}
                siteName = {"Dashboard"}
                noMargin = {true}
            >
            <div className="container-fluid dashboard-wrapper">
                <WidgetWrapper/>
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

Dashboard.propTypes = {
    dispatch: PropTypes.func.isRequired,
    breadcrumb: PropTypes.array.isRequired
};

Dashboard = connect(mapStateToProps)(Dashboard);

export default Dashboard
