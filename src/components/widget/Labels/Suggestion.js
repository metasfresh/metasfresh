import React, { Component } from 'react';

export default class Suggestion extends Component {
    handleMouseDown = () => {
        this.props.onAdd(this.props.suggestion);
    }

    render() {
        return (
            <div
                className={this.props.className}
                onMouseDown={this.handleMouseDown}
            >
                {Object.values(this.props.suggestion)[0]}
            </div>
        );
    }
}
