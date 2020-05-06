import * as React from 'react';
import Hammer from 'react-hammerjs';
import PropTypes from 'prop-types';

const isMobileDevice = !!(
  navigator.userAgent.match(/Android/i) ||
  navigator.userAgent.match(/webOS/i) ||
  navigator.userAgent.match(/iPhone/i) ||
  navigator.userAgent.match(/iPad/i) ||
  navigator.userAgent.match(/iPod/i) ||
  navigator.userAgent.match(/BlackBerry/i) ||
  navigator.userAgent.match(/Windows Phone/i)
);

export class WithMobileDoubleTap extends React.Component {
  render() {
    const { children } = this.props;
    if (isMobileDevice) {
      const { onDoubleClick, onClick } = children.props;
      return (
        <Hammer
          options={{
            recognizers: {
              tap: {
                requireFailure: 'doubletap',
              },
            },
          }}
          onTap={event => {
            if (event.tapCount === 2) {
              onDoubleClick(event.srcEvent);
            } else {
              onClick(event.srcEvent);
            }
          }}
        >
          {React.cloneElement(children, {
            onDoubleClick: undefined,
            onClick: undefined,
          })}
        </Hammer>
      );
    }
    return children;
  }
}

WithMobileDoubleTap.propTypes = {
  children: PropTypes.element.isRequired,
};
