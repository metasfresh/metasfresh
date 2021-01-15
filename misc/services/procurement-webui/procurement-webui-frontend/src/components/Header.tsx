import React, { FunctionComponent, ReactElement } from 'react';
import { useLocation, useHistory } from 'react-router-dom';
import { observer, inject } from 'mobx-react';

import { translate } from '../utils/translate';
import { RootInstance } from '../models/Store';

interface Props {
  store?: RootInstance;
}

const Header: FunctionComponent<Props> = inject('store')(
  observer(
    ({ store }): ReactElement => {
      const history = useHistory();
      const location = useLocation();
      let fakeAligner = null;
      let link = null;

      const logOut = () => {
        store.app.logOut().then(() => {
          history.push('/login');
        });
      };

      if (location.pathname === '/') {
        fakeAligner = <div className="header-aligner" />;
        link = (
          <div className="logout-link">
            <a onClick={logOut} className="button is-success">
              {translate('Logout.caption')}
            </a>
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
