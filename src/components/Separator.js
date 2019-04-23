import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

const Separator = props => {
  const { title, collapsible, sectionCollapsed, idx, onClick, tabId } = props;

  return (
    <div className="separator col-12">
      <span
        className={classnames('separator-title', {
          collapsible,
        })}
        onClick={() => onClick(idx, tabId)}
      >
        {title}
      </span>
      {collapsible && (
        <div className="panel-size-button">
          <button
            className={classnames(
              'btn btn-meta-outline-secondary btn-sm ignore-react-onclickoutside'
            )}
            onClick={() => onClick(idx, tabId)}
          >
            <i
              className={classnames('icon meta-icon-down-1', {
                'meta-icon-flip-horizontally': !sectionCollapsed,
              })}
            />
          </button>
        </div>
      )}
    </div>
  );
};

Separator.propTypes = {
  title: PropTypes.string,
  collapsible: PropTypes.bool,
  sectionCollapsed: PropTypes.bool,
  idx: PropTypes.number,
  tabId: PropTypes.string,
  onClick: PropTypes.func,
};

export default Separator;
