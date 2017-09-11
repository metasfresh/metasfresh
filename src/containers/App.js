import React, { Component } from 'react';
import { Provider } from 'react-redux';
import axios from 'axios';
import counterpart from 'counterpart';

import configureStore from '../store/configureStore';
import { getRoutes } from '../routes.js';
import {LOCAL_LANG}  from '../constants/Constants';

import { syncHistoryWithStore, push } from 'react-router-redux';
import { Router, browserHistory } from 'react-router';

import Auth from '../services/Auth';
import Translation from '../components/Translation';

import NotificationHandler
    from '../components/notifications/NotificationHandler';

import {
    noConnection
} from '../actions/WindowActions';

import {
    addNotification,
    logoutSuccess,
    getAvailableLang,
    setProcessSaved,
    languageSuccess
} from '../actions/AppActions';

import '../assets/css/styles.css';

const store = configureStore(browserHistory);
const history = syncHistoryWithStore(browserHistory, store);

export default class App extends Component {
    constructor() {
        super();

        this.auth = new Auth();

        axios.defaults.withCredentials = true;
        axios.defaults.headers.common['Content-Type'] = 'application/json';

        const cachedLang = localStorage.getItem(LOCAL_LANG);
        if(cachedLang){
            languageSuccess(cachedLang);
        }

        axios.interceptors.response.use(function (response) {
            return response;
        }, function (error) {
            if(!error.response){
                store.dispatch(noConnection(true));
            }

            /*
             * Authorization error
             */
            if(error.response.status == 401){
                store.dispatch(setProcessSaved());
                logoutSuccess(this.auth);
                store.dispatch(push('/login?redirect=true'));
            }else if(error.response.status == 503){
                store.dispatch(noConnection(true));
            }else if(error.response.status != 404){
                if(localStorage.isLogged){
                    const errorMessenger = (code) => {
                        switch(code){
                            case 500:
                                return 'Server error';
                            case 400:
                                return 'Client error';
                        }
                    }
                    const {
                        data, status
                    } = error.response;

                    const errorTitle = errorMessenger(status);

                    console.error(data.message);

                    // Chart disabled notifications
                    if(
                        error.response.request.responseURL
                            .includes('silentError=true')
                    ){
                        return;
                    }

                    store.dispatch(addNotification(
                        'Error: ' + data.message.split(' ', 4).join(' ') +
                        '...', data.message, 5000, 'error', errorTitle)
                    );
                }
            }

            if(error.response.request.responseURL.includes('showError=true')){
                const {
                    data
                } = error.response;

                store.dispatch(addNotification(
                    'Error: ' + data.message.split(' ', 4).join(' ') + '...',
                    data.message, 5000, 'error', '')
                );
            } else {
                return Promise.reject(error);
            }
        }.bind(this));

        getAvailableLang().then(response => {
            const {defaultValue, values} = response.data;
            const valuesFlatten = values.map(item => Object.keys(item)[0]);
            const lang = valuesFlatten.indexOf(navigator.language) > -1 ?
                navigator.language : defaultValue;
                
            languageSuccess(lang);
        });
        
        counterpart.setMissingEntryGenerator(() => '');
    }

    render() {
        return (
            <Provider store={store}>
                <Translation>
                    <NotificationHandler>
                        <Router
                            history={history}
                            routes={getRoutes(store, this.auth)}
                        />
                    </NotificationHandler>
                </Translation>
            </Provider>
        )
    }
}
