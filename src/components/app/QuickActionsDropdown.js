import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import onClickOutside from 'react-onclickoutside';

class QuickActionsDropdown extends Component {
    constructor(props){
        super(props);
    }

    handleClickOutside = () => {
        const {handleClickOutside} = this.props;

        handleClickOutside();
    }

    render() {
        const {
            actions,
            handleClick
        } = this.props;

        return (
            <div
                className="quick-actions-dropdown"
            >
                {actions.map((action, index) =>
                    <div
                        className={
                            'quick-actions-item ' +
                            (action.disabled ? 'quick-actions-item-disabled ' : '')}
                        key={index}
                        onClick={() => handleClick(action)}
                    >
                        {action.caption}
                        {action.disabled &&
                            <p className="one-line">
                                <small>({action.disabledReason})</small>
                            </p>
                        }
                    </div>
                )}
            </div>
        );
    }
}

QuickActionsDropdown = connect()(onClickOutside(QuickActionsDropdown))

export default QuickActionsDropdown;
