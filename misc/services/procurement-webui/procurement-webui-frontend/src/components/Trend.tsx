import React, { ReactElement } from 'react';
import classnames from 'classnames';
interface Props {
  trend: string;
}
const Trend: React.FunctionComponent<Props> = ({ trend }: Props): ReactElement => {
  return (
    <div>
      <i
        className={classnames('', {
          'fas fa-lg fa-arrow-up': trend === 'trend-up',
          'fas fa-lg fa-arrow-down': trend === 'trend-down',
          'fas fa-lg fa-arrow-right': trend === 'trend-even',
          'fas fa-lg fa-times': trend === 'trend-zero',
        })}
      ></i>
    </div>
  );
};

export default Trend;
