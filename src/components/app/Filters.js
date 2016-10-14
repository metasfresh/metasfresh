import React, { Component } from 'react';

class Filters extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div>
                <button className="btn btn-meta-outline-secondary btn-distance btn-sm">
                    <i className="meta-icon-preview" />
                    No search filters
                </button>
            </div>

        )
    }
}

export default Filters
