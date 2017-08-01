import React, { Component } from 'react';
import counterpart from 'counterpart';

import {
    getMessages
} from '../actions/AppActions';

class Translation extends Component {
    constructor(props) {
        super(props);
    }
    
    componentDidMount = () => {
        getMessages().then(response => {
            counterpart.registerTranslations('lang', response.data);
            counterpart.setLocale('lang');
            counterpart.setMissingEntryGenerator(function(key) {
                console.error('Missing translation: ' + key); 
                return '';
            });
        });
    }

    render = () => this.props.children;
}

export default Translation;
