import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
var DragDropContext = require('react-dnd').DragDropContext;
var HTML5Backend = require('react-dnd-html5-backend');
import Container from '../components/Container';
import Cont from './Cont';
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
            <div className="container-fluid">
                <Cont/>

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

    // text: PropTypes.string.isRequired,
    // // Injected by React DnD:
    // isDragging: PropTypes.bool.isRequired,
    // connectDragSource: PropTypes.func.isRequired
};

Dashboard = connect(mapStateToProps)(DragDropContext(HTML5Backend)(Dashboard));

export default Dashboard
