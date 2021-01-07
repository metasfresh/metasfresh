// import React from 'react';
import React, { FunctionComponent } from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import { withRouter } from 'react-router-dom';

interface Props {
  children?: any;
  location?: any;
}

const View: FunctionComponent<Props> = ({ children, location: { state } }) => {
  const cx = classNames('section view', {
    'view--prev': state && state.prev,
  });
  return <section className={cx}>{children}</section>;
};

// =======
// const View: FunctionComponent<Props> = ({
//   children,
//   location: {
//     state,
//   },
// }) => {
//   // const cx = classNames('section page', {
//   //   'view--prev': state && state.prev,
//   // });
//   return (
//     <section className="section page">
//       {children}
//     </section>
//   );
// }
// >>>>>>> Stashed changes

View.propTypes = {
  children: PropTypes.node.isRequired,
  location: PropTypes.any,
};

export default withRouter(View);
