import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import { nodePathsRequest } from '../../actions/MenuActions';

class MenuOverlay extends Component {
    constructor(props){
        super(props);

        this.state = {};

        const { dispatch, nodeId } = this.props;

        dispatch(nodePathsRequest(nodeId)).then(item => {
            item.data.children.length > 12 ? item.data.children.length = 10 : null;

            this.setState(Object.assign({}, this.state, {
                ...item.data
            }))
        });
    }
    handleClickOutside = (e) => {
        const {onClickOutside} = this.props;
        onClickOutside(e);
    }
    render() {
        const {caption, children} = this.state;
        return (
            <div className="menu-overlay menu-overlay-primary">
                <div className="menu-overlay-caption">{caption}</div>
                <div className="menu-overlay-body">
                    <p className="menu-overlay-header">{caption}</p>
                    {children && children.map((item, index) => <a href="" key={index}>{item.caption}</a>)}
                </div>
            </div>
        )
    }
}

MenuOverlay.propTypes = {
    dispatch: PropTypes.func.isRequired
};

MenuOverlay = connect()(onClickOutside(MenuOverlay));

export default MenuOverlay
