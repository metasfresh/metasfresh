import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

class Dropdown extends Component {
    constructor(props) {
        super(props);
    }
    handleBlur = (e) => {
        e.preventDefault();

        this.dropdown.classList.remove("input-dropdown-focused");
    }
    handleFocus = (e) => {
        e.preventDefault();
        this.dropdown.classList.add("input-dropdown-focused");
    }
    handleChange = (e) => {
        e.preventDefault();

    }
    render() {
        return (
            <div
                tabIndex="0"
                onFocus={()=>this.inputSearch.focus()}
                ref={(c) => this.dropdown = c}
                onBlur={this.handleBlur}
                className={"input-dropdown-container"}
            >
                <div className={"input-dropdown input-auto input-secondary"}>
                    <div className="input-editable input-dropdown-focused">
                        <input
                            type="text"
                            className="input-dropdown-field font-weight-bold"
                            readOnly="readonly"
                            onFocus={this.handleFocus}
                            onChange={this.handleChange}
                            ref={(c) => this.inputSearch = c}
                        />
                    </div>
                    <div className="input-icon">
                        <i className="meta-icon-down input-icon-right input-icon-sm"/>
                    </div>
                </div>
                <div className="input-dropdown-list">
                    <div className={"input-dropdown-list-option"}>
                        <p className="input-dropdown-item-title">asd</p>
                    </div>
                </div>
            </div>
        )
    }
}

export default Dropdown
