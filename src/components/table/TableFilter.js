import React, { Component } from 'react';

class TableFilter extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className="row">
                <div className="offset-xs-8 col-xs-4">
                    <div className="form-flex-align">
                        <input type="text" className="input-primary pull-xs-left" placeholder="Enter filter phrase" />
                        <button className="btn-icon btn-meta-outline-secondary pull-xs-right"><i className="meta-icon-full-1"/></button>
                        <button className="btn-icon btn-meta-outline-secondary pull-xs-right"><i className="meta-icon-add-1"/></button>
                    </div>
                </div>
            </div>
        )
    }
}

export default TableFilter
