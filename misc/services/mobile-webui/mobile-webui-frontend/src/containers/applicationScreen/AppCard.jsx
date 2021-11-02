import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import { setActiveApplication } from '../../actions/ApplicationsActions';
import { redirectToAppLaunchers } from '../../actions/RoutingActions';

class AppCard extends PureComponent {
  handleAppClick = () => {
    const { appId, captionKey, setActiveApplication, redirectToAppLaunchers } = this.props;

    setActiveApplication({ id: appId, caption: captionKey });
    redirectToAppLaunchers(appId);
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
  appId: PropTypes.string.isRequired,
  redirectToAppLaunchers: PropTypes.func.isRequired,
  setActiveApplication: PropTypes.func.isRequired,
};

export default connect(null, { redirectToAppLaunchers, setActiveApplication })(AppCard);
