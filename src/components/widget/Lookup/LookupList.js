import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

class LookupList extends Component {
    constructor(props) {
        super(props);
    }

    getDropdownComponent = (index, item) => {
        const {handleSelect, selected} = this.props;
        const name = item[Object.keys(item)[0]];
        const key = Object.keys(item)[0];
        return (
            <div
                key={key}
                className={
                    "input-dropdown-list-option " +
                    (selected === index ? 'input-dropdown-list-option-key-on' : "") }
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

    renderEmpty = () => {
        const {query} = this.props;
        return (
            <div
                className="input-dropdown-list-option input-dropdown-list-option-alt"
                onClick={() => this.handleAddNew(query)}
            >
                <p>New {query ? '"' + query + '"' : ""}</p>
            </div>
        )
    }

    renderLoader = () => {
        return (<div className="input-dropdown-list-header">
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
        </div>)
    }

    render() {
        const {
            loading, list
        } = this.props;

        return (
            <div className="input-dropdown-list">
                {(loading && list.length === 0) && this.renderLoader()}

                <div ref={(c) => this.items = c}>
                    {list.map((item, index) => this.getDropdownComponent(index, item))}
                    {list.length === 0 && this.renderEmpty()}
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
