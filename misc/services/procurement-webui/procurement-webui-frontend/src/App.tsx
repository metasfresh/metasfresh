import React from 'react';
import { 
  Route, 
  Switch, 
  BrowserRouter,
} from 'react-router-dom';
import { 
  CSSTransition, 
  TransitionGroup 
} from 'react-transition-group';

import Nav from './components/Nav';
import Home from './components/Home';
import About from './components/About';
import Error404 from './components/Error404';

const App = () => (
  <BrowserRouter>
    <div>
      <Nav />
      <main>
        <Route
          render={({ location }) => {
            const { pathname } = location;
            return (
              <TransitionGroup>
                <CSSTransition 
                  key={pathname}
                  classNames="view"
                  timeout={{
                    enter: 200,
                    exit: 200,
                  }}
                >
                  <Route
                    location={location}
                    render={() => (
                      <Switch>
                        <Route
                          exact
                          path="/"
                          component={Home}
                        />
                        <Route
                          path="/view2"
                          component={About}
                        />
                        <Route
                          component={Error404}
                        />
                      </Switch>
                    )}
                  />
                </CSSTransition>
              </TransitionGroup>
            );
          }}
        />
      </main>
    </div>
  </BrowserRouter>
);

export default App;
