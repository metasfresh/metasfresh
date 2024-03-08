import React from 'react';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';
import { connect, useDispatch } from 'react-redux';

import { appLaunchersLocation } from '../../routes/launchers';
import { getApplicationStartFunction } from '../../apps';

const ApplicationButton = ({ applicationId, caption, iconClassNames }) => {
  const dispatch = useDispatch();
  const history = useHistory();
  const handleAppClick = () => {
    const startApplicationFunc = getApplicationStartFunction(applicationId);
    if (startApplicationFunc) {
      dispatch(startApplicationFunc());
    } else {
      history.push(appLaunchersLocation({ applicationId }));
    }
  };

  return (
    <button className="button is-outlined complete-btn is-fullwidth" onClick={handleAppClick}>
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
  applicationId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  iconClassNames: PropTypes.string,
};

export default connect(null, null)(ApplicationButton);
