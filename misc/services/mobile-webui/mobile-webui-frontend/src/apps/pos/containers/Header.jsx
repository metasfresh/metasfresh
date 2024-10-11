import React from 'react';
import logoImage from '../../../assets/images/logo.png';
import { useDispatch, useSelector } from 'react-redux';
import { getUserFullnameFromState } from '../../../reducers/appHandler';
import './Header.scss';

import { usePOSTerminal } from '../actions/posTerminal';
import { MODAL_POSTerminalSelect, MODAL_SelectOrders, showModalAction } from '../actions/ui';
import { useOpenOrdersArray } from '../reducers/orderUtils';

const Header = () => {
  const dispatch = useDispatch();
  const posTerminal = usePOSTerminal();
  const userFullname = useSelector(getUserFullnameFromState);
  const openOrders = useOpenOrdersArray();
  const avatarLetter = userFullname ? userFullname.charAt(0).toUpperCase() : '';

  const onCloseJournalClicked = () => {
    posTerminal.changeStatusToClosing();
  };
  const onOrdersClicked = () => {
    dispatch(showModalAction({ modal: MODAL_SelectOrders }));
  };
  const onTerminalClicked = () => {
    dispatch(showModalAction({ modal: MODAL_POSTerminalSelect }));
  };

  return (
    <div className="pos-header">
      <div className="logo">
        <img src={logoImage} alt="metasfresh mobile" />
      </div>
      <div className="center">
        {posTerminal?.cashJournalOpen && (
          <div className="pos-header-button" onClick={onCloseJournalClicked}>
            <span className="text">Close register</span>
          </div>
        )}
      </div>
      <div className="right">
        <div className="pos-header-button" onClick={onOrdersClicked}>
          <div className="icon-letter"></div>
          <div className="text">Orders ({openOrders?.length ?? 0})</div>
        </div>
        {posTerminal.id && (
          <div className="pos-header-button posTerminal" onClick={onTerminalClicked}>
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
