import React, { Component } from 'react';
import counterpart from 'counterpart';

class ErrorScreen extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className="screen-freeze">
                <h3>{counterpart.translate('window.error.noStatus.title')}</h3>
                <p>
                    {counterpart.translate('window.error.noStatus.description')}
                </p>
            </div>
        )
    }
}

export default ErrorScreen
