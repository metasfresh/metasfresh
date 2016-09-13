import React, { Component } from 'react';

class MenuOverlayItem extends Component {
    constructor(props){
        super(props);
    }
    render() {
        const {nodeId, elementId, caption, children, handleClickOnFolder, handleRedirect} = this.props;

        return (
            <div
                className={
                    "menu-overlay-expanded-link "
                }
            >
                <span
                    className={
                        (children ? "menu-overlay-expand" : "menu-overlay-link")
                    }
                    onClick={e => children ? handleClickOnFolder(e, nodeId) : handleRedirect(elementId)}
                >
                    {caption}
                </span>
            </div>
        )
    }
}

export default MenuOverlayItem
