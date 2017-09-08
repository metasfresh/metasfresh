import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

class LookupList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            listElementHeight: 0,
            listVisibleElements: 0,
            shouldListScrollUpdate: false
        }
    }

    componentDidMount(){
        // needed for calculating scroll position
        const listElementHeight = 30;
        const listVisibleElements =
            Math.floor(this.listScrollWrap.clientHeight / listElementHeight);
        const shouldListScrollUpdate =
            listVisibleElements > this.items.childNodes.length;

        this.setState({
            listElementHeight: listElementHeight,
            listVisibleElements: listVisibleElements,
            shouldListScrollUpdate: shouldListScrollUpdate
        });
    }

    componentWillReceiveProps(nextProps){
        if (typeof nextProps.selected === 'number') {
            const container = this.listScrollWrap;
            let element = this.items.childNodes[nextProps.selected];

            if (container && element && element.scrollIntoView) {
                if (container.scrollHeight > container.offsetHeight) {
                    element.scrollIntoView();
                }
            }
        }
    }

    getDropdownComponent = (index, item) => {
        const {handleSelect, selected} = this.props;
        const name = item[Object.keys(item)[0]];
        const key = Object.keys(item)[0];

        return (
            <div
                key={key}
                className={
                    'input-dropdown-list-option ' +
                    (selected === index ?
                        'input-dropdown-list-option-key-on' : '') }
                onClick={() => {handleSelect(item)}}
            >
                <p className="input-dropdown-item-title">{name}</p>
            </div>
        )
    }

    handleClickOutside = () => {
        const {onClickOutside} = this.props;
        onClickOutside();
    }

    renderNew = () => {
        const {selected, handleAddNew, newRecordCaption} = this.props;
        return (
            <div className={
                'input-dropdown-list-option input-dropdown-list-option-alt '  +
                (selected === 'new' ? 'input-dropdown-list-option-key-on' : '')
            }
                onClick={handleAddNew}
            >
                <p>{newRecordCaption}</p>
            </div>
        )
    }

    renderEmpty = () => {
        return (
            <div className="input-dropdown-list-header">
                <div className="input-dropdown-list-header">
                    No results found
                </div>
            </div>
        )
    }

    renderLoader = () => {
        return (
            <div className="input-dropdown-list-header">
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
            </div>
        )
    }

    render() {
        const {
            loading, list, creatingNewDisabled, newRecordCaption
        } = this.props;

        return (
            <div
                className="input-dropdown-list"
                ref={c => this.listScrollWrap = c}
            >
                {(loading && list.length === 0) && this.renderLoader()}
                {(!loading && list.length === 0) && this.renderEmpty()}
                <div ref={(c) => this.items = c}>
                    {list.map((item, index) =>
                        this.getDropdownComponent(index, item))
                    }
                    {list.length === 0 && newRecordCaption &&
                        !creatingNewDisabled && this.renderNew()
                    }
                </div>
            </div>
        )

    }
}

LookupList.propTypes = {
    dispatch: PropTypes.func.isRequired
}

LookupList = connect()(onClickOutside(LookupList))

export default LookupList
