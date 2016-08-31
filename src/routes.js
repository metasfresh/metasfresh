import React from 'react';
import { Router, Route, IndexRoute, NoMatch } from 'react-router';

import Main from './containers/Main.js';
import Dashboard from './containers/Dashboard.js';
import MasterWindow from './containers/MasterWindow.js';

import {
    createWindow
} from './actions/WindowActions';

export const getRoutes = (store) => {
    return (
        <Route path="/">
            <Route component={Main}>
                <IndexRoute component={Dashboard} />
            </Route>
            <Route path="/window/:windowType(/:docId)" component={MasterWindow} onEnter={(nextState) => store.dispatch(createWindow(nextState.params.windowType,nextState.params.docId))} />
            <Route path="login" component={NoMatch} />
            <Route path="*" component={NoMatch} />
        </Route>
    )
}
