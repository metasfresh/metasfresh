import React, { useState } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { getOptionByIndex } from './utils';
import { postGenerateHUQRCodes } from '../../../../api/generateHUQRCodes';
import { toastError } from '../../../../utils/toast';

const ConfirmOptionScreen = () => {
  const {
    params: { wfProcessId, activityId, optionIndex },
  } = useRouteMatch();
  const optionInfo = useSelector((state) => getOptionByIndex({ state, wfProcessId, activityId, optionIndex }));

  const [qtyTUs, setQtyTUs] = useState(optionInfo.qtyTUs);

  const onCountChanged = (event) => setQtyTUs(Math.floor(event.target.value));

  const history = useHistory();
  const onPrintClick = () => {
    postGenerateHUQRCodes({
      wfProcessId,
      finishedGoodsReceiveLineId: optionInfo.finishedGoodsReceiveLineId,
      qtyTUs,
    })
      .then(() => history.go(-2)) // back to wfProcess screen
      .catch((axiosError) => toastError({ axiosError }));
  };

  return (
    <div className="pt-2 section">
      <pre>{JSON.stringify(optionInfo, null, 2)}</pre>
      <input className="input" type="number" value={qtyTUs} onChange={onCountChanged} />
      <button className="button btn-green is-outlined complete-btn" onClick={onPrintClick}>
        Print {qtyTUs}
      </button>
    </div>
  );
};

export default ConfirmOptionScreen;
