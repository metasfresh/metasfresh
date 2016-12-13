import React, { Component } from 'react';
import { Provider } from 'react-redux';
import axios from 'axios';

import configureStore from '../store/configureStore';
import { getRoutes } from '../routes.js';

import { syncHistoryWithStore, push } from 'react-router-redux';
import { Router, Route, browserHistory } from 'react-router';

import Moment from 'moment';

import {
    noConnection
} from '../actions/WindowActions'

import {
    addNotification,
    logoutSuccess,
    getAvailableLang
} from '../actions/AppActions';


const store = configureStore(browserHistory);
const history = syncHistoryWithStore(browserHistory, store);



axios.defaults.withCredentials = true;

axios.interceptors.response.use(function (response) {
    return response;
}, function (error) {
    if(error.response.status == 401){
        store.dispatch(logoutSuccess());
        store.dispatch(push('/login?redirect=true'));
    }

    if(localStorage.isLogged){
        store.dispatch(addNotification('Error', error.response.data.message, 5000, 'error'));
    }

    if(!error.response){
        store.dispatch(noConnection(true));
    }

    return Promise.reject(error);
});

export default class App extends Component {
    constructor() {
        super();

        store.dispatch(getAvailableLang()).then(response => {
            if(response.data.values.indexOf(navigator.language)){
                Moment.locale(navigator.language);
            }else{
                Moment.locale(response.data.defaultValue);
            }
        });
    }

    render() {
        return (
            <Provider store={store}>
                <Router history={history} routes={getRoutes(store)} />
            </Provider>
        )
    }
}
