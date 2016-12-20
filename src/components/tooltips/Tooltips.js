import React, { Component,PropTypes } from 'react';

class Tooltips extends Component {
    constructor(props) {
        super(props);
    }



    render() {
        const {name, type} = this.props;
        return (
            <div 
                className={"tooltip-wrapp" + " tooltip-"+type}
            >
                <div className="tooltip-shortcut">{name}</div>
                <div className="tooltip-name">Action menu</div>
            </div>    
        )
    }
}

export default Tooltips
