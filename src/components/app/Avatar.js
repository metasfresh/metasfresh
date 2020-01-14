import React, { PureComponent } from 'react';

import { getAvatar } from '../../api';
import defaultAvatar from '../../assets/images/default-avatar.png';

export default class Avatar extends PureComponent {
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
