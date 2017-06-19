import React, { Component } from 'react';
import defaultAvatar from '../../assets/images/default-avatar.png';

class Avatar extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {src, size, className} = this.props;
        return (
            <img
                src={src ? src : defaultAvatar}
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
