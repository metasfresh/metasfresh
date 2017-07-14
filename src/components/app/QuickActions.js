import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import counterpart from 'counterpart';

import {
    quickActionsRequest
} from '../../actions/ListActions';

import {
    openModal
} from '../../actions/WindowActions';

import QuickActionsDropdown from './QuickActionsDropdown';

import keymap from '../../keymap.js';
import QuickActionsContextShortcuts
    from '../shortcuts/QuickActionsContextShortcuts';
import Tooltips from '../tooltips/Tooltips.js'
import { ShortcutManager } from 'react-shortcuts';
const shortcutManager = new ShortcutManager(keymap);

class QuickActions extends Component {
    constructor(props){
        super(props);

        this.state = {
            actions: [],
            isDropdownOpen: false,
            isTooltip: false,
            loading: false
        }

        const {fetchOnInit} = this.props;

        if(fetchOnInit){
            this.fetchActions();
        }
    }

    componentDidMount = () => {
        this.mounted = true;
    }

    componentWillUnmount = () => {
        this.mounted = false;
    }

    componentDidUpdate = (prevProps) => {
        const {
            selected, refresh, shouldNotUpdate, viewId, selectedWindowType,
            windowType, inBackground
        } = this.props;

        if(shouldNotUpdate){
            return;
        }

        if(selectedWindowType && (selectedWindowType !== windowType)){
            return;
        }

        if (inBackground === true && (prevProps.inBackground === false)) {
            // gained focus after modal closed
            this.setState({
                loading: false
            });
        }

        if(
            (JSON.stringify(prevProps.selected) !== JSON.stringify(selected)) ||
            (JSON.stringify(prevProps.refresh) !== JSON.stringify(refresh)) ||
            (JSON.stringify(prevProps.viewId) !== JSON.stringify(viewId))
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
        const {dispatch, viewId, selected} = this.props;

        if(action.disabled){
            return;
        }

        this.setState({
            loading: true
        });

        let result = dispatch(
            openModal(
                action.caption, action.processId, 'process', null, null, false,
                viewId, selected
            )
        );

        this.toggleDropdown();
    }

    fetchActions = () => {
        const {windowType, viewId, selected} = this.props;

        this.mounted && this.setState({
            loading: true
        });

        quickActionsRequest(windowType, viewId, selected)
            .then(response => {
                this.mounted && this.setState({
                    actions: response.data.actions,
                    loading: false
                })
            })
            .catch(() => {
                this.mounted && this.setState({
                    loading: false
                })
            });
    }

    toggleDropdown = (option) => {
        this.setState({
            isDropdownOpen: option
        })
    }

    toggleTooltip = (visible) => {
        this.setState({
            isTooltip: visible
        })
    }

    render() {
        const {
            actions, isDropdownOpen, isTooltip
        } = this.state;

        const {
            shouldNotUpdate, processStatus, disabled
        } = this.props;

        const disabledDuringProcessing = (processStatus === "pending") || this.state.loading;

        if(actions.length){
            return (
                <div
                    className={'js-not-unselect ' +
                        (disabled ? 'disabled ' : '')
                    }
                >
                    <span className="spacer-right">
                        {counterpart.translate('window.quickActions.caption')}:
                    </span>
                    <div className="quick-actions-wrapper">
                        <div
                            className={
                                'tag tag-success tag-xlg spacer-right ' +
                                'quick-actions-tag ' +
                                ((actions[0].disabled || disabledDuringProcessing) ?
                                    'tag-default ' : 'pointer '
                                )
                            }
                            onClick={(e) => {
                                e.preventDefault();

                                if (!disabledDuringProcessing) {
                                    this.handleClick(actions[0])
                                }
                            }}
                            title={actions[0].caption}
                        >
                            {actions[0].caption}
                        </div>
                        <div
                            className={
                                'btn-meta-outline-secondary btn-icon-sm ' +
                                'btn-inline btn-icon pointer tooltip-parent ' +
                                ((isDropdownOpen || disabledDuringProcessing) ? 'btn-disabled ' : '')
                            }
                            onMouseEnter={() => this.toggleTooltip(true)}
                            onMouseLeave={() => this.toggleTooltip(false)}
                            onClick={
                                () => {
                                    if (!disabledDuringProcessing) {
                                        this.toggleDropdown(!isDropdownOpen)
                                    }
                                }
                            }
                        >
                            <i className="meta-icon-down-1" />
                            {isTooltip &&
                                <Tooltips
                                    name={
                                        keymap.QUICK_ACTIONS.QUICK_ACTION_TOGGLE
                                    }
                                    action={
                                            'Toggle list'
                                    }
                                    type={''}
                                />
                            }
                        </div>

                        {isDropdownOpen &&
                            <QuickActionsDropdown
                                actions={actions}
                                handleClick={this.handleClick}
                                handleClickOutside={() =>
                                    this.toggleDropdown(false)
                                }
                                disableOnClickOutside={!isDropdownOpen}
                            />
                        }
                    </div>
                    <QuickActionsContextShortcuts
                        handleClick={() => shouldNotUpdate ?
                            null : this.handleClick(actions[0])
                        }
                        onClick={() => this.toggleDropdown(!isDropdownOpen)}
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
