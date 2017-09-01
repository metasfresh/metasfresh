import React, { Component } from 'react';

import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

class RawList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            selected: props.selected || 0,
            dropdownList: this.props.list || [],
            isOpen: false
        }
    }

    componentDidMount = () => {
        const { autofocus, onRequestListData } = this.props;
        if (this.dropdown && autofocus && onRequestListData) {
            onRequestListData();
        }
    }

    componentWillUnmount() {
        this.clearComponentState();
    }

    componentDidUpdate = (prevProps, prevState) => {
        const {
            list, mandatory, defaultValue, autofocus, blur, property,
            initialFocus, selected, doNotOpenOnFocus, lastProperty, loading,
            disableAutofocus
        } = this.props;

        if (prevProps.blur !== blur) {
            blur && this.handleBlur();
        }

        if (list.length === 0 && (prevProps.loading !== loading) &&
            loading === false && lastProperty){
            disableAutofocus();
        }

        if (this.dropdown && autofocus) {
            if (prevState.selected !== this.state.selected) {
                list.length === 1 && this.handleSelect(list[0]);

                if (!doNotOpenOnFocus && list.length > 1) {
                    this.setState({
                        isOpen: true
                    });
                }
            }
        }

        if (this.dropdown) {
            if (autofocus) {
                this.dropdown.focus();
            }
            else {
                if (prevProps.defaultValue !== defaultValue && property) {
                    this.dropdown.focus();
                }
                else {
                    if (initialFocus && !defaultValue) {
                        this.dropdown.focus();
                    }
                }
            }

        }

        if (prevProps.list !== list) {
            let dropdown = [];

            if (!mandatory) {
                dropdown.push(0);
            }

            if (list.length > 0) {
                let openDropdownState = {};

                if (this.openDropdown && (list.length > 1)) {
                    this.openDropdown = false;
                    openDropdownState.isOpen = true;
                }

                let dropdownList = dropdown.concat(list);

                this.setState(
                    Object.assign(
                        {
                            dropdownList: dropdownList,
                            selected: defaultValue ? defaultValue : list[0],
                        },
                        openDropdownState
                    )
                );
            }
        }

        if (prevProps.selected !== selected) {
            this.setState({
                selected: selected
            });
        }

        const { isOpen } = this.state;

        // no need for updating scroll
        if (!isOpen || !list.length) {
            return;
        }

        const { listScrollWrap, items } = this.refs;

        const listElementHeight = this.optionElement.offsetHeight;
        const listVisibleElements = Math.floor(listScrollWrap.offsetHeight / listElementHeight);
        const shouldListScrollUpdate = (listVisibleElements <= items.childNodes.length);

        if (!shouldListScrollUpdate) {
            return;
        }

        const selectedIndex = this.getSelectedIndex();
        const visibleMin = listScrollWrap.scrollTop;
        const visibleMax = visibleMin + listVisibleElements * listElementHeight;

        //not visible from down
        const scrollFromUp = listElementHeight * (selectedIndex - listVisibleElements + 1);

        if (
            (selectedIndex + 1) * listElementHeight > visibleMax &&
            listScrollWrap.scrollTop !== scrollFromUp
        ) {
            return listScrollWrap.scrollTop = scrollFromUp;
        }

        //not visible from above
        const scrollFromDown = selectedIndex * listElementHeight;

        if ((selectedIndex * listElementHeight < visibleMin) && (listScrollWrap.scrollTop !== scrollFromDown)) {
            listScrollWrap.scrollTop = scrollFromDown;
        }
    }

    clearComponentState = () => {
        this.state = {
            selected: 0,
            dropdownList: [],
            isOpen: false
        }
    }

    focus() {
        if (this.dropdown) {
            this.dropdown.focus();
        }
    }

    openDropdownList() {
        this.setState({
            isOpen: true
        }, () => {
            this.focus();
        });
    }

    closeDropdownList() {
        if (this.state && this.state.isOpen) {
            this.setState({
                isOpen: false
            });
        }
    }

    getSelectedIndex() {
        const { list, mandatory } = this.props;
        const { selected } = this.state;

        if (selected === 0) {
            return 0;
        }

        let baseIndex = list.indexOf(selected);
        if (selected && (baseIndex < 0)) {
            let selectedKey = Object.keys(selected)[0];

            baseIndex = list.findIndex( (item) => Object.keys(item)[0] === selectedKey );
        }

        if (!mandatory) {
            return baseIndex + 1;
        }

        return baseIndex;
    }

    areOptionsEqual(selected, option) {
        // different types - not equal for sure
        if (typeof option !== typeof selected) {
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
        const selectedKeys = selected && Object.keys(selected);
        const firstOption = option[optionKeys[0]];
        const firstSelected = selected && selected[selectedKeys[0]];

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

    navigateToAlphanumeric = (char) => {
        const { list } = this.props;
        const { isOpen, selected } = this.state;

        if (!isOpen) {
            this.setState({
                isOpen: true
            });
        }

        const caption = (item) => item[Object.keys(item)[0]];
        const items = list.filter( (item) =>
            caption(item)[0].toUpperCase() === char.toUpperCase()
        );

        const selectedIndex = items.indexOf(selected);
        const item = (selectedIndex > -1) ? items[selectedIndex + 1] : items[0];

        if (!item) {
            return;
        }

        this.setState({
            selected: item
        });
    }

    navigate = (up) => {
        const { selected, dropdownList, isOpen } = this.state;

        if (!isOpen) {
            this.setState({
                isOpen: true
            });
        }

        let selectedIndex = null;

        dropdownList.map((item, index) => {
            if (JSON.stringify(item) === JSON.stringify(selected)) {
                selectedIndex = index;
            }
        });

        const next = up ? selectedIndex + 1 : selectedIndex - 1;

        this.setState({
            selected: ((next >= 0) && (next <= dropdownList.length - 1)) ? dropdownList[next] : selected
        });
    }

    handleBlur = () => {
        const { selected, doNotOpenOnFocus } = this.props;

        if (!doNotOpenOnFocus && this.dropdown) {
            this.dropdown.blur();
        }

        this.setState({
            isOpen: false,
            selected: selected || 0
        });
    }

    /*
     * Alternative method to open dropdown, in case of disabled opening
     * on focus.
     */
    handleClick = (e) => {
        e.preventDefault();

        const { onFocus } = this.props;

        onFocus && onFocus();

        this.setState({
            isOpen: true
        });
    }

    handleFocus = (e) => {
        if (e) {
            e.preventDefault();
        }

        const { onFocus, doNotOpenOnFocus, autofocus } = this.props;

        onFocus && onFocus();

        if (!doNotOpenOnFocus && !autofocus) {
            this.openDropdown = true;
        }
    }

    handleChange = (e) => {
        e.preventDefault();

        this.handleBlur();
    }

    handleSelect = (option) => {
        const { onSelect } = this.props;

        if (option) {
            onSelect(option);
        } else {
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

    handleKeyDown = (e) => {
        const { onSelect, list } = this.props;
        const { selected, isOpen } = this.state;

        if ((e.keyCode > 47) && (e.keyCode < 123)) {
            this.navigateToAlphanumeric(e.key);
        } else {
            switch (e.key) {
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

                    if (isOpen) {
                        e.stopPropagation();
                    }

                    if (selected) {
                        this.handleSelect(selected);
                    } else {
                        onSelect(null);
                    }

                    break;

                case 'Escape':
                    e.preventDefault();
                    this.handleBlur();
                    break;

                case 'Tab':
                    (list.length === 0) && onSelect(null);
                    break;
            }
        }
    }

    getRow = (index, option, label) => {
        const {defaultValue} = this.props;
        const {selected} = this.state;

        const value = defaultValue && defaultValue[Object.keys(defaultValue)[0]]

        return (
            <div
                key={index}
                className={'input-dropdown-list-option'  +
                    (
                        selected === 0 ? '' :
                        this.areOptionsEqual(selected, option) ?
                        ' input-dropdown-list-option-key-on ' :
                        value === option[Object.keys(option)[0]] && !selected ?
                        ' input-dropdown-list-option-key-on ' :
                        !value && !selected && index == 1 ?
                        ' input-dropdown-list-option-key-on ':
                        ''
                    )
                }
                onMouseEnter={() => this.handleSwitch(option)}
                onClick={() => this.handleSelect(option)}
                ref={option => this.optionElement = option}
            >
                <p className="input-dropdown-item-title">
                    {label ? label : option[Object.keys(option)[0]]}
                </p>
            </div>
        )
    }

    renderOptions = () => {
        const {list, mandatory, emptyText} = this.props;

        return <div ref="items">{[
            // if field is not mandatory add extra empty row
            ...(!mandatory && emptyText ? [this.getRow(0, 0, emptyText)] : []),
            // fill with options
            ...list.map((option, index) => this.getRow(index + 1, option))
        ]}</div>;
    }

    render() {
        const {
            list, rank, readonly, defaultValue, selected, align, updated,
            loading, rowId, isModal, tabIndex, disabled, mandatory, validStatus,
            lookupList
        } = this.props;

        let value = '';
        const isListEmpty = (list.length === 0);
        const { isOpen } = this.state;

        if (typeof defaultValue === 'string') {
            value = defaultValue;
        } else {
            value = defaultValue && defaultValue[Object.keys(defaultValue)[0]];
        }

        return (
            <div
                ref={ (c) => this.dropdown = c }
                className={
                    'input-dropdown-container ' +
                    (readonly ? 'input-disabled ' : '') +
                    (rowId ? 'input-dropdown-container-static ' : '') +
                    ((rowId && !isModal) ? 'input-table ' : '')
                }
                tabIndex={tabIndex ? tabIndex : 0}
                onFocus={!readonly && this.handleFocus}
                onBlur={this.handleBlur}
                onClick={!readonly && this.handleClick}
                onKeyDown={this.handleKeyDown}
            >
                <div className={
                    'input-dropdown input-block input-readonly input-' +
                    (rank ? rank : 'secondary') +
                    (updated ? ' pulse ' : ' ') +
                    ((mandatory && !selected) ? 'input-mandatory ' : '') +
                    (validStatus &&
                        (
                            !validStatus.valid &&
                            !validStatus.initialValue

                        ) &&
                        !isOpen ? 'input-error ' : '')
                }>
                    <div className={
                        'input-editable input-dropdown-focused ' +
                        (align ? 'text-xs-' + align + ' ' : '')
                    }>
                        <input
                            ref={ (c) => this.inputSearch = c }
                            type="text"
                            className={
                                'input-field js-input-field ' +
                                'font-weight-semibold ' +
                                (disabled ? 'input-disabled ' : '')
                            }
                            readOnly
                            tabIndex={-1}
                            placeholder={value}
                            value={lookupList ? value : (selected ?
                                selected[Object.keys(selected)[0]] : '')}
                            disabled={readonly || disabled}
                            onChange={this.handleChange}
                        />
                    </div>

                    <div className="input-icon">
                        <i className="meta-icon-down-1 input-icon-sm"/>
                    </div>
                </div>

                {isOpen && (
                    <div
                        className="input-dropdown-list"
                        ref="listScrollWrap"
                    >
                        {(isListEmpty && loading === false) && (
                            <div className="input-dropdown-list-header">
                                There is no choice available
                            </div>
                        )}

                        {(loading && isListEmpty) && (
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
                    </div>
                )}
            </div>
        )
    }
}

export default RawList;
