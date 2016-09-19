import React, { Component } from 'react';

class NavigationTreeSubitem extends Component {
    constructor(props){
        super(props);
    }
    render() {
        const {nodeId, elementId, caption, childArray, handleRedirect} = this.props;

        return (
            <div className="nav-tree-children">
                <span className={ (childArray ? "" : "menu-overlay-link")} onClick={e => childArray ? "" : handleRedirect(elementId)}>
                    {caption}
                </span>
                <div>
                {childArray && childArray.map((subitem, subindex) =>
                  <NavigationTreeSubitem
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

export default NavigationTreeSubitem
