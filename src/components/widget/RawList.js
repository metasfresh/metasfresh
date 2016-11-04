import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

class RawList extends Component {
    constructor(props) {
        super(props);
    }

    handleBlur = (e) => {
        this.dropdown.classList.remove("input-dropdown-focused");
    }

    handleFocus = (e) => {
        e.preventDefault();
        const {onFocus} = this.props;

        onFocus();

        this.dropdown.classList.add("input-dropdown-focused");
    }

    handleChange = (e) => {
        e.preventDefault();

        this.handleBlur();
    }

    handleSelect = (option) => {
        const {onSelect} = this.props;

        onSelect(option);

        this.handleBlur();
    }

    renderOptions = () => {
        const {list} = this.props;
        return list.map((option, index) => (
                <div key={index} className={"input-dropdown-list-option"} onClick={() => this.handleSelect(option)}>
                    <p className="input-dropdown-item-title">{option[Object.keys(option)[0]]}</p>
                </div>
            )
        )
    }

    render() {
        const {list, rank, readonly, defaultValue, selected, align, updated, loading} = this.props;
        return (
            <div
                tabIndex="0"
                onFocus={()=>this.inputSearch.focus()}
                ref={(c) => this.dropdown = c}
                onBlur={this.handleBlur}
                className={"input-dropdown-container"}
            >
                <div className={"input-dropdown input-block input-readonly input-" + (rank ? rank : "secondary") + (updated ? " pulse" : "")}>
                    <div className={
                        "input-editable input-dropdown-focused " +
                        (align ? "text-xs-" + align + " " : "")
                    }>
                        <input
                            type="text"
                            className="input-field js-input-field font-weight-semibold"
                            readOnly
                            placeholder={defaultValue}
                            value={selected ? selected[Object.keys(selected)[0]] : ""}
                            onFocus={this.handleFocus}
                            onChange={this.handleChange}
                            ref={(c) => this.inputSearch = c}
                            disabled={readonly}
                        />
                    </div>
                    <div className="input-icon">
                        <i className="meta-icon-down-1 input-icon-sm"/>
                    </div>
                </div>
                <div className="input-dropdown-list">
                    {(list.length === 0 && loading === false) && (
                        <div className="input-dropdown-list-header">
                            There is no choice available
                        </div>
                    )}
                    {(loading && list.length === 0) && (
                        <div className="input-dropdown-list-header">
                            <ReactCSSTransitionGroup transitionName="rotate" transitionEnterTimeout={1000} transitionLeaveTimeout={1000}>
                                <div className="rotate icon-rotate">
                                    <i className="meta-icon-settings"/>
                                </div>
                            </ReactCSSTransitionGroup>
                        </div>
                    )}
                    {this.renderOptions()}
                </div>
            </div>
        )
    }
}

export default RawList;
