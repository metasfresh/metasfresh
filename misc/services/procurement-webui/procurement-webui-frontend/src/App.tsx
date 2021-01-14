/* eslint-disable */
import React, { Component } from 'react';
import { 
  Route, 
  Switch, 
  BrowserRouter,
} from 'react-router-dom';
import { observer, inject } from 'mobx-react';

import { RootInstance } from './models/Store';

import Header from './components/Header';
import Weekly from './components/Weekly';
import Daily from './components/Daily';
import Error404 from './components/Error404';
import Login from './components/Login';
import BottomNav from './components/BottomNav';
import Info from './components/Info';
import RfQ from './components/RfQ';
import ProductAdd from './components/ProductAdd';

interface AppProps {
  store?: RootInstance;
}

const routes = [
  { path: '/', name: 'Daily Reporting', Component: Daily },
  { path: '/daily/:productId', name: 'Daily Reporting Details', Component: Daily },
  { path: '/weekly', name: 'Weekly Reporting', Component: Weekly },
  { path: '/products', name: 'Products', Component: ProductAdd },
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
  // shouldComponentUpdate(nextProps) {
  //   const { location } = this.props;
  //   const { location: nextLocation } = nextProps;

  //   if (_.isEqual(location, nextLocation)) {
  //     return false;
  //   }
  //   return true;
  // }

  render() {
    return childRoutes;
  }
}

@inject('store')
@observer
class App extends React.Component<AppProps> {
  componentDidMount() {
    const { store } = this.props;

    store.getUserSession();
  }

  render() {
    return(
      <BrowserRouter>
        <>
          <Switch>
            <Route
              path="/login"
              component={({ location }) => (
                <Login />
              )}
            />
            <Route
              path="/forgottenPassword"
              component={({ location }) => (
                <Login splat={location.pathname.replace('/', '')} />
              )}
            />
            <Route
              path="/resetPassword"
              component={({ location }) => (
                <Login splat={location.pathname.replace('/', '')} />
              )}
            />
            <Route path="/" component={Index} />
          </Switch>
        </>
      </BrowserRouter>
    );
  }
};

export default App;
