import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import { withRouter } from 'react-router';

const mapStateToProps = ({ applications }, { location }) => ({
  appId: applications.activeApplication ? applications.activeApplication.id : null,
  location,
});

function LineButton(WrappedComponent) {
  class Wrapped extends PureComponent {
    handleClick = () => {
      const { wfProcessId, activityId, lineId } = this.props;
      const { dispatch } = this.props;

      const location = `/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`;
      dispatch(push(location));
    };

    render() {
      const { lineId, isUserEditable } = this.props;

      return (
        <div className="buttons">
          <button
            key={lineId}
            className="button is-outlined complete-btn"
            disabled={!isUserEditable}
            onClick={this.handleClick}
          >
            <WrappedComponent {...this.props} onHandleClick={this.handleClick} />
          </button>
        </div>
      );
    }
  }

  Wrapped.propTypes = {
    //
    // Props
    wfProcessId: PropTypes.string.isRequired,
    activityId: PropTypes.string.isRequired,
    lineId: PropTypes.string.isRequired,
    isUserEditable: PropTypes.bool.isRequired,
    //
    // Actions
    dispatch: PropTypes.func.isRequired,
  };

  return withRouter(connect(mapStateToProps)(Wrapped));
}

export default LineButton;
