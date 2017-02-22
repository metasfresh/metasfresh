import React, { Component } from 'react';

import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

class RawList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            selected: props.selected || 0,
            isOpen: false
        }
    }

    handleBlur = () => {
        const { selected } = this.props;

        this.dropdown.blur();
        this.setState({
            isOpen: false,
            selected: selected || 0
        })
    }

    handleFocus = (e) => {
        e.preventDefault();
        const {onFocus} = this.props;

        onFocus && onFocus();

        this.setState({
            isOpen: true
        })
    }

    handleChange = (e) => {
        e.preventDefault();

        this.handleBlur();
    }

    handleSelect = (option) => {
        const {onSelect} = this.props;

        if(option){
            onSelect(option);
        }else{
            onSelect(null);
        }

        this.setState({
            selected: (option || 0)
        }, () => this.handleBlur())
    }

    handleSwitch = (option) => {
        this.setState({
            selected: (option || 0)
        })
    }

    navigate = (up) => {
        const {selected} = this.state;
        const {list} = this.props;

        const next = up ? selected + 1 : selected - 1;

        this.setState({
            selected: (next >= 0 && next <= list.length) ? next : selected
        });
    }

    handleKeyDown = (e) => {
        const {list} = this.props;
        const {selected} = this.state;

        switch(e.key){
            case 'ArrowDown':
                e.preventDefault();
                this.navigate(true);
                break;
            case 'ArrowUp':
                e.preventDefault();
                this.navigate(false);
                break;
            case 'Enter':
                e.preventDefault();
                this.handleSelect(list[Object.keys(list)[selected-1]])
                break;
            case 'Escape':
                e.preventDefault();
                this.handleBlur();
                break;
        }
    }

    areOptionsEqual(selected, option){
        // different types - not equal for sure
        if (typeof option !== typeof selected){
            return false;
        }

        // option and selected are not objects
        if(
            typeof option !== 'object' &&
            typeof selected !== 'object' &&
            selected === option
        ){
            return true;
        }

        const optionKeys = Object.keys(option);
        const selectedKeys = Object.keys(selected);
        const firstOption = option[optionKeys[0]];
        const firstSelected = selected[selectedKeys[0]];

        // objects, and first elements are not
        if (
            typeof option === 'object' &&
            typeof selected === 'object' &&
            typeof firstOption !== 'object' &&
            typeof firstSelected !== 'object'
        )
        {
            return optionKeys[0] === selectedKeys[0] &&
                    firstOption === firstSelected;
        }

        // first elements are nested objects, repeat checking
        return this.areOptionsEqual(firstOption, firstSelected);
    }

    getRow = (index, option, label) => {
        const {selected} = this.state;

        return (
            <div
                key={index}
                className={'input-dropdown-list-option'  +
                    (
                        this.areOptionsEqual(selected, option) ?
                        ' input-dropdown-list-option-key-on' :
                        ''
                    )
                }
                onMouseEnter={() => this.handleSwitch(option)}
                onClick={() => this.handleSelect(option)}
            >
                <p className="input-dropdown-item-title">{label ? label : option[Object.keys(option)[0]]}</p>
            </div>
        )
    }

    renderOptions = () => {
        const {list, mandatory, emptyText} = this.props;

        let ret = [];

        if(!mandatory){
            emptyText && ret.push(this.getRow(0, 0, emptyText));
        }

        list.map((option, index) => {
            ret.push(this.getRow(index + 1, option))
        })

        return ret;
    }

    render() {
        const {
            list, rank, readonly, defaultValue, selected, align, updated, loading,
            rowId, isModal, tabIndex, disabled, mandatory
        } = this.props;

        const {
            isOpen
        } = this.state;

        return (
            <div
                tabIndex={tabIndex ? tabIndex : 0}
                onFocus={!readonly && this.handleFocus}
                ref={(c) => this.dropdown = c}
                onBlur={this.handleBlur}
                onKeyDown={this.handleKeyDown}
                className={
                    'input-dropdown-container ' +
                    (readonly ? 'input-disabled ' : '') +
                    (rowId ? 'input-dropdown-container-static ' : '') +
                    ((rowId && !isModal) ? 'input-table ' : '')
                }
            >
                <div className={
                    'input-dropdown input-block input-readonly input-' +
                    (rank ? rank : 'secondary') +
                    (updated ? ' pulse ' : ' ') +
                    ((mandatory && !selected) ? 'input-mandatory ' : '')
                }>
                    <div className={
                        'input-editable input-dropdown-focused ' +
                        (align ? 'text-xs-' + align + ' ' : '')
                    }>
                        <input
                            type="text"
                            className={
                                'input-field js-input-field font-weight-semibold ' +
                                (disabled ? 'input-disabled ' : '')
                            }
                            readOnly
                            tabIndex={-1}
                            placeholder={defaultValue}
                            value={selected ? selected[Object.keys(selected)[0]] : ''}
                            onChange={this.handleChange}
                            ref={(c) => this.inputSearch = c}
                            disabled={readonly || disabled}
                        />
                    </div>
                    <div className="input-icon">
                        <i className="meta-icon-down-1 input-icon-sm"/>
                    </div>
                </div>
                {isOpen && <div className="input-dropdown-list">
                    {(list.length === 0 && loading === false) && (
                        <div className="input-dropdown-list-header">
                            There is no choice available
                        </div>
                    )}
                    {(loading && list.length === 0) && (
                        <div className="input-dropdown-list-header">
                            <ReactCSSTransitionGroup 
                                transitionName="rotate" 
                                transitionEnterTimeout={1000} 
                                transitionLeaveTimeout={1000}
                            >
                                <div className="rotate icon-rotate">
                                    <i className="meta-icon-settings"/>
                                </div>
                            </ReactCSSTransitionGroup>
                        </div>
                    )}
                    {this.renderOptions()}
                </div>}
            </div>
        )
    }
}

export default RawList;
