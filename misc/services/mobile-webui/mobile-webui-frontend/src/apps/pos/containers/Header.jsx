import React from 'react';
import logoImage from '../../../assets/images/logo.png';

const Header = () => {
  return (
    <div className="pos-header">
      <div className="logo">
        <img src={logoImage} alt="metasfresh mobile" />
      </div>
    </div>
  );
};

export default Header;
