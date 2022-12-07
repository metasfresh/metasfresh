import React, { useEffect, useState } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { getOptionByIndex } from './utils';
import { postGenerateHUQRCodes } from '../../../../api/generateHUQRCodes';
import { toastError } from '../../../../utils/toast';
import Button from '../../../../components/buttons/Button';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import QtyInputField from '../../../../components/QtyInputField';
import { qtyInfos } from '../../../../utils/qtyInfos';
import { trl } from '../../../../utils/translations';

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
            caption: trl('activities.mfg.generateHUQRCodes.packing'),
            value: optionInfo.caption,
          },
          {
            caption: trl('activities.mfg.generateHUQRCodes.qtyTUs'),
            value: qtyTUs,
          },
        ],
      })
    );
  }, [qtyTUs]);

  const validateQtyEntered = (qtyTUsEntered) => {
    if (qtyTUsEntered <= 0) {
      return 'QtyTUs shall be greater than 0';
    }

    return null; // OK
  };

  const history = useHistory();
  const onPrintClick = () => {
    postGenerateHUQRCodes({
      wfProcessId,
      finishedGoodsReceiveLineId: optionInfo.finishedGoodsReceiveLineId,
      tuPackingInstructionsId: optionInfo.tuPackingInstructionsId,
      qtyTUs,
    })
      .then(() => history.go(-2)) // back to wfProcess screen
      .catch((axiosError) => toastError({ axiosError }));
  };

  const isValidQtyTUs = qtyTUs != null && qtyTUs > 0;

  return (
    <div className="section pt-2">
      <QtyInputField
        qty={qtyTUs}
        integerValuesOnly
        validateQtyEntered={validateQtyEntered}
        onQtyChange={({ qty }) => setQtyTUs(qtyInfos.toNumberOrString(qty))}
        isRequestFocus={true}
      />
      <Button
        caption={trl('activities.mfg.generateHUQRCodes.print')}
        disabled={!isValidQtyTUs}
        onClick={onPrintClick}
      />
    </div>
  );
};

export default ConfirmOptionScreen;
