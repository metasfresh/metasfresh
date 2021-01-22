import React, { FunctionComponent, ReactElement } from 'react';
import { Link, useLocation, useHistory } from 'react-router-dom';
import { observer, inject } from 'mobx-react';
import classnames from 'classnames';

import { confirmDataEntry } from '../api';
import { translate } from '../utils/translate';
import { RootInstance } from '../models/Store';

interface ForcedState {
  path: string;
  text?: string;
}
interface Props {
  store?: RootInstance;
  location?: LocationState;
  forcedState?: ForcedState;
}

interface LocationState {
  path?: string;
  text?: string;
}

const BottomNav: FunctionComponent<Props> = inject('store')(
  observer(
    ({ store, forcedState }): ReactElement => {
      const history = useHistory();
      const location = useLocation<LocationState>();
      const { navigation, app } = store;
      const { countUnconfirmed } = app;
      const { pathname } = location;
      let links = null;
      let classes = '';
      const text = forcedState ? forcedState.text : navigation.getLastView();

      if (['login', 'password'].indexOf(pathname) >= 0) {
        return null;
      }

      if (pathname === '/') {
        classes = 'p0 icons-bottom-nav ';
        links = [
          <Link
            to={{ pathname: '/weekly' }}
            className="link is-flex is-flex-direction-column is-justify-content-center"
            key="0"
          >
            <i className="far fa-calendar-alt" />
            <span className="link-text">{translate('DailyReportingView.weekViewButton')}</span>
          </Link>,
          <Link
            to={{ pathname: '/products' }}
            className="link is-flex is-flex-direction-column is-justify-content-center"
            key="1"
          >
            <i className="fas fa-plus" />
            <span className="link-text">{translate('DailyReportingView.addProductButton')}</span>
          </Link>,
          <a
            className="link is-flex is-flex-direction-column is-justify-content-center is-relative"
            key="2"
            onClick={() => {
              confirmDataEntry().then(() => {
                store.fetchDailyReport(store.app.currentDay);
                store.app.getUserSession();
              });
            }}
          >
            <i className="fas fa-check" />
            <span className="link-text">{translate('DailyReportingView.sendButton')}</span>
            {countUnconfirmed ? <span className="unconfirmed-count">{countUnconfirmed}</span> : null}
          </a>,
          <Link
            to={{ pathname: '/info' }}
            className="link is-flex is-flex-direction-column is-justify-content-center"
            key="3"
          >
            <i className="fas fa-info" />
            <span className="link-text">{translate('InfoMessageView.caption.short')}</span>
          </Link>,
          <Link
            to={{ pathname: '/quotations' }}
            className="link is-flex is-flex-direction-column is-justify-content-center"
            key="4"
          >
            <i className="far fa-money-bill-alt" />
            <span className="link-text">{translate('RfQView.caption')}</span>
          </Link>,
        ];
      } else {
        classes = 'p4 back-bottom-nav';
        links = (
          <a
            className="link is-flex is-flex-direction-column is-justify-content-center is-relative"
            key="2"
            onClick={() => {
              navigation.removeViewFromHistory();
              history.goBack();
            }}
          >
            <i className="fas fa-chevron-left" />
            <span className="pl-3 is-size-4">{text}</span>
          </a>
        );
      }

      return <footer className={classnames('navbar is-flex is-fixed-bottom', classes)}>{links}</footer>;
    }
  )
);
export default BottomNav;
