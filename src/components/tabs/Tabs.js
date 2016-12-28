import React, { Component } from 'react';

class Tabs extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selected: this.props.children[0].key,
            fullScreen: null
        }
    }

    handleClick = (e, id) => {
        e.preventDefault();
        this.setState(Object.assign({}, this.state, {
            'selected': id
        }));
    }

    toggleTableFullScreen = (tabId) => {
        const {fullScreen} = this.state;
        this.setState(Object.assign({}, this.state, {
            fullScreen: tabId
        }));
    }

    handlePillKeyDown = (e, key) => {
        if(e.key === "Enter"){
            this.handleClick(e, key);
            this.tabContent.focus();
        }
    }

    renderPills = (pills) => {
        const {tabIndex} = this.props;

        return pills.map((item) => {
            return (
                <li
                    className="nav-item"
                    key={"tab" + item.key}
                    onClick={(e) => this.handleClick(e, item.key)}
                    tabIndex={tabIndex}
                    onKeyDown={(e) => this.handlePillKeyDown(e, item.key)}
                >
                    <a className={"nav-link " + ((this.state.selected === item.key) ? "active" : "")}>{item.props.caption}</a>
                </li>
            );
        });
    }

    renderTabs = (tabs) => {
        const {tabIndex} = this.props;
        const {selected, fullScreen} = this.state;
        return tabs.map((item) => {
            const itemWithProps = Object.assign({}, item, {
                props: Object.assign({}, item.props, {
                    toggleFullScreen: this.toggleTableFullScreen,
                    fullScreen: fullScreen
                })
            });

            return (
                <div
                    key={"pane" + item.key}
                    className={"tab-pane " +
                        ((selected == item.key) ? "active " : "") +
                        ((fullScreen === item.key) ? "tab-pane-fullscreen panel-spaced " : "")
                    }
                >
                    {itemWithProps}
                </div>
            );
        });
    }

    render() {
        const {children, tabIndex} = this.props;
        const {fullScreen} = this.state;
        return (
            <div className="mb-1">
                <ul className="nav nav-tabs mt-1">
                    {this.renderPills(children)}
                </ul>
                <div
                    className={"tab-content"}
                    tabIndex={tabIndex}
                    ref={c => this.tabContent = c}
                >
                    {this.renderTabs(children)}
                </div>
            </div>
        )
    }
}

export default Tabs;
