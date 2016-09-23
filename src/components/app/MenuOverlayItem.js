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
        const {nodeId, elementId, caption, children, handleClickOnFolder, handleRedirect, query} = this.props;

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


                    {query ? (children ? children.map((item, id) => <span className="queried-result" key={id}> {caption + ' > ' + item.caption} </span>) : caption ) : caption}
                    



                </span>
            </div>
        )
    }
}

export default MenuOverlayItem
