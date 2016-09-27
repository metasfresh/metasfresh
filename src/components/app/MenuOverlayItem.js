import React, { Component } from 'react';

class MenuOverlayItem extends Component {
    constructor(props){
        super(props);
    }
    showQueriedChildren = (children) => {
        // console.log("aaaa");
        // console.log(query);
        // caption +' > '+children[0].caption
        // {query ? (children ? this.showQueriedChildren(caption, children) : caption ) : caption}

        console.log(children);

    }
    render() {
        const {nodeId, type, elementId, caption, children, handleClickOnFolder, handleRedirect, handleNewRedirect, query} = this.props;

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
                    onClick={e => children ? handleClickOnFolder(e, nodeId) : (type==='newRecord' ? handleNewRedirect(elementId) : handleRedirect(elementId) )}
                >


                    {query ? (children ? children.map(
                        (item, id) => 
                        

                        <div key={id} className="query-results">
                            <div className="query-caption">{caption +' > '}</div>
                            <MenuOverlayItem
                                query={true}
                                {...item}
                            />

                        </div>
                        

                        ) : caption ) : caption}
                    



                </span>
            </div>
        )
    }
}

export default MenuOverlayItem
