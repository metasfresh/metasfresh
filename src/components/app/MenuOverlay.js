import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import {
    nodePathsRequest,
    queryPathsRequest
 } from '../../actions/MenuActions';

class MenuOverlay extends Component {
    constructor(props){
        super(props);
        this.state = {
            queriedResults: [],
            query: ""
        };
    }
    handleClickOutside = (e) => {
        const {onClickOutside} = this.props;
        onClickOutside(e);
    }
    handleQuery = (e) => {
        const {dispatch} = this.props;


        e.preventDefault();
        if(!!e.target.value){
            this.setState(Object.assign({}, this.state, {
                query: e.target.value
            }));

            dispatch(queryPathsRequest(e.target.value)).then(response => {
                response.data.children && response.data.children.length > 9 ? response.data.children.length = 9: null;

                this.setState(Object.assign({}, this.state, {
                    queriedResults: response.data.children
                }))
            });
        }else{
            this.setState(Object.assign({}, this.state, {
                queriedResults: [],
                query: ""
            }))
        }
    }
    handleClear = (e) => {
        e.preventDefault();

        this.setState(Object.assign({}, this.state, {
            query: "",
            queriedResults: []
        }));
    }
    render() {
        const {queriedResults} = this.state;
        const {nodeId, node} = this.props;
        const nodeData = node.children;
        return (
            <div className="menu-overlay menu-overlay-primary">
                <div className="menu-overlay-caption">{nodeData.caption}</div>
                <div className="menu-overlay-body">
                    {nodeId == 0 ?
                        <div className="menu-overlay-root-body">
                            <div className="menu-overlay-container">
                                {nodeData.children.map((item,index) =>
                                    <div className="menu-overlay-node-container" key={index}>
                                        <p className="menu-overlay-header">{item.caption}</p>
                                        {item.children.map((subitem, subindex) =>
                                            <span className="menu-overlay-expanded-link" key={subindex}>{subitem.caption}</span>
                                        )}
                                    </div>
                                )}
                            </div>
                            <div className="menu-overlay-query">
                                <div className="input-flex input-primary">
                                    <i className="input-icon meta-icon-preview"/>
                                    <input type="text" className="input-field" placeholder="Type phrase here" value={this.state.query} onChange={e => this.handleQuery(e) } />
                                    {this.state.query && <i className="input-icon meta-icon-close-alt pointer" onClick={e => this.handleClear(e) } />}
                                </div>
                                {queriedResults && queriedResults.map((result, index) =>
                                    <span className="menu-overlay-expanded-link" key={index}>{result.caption}</span>
                                )}
                            </div>
                        </div> :
                        <div className="menu-overlay-node-container">
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
