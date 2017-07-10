import React, { Component } from 'react';
import { Shortcuts } from 'react-shortcuts';

class ModalContextShortcuts extends Component {
    constructor(props){
        super(props);
    }
    handleShortcuts = (action, event) => {
        const {start, close, cancel} = this.props;

        switch (action) {
            case 'START':
                event.preventDefault();
                start();
                break;
            case 'CLOSE':
                event.preventDefault();
                close();
                break;
            case 'CANCEL':
                event.preventDefault();
                cancel();
                break;
        }
    }

    render() {
        return (
        <Shortcuts
            name="MODAL_CONTEXT"
            handler = { this.handleShortcuts }
            targetNodeSelector = "body"
            isolate = { true }
            preventDefault = { true }
            stopPropagation = { true }
            alwaysFireHandler = { true }
        />
        )
    }
}

export default ModalContextShortcuts;
