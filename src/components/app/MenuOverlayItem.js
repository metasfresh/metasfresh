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

        //onClick={ children ? '' : (e => type === 'group' ? handleClickOnFolder(e, nodeId) : (e => type === 'newRecord' ? handleNewRedirect(e, elementId) : handleRedirect(elementId)) ) }

        console.log(children);

    }
    clickedItem = (e, elementId, nodeId, type ) => {
        const {handleClickOnFolder, handleRedirect, handleNewRedirect} = this.props;

        if(type === 'newRecord'){
            handleNewRedirect(elementId);
        } else if (type === 'window') {
            handleRedirect(elementId)
        } else if (type === 'group') {
            handleClickOnFolder(e, nodeId)
        }
    }
    render() {
        const {nodeId, type, elementId, caption, children, handleClickOnFolder, handleRedirect, handleNewRedirect, query} = this.props;


        return (
            <div
                className={
                    "menu-overlay-expanded-link "
                }
            >

            { !query &&
                <span
                    className={
                        (children ? "menu-overlay-expand" : "menu-overlay-link")
                    }
                    onClick={query? '' : e => children ? handleClickOnFolder(e, nodeId) : (type==='newRecord' ? handleNewRedirect(elementId) : handleRedirect(elementId) )}
                >
                {caption}
                </span>

            }

            { query &&
               <span className={children ? "" : (type === 'group'? "query-clickable-group" : "query-clickable-link")} onClick={ children ? '' : e => this.clickedItem(e, elementId, nodeId, type)  }> 
                    {children ? children.map(
                        (item, id) => 
                        

                        <div key={id} className="query-results" >
                            <div className="query-caption">{caption +' / '}</div>
                            <MenuOverlayItem
                                handleClickOnFolder={handleClickOnFolder}
                                handleRedirect={handleRedirect}
                                handleNewRedirect={handleNewRedirect}
                                query={true}
                                {...item}
                            />

                        </div>
                        

                        ) : caption}


               </span>
            }


                



            </div>
        )
    }
}

export default MenuOverlayItem
