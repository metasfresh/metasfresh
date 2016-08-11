import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    dropdownRequest
} from '../../actions/SalesOrderActions';

class Dropdown extends Component {
    constructor(props) {
        super(props);
    }
    handleBlur = (e) => {
        this.dropdown.classList.remove("input-dropdown-focused");
    }
    handleFocus = (e) => {
        e.preventDefault();
        const {property, dispatch} = this.props;
        dispatch(dropdownRequest(143, property));
        this.dropdown.classList.add("input-dropdown-focused");
    }
    handleChange = (e) => {
        e.preventDefault();
        this.handleBlur();
    }
    handleSelect = (index) => {
        this.inputSearch.value = this.props.options[index];
        this.handleBlur();
    }
    renderOptions = () => {
        return this.props.options.map((option, index) => (
                <div key={index} className={"input-dropdown-list-option"} onClick={() => this.handleSelect(index)}>
                    <p className="input-dropdown-item-title">{option}</p>
                </div>
            )
        )
    }
    render() {
        const {options, rank} = this.props;
        return (
            <div
                tabIndex="0"
                onFocus={()=>this.inputSearch.focus()}
                ref={(c) => this.dropdown = c}
                onBlur={this.handleBlur}
                className={"input-dropdown-container"}
            >
                <div className={"input-dropdown input-block input-readonly input-" + (rank ? rank : "secondary")}>
                    <div className="input-editable input-dropdown-focused">
                        <input
                            type="text"
                            className="input-field font-weight-bold"
                            readOnly="readonly"
                            placeholder={this.props.defaultValue}
                            onFocus={this.handleFocus}
                            onChange={this.handleChange}
                            ref={(c) => this.inputSearch = c}
                        />
                    </div>
                    <div className="input-icon">
                        <i className="meta-icon-down input-icon-sm"/>
                    </div>
                </div>
                <div className="input-dropdown-list">
                    {this.renderOptions()}
                </div>
            </div>
        )
    }
}

Dropdown.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const {salesOrderStateHandler} = state;
    return {
    }
}

Dropdown = connect(mapStateToProps)(Dropdown)

export default Dropdown
