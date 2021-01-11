import React, { FunctionComponent, ReactElement } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { observer, inject } from 'mobx-react';

import { RootInstance } from '../models/Store';

interface Props {
  store?: RootInstance;
  children?: any;
}

const Header: FunctionComponent<Props> = inject('store')(
  observer(
    ({ store }): ReactElement => {
      const location = useLocation();
      let fakeAligner = null;
      let link = null;

      if (location.pathname === '/') {
        fakeAligner = <div className="header-aligner" />;
        link = (
          <div className="logout-link">
            <Link
              to={{
                pathname: '/logout',
                state: { prev: true },
              }}
              className="button is-success"
            >
              Logout
            </Link>
          </div>
        );
      }

      return (
        <header className="p-4 navbar is-fixed-top is-flex is-align-items-center is-justify-content-space-around header">
          {fakeAligner}
          <div className="header-title">
            <h4 className="title is-4">{store.navigation.viewName}</h4>
          </div>
          {link}
        </header>
      );
    }
  )
);

export default Header;
