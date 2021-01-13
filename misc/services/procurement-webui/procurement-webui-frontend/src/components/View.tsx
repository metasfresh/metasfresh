import React, { FunctionComponent } from 'react';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router-dom';
import * as H from 'history';

interface Props {
  children?: React.ReactNode;
  location?: H.Location;
}

const View: FunctionComponent<Props> = ({ children }) => {
  return <section className="container page mt-1">{children}</section>;
};

View.propTypes = {
  children: PropTypes.node.isRequired,
  location: PropTypes.any,
};

export default withRouter(View);
