import React from 'react';
import logoImage from '../assets/images/logo.png';

const LogoHeader = () => {
  return (
    <div className="p-3 centered-text">
      <img src={logoImage} alt="metasfresh mobile" />
    </div>
  );
};

export default LogoHeader;
