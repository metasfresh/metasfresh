import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import { nodePathsRequest } from '../../actions/MenuActions';

class MenuOverlay extends Component {
    constructor(props){
        super(props);
    }
    handleClickOutside = (e) => {
        const {onClickOutside} = this.props;
        onClickOutside(e);
    }
    render() {
        const {nodeId, node} = this.props;
        const nodeData = node.children;
        return (
            <div className="menu-overlay menu-overlay-primary">
                <div className="menu-overlay-caption">{nodeData.caption}</div>
                <div className="menu-overlay-body">
                    {nodeId == 0 ?
                        <div>
                            {nodeData.children.map((item,index) =>
                                <div key={index}>
                                    <p className="menu-overlay-header">{item.caption}</p>
                                    {item.children.map((subitem, subindex) =>
                                        <span className="menu-overlay-expanded-link" key={subindex}>{subitem.caption}</span>
                                    )}
                                </div>
                            )}
                        </div> :
                        <div>
                            <p className="menu-overlay-header">{nodeData.caption}</p>
                            {nodeData && nodeData.children.map((item, index) => <span className="menu-overlay-expanded-link" key={index}>{item.caption}</span>)}
                        </div>
                    }

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
