import React, { Component, PureComponent } from 'react';
// import { push } from 'connected-react-router';
import {
  Route,
  Switch,
  Redirect,
  BrowserRouter,
  Router,
  withRouter,
} from 'react-router-dom';
import qs from 'qs';
import _ from 'lodash';
import qhistory from 'qhistory';
import { stringify, parse } from 'qs';
import { createBrowserHistory } from 'history';

import { loginSuccess, logoutSuccess } from './actions/AppActions';
import {
  localLoginRequest,
  logoutRequest,
  getResetPasswordInfo,
  loginWithToken,
} from './api';
import { clearNotifications, enableTutorial } from './actions/AppActions';
import { createWindow } from './actions/WindowActions';
import { setBreadcrumb } from './actions/MenuActions';
import { useAuth } from './hooks/useAuth';

import Translation from './components/Translation';
import BlankPage from './components/BlankPage';
import Board from './containers/Board.js';
import Dashboard from './containers/Dashboard.js';
import Calendar from './containers/Calendar';
import DocList from './containers/DocList.js';
import InboxAll from './containers/InboxAll.js';
import Login from './containers/Login.js';
import MasterWindow from './containers/MasterWindow.js';
import NavigationTree from './containers/NavigationTree.js';
import PluginContainer, { pluginWrapper } from './components/PluginContainer';
import PaypalReservationConfirm from './containers/PaypalReservationConfirm.js';

let hasTutorial = false;
const history = qhistory(createBrowserHistory(), stringify, parse);

const ChildRoutes = () => {
  const auth = useAuth();
  const loggedIn = auth.isLoggedIn();

  return (
  // const childRoutes = (
    <>
      <Switch>
        <Route exact path="/" component={Dashboard} />
        <Route path="/window/:windowId/:docId" component={MasterWindowRoute} />
        <Route path="/window/:windowId" component={DocListRoute} />
        <Route path="/sitemap" component={NavigationTree} />
        <Route path="/board/:boardId" component={BoardRoute} />
        <Route path="/inbox" component={InboxAll} />

        <Route
          path="/logout"
          render={() => {
            console.log('logout path: ', loggedIn);
            if (loggedIn) {
              logoutRequest()
                .then(() => logoutSuccess(auth.auth))
                .then(() => {
                  console.log('store dispatch');
                  // dispatch(push('/login'));
                  return <Redirect to="/login" />;
                });

              // return null;
            }

            console.log('logout to redirect');

            return <Redirect to="/login" />;
          }}
        />
      </Switch>
    </>
  );
};

// class LoginRoute extends Component {
const LoginRoute = (props) => {
  // shouldComponentUpdate(nextProps) {
  //   const { location, match } = this.props;
  //   const { location: nextLocation, match: nextMatch } = nextProps;

  //   if (_.isEqual(location, nextLocation) || _.isEqual(match, nextMatch)) {
  //     // console.log('EQUAL')
  //     return false;
  //   }
  //   // console.log('LOGINROUTE NOTEQUAL ')
  //   return true;
  // }

  // render() {
    
    const auth = useAuth();
    const { location } = props;
    const query = qs.parse(location.search, { ignoreQueryPrefix: true });
    const logged = auth.isLoggedIn();

    console.log('LoginRoute params: ', props, logged)
    

    return <Login redirect={query.redirect} {...{ auth }} logged={logged} />;
  // }
};

function DocListRoute({ location, match }) {
  const query = qs.parse(location.search);

  console.log('DocListRoute')

  return <DocList query={query} windowId={match.params.windowId} />;
}
function BoardRoute({ location, match }) {
  const query = qs.parse(location.search);

  return <Board query={query} boardId={match.params.boardId} />;
}

// TODO: PASS PARAMS
class MasterWindowRoute extends PureComponent {
  constructor(props) {
    super(props);

    const {
      match: { params },
      dispatch,
    } = props;

    dispatch(createWindow(params.windowId, params.docId));
  }

  render() {
    return <MasterWindow {...this.props} params={this.props.match.params} />;
  }
}

const PrivateRoute = (props) => {
  // class PrivateRoute extends Component {
    // shouldComponentUpdate(nextProps) {
    //   const { location } = this.props;
    //   const { location: nextLocation } = nextProps;

    //   // console.log('PrivateRoute location: ', location, nextLocation)

    //   if (_.isEqual(location, nextLocation)) {
    //     return false;
    //   }
    //   return true;
    // }
    // shouldComponentUpdate(nextProps) {
    //   const { location, match } = this.props;
    //   const { location: nextLocation, match: nextMatch } = nextProps;

    //   if (_.isEqual(location, nextLocation) || _.isEqual(match, nextMatch)) {
    //     return false;
    //   }
    //   return true;
    // }
    const auth = useAuth();
    // const { dispatch } = props;

    // render() {
      const loggedIn = auth.isLoggedIn();
      // const { dispatch, auth } = this.props;
    // const authRequired = (nextState, replace, callback) => {
    //   hasTutorial =
    //     nextState &&
    //     nextState.location &&
    //     nextState.location.query &&
    //     typeof nextState.location.query.tutorial !== 'undefined';
      console.log('PrivateRoute: ', props);

      let pathname = '/login';

      // if (!loggedIn) {
      //   console.log('notlogged')
      //   localLoginRequest().then((resp) => {
      //     console.log('logindata: ', resp)
      //     if (resp.data) {
      //       dispatch(loginSuccess(auth.auth));
      //       // callback(null, nextState.location.pathname);
      //     } else {
      //       console.log('nie ma logindata ')
      //       //redirect tells that there should be
      //       //step back in history after login
      //       // dispatch(push('/login?redirect=true'));
      //       pathname = '/login?redirect=true';
      //     }
      //   });
      // } else {
      //   // if (hasTutorial) {
      //   //   dispatch(enableTutorial());
      //   // }
      //   console.log('PrivateRoute logged: ', props.location)

      //   if (props.location.pathname !== 'logout') {
      //     dispatch(clearNotifications());
      //     dispatch(loginSuccess(auth));

      //     // return null;
      //   }
      //   else {
      //     // return null;
      //     console.log('PATHNAME: ', pathname)
      //   }

        // callback();
      // }
    // };

      return (
        <Route
          {...props}
          render={() =>
            loggedIn ? (
              <ChildRoutes />
            ) : (
              <Redirect
                to={{
                  pathname,
                }}
              />
            )
          }
        />
      );
    }
  // }

// class Index extends Component {
//   // shouldComponentUpdate(nextProps) {
//   //   const { location, match } = this.props;
//   //   const { location: nextLocation, match: nextMatch } = nextProps;

//   //   if (_.isEqual(location, nextLocation) || _.isEqual(match, nextMatch)) {
//   //     return false;
//   //   }
//   //   return true;
//   // }

//   render() {
//   console.log('render index')
//     return this.props.childRoutes;
//   }
// }

// const WrappedIndex = withRouter(Index);

const Routes = (props) => {
  const auth = useAuth();
  const loggedIn = auth.isLoggedIn();
  const { dispatch } = props;

  console.log('routes render loggedIN: ', loggedIn);

  return (
    <Router history={history}>
      <>
        <Switch>
          <Route exact path="/login">
            {loggedIn ? <Redirect to="/" /> : <Route component={LoginRoute} />}
          </Route>
          <PrivateRoute dispatch={dispatch} path="/" />
        </Switch>
      </>
    </Router>
  );
};
// }

// function propsAreEqual(prevProps, nextProps) {
//   const { auth, dispatch } = prevProps;
//   const { auth: nextAuth, dispatch: nextDispatch } = nextProps;

//   console.log('PROPSAREEQUAL1')

//   if (!dispatch || !nextDispatch) {
//     return false;
//   }

//   if (!auth || !nextAuth || auth.id !== nextAuth.id) {
//     return false;
//   }

//   console.log('PROPSAREEQUAL2')

//   return true;
// }

// export default React.memo(Routes, propsAreEqual);
export default Routes;
