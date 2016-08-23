import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Widget from '../components/Widget';
import Tabs from '../components/widget/Tabs';
import Table from '../components/table/Table';
import Header from '../components/app/Header';
import OrderList from '../components/app/OrderList';

class Window extends Component {
    constructor(props){
        super(props);
    }
    renderTabs = (tabs) => {
        return(
            <Tabs>
                {
                    tabs.map((elem, id)=> {
                        const {tabid, caption, elements} = elem;
                        return (
                            <div
                                caption={caption}
                                key={tabid}>
                                <Table cols={elements} />
                            </div>
                        )
                    })
                }
            </Tabs>
        )
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
        const maxRows = 12;
        const colWidth = Math.floor(maxRows / columns.length);
        return columns.map((elem, id)=> {
            const elementGroups = elem.elementGroups;
            return (
                <div className={"col-xs-" + colWidth} key={'col' + id}>
                    {this.renderElementGroups(elementGroups)}
                </div>
            )
        })
    }
    renderElementGroups = (group) => {
        return group.map((elem, id)=> {
            const {type, elementsLine} = elem;
            return (
                <div
                    key={'elemGroups' + id}
                    className={"panel panel-spaced panel-distance " + ((type === "primary") ? "panel-bordered panel-primary" : "panel-secondary")}
                >
                    {this.renderElementsLine(elementsLine)}
                </div>
            )
        })
    }
    renderElementsLine = (elementsLine) => {
        return elementsLine.map((elem, id)=> {
            const {elements} = elem;
            return (
                <div className="elements-line" key={"line" + id}>
                    {this.renderElements(elements)}
                </div>
            )
        })
    }
    renderElements = (elements) => {
        const {type} = this.props.layout;
        return elements.map((elem, id)=> {
            return (
                <Widget key={'element' + id} windowType={type} {...elem} />
            )
        })
    }

    render() {
        const {sections, tabs} = this.props.layout;
        return (
            <div>
                <Header />
                <div className="container header-sticky-distance" key="window">
                    {sections && this.renderSections(sections)}
                    <div className="m-t-1 m-b-2">
                        {tabs && this.renderTabs(tabs)}
                    </div>
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
