import React, { Component } from 'react';
import Lane from './Lane';

class Lanes extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {lanes, onDrop, onHover, targetIndicator} = this.props;
        
        if(!lanes) return false;
        
        return (
            <div className="board-lanes">
                {lanes.map((lane, i) => (
                    <Lane 
                        key={i}
                        {...{onDrop, onHover, targetIndicator}}
                        {...lane}
                    />)
                )}
            </div>
        );
    }
}

export default Lanes;
