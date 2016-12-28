import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    findRowByPropName
} from '../actions/WindowActions';

import MasterWidget from '../components/widget/MasterWidget';
import Tabs from '../components/tabs/Tabs';
import Table from '../components/table/Table';

import logo from '../assets/images/metasfresh_logo_green_thumb.png';

class Window extends Component {
    constructor(props){
        super(props);

        this.tabIndex = {
            firstColumn: 1,
            tabs: 5,
            secondColumn: 10
        }
    }

    renderTabs = (tabs) => {
        const {type} = this.props.layout;
        const {data, rowData, newRow} = this.props;
        const dataId = findRowByPropName(data, "ID").value;

        return(
            <Tabs
                tabIndex={this.tabIndex.tabs}
            >
                {tabs.map((elem)=> {
                    const {
                        tabid, caption, elements, emptyResultText, emptyResultHint
                    } = elem;
                    return (
                        <Table
                            caption={caption}
                            key={tabid}
                            rowData={rowData}
                            cols={elements}
                            tabid={tabid}
                            type={type}
                            docId={dataId}
                            emptyText={emptyResultText}
                            emptyHint={emptyResultHint}
                            newRow={newRow}
                            tabIndex={this.tabIndex.tabs}
                        />
                    )
                })}
            </Tabs>
        )
    }

    renderSections = (sections) => {
        return sections.map((elem, id)=> {
            const columns = elem.columns;
            return (
                <div className="row" key={"section" + id}>
                    {columns && this.renderColumns(columns)}
                </div>
            )
       })
    }

    renderColumns = (columns) => {
        const maxRows = 12;
        const colWidth = Math.floor(maxRows / columns.length);
        return columns.map((elem, id)=> {
            const isFirst = (id === 0);
            const elementGroups = elem.elementGroups;
            return (
                <div className={"col-sm-" + colWidth} key={'col' + id}>
                    {elementGroups && this.renderElementGroups(elementGroups, isFirst)}
                </div>
            )
        })
    }

    renderElementGroups = (group, isFirst) => {
        return group.map((elem, id)=> {
            const {type, elementsLine} = elem;
            const shouldBeFocused = isFirst && (id === 0);

            const tabIndex = (type === "primary") ?
                this.tabIndex.firstColumn:
                this.tabIndex.secondColumn;

            return (
                elementsLine && elementsLine.length > 0 &&
                    <div
                        key={'elemGroups' + id}
                        tabIndex={shouldBeFocused ? 0 : undefined}
                        className={
                            "panel panel-spaced panel-distance " +
                            ((type === "primary") ? "panel-bordered panel-primary" : "panel-secondary")
                        }
                        ref={c => {(shouldBeFocused && c) && c.focus()}}
                    >
                        {this.renderElementsLine(elementsLine, tabIndex)}
                    </div>
            )
        })
    }

    renderElementsLine = (elementsLine, tabIndex) => {
        return elementsLine.map((elem, id)=> {
            const {elements} = elem;
            return (
                elements && elements.length > 0 &&
                    <div className="elements-line" key={"line" + id}>
                        {this.renderElements(elements, tabIndex)}
                    </div>
            )
        })
    }

    renderElements = (elements, tabIndex) => {
        const {type} = this.props.layout;
        const {data, modal, tabId,rowId, dataId, isAdvanced} = this.props;
        return elements.map((elem, id)=> {
            let widgetData = elem.fields.map(item => findRowByPropName(data, item.field));
            let relativeDocId = findRowByPropName(data, "ID").value;
            return (
                <MasterWidget
                    entity="window"
                    key={'element' + id}
                    windowType={type}
                    dataId={dataId}
                    widgetData={widgetData}
                    isModal={!!modal}
                    tabId={tabId}
                    rowId={rowId}
                    relativeDocId={relativeDocId}
                    isAdvanced={isAdvanced}
                    tabIndex={tabIndex}
                    {...elem}
                />
            )
        })
    }

    render() {
        const {sections, type, tabs} = this.props.layout;
        const {data, isModal} = this.props;
        return (
            <div key="window" className="window-wrapper">
                <div className="sections-wrapper">
                    {sections && this.renderSections(sections)}
                </div>
                <div className="mt-1 tabs-wrapper">
                    {tabs && this.renderTabs(tabs)}
                </div>
            </div>
        );
    }
}

export default Window;
