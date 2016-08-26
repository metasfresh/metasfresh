import React, { Component } from 'react';

class ActionButton extends Component {
    constructor(props) {
        super(props);
    }
    handleDropdownBlur = () => {
        this.statusDropdown.classList.remove('dropdown-status-open');
    }
    handleDropdownFocus = () => {
        this.statusDropdown.classList.add('dropdown-status-open');
    }
    handleChangeStatus = (status) => {
        //action here
        this.statusDropdown.blur();
    }
    getStatusClassName = (abrev) => {
        if(abrev === 'DR'){
            return "dropdown-status-item-def";
        }else if (abrev === 'CO'){
            return "dropdown-status-item-def-1";
        }else{
            return "";
        }
    }
    getStatusContext = (abrev) => {
        if(abrev === 'DR'){
            return "primary"
        }else if (abrev === 'CO'){
            return "success"
        }else {
            return "default"
        }
    }
    renderStatusList = () => {
        return this.status.map((item, index) => {
            if(index != this.props.orderStatus){
                return <li
                    key={index}
                    className={
                        "dropdown-status-item " +
                        this.getStatusClassName(index)
                    }
                    onClick={() => this.handleChangeStatus(index)}
                >
                    {item}
                </li>
            }
        })
    }
    render() {
        const {data} = this.props
        const abrev = Object.keys(data.value)[0];
        const value = data.value[abrev];
        return (
            <div
                className="meta-dropdown-toggle dropdown-status-toggler"
                tabIndex="0"
                ref={(c) => this.statusDropdown = c}
                onBlur={this.handleDropdownBlur}
                onFocus={this.handleDropdownFocus}
            >
                <div className={"tag tag-" + this.getStatusContext(abrev)}>{value}</div>
                <i className={"meta-icon-chevron-1 meta-icon-" + this.getStatusContext(abrev)} />
                <ul className="dropdown-status-list">
                </ul>
            </div>
        )
    }
}

export default ActionButton
