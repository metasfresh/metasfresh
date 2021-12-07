import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

import { setActiveApplication } from '../../actions/ApplicationsActions';
import { gotoAppLaunchers } from '../../routes/launchers';
import { getApplicationStartFunction } from '../../apps';
import { connect } from 'react-redux';

class AppCard extends PureComponent {
  handleAppClick = () => {
    const { applicationId, captionKey, dispatch } = this.props;

    dispatch(setActiveApplication({ id: applicationId, caption: captionKey }));

    const startApplicationFunc = getApplicationStartFunction(applicationId);
    if (startApplicationFunc) {
      dispatch(startApplicationFunc());
    } else {
      dispatch(gotoAppLaunchers(applicationId));
    }
  };

  render() {
    const { captionKey } = this.props;

    return (
      <div className="column py-0">
        <div className="card m-5 pt-5" onClick={this.handleAppClick}>
          <div className="card-content has-text-centered">
            <p className="is-size-4">{captionKey}</p>
          </div>
        </div>
      </div>
    );
  }
}

AppCard.propTypes = {
  captionKey: PropTypes.string.isRequired,
  applicationId: PropTypes.string.isRequired,
  dispatch: PropTypes.func.isRequired,
};

export default connect(null, null)(AppCard);
