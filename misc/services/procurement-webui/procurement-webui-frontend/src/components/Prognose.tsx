import React, { FunctionComponent, ReactElement } from 'react';
import { translate } from '../utils/translate';

interface Props {
  nextWeek: string;
}
const Prognose: FunctionComponent<Props> = ({ nextWeek }: Props): ReactElement => {
  let prognoseHeader = translate('WeeklyDetailedReportingView.toolbar.caption');
  prognoseHeader = prognoseHeader.replace(`{0}`, nextWeek);
  return (
    <div>
      <div className="container prognose-box pl-3 pr-3">
        <div className="columns is-mobile">
          <div className="column is-12 has-text-centered pb-0">{prognoseHeader}</div>
        </div>
        <div className="columns is-mobile has-text-centered pb-3">
          <div className="column is-mobile-4 up">
            <i className="fas fa-arrow-up fa-lg"></i>
          </div>
          <div className="column is-mobile-4 down">
            <i className="fas fa-arrow-down fa-lg"></i>
          </div>
          <div className="column is-mobile-4 right">
            <i className="fas fa-arrow-right fa-lg"></i>
          </div>
          <div className="column is-mobile-4 disabled">
            <i className="fas fa-times fa-lg"></i>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Prognose;
