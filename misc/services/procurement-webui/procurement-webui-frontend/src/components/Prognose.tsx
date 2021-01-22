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
      <div className="container prognose-box">
        <div className="columns is-mobile">
          <div className="column is-12 has-text-centered">{prognoseHeader}</div>
        </div>
      </div>
    </div>
  );
};

export default Prognose;
