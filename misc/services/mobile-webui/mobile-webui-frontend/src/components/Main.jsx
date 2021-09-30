import React from 'react';

import Header from './Header';

function Main({ children }) {
  return (
    <>
      <Header appName="webUI app" />
      {children}
    </>
  );
}

export default Main;
