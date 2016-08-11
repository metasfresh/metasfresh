import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Widget from '../components/Widget';

class Window extends Component {
    constructor(props){
        super(props);
    }
    renderTabs = (tabs) => {
        return tabs.map((elem, id)=> {
            return (
                <div className="row" key={"tabs" + id}>
                    Tab {elem.caption}
                </div>
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
                <div className="col-xs-6" key={id}>
                    {this.renderElementGroups(elementGroups)}
                </div>
            )
        })
    }
    renderElementGroups = (group) => {
        return group.map((elem, id)=> {
            const {type, elements} = elem;
            return (
                <div key={id} className={"panel panel-spaced panel-bordered panel-distance panel-" + type}>
                    {this.renderElements(elements)}
                </div>
            )
        })
    }
    renderElements = (elements) => {
        return elements.map((elem, id)=> {
            return (
                <div key={id} className="form-group m-t-1 row">
                    <div className="col-xs-12">
                        <Widget {...elem} />

                    </div>
                </div>
            )
        })
    }

    render() {
        const {sections, tabs} = this.props.layout;
        return (
            <div className="container" key="window">
                {sections && this.renderSections(sections)}
                {tabs && this.renderTabs(tabs)}
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
