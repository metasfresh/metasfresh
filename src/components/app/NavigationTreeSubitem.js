import React, { Component } from 'react';

class NavigationTreeSubitem extends Component {
    constructor(props){
        super(props);
    }
    render() {
        const {nodeId, elementId, type, caption, childArray, handleRedirect, handleNewRedirect} = this.props;

        return (
            <div className="nav-tree-children">
                <span className={ (childArray ? "" : "menu-overlay-link")} onClick={e => childArray ? "" : (type==='newRecord' ? handleNewRedirect(elementId) : handleRedirect(elementId) )}>
                    {caption}
                </span>
                <div>
                {childArray && childArray.map((subitem, subindex) =>
                  <NavigationTreeSubitem
                      key={subindex}
                      handleRedirect={handleRedirect}
                      handleNewRedirect={handleNewRedirect}
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
