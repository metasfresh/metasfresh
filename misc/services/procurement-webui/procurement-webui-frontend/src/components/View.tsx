import React, { FunctionComponent } from 'react';
import PropTypes from 'prop-types';

interface Props {
  children?: React.ReactNode;
}

const View: FunctionComponent<Props> = ({ children }) => {
  return <section className="container page">{children}</section>;
};

View.propTypes = {
  children: PropTypes.node.isRequired,
};

export default View;
