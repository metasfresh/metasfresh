import React, { Component } from 'react';
import { 
  Route, 
  Switch, 
  BrowserRouter,
} from 'react-router-dom';
import { CSSTransition } from 'react-transition-group';

import Nav from './components/Nav';
import Home from './components/Home';
import About from './components/About';
import Error404 from './components/Error404';
import Login from './components/Login';

const routes = [
  { path: '/', name: 'Home', Component: Home },
  { path: '/view2', name: 'About', Component: About },
];

const childRoutes = (
  <main>
    <Nav />
    <Switch>
      {routes.map(({ path, Component }) => (
        <Route key={path} exact path={path}>
          {({ match }) => (
            <CSSTransition
              in={match != null}
              classNames="view"
              timeout={300}
              unmountOnExit
            >
              <Component />
            </CSSTransition>
          )}
        </Route>
      ))}
      <Route path="*" component={Error404} />
    </Switch>
  </main>
);

class Index extends Component {

  installApp = async ()=>{
    if(!this.installPrompt) return false;
    this.installPrompt.prompt();
    let outcome = await this.installPrompt.userChoice;
    if(outcome.outcome === 'accepted'){
      console.log('App is already installed')
    }
    else{
      console.log('App is not installed');
    }
    // Remove the event reference
    this.installPrompt = null;
    // Hide the button
    this.setState({ installButton: false })
  }

  installPrompt = null;

  componentDidMount() {
     window.addEventListener('beforeinstallprompt', (e) =>{
      e.preventDefault();
      console.log('Install Prompt fired');
      this.installPrompt = e;
      // See if the app is already installed, in that case, do nothing
      if((window.matchMedia && window.matchMedia('(display-mode: standalone)').matches)){
        console.log('Already installed')
        return false;
      }
      // Set the state variable to make button visible
      this.setState({ installButton: true })
    })
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

const App = () => (
  <BrowserRouter>
    <div className="app">
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
    </div>
  </BrowserRouter>
);

export default App;
