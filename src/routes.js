import React from 'react';
import { Router, Route, IndexRoute, NoMatch } from 'react-router';

import Dashboard from './containers/Dashboard.js';
import MasterWindow from './containers/MasterWindow.js';
import DocList from './containers/DocList.js';
import NavigationTree from './containers/NavigationTree.js';

import {
    createWindow
} from './actions/WindowActions';

export const getRoutes = (store) => {
    return (
        <Route path="/">
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
            <Route path="*" component={NoMatch} />
        </Route>
    )
}
