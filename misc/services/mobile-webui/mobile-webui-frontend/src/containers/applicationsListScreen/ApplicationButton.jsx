import React from 'react';
import PropTypes from 'prop-types';

const ApplicationButton = ({ caption, iconClassNames, onClick }) => {
  return (
    <button className="button is-outlined complete-btn is-fullwidth" onClick={onClick}>
      <div className="full-size-btn">
        <div className="left-btn-side">
          <span className="icon">
            <i className={iconClassNames} />
          </span>
        </div>
        <div className="caption-btn is-left">
          <div className="rows">
            <div className="row is-full pl-5">{caption}</div>
          </div>
        </div>
      </div>
    </button>
  );
};

ApplicationButton.propTypes = {
  caption: PropTypes.string.isRequired,
  iconClassNames: PropTypes.string,
  onClick: PropTypes.func.isRequired,
};

export default ApplicationButton;
