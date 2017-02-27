import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

class LookupList extends Component {
    constructor(props) {
        super(props);
    }

    componentDidMount(){
        // needed for calculating scroll position
        const listElementHeight = 30;
        const listVisibleElements = Math.floor(this.listScrollWrap.clientHeight / listElementHeight);
        const shouldListScrollUpdate = listVisibleElements > this.items.childNodes.length;

        this.setState({
            listElementHeight: listElementHeight,
            listVisibleElements: listVisibleElements,
            shouldListScrollUpdate: shouldListScrollUpdate
        });
    }

    componentWillReceiveProps(nextProps){
        const {shouldListScrollUpdate, listElementHeight, listVisibleElements} = this.state;

        // no need for updating scroll
        if (
            !shouldListScrollUpdate ||
            typeof nextProps.selected !== 'number' ||
            nextProps.selected === this.props.selected
        ){
            return;
        }

        const visibleMin = this.listScrollWrap.scrollTop;
        const visibleMax = this.listScrollWrap.scrollTop +
            listVisibleElements * listElementHeight;

        //not visible from down
        if ((nextProps.selected + 1) * listElementHeight > visibleMax){
            this.listScrollWrap.scrollTop = listElementHeight *
                (nextProps.selected - listVisibleElements)
        }

        //not visible from above
        if (nextProps.selected * listElementHeight < visibleMin){
            this.listScrollWrap.scrollTop = nextProps.selected * listElementHeight
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
                    (selected === index ? 'input-dropdown-list-option-key-on' : '') }
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
        const {query, selected, handleAddNew} = this.props;
        return (
            <div
                className={
                    'input-dropdown-list-option input-dropdown-list-option-alt '  +
                    (selected === 'new' ? 'input-dropdown-list-option-key-on' : '')
                }
                onClick={() => handleAddNew(query)}
            >
                <p>New {query ? '"' + query + '"' : ''}</p>
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
            loading, list, creatingNewDisabled
        } = this.props;

        return (
            <div className="input-dropdown-list" ref={c => this.listScrollWrap = c}>
                {(loading && list.length === 0) && this.renderLoader()}
                {(!loading && list.length === 0) && this.renderEmpty()}
                <div ref={(c) => this.items = c}>
                    {list.map((item, index) => this.getDropdownComponent(index, item))}
                    {list.length === 0 && !creatingNewDisabled && this.renderNew()}
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
