import React, { Component } from 'react';

export default class Suggestion extends Component {
    handleMouseDown = () => {
        this.props.onMouseDown(this.props.suggestion);
    }

    render() {
        return (
            <div onMouseDown={this.handleMouseDown}>
                {Object.values(this.props.suggestion)[0]}
            </div>
        );
    }
}
