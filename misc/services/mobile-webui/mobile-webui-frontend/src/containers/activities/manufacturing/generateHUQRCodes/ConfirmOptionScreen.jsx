import React, { useEffect, useState } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { getOptionByIndex } from './utils';
import { postGenerateHUQRCodes } from '../../../../api/generateHUQRCodes';
import { toastError } from '../../../../utils/toast';
import Button from '../../../../components/buttons/Button';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';

const ConfirmOptionScreen = () => {
  const {
    url,
    params: { wfProcessId, activityId, optionIndex },
  } = useRouteMatch();
  const optionInfo = useSelector((state) => getOptionByIndex({ state, wfProcessId, activityId, optionIndex }));
  const [qtyTUs, setQtyTUs] = useState(optionInfo.qtyTUs);

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        values: [
          {
            caption: 'Packing', // TODO trl
            value: optionInfo.caption,
          },
          {
            caption: 'TUs', // TODO trl
            value: qtyTUs,
          },
        ],
      })
    );
  }, [qtyTUs]);

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
    <div className="section pt-2">
      <pre>{JSON.stringify(optionInfo, null, 2)}</pre>
      <input className="input" type="number" value={qtyTUs} onChange={onCountChanged} />
      <Button caption={`Print ${qtyTUs}`} onClick={onPrintClick} />
    </div>
  );
};

export default ConfirmOptionScreen;
