import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    quickActionsRequest
} from '../../actions/ListActions';

import {
    openModal
} from '../../actions/WindowActions';

import QuickActionsDropdown from './QuickActionsDropdown';

import keymap from '../../keymap.js';
import QuickActionsContextShortcuts from '../shortcuts/QuickActionsContextShortcuts';
import { ShortcutManager } from 'react-shortcuts';
const shortcutManager = new ShortcutManager(keymap);

class QuickActions extends Component {
    constructor(props){
        super(props);

        this.state = {
            actions: [],
            isDropdownOpen: false
        }

        this.fetchActions();
    }

    componentDidUpdate = (prevProps) => {
        const {selected, refresh, shouldNotUpdate} = this.props;
        if(shouldNotUpdate){
            return;
        }

        if(
            (JSON.stringify(prevProps.selected) !== JSON.stringify(selected)) ||
            (JSON.stringify(prevProps.refresh) !== JSON.stringify(refresh))
        ){
            this.fetchActions();
        }
    }

    getChildContext = () => {
        return { shortcuts: shortcutManager }
    }

    handleClickOutside = () => {
        this.toggleDropdown();
    }

    handleClick = (action) => {
        const {dispatch, viewId} = this.props;
        if(!action.disabled){
            dispatch(
                openModal(
                    action.caption, action.processId, 'process', null, null, false,
                    viewId
                )
            );
        }
    }

    fetchActions = () => {
        const {dispatch, windowType, viewId, selected} = this.props;
        dispatch(quickActionsRequest(windowType, viewId, selected)).then(response => {
            this.setState({
                actions: response.data.actions
            })
        });
    }

    toggleDropdown = (option) => {
        this.setState({
            isDropdownOpen: option
        })
    }

    render() {
        const {
            actions,
            isDropdownOpen
        } = this.state;

        if(actions.length){
            return (
                <div className="js-not-unselect">
                    <span className="spacer-right">Actions:</span>
                    <div className="quick-actions-wrapper">
                        <div
                            className={'tag tag-success tag-xlg spacer-right ' +
                                (actions[0].disabled ? 'tag-default ' : 'pointer ')
                            }
                            onClick={() => this.handleClick(actions[0])}
                        >
                            <i className="meta-icon-accept" /> {actions[0].caption}
                        </div>

                        <div
                            className={
                                'btn-meta-outline-secondary btn-icon-sm btn-inline btn-icon pointer ' +
                                (isDropdownOpen ? 'btn-disabled ' : '')
                            }
                            onClick={() => this.toggleDropdown(!isDropdownOpen)}
                        >
                            <i className="meta-icon-down-1" />
                        </div>

                        {isDropdownOpen &&
                            <QuickActionsDropdown
                                actions={actions}
                                handleClick={this.handleClick}
                                handleClickOutside={() => this.toggleDropdown(false)}
                                disableOnClickOutside={!isDropdownOpen}
                            />
                        }
                    </div>
                    <QuickActionsContextShortcuts
                        handleClick={() => this.handleClick(actions[0])}
                    />
                </div>
            );
        }else{
            return false;
        }
    }
}

QuickActions.childContextTypes = {
    shortcuts: PropTypes.object.isRequired
}

QuickActions.propTypes = {
    dispatch: PropTypes.func.isRequired
};

QuickActions = connect()(QuickActions)

export default QuickActions;
