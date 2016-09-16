import React, { Component } from 'react';
import NavigationTreeSubitem from './NavigationTreeSubitem';
import MenuOverlayItem from './MenuOverlayItem';

class NavigationTreeItem extends Component {
    constructor(props){
        super(props);
    }
    render() {
        const {nodeId, elementId, caption, children, handleClickOnFolder, handleRedirect} = this.props;

        return (
                <div className="col-md-4 col-lg-3">
                    <div className="nav-tree-item">
                        <span className="menu-overlay-header" >
                            {caption}
                        </span>
                        {children && children.map((subitem, subindex) =>
                            <MenuOverlayItem
                                key={subindex}
                                handleClickOnFolder={handleClickOnFolder}
                                handleRedirect={handleRedirect}
                                {...subitem}
                            />
                        )}
                    </div>
                </div>


        )
    }
}

export default NavigationTreeItem
