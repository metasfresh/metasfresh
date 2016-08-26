import React, { Component } from 'react';

class ErrorScreen extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className="screen-freeze">
                <h3>Connection lost.</h3>
                <p>There are some connection issues. Check connection and try to refresh the page.</p>
            </div>
        )
    }
}

export default ErrorScreen
