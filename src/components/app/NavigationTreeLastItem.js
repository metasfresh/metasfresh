import React, { Component } from 'react';

class NavigationTreeLastItem extends Component {
    constructor(props){
        super(props);
    }
    render() {
        const {nodeId, elementId, caption, handleRedirect} = this.props;
        return (
            <div className="nav-tree-children">
                <span className="menu-overlay-link" onClick={e => handleRedirect(elementId)}>
                    {caption}
                </span>
            </div>
        )
    }
}

export default NavigationTreeLastItem
