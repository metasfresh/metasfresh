import React, { cloneElement } from 'react';
import Hammer from 'rc-hammerjs';
import PropTypes from 'prop-types';
import currentDevice from 'current-device';

const WithMobileDoubleTap = ({ children }) => {
  if (currentDevice.type === 'mobile') {
    const { onDoubleClick, onClick } = children.props;
    return (
      <Hammer
        options={{
          recognizers: {
            tap: {
              requireFailure: 'doubletap',
            },
          },
          touchAction: 'pan-x pan-y',
        }}
        onTap={(event) => {
          if (event.tapCount === 2) {
            onDoubleClick(event.srcEvent);
          } else {
            onClick(event.srcEvent);
          }
        }}
      >
        {cloneElement(children, {
          onDoubleClick: () => {},
          onClick: () => {},
        })}
      </Hammer>
    );
  }

  return children;
};

WithMobileDoubleTap.propTypes = {
  children: PropTypes.element.isRequired,
};

export default WithMobileDoubleTap;
