import React, { Component } from 'react';

export default class Suggestion extends Component {
    handleClick = () => {
        this.props.onClick(this.props.suggestion);
    }

    render() {
        return (
            <div onClick={this.handleClick}>
                {Object.values(this.props.suggestion)[0]}
            </div>
        );
    }
}
