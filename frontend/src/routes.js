import React, { Component, PureComponent } from 'react';
import { push } from 'connected-react-router';
import { Route, Switch, Redirect, Link, withRouter } from 'react-router-dom';
import qs from 'qs';
import _ from 'lodash';

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

// const DocListRoute = (nextState) => (
//   <DocList
//     query={nextState.location.query}
//     windowId={nextState.params.windowId}
//   />
// );
// const BoardRoute = (nextState) => (
//   <Board query={nextState.location.query} boardId={nextState.params.boardId} />
// );

class LoginRoute extends Component {
  shouldComponentUpdate(nextProps) {
    const { location, match } = this.props;
    const { location: nextLocation, match: nextMatch } = nextProps;

    if (_.isEqual(location, nextLocation) || _.isEqual(match, nextMatch)) {
      // console.log('EQUAL')
      return false;
    }
    // console.log('LOGINROUTE NOTEQUAL ')
    return true;
  }

  render() {
    const { location, auth } = this.props;
    const query = qs.parse(location.search, { ignoreQueryPrefix: true });

    const logged = localStorage.isLogged;

    // console.log('LoginRoute params: ', location, query)

    return <Login redirect={query.redirect} {...{ auth }} logged={logged} />;
  }
}

// class LogoutRoute extends Component {
//   constructor(props) {
//     // super(props);

//     // this.state = {
//     //   loggedIn: true,
//     // };

//     console.log('LOGOUTROUTE')

//     logoutRequest()
//       .then(() => logoutSuccess(auth))
//       .then(() => {
//         console.log('redirecting to login')
//         dispatch(setBreadcrumb([]));
//         dispatch(push('/login'));
//         // this.setState({ loggedIn: false })
//       });
//   }

//   render() {
//     // const { component: Component, ...rest } = this.props;
//     // const { loggedIn } = this.state;

//     // return (
//     //   <Route
//     //     {...rest}
//     //     render={(props) => {
//     //       if (loggedIn) {
//     //         return <Component {...props} />;
//     //       } else {
//     //         //redirect tells that there should be
//     //         //step back in history after login
//     //         return <Redirect to="/login" />;
//     //       }
//     //     }}
//     //   />
//     // );
//     // if (loggedIn) {
//       return null;
//     // }

//     // return <Redirect to="/login" />;
//   }
// }

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

class Index extends PureComponent {
  // shouldComponentUpdate(nextProps) {
  //   const { location } = this.props;
  //   const { location: nextLocation } = nextProps;

  //   if (_.isEqual(location, nextLocation)) {
  //     return false;
  //   }
  //   return true;
  // }

  render() {
  console.log('render index')
    return this.props.childRoutes;
  }
}

const WrappedIndex = withRouter(Index);

// export const getRoutes = (store, auth, plugins) => {
class Routes extends PureComponent {
  // TODO
  // const authRequired = (nextState, replace, callback) => {
  //   hasTutorial =
  //     nextState &&
  //     nextState.location &&
  //     nextState.location.query &&
  //     typeof nextState.location.query.tutorial !== 'undefined';

  //   if (!localStorage.isLogged) {
  //     localLoginRequest().then((resp) => {
  //       if (resp.data) {
  //         dispatch(loginSuccess(auth));
  //         callback(null, nextState.location.pathname);
  //       } else {
  //         //redirect tells that there should be
  //         //step back in history after login
  //         dispatch(push('/login?redirect=true'));
  //       }
  //     });
  //   } else {
  //     if (hasTutorial) {
  //       dispatch(enableTutorial());
  //     }

  //     dispatch(clearNotifications());
  //     dispatch(loginSuccess(auth));

  //     callback();
  //   }
  // };

  // MASTER
  /**
   * @method tokenAuthentication
   * @summary - method executed when we authenticate directly by using a `token` without the need to supply a `username` and a `password`
   * @param {object} - tokenId prop given as param to the /token path i.e  /token/xxxxxxx   (`xxxxxxx` will be the value of the tokenId )
   */
  // const tokenAuthentication = ({ params: { tokenId } }) => {
  //   loginWithToken(tokenId)
  //     .then(() => {
  //       dispatch(push('/'));
  //     })
  //     .catch(() => {
  //       dispatch(push('/login?redirect=true'));
  //     });
  // };
  //

  // TODO
  // const onResetEnter = (nextState, replace, callback) => {
  //   const token = nextState.location.query.token;

  //   if (!token) {
  //     callback(null, nextState.location.pathname);
  //   }

  //   return getResetPasswordInfo(token).then(() => {
  //     return Translation.getMessages().then(() => {
  //       callback(null, nextState.location.pathname);
  //     });
  //   });
  // };

  // MASTER
  // const logout = () => {
  //   logoutRequest()
  //     .then(() => logoutSuccess(auth))
  //     .then(() => {
  //       dispatch(setBreadcrumb([]));
  //       dispatch(push('/login'));
  //     });
  // };
  //

  // function setPluginBreadcrumbHandlers(routesArray, currentBreadcrumb) {
  //   routesArray.forEach((route) => {
  //     const routeBreadcrumb = [
  //       ...currentBreadcrumb,
  //       {
  //         caption: route.breadcrumb.caption,
  //         type: route.breadcrumb.type,
  //       },
  //     ];

  //     route.onEnter = () => dispatch(setBreadcrumb(routeBreadcrumb));

  //     if (route.childRoutes) {
  //       setPluginBreadcrumbHandlers(route.childRoutes, routeBreadcrumb);
  //     }
  //   });
  // }

  // const getPluginsRoutes = (plugins) => {
  //   if (plugins.length) {
  //     const routes = plugins.map((plugin) => {
  //       if (plugin.routes && plugin.routes.length) {
  //         const pluginRoutes = [...plugin.routes];
  //         const ParentComponent = pluginRoutes[0].component;

  //         // wrap main plugin component in a HOC that'll render it
  //         // inside the app using a Container element
  //         if (ParentComponent.name !== 'WrappedPlugin') {
  //           const wrapped = pluginWrapper(PluginContainer, ParentComponent);

  //           pluginRoutes[0].component = wrapped;

  //           if (pluginRoutes[0].breadcrumb) {
  //             setPluginBreadcrumbHandlers(pluginRoutes, []);
  //           }
  //         }

  //         return pluginRoutes[0];
  //       }

  //       return [];
  //     });

  //     return routes;
  //   }

  //   return [];
  // };
  // const pluginRoutes = getPluginsRoutes(plugins);

  //MASTER
  //   const childRoutes = [
  //     {
  //       path: '/window/:windowId',
  //       // eslint-disable-next-line react/display-name
  //       component: DocListRoute,
  //     },
  //     {
  //       path: '/window/:windowType/:docId',
  //       component: MasterWindow,
  //       onEnter: ({ params }) =>
  //         dispatch(
  //           createWindow({
  //             windowId: params.windowType,
  //             docId: params.docId,
  //           })
  //         ),
  //     },
  //     {
  //       path: '/sitemap',
  //       component: NavigationTree,
  //     },
  //     {
  //       path: '/board/:boardId',
  //       // eslint-disable-next-line react/display-name
  //       component: BoardRoute,
  //     },
  //     {
  //       path: '/inbox',
  //       component: InboxAll,
  //     },
  //     {
  //       path: 'logout',
  //       onEnter: logout,
  //     },
  //     ...pluginRoutes,
  //   ];

  //   return (
  //     <Route path="/">
  //       <Route onEnter={authRequired} childRoutes={childRoutes}>
  //         <IndexRoute component={Dashboard} />
  //       </Route>
  //       <Route path="/token/:tokenId" onEnter={tokenAuthentication} />
  //       <Route
  //         path="/login"
  //         component={({ location }) => (
  //           <Login
  //             redirect={location.query.redirect}
  //             logged={localStorage.getItem('isLogged') === 'true'}
  //             {...{ auth }}
  //           />
  //         )}
  //       />
  //       <Route
  //         path="/forgottenPassword"
  //         component={({ location }) => (
  //           <Login splat={location.pathname.replace('/', '')} {...{ auth }} />
  //         )}
  //       />
  //       <Route
  //         path="/resetPassword"
  //         onEnter={onResetEnter}
  //         component={({ location }) => (
  //           <Login
  //             splat={location.pathname.replace('/', '')}
  //             token={location.query.token}
  //             {...{ auth }}
  //           />
  //         )}
  //       />
  //       <Route
  //         path="/paypal_confirm"
  //         component={({ location }) => (
  //           <PaypalReservationConfirm
  //             token={location.query.token}
  //             {...{ auth }}
  //           />
  //         )}
  //       />
  //       <Route path="/calendar" component={Calendar} />
  //       <Route path="*" component={NoMatch} />
  //     </Route>
  //   );
  // };


  // const PrivateRoute = ({ component: Component, ...rest }) => (
  //   <Route
  //     {...rest}
  //     render={(props) => {
  //       const { location } = props;
  //       const query = qs.parse(location.search, { ignoreQueryPrefix: true });

  //       // console.log('COMPONENT: ', localStorage.isLogged)

  //       hasTutorial = query && typeof query.tutorial !== 'undefined';

  //       if (!localStorage.isLogged) {
  //         // console.log('1');
  //         localLoginRequest().then((resp) => {
  //           // console.log('2')
  //           if (resp.data) {
  //             // console.log('3: ', resp.data)
  //             dispatch(loginSuccess(auth));

  //             return <Component {...props} />;
  //           } else {
  //             // console.log('4');
  //             //redirect tells that there should be
  //             //step back in history after login
  //             dispatch(push('/login?redirect=true'))
  //           }
  //         });
  //       } else {
  //         // console.log('5')
  //         if (hasTutorial) {
  //           dispatch(enableTutorial());
  //         }

  //         dispatch(clearNotifications());
  //         dispatch(loginSuccess(auth));

  //         return <Component {...props} />;
  //       }
  //     }}
  //   />
  // );

  // const childRoutes = (
  //   <Switch>
  //     <PrivateRoute exact path="/" component={Dashboard} />
  //     <PrivateRoute
  //       path="/window/:windowId/:docId"
  //       component={MasterWindowRoute}
  //     />
  //     <PrivateRoute path="/window/:windowId" component={DocListRoute} />
  //     <PrivateRoute path="/sitemap" component={NavigationTree} />
  //     <PrivateRoute path="/board/:boardId" component={BoardRoute} />
  //     <PrivateRoute path="/inbox" component={InboxAll} />
  //     <Route
  //       path="/logout"
  //       render={() => {
  //         // console.log('BLA: ', localStorage.isLogged)
  //         if (localStorage.isLogged) {
  //           logoutRequest()
  //             .then(() => logoutSuccess(auth))
  //             .then(() => {
  //               console.log('store dispatch');
  //               dispatch(push('/login'));
  //             });

  //           return null;
  //         }

  //         return <Redirect to="/login" />;
  //       }}
  //     />
  //     {pluginRoutes}
  //     <Route path="*" component={BlankPage} />
  //   </Switch>
  // );

  // function LogoutPath() {
  //   console.log('FOOO LOGOUT')
  //   logoutRequest()
  //     .then(() => logoutSuccess(auth))
  //     .then(() => {
  //       dispatch(setBreadcrumb([]));
  //       dispatch(push('/login'));
  //     });

  //   return (
  //     <Route
  //       render={() => (
  //         <Redirect
  //           to={{
  //             pathname: '/login',
  //           }}
  //         />
  //       )}
  //     />
  //   );
  // }

//   return (
//     <Switch>
//       <Route
//         path="/login"
//         component={({ location }) => {
//           // console.log('LOGIN !!')
//           const query = qs.parse(location.search, { ignoreQueryPrefix: true });
//           return (
//             <Login
//               redirect={query.redirect}
//               logged={localStorage.getItem('isLogged') === 'true'}
//               {...{ auth }}
//             />
//           );
//         }}
//       />
//       <Route
//         path="/forgottenPassword"
//         component={({ location }) => (
//           <Login splat={location.pathname.replace('/', '')} {...{ auth }} />
//         )}
//       />
//       <Route
//         path="/resetPassword"
//         onEnter={onResetEnter}
//         component={({ location }) => {
//           const query = qs.parse(location.search, { ignoreQueryPrefix: true });
//           return (
//             <Login
//               splat={location.pathname.replace('/', '')}
//               token={query.token}
//               {...{ auth }}
//             />
//           );
//         }}
//       />
//       <Route
//         path="/paypal_confirm"
//         component={({ location }) => {
//           const query = qs.parse(location.search, { ignoreQueryPrefix: true });
//           return <PaypalReservationConfirm token={query.token} {...{ auth }} />;
//         }}
//       />
//       <Route path="/calendar" component={Calendar} />
//       <Route path="/" component={Index} />
//       <Route path="*" component={BlankPage} />
//     </Switch>
//   );
  constructor(props) {
    super(props);

    this.childRoutes = (
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
              console.log('logout path: ', localStorage.isLogged)
              if (localStorage.isLogged) {
                logoutRequest()
                  .then(() => logoutSuccess(props.auth))
                  .then(() => {
                    console.log('store dispatch');
                    // dispatch(push('/login'));
                  });

                // return null;
              }

              return <Redirect to="/login" />;
            }}
          />
        </Switch>
      </>
    );
  }

  render() {
    const { dispatch, auth } = this.props;
    const getChildRoutes = this.childRoutes;

    // const childRoutes = (
    //   <>
    //     <Switch>
    //       <Route exact path="/" component={Dashboard} />
    //       <Route path="/window/:windowId/:docId" component={MasterWindowRoute} />
    //       <Route path="/window/:windowId" component={DocListRoute} />
    //       <Route path="/sitemap" component={NavigationTree} />
    //       <Route path="/board/:boardId" component={BoardRoute} />
    //       <Route path="/inbox" component={InboxAll} />

    //       <Route
    //         path="/logout"
    //         render={() => {
    //           console.log('logout path: ', localStorage.isLogged)
    //           if (localStorage.isLogged) {
    //             logoutRequest()
    //               .then(() => logoutSuccess(auth))
    //               .then(() => {
    //                 console.log('store dispatch');
    //                 // dispatch(push('/login'));
    //               });

    //             // return null;
    //           }

    //           return <Redirect to="/login" />;
    //         }}
    //       />
    //     </Switch>
    //   </>
    // );

    class PrivateRoute extends Component {
      shouldComponentUpdate(nextProps) {
        const { location } = this.props;
        const { location: nextLocation } = nextProps;

        // console.log('PrivateRoute location: ', location, nextLocation)

        if (_.isEqual(location, nextLocation)) {
          return false;
        }
        return true;
      }

      render() {
        const loggedIn = localStorage.isLogged;
        // const { dispatch, auth } = this.props;
      // const authRequired = (nextState, replace, callback) => {
      //   hasTutorial =
      //     nextState &&
      //     nextState.location &&
      //     nextState.location.query &&
      //     typeof nextState.location.query.tutorial !== 'undefined';
        console.log('PrivateRoute: ', this.props);

        let pathname = '/login';

        if (!localStorage.isLogged) {
          console.log('notlogged')
          localLoginRequest().then((resp) => {
            console.log('logindata: ', resp)
            if (resp.data) {
              dispatch(loginSuccess(auth));
              // callback(null, nextState.location.pathname);
            } else {
              console.log('nie ma logindata ')
              //redirect tells that there should be
              //step back in history after login
              // dispatch(push('/login?redirect=true'));
              pathname = '/login?redirect=true';
            }
          });
        } else {
          // if (hasTutorial) {
          //   dispatch(enableTutorial());
          // }
          console.log('PrivateRoute logged: ', this.props.location)

          if (this.props.location.pathname !== 'logout') {
            dispatch(clearNotifications());
            dispatch(loginSuccess(auth));

            // return null;
          } else {
            return null;
          }

          // callback();
        }
      // };

        return (
          <Route
            {...this.props}
            render={() =>
              loggedIn ? (
                <WrappedIndex childRoutes={getChildRoutes} />
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
    }

    const WrappedLoginRoute = withRouter(LoginRoute);
    const loggedIn = localStorage.isLogged;

    console.log('routes render loggedIN: ', loggedIn)

    return (
      <>
        <Switch>
          <Route exact path="/login">
            {loggedIn ? <Redirect to="/" /> : <WrappedLoginRoute auth={auth} />}
          </Route>
          <PrivateRoute path="/" />
        </Switch>
      </>
    );
  }
}

function propsAreEqual(prevProps, nextProps) {
  const { auth, dispatch } = prevProps;
  const { auth: nextAuth, dispatch: nextDispatch } = nextProps;

  console.log('PROPSAREEQUAL1')

  if (!dispatch || !nextDispatch) {
    return false;
  }

  if (!auth || !nextAuth || auth.id !== nextAuth.id) {
    return false;
  }

  console.log('PROPSAREEQUAL2')

  return true;
}

export default React.memo(Routes, propsAreEqual);
