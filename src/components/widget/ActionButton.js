import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    dropdownRequest
} from '../../actions/AppActions';

class ActionButton extends Component {
    constructor(props) {
        super(props);
        this.state = {
            list: {
                values: []
            }
        }
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
        return list.values.map((item, index) => {
            const key = Object.keys(item)[0];
            return <li
                key={index}
                className={
                    "dropdown-status-item " +
                    this.getStatusClassName(key)
                }
                onClick={() => this.handleChangeStatus(item)}
            >
                {item[key]}
            </li>
        })


    }
    render() {
        const {data} = this.props
        const abrev = (data.status.value !== undefined) ? Object.keys(data.status.value)[0] : null;
        const value = (abrev !== null || undefined) ? data.status.value[abrev] : null;
        return (
            <div
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

function mapStateToProps(state) {
    return {
    }
}

ActionButton = connect(mapStateToProps)(ActionButton)

export default ActionButton
