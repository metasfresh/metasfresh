import React from 'react';
import classnames from 'classnames';

import { INITIALLY_OPENED } from '../constants/Constants';

const Separator = props => {
  const {
    title,
    collapsible,
    closableMode,
    sectionCollapsed,
    idx,
    onClick,
  } = props;

  return (
    <div className="separator col-12">
      <span className="separator-title">{title} - {sectionCollapsed}</span>
      {collapsible && (
        <div className="panel-size-button">
          <button
            className={classnames(
              'btn btn-meta-outline-secondary btn-sm ignore-react-onclickoutside'
            )}
            onClick={() => onClick(idx)}
          >
            <i
              className={classnames('icon meta-icon-down-1', {
                'meta-icon-flip-horizontally':
                  !sectionCollapsed || closableMode === INITIALLY_OPENED,
              })}
            />
          </button>
        </div>
      )}
    </div>
  );
};

export default Separator;
