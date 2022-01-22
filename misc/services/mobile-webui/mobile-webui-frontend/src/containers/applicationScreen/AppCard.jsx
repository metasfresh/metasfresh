import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

import { gotoAppLaunchers } from '../../routes/launchers';
import { getApplicationStartFunction } from '../../apps';
import { connect } from 'react-redux';

class AppCard extends PureComponent {
  handleAppClick = () => {
    const { applicationId, dispatch } = this.props;

    const startApplicationFunc = getApplicationStartFunction(applicationId);
    if (startApplicationFunc) {
      dispatch(startApplicationFunc());
    } else {
      dispatch(gotoAppLaunchers(applicationId));
    }
  };

  getIconClassNames = () => {
    const { applicationId } = this.props;
    switch (applicationId) {
      case 'picking':
        return 'fas fa-box-open';
      case 'distribution':
        return 'fas fa-people-carry';
      case 'mfg':
        return 'fas fa-industry';
      case 'huManager':
        return 'fas fa-boxes';
      default:
        return '';
    }
  };

  render() {
    const { captionKey } = this.props;

    return (
      <button className="button is-outlined complete-btn is-fullwidth" onClick={this.handleAppClick}>
        <div className="full-size-btn">
          <div className="left-btn-side">
            <span className="icon">
              <i className={this.getIconClassNames()} />
            </span>
          </div>
          <div className="caption-btn is-left">
            <div className="rows">
              <div className="row is-full pl-5">{captionKey}</div>
            </div>
          </div>
        </div>
      </button>
    );
  }
}

AppCard.propTypes = {
  captionKey: PropTypes.string.isRequired,
  applicationId: PropTypes.string.isRequired,
  dispatch: PropTypes.func.isRequired,
};

export default connect(null, null)(AppCard);
