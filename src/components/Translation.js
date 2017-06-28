import React, { Component } from 'react';
import counterpart from 'counterpart';

import {
    getMessages
} from '../actions/AppActions';

class Translation extends Component {
    constructor(props) {
        super(props);

        this.state = {
            reRender: false
        }
    }
    
    componentDidMount = () => {
        getMessages().then(response => {
            counterpart.registerTranslations('lang', response.data);
            counterpart.setLocale('lang');
            this.setState({reRender:true},
                () => this.setState({reRender:false}));
            counterpart.setMissingEntryGenerator(function(key) {
                console.error('Missing translation: ' + key); 
                return '';
            });
        });
    }

    render = () => !this.state.reRender && this.props.children;
}

export default Translation;
