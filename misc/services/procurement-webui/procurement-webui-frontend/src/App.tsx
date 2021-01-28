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
import RfQDetails from './components/RfQDetails';
import RfQList from './components/RfQList';
import ProductScreen from './components/ProductScreen';
import ProductAdd from './components/ProductAdd';
import PasswordRecovery from './components/PasswordRecovery';
import ProductWeeklyScreen from './components/ProductWeeklyScreen';
import ProductWeeklyEdit from './components/ProductWeeklyEdit';
import RfQPriceEdit from './components/RfQPriceEdit';
import RfQDailyEdit from './components/RfQDailyEdit';

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
  { path: '/weekly/edit/:productId/:targetDay?/:targetDayCaption?', name: 'Weekly Product Edit', Component: ProductWeeklyEdit },
  { path: '/products', name: 'Products', Component: ProductAdd },
  { path: '/products/:productId/:targetDay?/:targetDayCaption?', name: 'Product', Component: ProductScreen },
  { path: '/info', name: 'Info/News', Component: Info },
  { path: '/rfq', name: 'RfQ', Component: RfQList },
  { path: '/rfq/:quotationId', name: 'RfQ Details', Component: RfQDetails },
  { path: '/rfq/price/:rfqId', name: 'RfQ Price Edit', Component: RfQPriceEdit },
  { path: '/rfq/:rfqId/dailyQty/:targetDate', name: 'RfQ Daily Quantity Edit', Component: RfQDailyEdit },
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
      this.installPrompt = e;
      // See if the app is already installed, in that case, do nothing
      if (window.matchMedia && window.matchMedia('(display-mode: standalone)').matches) {
        // Already installed
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
