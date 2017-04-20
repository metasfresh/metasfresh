import React, { Component } from 'react';

class OverlayField extends Component {

    componentDidMount(){
        this.overlayWidget.focus();
    }

    render() {
        return (
            <div className="overlay-field">
                <input
                    className="overlay-type-field"
                    ref={c => this.overlayWidget = c}
                />
            </div>
        )
    }
}

export default OverlayField
