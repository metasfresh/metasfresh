import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Widget from '../components/Widget';
import Header from '../components/app/Header';
import OrderList from '../components/app/OrderList';

class Window extends Component {
    constructor(props){
        super(props);
    }
    renderTabs = (tabs) => {
        return tabs.map((elem, id)=> {
            return (
                <li className="nav-item" key={"tab" + id}>
                    <a className="nav-link active">{elem.caption}</a>
                </li>
            )
        })
    }
    renderSections = (sections) => {
        return sections.map((elem, id)=> {
            const columns = elem.columns;
            return (
                <div className="row" key={"section" + id}>
                    {this.renderColumns(columns)}
                </div>
            )
       })
    }
    renderColumns = (columns) => {
        return columns.map((elem, id)=> {
            const elementGroups = elem.elementGroups;
            return (
                <div className="col-xs-6" key={'col' + id}>
                    {this.renderElementGroups(elementGroups)}
                </div>
            )
        })
    }
    renderElementGroups = (group) => {
        return group.map((elem, id)=> {
            const {type, elements} = elem;
            return (
                <div
                    key={'elemGroups' + id}
                    className={"panel panel-spaced panel-bordered panel-distance panel-" + type}
                >
                    {this.renderElements(elements)}
                </div>
            )
        })
    }
    renderElements = (elements) => {

        const {type} = this.props.layout;

        return elements.map((elem, id)=> {
            return (
                <div key={'element' + id} className="form-group m-t-1 row">
                    <div className="col-xs-12">
                        <Widget windowType={type} {...elem} />
                    </div>
                </div>
            )
        })
    }

    render() {
        const {sections, tabs} = this.props.layout;
        return (
            <div>
                <Header />
                <OrderList />
                <div className="container header-sticky-distance" key="window">
                    {sections && this.renderSections(sections)}
                    <ul className="nav nav-tabs m-t-1 m-b-2">
                        {tabs && this.renderTabs(tabs)}
                    </ul>
                </div>
            </div>
        );
    }
}

Window.propTypes = {
    layout: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { windowHandler } = state;
    const {
        layout
    } = windowHandler || {
        layout: {}
    }
    return {
        layout
    }
}

Window = connect(mapStateToProps)(Window)

export default Window;
