import React, { forwardRef } from 'react';
import { withRouter } from 'react-router-dom';

/**
 * @method withRouterAndRef
 * @summary HOC to pass ref over the parent to a child component and wrap it
 * in a `withRouter` HOC
 */
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

/**
 * @method withForwardedRef
 * @summary HOC to pass ref over the parent to a child component
 */
const withForwardedRef = (Wrapped) => {
  const handle = (props, forwardedRef) => {
    return <Wrapped {...props} forwardedRef={forwardedRef} />;
  };

  const name = Wrapped.displayName || Wrapped.name;
  handle.displayName = `withForwardedRef(${name})`;

  return forwardRef(handle);
};

export { withRouterAndRef, withForwardedRef };
