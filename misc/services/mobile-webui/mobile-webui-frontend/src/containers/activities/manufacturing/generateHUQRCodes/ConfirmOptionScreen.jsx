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
  const [numberOfHUs, setNumberOfHUs] = useState(optionInfo.numberOfHUs);
  const [numberOfCopies, setNumberOfCopies] = useState(0);

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
            caption: trl('activities.mfg.generateHUQRCodes.numberOfHUs'),
            value: numberOfHUs,
          },
        ],
      })
    );
  }, [numberOfHUs]);

  const validateQtyEntered = (numberOfHUsEntered) => {
    if (numberOfHUsEntered <= 0) {
      return 'numberOfHUs shall be greater than 0';
    }

    return null; // OK
  };

  const validateNumberOfCopies = (numberOfCopiesEntered) => {
    if (numberOfCopiesEntered < 0) {
      return 'numberOfCopies shall be equal or greater than 0';
    }

    return null; // OK
  };

  const history = useHistory();
  const onPrintClick = () => {
    postGenerateHUQRCodes({
      wfProcessId,
      finishedGoodsReceiveLineId: optionInfo.finishedGoodsReceiveLineId,
      huPackingInstructionsId: optionInfo.packingInstructionsId,
      numberOfHUs,
      numberOfCopies,
    })
      .then(() => history.go(-2)) // back to wfProcess screen
      .catch((axiosError) => toastError({ axiosError }));
  };

  const isValidNumberOfHUs = numberOfHUs != null && numberOfHUs > 0;

  return (
    <div className="section pt-2">
      <QtyInputField
        qty={numberOfHUs}
        integerValuesOnly
        validateQtyEntered={validateQtyEntered}
        onQtyChange={({ qty }) => setNumberOfHUs(qtyInfos.toNumberOrString(qty))}
        isRequestFocus={true}
      />
      <QtyInputField
        qty={numberOfCopies}
        integerValuesOnly
        validateQtyEntered={validateNumberOfCopies}
        onQtyChange={({ qty }) => setNumberOfCopies(qtyInfos.toNumberOrString(qty))}
        isRequestFocus={true}
      />
      <Button
        caption={trl('activities.mfg.generateHUQRCodes.print')}
        disabled={!isValidNumberOfHUs}
        onClick={onPrintClick}
      />
    </div>
  );
};

export default ConfirmOptionScreen;
