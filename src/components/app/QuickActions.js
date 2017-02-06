import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    quickActionsRequest
} from '../../actions/ListActions';

import {
    openModal
} from '../../actions/WindowActions';

import QuickActionsDropdown from './QuickActionsDropdown';

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
        const {selected, refresh} = this.props;
        if(
            (JSON.stringify(prevProps.selected) !== JSON.stringify(selected)) ||
            (JSON.stringify(prevProps.refresh) !== JSON.stringify(refresh))
        ){
            this.fetchActions();
        }
    }

    handleClickOutside = () => {
        this.toggleDropdown();
    }

    handleClick = (action) => {
        const {dispatch} = this.props;
        if(!action.disabled){
            dispatch(openModal(action.caption, action.processId, "process", null, null, false));
        }
    }

    fetchActions = () => {
        const {dispatch, windowType, viewId, selected} = this.props;

        dispatch(quickActionsRequest(windowType, viewId, selected)).then(response => {
            this.setState(Object.assign({}, this.state, {
                actions: response.data.actions
            }))
        });
    }

    toggleDropdown = (option) => {
        this.setState(Object.assign({}, this.state, {
            isDropdownOpen: option
        }))
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
                            className={"tag tag-success tag-xlg spacer-right " +
                                (actions[0].disabled ? "tag-default " : "pointer ")
                            }
                            onClick={() => this.handleClick(actions[0])}
                        >
                            <i className="meta-icon-accept" /> {actions[0].caption}
                        </div>

                        <div
                            className={
                                "btn-meta-outline-secondary btn-icon-sm btn-inline btn-icon pointer " +
                                (isDropdownOpen ? "btn-disabled " : "")
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
                </div>
            );
        }else{
            return false;
        }
    }
}

QuickActions.propTypes = {
    dispatch: PropTypes.func.isRequired
};

QuickActions = connect()(QuickActions)

export default QuickActions;
