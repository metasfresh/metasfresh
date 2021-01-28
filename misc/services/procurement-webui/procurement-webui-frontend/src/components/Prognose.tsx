import React, { ReactElement } from 'react';
import { translate } from '../utils/translate';
import classnames from 'classnames';
interface Props {
  productId: string;
  nextWeek: string;
  nextWeekCaption: string;
  trend?: string;
  currentWeek: string;
  prognoseChange?: (productId: { productId: string; trend: string; week: string }) => void;
}
const Prognose: React.FunctionComponent<Props> = ({
  productId,
  prognoseChange,
  nextWeekCaption,
  trend,
  currentWeek,
}: Props): ReactElement => {
  let prognoseHeader = translate('WeeklyDetailedReportingView.toolbar.caption');
  prognoseHeader = prognoseHeader.replace(`{0}`, nextWeekCaption);

  const formatPostObject = (trend: string) => {
    return {
      productId,
      trend,
      week: currentWeek,
    };
  };

  return (
    <div>
      <div className="container prognose-box pb-1 pl-3 pr-3">
        <div className="columns is-mobile">
          <div className="column is-12 has-text-centered pb-1">{prognoseHeader}</div>
        </div>
        <div className="columns is-mobile has-text-centered pb-3">
          <div
            onClick={() => prognoseChange(formatPostObject('trend-up'))}
            className={classnames('column is-mobile-4', {
              'up-active': trend === 'trend-up',
              up: trend !== 'trend-up',
            })}
          >
            <i className="fas fa-arrow-up fa-lg"></i>
          </div>
          <div
            onClick={() => prognoseChange(formatPostObject('trend-down'))}
            className={classnames('column is-mobile-4', {
              'down-active': trend === 'trend-down',
              down: trend !== 'trend-down',
            })}
          >
            <i className="fas fa-arrow-down fa-lg"></i>
          </div>
          <div
            onClick={() => prognoseChange(formatPostObject('trend-even'))}
            className={classnames('column is-mobile-4', {
              'right-active': trend === 'trend-even',
              right: trend !== 'trend-even',
            })}
          >
            <i className="fas fa-arrow-right fa-lg"></i>
          </div>
          <div
            onClick={() => prognoseChange(formatPostObject('trend-zero'))}
            className={classnames('column is-mobile-4', {
              'disabled-active': trend === 'trend-zero',
              disabled: trend !== 'trend-zero',
            })}
          >
            <i className="fas fa-times fa-lg"></i>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Prognose;
