import React from 'react';
import PropTypes from 'prop-types';
import { getAvatar } from '../../api';
import defaultAvatar from '../../assets/images/default-avatar.png';

const Avatar = ({ size, className, id, title }) => {
  return (
    <img
      src={id ? getAvatar(id) : defaultAvatar}
      title={title}
      className={
        'avatar img-fluid rounded-circle ' +
        (size ? 'avatar-' + size + ' ' : '') +
        (className ? className : '')
      }
      alt="avatar"
    />
  );
};
Avatar.propTypes = {
  size: PropTypes.string,
  className: PropTypes.string,
  id: PropTypes.string,
  title: PropTypes.string,
};

export default Avatar;
