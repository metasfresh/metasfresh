import React, { useEffect, useState } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { getOptionByIndex } from './utils';
import { postGenerateHUQRCodes } from '../../../../api/generateHUQRCodes';
import { toastError } from '../../../../utils/toast';
import Button from '../../../../components/buttons/Button';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import QtyInputField from '../../../../components/dialogs/QtyInputField';

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
      qtyTUs,
    })
      .then(() => history.go(-2)) // back to wfProcess screen
      .catch((axiosError) => toastError({ axiosError }));
  };

  const isValidQtyTUs = qtyTUs != null && qtyTUs > 0;
  const printButtonCaption = isValidQtyTUs ? `Print ${qtyTUs}` : 'Print';

  return (
    <div className="section pt-2">
      <pre>{JSON.stringify(optionInfo, null, 2)}</pre>
      <QtyInputField
        qtyInitial={qtyTUs}
        integerValuesOnly
        validateQtyEntered={validateQtyEntered}
        onQtyChange={setQtyTUs}
        isRequestFocus={true}
      />
      <Button caption={printButtonCaption} disabled={!isValidQtyTUs} onClick={onPrintClick} />
    </div>
  );
};

export default ConfirmOptionScreen;
