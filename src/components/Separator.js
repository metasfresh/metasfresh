import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

/**
 * __Separator for element groups__.
 * The separator component allows to create collapsible sections in the window component.
 * The separator can  have a title. It is used as layout and grouping element in WebUI.
 * @param {object} props Component props
 * @param {string} props.title Separator title
 * @param {bool} props.collapsible Separator collapsible
 * @param {bool} props.sectionCollapsed Separator collapsed
 * @param {number} props.idx Index parameter for onClick Handler
 * @param {function} props.onClick Callback function for onClick Handler
 * @param {string} props.tabId Tab ID for onClick Handler
 * @category Components
 */
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
