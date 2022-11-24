import React from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import Header from './Header';

function Main({ children }) {
  return (
    <>
      <Header appName={counterpart.translate('appName')} />
      {children}
    </>
  );
}

Main.propTypes = {
  children: PropTypes.node.isRequired,
};

export default Main;
