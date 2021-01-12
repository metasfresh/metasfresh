import React, { FunctionComponent, ReactElement } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { observer, inject } from 'mobx-react';
import classnames from 'classnames';

import { RootInstance } from '../models/Store';

interface Props {
  store?: RootInstance;
  location?: any;
}

interface LocationState {
  path?: string;
  text?: string;
}

const LinkComponent: FunctionComponent = (props): ReactElement => {
  return <a {...props}>{props.children}</a>;
};

const BottomNav: FunctionComponent<Props> = inject('store')(
  observer(
    ({ store }): ReactElement => {
      const location = useLocation<LocationState>();
      const { navigation } = store;
      const { state, pathname } = location;
      let links = null;
      let classes = '';
      const path = state ? state.path : null;
      const text = state ? state.text : null;
      const navigateBackObject = {
        path: pathname,
        text: navigation.viewName,
      };

      if (['login', 'password'].indexOf(pathname) >= 0) {
        return null;
      }

      if (path) {
        classes = 'p4 back-bottom-nav';
        links = (
          <Link
            to={{
              pathname: path,
            }}
            className="link"
          >
            <i className="fas fa-chevron-left" />
            {text}
          </Link>
        );
      } else {
        classes = 'p0 icons-bottom-nav ';
        links = [
          <Link
            to={{
              pathname: '/weekly',
              state: navigateBackObject,
            }}
            className="link"
            key="0"
          >
            <i className="far fa-calendar-alt" />
          </Link>,
          <Link
            to={{
              pathname: '/weekly',
              state: navigateBackObject,
            }}
            className="link"
            key="1"
          >
            <i className="fas fa-plus" />
          </Link>,
          <a className="link" key="2">
            <i className="fas fa-check" />
          </a>,
          <Link
            to={{
              pathname: '/info',
              state: navigateBackObject,
            }}
            component={LinkComponent}
            className="link"
            key="3"
          >
            <i className="fas fa-info" />
          </Link>,
          <Link
            to={{
              pathname: '/quotations',
              state: navigateBackObject,
            }}
            className="link"
            key="4"
          >
            <i className="far fa-money-bill-alt" />
          </Link>,
        ];
      }

      return <footer className={classnames('navbar is-flex is-fixed-bottom', classes)}>{links}</footer>;
    }
  )
);
export default BottomNav;
