import React, { PureComponent } from 'react';
import { connect } from 'react-redux';

const mapStateToProps = ({ applications }) => ({
  appId: applications.activeApplication ? applications.activeApplication.id : null,
});

function LineButton(WrappedComponent) {
  class Wrapped extends PureComponent {
    render() {
      return (
        <div className="buttons">
          <WrappedComponent {...this.props} />
        </div>
      );
    }
  }

  return connect(mapStateToProps)(Wrapped);
}

export default LineButton;
