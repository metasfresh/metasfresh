import React, { Component } from 'react';

class Tabs extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selected: this.props.children[0].key
        }
    }
    handleClick = (e, id) => {
        e.preventDefault();
        this.setState({'selected': id});
    }
    renderPills = (pills) => {
        return pills.map((item) => {
            return (
                <li className="nav-item" key={"tab" + item.key} onClick={(e) => this.handleClick(e, item.key)}>
                    <a className={"nav-link " + ((this.state.selected === item.key) ? "active" : "")}>{item.props.caption}</a>
                </li>
            );
        });
    }
    renderTabs = (tabs) => {
        return tabs.map((item) => {
            return (
                <div key={"pane" + item.key} className={"tab-pane " + ((this.state.selected == item.key) ? "active" : "")}>{item}</div>
            );
        });
    }
    render() {
        return (
            <div className="mb-1">
                <ul className="nav nav-tabs mt-1">
                    {this.renderPills(this.props.children)}
                </ul>
                <div className="tab-content">
                    {this.renderTabs(this.props.children)}
                </div>
            </div>
        )
    }
}

export default Tabs;
