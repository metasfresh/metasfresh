import React from 'react';
import { Route, Switch } from 'react-router-dom';
import MasterWindowRoute from './MasterWindowRoute';
import DocumentViewRoute from './DocumentViewRoute';
import BoardRoute from './BoardRoute';
import Board from '../containers/Board.js';
import Dashboard from '../containers/Dashboard.js';
import InboxAll from '../containers/InboxAll.js';
import NavigationTree from '../containers/NavigationTree.js';
import CalendarPage from '../pages/calendar/CalendarPage';
import LogoutRoute from './LogoutRoute';

/**
 * @file Functional component.
 * @module routes/ChildRoutes
 * Wrapper around the routes that need to be authentication-protected
 */
const ChildRoutes = () => {
  return (
    <>
      <Switch>
        <Route exact path="/" component={Dashboard} />
        <Route path="/window/:windowId/:docId" component={MasterWindowRoute} />
        <Route path="/window/:windowId" component={DocumentViewRoute} />
        <Route path="/sitemap" component={NavigationTree} />
        <Route path="/board/:boardId" component={BoardRoute} />
        <Route path="/inbox" component={InboxAll} />
        <Route path="/calendar" component={CalendarPage} />
        <Route path="/logout" component={LogoutRoute} />
        <Route path="*" render={(props) => <Board board="404" {...props} />} />
      </Switch>
    </>
  );
};

export default React.memo(ChildRoutes);
