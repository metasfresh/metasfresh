import React from 'react';
import PropTypes from 'prop-types';

const ApplicationButton = ({ caption, iconClassNames, onClick }) => {
  return (
    <button className="button is-outlined complete-btn is-fullwidth" onClick={onClick}>
      <div className="full-size-btn">
        <div className="left-btn-side">
          <i className={iconClassNames} />
        </div>
        <div className="caption-btn">
          <div className="rows">
            <div className="row pl-5">{caption}</div>
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
