import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    dropdownRequest
} from '../../actions/GenericActions';

class ActionButton extends Component {
    constructor(props) {
        super(props);
        this.state = {
            list: {
                values: []
            },
            selected: 0
        }
    }

    handleKeyDown = (e) => {
        const {list, selected} = this.state;
        switch(e.key){
            case "ArrowDown":
                e.preventDefault();
                this.navigate(true);
                break;
            case "ArrowUp":
                e.preventDefault();
                this.navigate();
                break;
            case "Enter":
                e.preventDefault();
                if(selected != null){
                    this.handleChangeStatus(list.values[selected]);
                }
                break;
            case "Escape":
                e.preventDefault();
                this.handleDropdownBlur();
                break;
        }
    }

    navigate = (up) => {
        const {selected,list} = this.state;
        const next = up ? selected + 1 : selected - 1;

        this.setState(Object.assign({}, this.state, {
            selected: (next >= 0 && next <= list.values.length) ? next : selected
        }));
    }

    handleDropdownBlur = () => {
        this.statusDropdown.classList.remove('dropdown-status-open');
    }

    handleDropdownFocus = () => {
        const { dispatch, windowType, fields, dataId} = this.props;

        dispatch(dropdownRequest(windowType, fields[1].field, dataId, null, null, "window")).then((res) => {
            this.setState({list: res.data});
        });
        this.statusDropdown.classList.add('dropdown-status-open');
    }

    handleChangeStatus = (status) => {
        this.props.onChange(status);
        this.statusDropdown.blur();
    }

    getStatusClassName = (abrev) => {
        const {data} = this.props;

        if((data.action.value !== undefined) && Object.keys(data.action.value)[0] !== abrev){
            return "";
        }

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

    renderStatusList = (list) => {
        const {selected} = this.state;
        return list.values.map((item, index) => {
            const key = Object.keys(item)[0];
            return <li
                key={index}
                className={
                    "dropdown-status-item " +
                    (selected === index ? "dropdown-status-item-on-key " : "") +
                    this.getStatusClassName(key)
                }
                onClick={() => this.handleChangeStatus(item)}
            >
                {item[key]}
            </li>
        })
    }

    render() {
        const {data} = this.props;
        const abrev = (data.status.value !== undefined) ? Object.keys(data.status.value)[0] : null;
        const value = (abrev !== null || undefined) ? data.status.value[abrev] : null;
        return (
            <div
                onKeyDown={this.handleKeyDown}
                className="meta-dropdown-toggle dropdown-status-toggler"
                tabIndex="0"
                ref={(c) => this.statusDropdown = c}
                onBlur={this.handleDropdownBlur}
                onFocus={this.handleDropdownFocus}
            >
                <div className={"tag tag-" + this.getStatusContext(abrev)}>{value} </div>
                <i className={"meta-icon-chevron-1 meta-icon-" + this.getStatusContext(abrev)} />
                <ul className="dropdown-status-list">
                    {this.renderStatusList(this.state.list)}
                </ul>
            </div>
        )
    }
}

ActionButton.propTypes = {
    dispatch: PropTypes.func.isRequired
};

ActionButton = connect()(ActionButton)

export default ActionButton
