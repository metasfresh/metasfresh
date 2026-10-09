import React from 'react';
import Spinner from '../app/SpinnerOverlay';

const LoadingView = () => {
  const style = { width: '100%', height: '50px' };
  return (
    <div style={style}>
      <Spinner iconSize={50} spinnerType="modal" />
    </div>
  );
};

export default LoadingView;
