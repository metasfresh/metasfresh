import React, { ReactElement } from 'react';
import classnames from 'classnames';
interface Props {
  trend: string;
}
class Trend extends React.Component<Props> {
  render(): ReactElement {
    const { trend } = this.props;
    return (
      <div>
        <i
          className={classnames('', {
            'fas fa-lg fa-arrow-up up': trend === 'trend-up',
            'fas fa-lg fa-arrow-down down': trend === 'trend-down',
            'fas fa-lg fa-arrow-right right': trend === 'trend-even',
            'fas fa-lg fa-times disabled': trend === 'trend-zero',
          })}
        ></i>
      </div>
    );
  }
}

export default Trend;
