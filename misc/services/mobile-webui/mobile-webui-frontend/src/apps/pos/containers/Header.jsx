import React from 'react';
import logoImage from '../../../assets/images/logo.png';
import { useSelector } from 'react-redux';
import { getUserFullnameFromState } from '../../../reducers/appHandler';
import './Header.scss';

import { usePOSTerminal } from '../actions/posTerminal';

const Header = () => {
  const posTerminal = usePOSTerminal();
  const userFullname = useSelector(getUserFullnameFromState);
  const avatarLetter = userFullname ? userFullname.charAt(0).toUpperCase() : '';

  const onCloseJournalClick = () => {
    posTerminal.changeStatusToClosing();
  };

  return (
    <div className="pos-header">
      <div className="logo">
        <img src={logoImage} alt="metasfresh mobile" />
      </div>
      <div className="center">
        {posTerminal?.cashJournalOpen && (
          <div className="pos-header-button" onClick={onCloseJournalClick}>
            <span className="text">Close register</span>
          </div>
        )}
      </div>
      <div className="right">
        <div className="pos-header-button userFullname">
          <span className="icon-letter">{avatarLetter}</span>
          <span className="text">{userFullname}</span>
        </div>
      </div>
    </div>
  );
};

export default Header;
