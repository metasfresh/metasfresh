// import React from 'react';
import React, { FunctionComponent } from 'react';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router-dom';

interface Props {
  children?: React.ReactNode;
  location?: any;
}

const View: FunctionComponent<Props> = ({ children }) => {
  return <section className="container page mt-1">{children}</section>;
};

View.propTypes = {
  children: PropTypes.node.isRequired,
  location: PropTypes.any,
};

export default withRouter(View);
