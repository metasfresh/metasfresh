/* eslint-disable */
import React, { Component } from 'react';
import { 
  Route, 
  Switch, 
  BrowserRouter,
  Redirect,
  withRouter,
} from 'react-router-dom';
import { observer, inject } from 'mobx-react';
import qs from 'qs';

import { RootInstance } from './models/Store';

import Header from './components/Header';
import Weekly from './components/Weekly';
import Daily from './components/Daily';
import Login from './components/Login';
import BottomNav from './components/BottomNav';
import Info from './components/Info';
import RfQ from './components/RfQ';
import ProductScreen from './components/ProductScreen';
import ProductAdd from './components/ProductAdd';
import PasswordRecovery from './components/PasswordRecovery';
import ProductWeeklyScreen from './components/ProductWeeklyScreen';

interface IProps {
  store?: RootInstance;
}

interface IState {
  loggedIn: boolean;
}

const routes = [
  { path: '/', name: 'Daily Reporting', Component: Daily },
  { path: '/daily/:productId', name: 'Daily Reporting Details', Component: Daily },
  { path: '/weekly', name: 'Weekly Reporting', Component: Weekly },
  { path: '/weekly/:productId', name: 'Weekly Product', Component: ProductWeeklyScreen },
  { path: '/products', name: 'Products', Component: ProductAdd },
  { path: '/products/:productId/:targetDay?/:targetDayCaption?', name: 'Product', Component: ProductScreen },
  { path: '/info', name: 'Info/News', Component: Info },
  { path: '/quotations', name: 'RfQ', Component: RfQ },
  { path: '/quotations/:quotationId', name: 'RfQ Details', Component: RfQ },
];

const childRoutes = (
  <div className="view fullsize">
    <Switch>
    {routes.map(({ path, Component }) => (
      <Route key={path} exact path={path}>
        <Header />
        <Component />
        <BottomNav />
      </Route>
    ))}
    </Switch> 
  </div>
);

class Index extends Component {
  installPrompt = null;

  constructor(props) {
    super(props);
  }

  componentDidMount() {
    window.addEventListener('beforeinstallprompt', (e: { preventDefault: () => void }) => {
      // e.preventDefault(); - this is going to disable the prompt if uncommented !
      console.log('Install Prompt fired');
      this.installPrompt = e;
      // See if the app is already installed, in that case, do nothing
      if (window.matchMedia && window.matchMedia('(display-mode: standalone)').matches) {
        console.log('Already installed');
        return false;
      }
    });
  }
  render() {
    return childRoutes;
  }
}

const WrappedIndex = withRouter(Index);

function PrivateRoute({ loggedIn, ...rest }) {
  return (
    <Route
      {...rest}
      render={() =>
        loggedIn ? (
            <WrappedIndex />
        ) : (
          <Redirect
            to={{
              pathname: "/login",
            }}
          />
        )
      }
    />
  );
}

// TODO: We should try making this a PureComponent
@inject('store')
@observer
class App extends React.Component<IProps, IState> {
  render() {
    const { store } = this.props;
    const { loggedIn } = store.app;

    return(
      <BrowserRouter>
        <>
          <Switch>
            <Route exact path="/login">
              {loggedIn ? <Redirect to="/" /> : <Login />}
            </Route>
            <Route
              path="/forgottenPassword"
              component={({ location }) => (
                <PasswordRecovery splat={location.pathname.replace('/', '')} />
              )}
            />
            <Route
              path="/resetPassword"
              component={({ location }) => {
                const query = qs.parse(location.search, { ignoreQueryPrefix: true });

                return (
                  <PasswordRecovery
                    splat={location.pathname.replace('/', '')}
                    token={query.token}
                  />
                );
              }}
            />
            <PrivateRoute path="/" loggedIn={loggedIn} />
          </Switch>
        </>
      </BrowserRouter>
    );
  }
};

export default App;
