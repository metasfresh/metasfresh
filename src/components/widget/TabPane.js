import React, { Component } from 'react';

class TabPane extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div>
                {this.props.children}
            </div>
        )
    }
}

export default TabPane
