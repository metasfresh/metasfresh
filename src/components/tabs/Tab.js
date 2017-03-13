import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    getTab,
    addRowData
} from '../../actions/WindowActions';

class Tab extends Component {
    constructor(props) {
        super(props);

        const {
            dispatch, tabid, windowType, queryOnActivate, docId
        } = this.props;

        if(queryOnActivate){
            dispatch(getTab(tabid, windowType, docId)).then(res => {
                dispatch(addRowData({[tabid]: res}, 'master'));
            });
        }
    }

    render() {
        const {children} = this.props;

        return (
            <div className="table-flex-wrapper">
                {children}
            </div>
        );
    }
}

Tab.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Tab = connect()(Tab);

export default Tab;
