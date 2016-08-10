import React from 'react';
import { Router, Route, IndexRoute, NoMatch } from 'react-router';

import Main from './containers/Main.js';
import Dashboard from './containers/Dashboard.js';
import NewSalesOrder from './containers/NewSalesOrder.js';
import UiElements from './containers/UiElements.js';

export const getRoutes = (store) => {
    const authRequired = (nextState, replace, callback) => {
        // if( !localStorage.accessToken && !localStorage.refreshToken ){
        //     replace('/login')
        //     callback()
        // }else if( !store.getState().salesOrderStateHandler.user ){
        //     store.dispatch(localLogin()).then(() => {
        //         callback(null, nextState.location.pathname)
        //     })
        // }else{
        //     callback()
        // }
        callback()
    }

    return (
        <Route path="/">
            <Route component={Main} onEnter={authRequired}>
                <IndexRoute component={Dashboard}/>
                <Route path="sales-order" component={NewSalesOrder}/>
            </Route>
            <Route path="login" component={NoMatch}/>
            <Route path="ui" component={UiElements}/>
            <Route path="*" component={NoMatch}/>
        </Route>
    )
}
