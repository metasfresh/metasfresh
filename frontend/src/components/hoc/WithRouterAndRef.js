import React, { forwardRef } from 'react';
import { withRouter } from 'react-router';

const withRouterAndRef = (Wrapped) => {
  const WithRouter = withRouter(({ forwardRef, ...otherProps }) => (
    <Wrapped ref={forwardRef} {...otherProps} />
  ));

  const WithRouterAndRef = forwardRef((props, ref) => (
    <WithRouter {...props} forwardRef={ref} />
  ));

  const name = Wrapped.displayName || Wrapped.name;
  WithRouterAndRef.displayName = `withRouterAndRef(${name})`;

  return WithRouterAndRef;
};

const withForwardedRef = (Wrapped) => {
  const handle = (props, forwardedRef) => {
    return <Wrapped {...props} forwardedRef={forwardedRef} />;
  };

  const name = Wrapped.displayName || Wrapped.name;
  handle.displayName = `withForwardedRef(${name})`;

  return forwardRef(handle);
};

export { withRouterAndRef, withForwardedRef };
