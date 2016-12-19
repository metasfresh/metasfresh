import React, { Component,PropTypes } from 'react';

class Tooltips extends Component {
    constructor(props) {
        super(props);
    }



    render() {
        const {open, x, y, name, type} = this.props;
        return (
            open &&
            <div 
                className={"tooltip-wrapp" + " tooltip-"+type}
                style={{
                    left: x,
                    top: y
                }}
            >
                <div className="tooltip-shortcut">{name}</div>
                <div className="tooltip-name">Action menu</div>
            </div>    
        )
    }
}

export default Tooltips
