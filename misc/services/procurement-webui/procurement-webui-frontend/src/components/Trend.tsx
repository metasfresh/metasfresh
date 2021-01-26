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
          'fas fa-lg fa-arrow-up up': trend === 'trend-up',
          'fas fa-lg fa-arrow-down down': trend === 'trend-down',
          'fas fa-lg fa-arrow-right right': trend === 'trend-even',
          'fas fa-lg fa-times disabled': trend === 'trend-zero',
        })}
      />
    </div>
  );
};

export default Trend;
