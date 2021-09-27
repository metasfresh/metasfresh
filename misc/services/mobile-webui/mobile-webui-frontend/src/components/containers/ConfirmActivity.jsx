import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { userConfirmation } from '../../api/confirmation';
import ConfirmButton from '../../components/ConfirmButton';

class ConfirmActivity extends PureComponent {
  render() {
    return (
      <div>
        <ConfirmButton onConfirmExec={userConfirmation} {...this.props} />
      </div>
    );
  }
}

ConfirmActivity.propTypes = {
  caption: PropTypes.string,
  componentProps: PropTypes.object,
};

export default ConfirmActivity;
