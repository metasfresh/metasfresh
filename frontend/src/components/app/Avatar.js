import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { getAvatar } from '../../api';
import defaultAvatar from '../../assets/images/default-avatar.png';

class Avatar extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { size, className, id, title } = this.props;
    return (
      <img
        src={id ? getAvatar(id) : defaultAvatar}
        title={title}
        className={
          'avatar img-fluid rounded-circle ' +
          (size ? 'avatar-' + size + ' ' : '') +
          (className ? className : '')
        }
      />
    );
  }
}

Avatar.propTypes = {
  size: PropTypes.string,
  className: PropTypes.string,
  id: PropTypes.string,
  title: PropTypes.string,
};

export default Avatar;
