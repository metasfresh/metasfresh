import React, { FunctionComponent, ReactElement } from 'react';
import { useLocation } from 'react-router-dom';
import { observer, inject } from 'mobx-react';

import { translate } from '../utils/translate';
import { RootInstance } from '../models/Store';

interface Props {
  store?: RootInstance;
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
            <a onClick={store.app.logOut} className="button is-success is-green-bg">
              {translate('Logout.caption')}
            </a>
          </div>
        );
      }

      return (
        <header className="p-4 navbar is-fixed-top is-flex is-align-items-center is-justify-content-space-around header">
          {fakeAligner}
          <div className="header-title">
            <h4 className="title is-4">{store.navigation.topViewName}</h4>
          </div>
          {link}
        </header>
      );
    }
  )
);

export default Header;
