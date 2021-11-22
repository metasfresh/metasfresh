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
    // Not ideal solution, but since WrappedComponents can be of different type and will have different
    // functionality within their `clickHandlers` we need them separate for each type. This is a bit overcomplicated way call them from the HOC
    // but does the job for now.
    proc(wrappedComponentInstance) {
      if (wrappedComponentInstance) {
        this.wrappedComponent = wrappedComponentInstance;
      }
    }

    handleClick = () => {
      const { wfProcessId, activityId, lineId } = this.props;
      const { dispatch } = this.props;

      const location = `/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`;
      dispatch(push(location));

      this.wrappedComponent.handleClick();
    };

    render() {
      const { lineId, isUserEditable } = this.props;
      const props = Object.assign({}, this.props, { ref: this.proc.bind(this) });

      return (
        <div className="buttons">
          <button
            key={lineId}
            className="button is-outlined complete-btn"
            disabled={!isUserEditable}
            onClick={this.handleClick}
          >
            <WrappedComponent {...props} />
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
