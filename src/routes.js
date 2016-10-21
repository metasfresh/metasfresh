import React from 'react';
import { Router, Route, IndexRoute, NoMatch } from 'react-router';
import {push, replace} from 'react-router-redux';

import Login from './containers/Login.js';
import Dashboard from './containers/Dashboard.js';
import MasterWindow from './containers/MasterWindow.js';
import DocList from './containers/DocList.js';
import NavigationTree from './containers/NavigationTree.js';

import {
    logoutRequest,
    logoutSuccess
} from './actions/AppActions';

import {
    createWindow
} from './actions/WindowActions';

export const getRoutes = (store) => {
    const authRequired = (nextState, replace, callback) => {
            if( !localStorage.isLogged ){
                replace('/login');
                callback();
            }else{
                callback();
            }
        }
    const logout = () => {
        store.dispatch(logoutRequest()).then(()=>
            store.dispatch(logoutSuccess())
        ).then(()=>
            store.dispatch(push('/login'))
        );
    }
    return (
        <Route path="/">
            <Route onEnter={authRequired}>
                <IndexRoute component={Dashboard} />
                <Route path="/window/:windowType"
                    component={(nextState) =>
                        <DocList
                            windowType={nextState.params.windowType}
                        />
                    }
                />
                <Route path="/window/:windowType/:docId"
                    component={MasterWindow}
                    onEnter={(nextState) => store.dispatch(createWindow(nextState.params.windowType, nextState.params.docId))}
                />
                <Route path="/sitemap" component={NavigationTree} />
                <Route path="/logout" onEnter={logout} />
            </Route>
            <Route path="/login" component={Login} />
            <Route path="*" component={NoMatch} />
        </Route>
    )
}
