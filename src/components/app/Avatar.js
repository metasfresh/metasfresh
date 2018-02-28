import React, { Component } from 'react';

import { getAvatar } from '../../actions/AppActions';
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

export default Avatar;
