import React from 'react';
import logoImage from '../../../assets/images/logo.png';
import { useDispatch, useSelector } from 'react-redux';
import { getUserFullnameFromState } from '../../../reducers/appHandler';
import './Header.scss';

import { usePOSTerminal } from '../actions/posTerminal';
import { MODAL_POSTerminalSelect, showModalAction } from '../actions/ui';

const Header = () => {
  const dispatch = useDispatch();
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
        {posTerminal.id && (
          <div
            className="pos-header-button posTerminal"
            onClick={() => dispatch(showModalAction({ modal: MODAL_POSTerminalSelect }))}
          >
            <div className="icon-letter">
              <i className="fa-solid fa-display" />
            </div>
            <span className="text">{posTerminal.caption}</span>
          </div>
        )}
        <div className="pos-header-button userFullname">
          <div className="icon-letter">{avatarLetter}</div>
          <div className="text">{userFullname}</div>
        </div>
      </div>
    </div>
  );
};

export default Header;
