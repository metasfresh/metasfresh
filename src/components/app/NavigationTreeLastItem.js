import React, { Component } from 'react';

class NavigationTreeLastItem extends Component {
    constructor(props){
        super(props);
    }
    render() {
        const {nodeId, elementId, caption, handleRedirect, childArray} = this.props;
        return (
                <div className="nav-tree-children">
                    <span className="menu-overlay-link" onClick={e => handleRedirect(elementId)}>
                        {caption}
                    </span>

                    <div>
                        {childArray && childArray.map((subitem, subindex) =>
                        <NavigationTreeLastItem
                            key={subindex}
                            handleRedirect={handleRedirect}
                            childArray={subitem.children}
                            {...subitem}
                        />
                        )}

                    </div>

                </div>
        )
    }
}

export default NavigationTreeLastItem
