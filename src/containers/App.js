import React, { Component } from 'react';
import { Provider } from 'react-redux';
import axios from 'axios';

import configureStore from '../store/configureStore';
import { getRoutes } from '../routes.js';
import config from '../config.js';

import { syncHistoryWithStore, push } from 'react-router-redux';
import { Router, Route, browserHistory } from 'react-router';

import Moment from 'moment';

import {
    noConnection
} from '../actions/WindowActions'

import {
    addNotification,
    logoutSuccess
} from '../actions/AppActions';

Moment.locale(navigator.language);

const store = configureStore(browserHistory);
const history = syncHistoryWithStore(browserHistory, store);

axios.defaults.withCredentials = true;

axios.interceptors.response.use(function (response) {
    return response;
}, function (error) {
    if(error.response.status == 403){
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
    render() {
        return (
            <Provider store={store}>
                <Router history={history} routes={getRoutes(store)} />
            </Provider>
        )
    }
}
