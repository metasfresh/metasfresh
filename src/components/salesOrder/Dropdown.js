import React, { Component } from 'react';

class Dropdown extends Component {
    constructor(props) {
        super(props);
    }
    handleSelect = (e) => {
        e.preventDefault();
        this.dropdown.blur();
    }
    render() {
        return (
            <div tabIndex="0" ref={(c) => this.dropdown = c} className="input-dropdown">
                <div className="input-toggled">
                    <span className="font-weight-bold">Jazzy Innovations</span>
                    <i className="icon-rounded icon-rounded-space pull-xs-right">x</i>
                    <span className="pull-xs-right">Tracka 18, Gliwice, Poland, VAT 541-141-56-23</span>
                </div>
                <div className="input-dropdown-list">
                    <div className="input-dropdown-list-header">
                        Recent partners
                    </div>
                    <div className="input-dropdown-list-option" onClick={this.handleSelect}>
                        <p className="input-dropdown-item-title">Jazzy Innovations</p>
                        <p className="input-dropdown-item-subtitle">Tracka 18, Gliwice, Poland</p>
                        <p className="input-dropdown-item-subtitle">VAT 541-141-56-23</p>
                    </div>
                </div>
            </div>
        )
    }
}

export default Dropdown
