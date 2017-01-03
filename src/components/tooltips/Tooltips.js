import React, { Component,PropTypes } from 'react';

class Tooltips extends Component {
    constructor(props) {
        super(props);

        this.state = {
            opacity: 0
        }
    }

    componentDidMount() {
        this.timeout = setTimeout(() => {
            this.setState(Object.assign({}, this.state, {
                opacity: 1
            }))
        }, 1000);
    }

    componentWillUnmount() {
        clearTimeout(this.timeout);
    }

    render() {
        const {name, action, type, extraClass} = this.props;
        const {opacity} = this.state;
        return (
            <div style={{opacity: opacity}}>
                <div 
                    className={"tooltip-wrapp" + " tooltip-"+type + " " + extraClass}
                >
                    <div className="tooltip-shortcut">{name}</div>
                    <div className="tooltip-name">{action}</div>
                </div>  
            </div>
              
        )
    }
}

export default Tooltips
