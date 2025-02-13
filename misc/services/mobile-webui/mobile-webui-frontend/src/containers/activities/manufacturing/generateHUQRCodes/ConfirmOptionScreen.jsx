import React, { useEffect, useState } from 'react';
import { useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { getOptionByIndex } from './utils';
import { postGenerateHUQRCodes } from '../../../../api/generateHUQRCodes';
import { toastError } from '../../../../utils/toast';
import Button from '../../../../components/buttons/Button';
import { updateHeaderEntry } from '../../../../actions/HeaderActions';
import QtyInputField from '../../../../components/QtyInputField';
import { qtyInfos } from '../../../../utils/qtyInfos';
import { trl } from '../../../../utils/translations';
import * as uiTrace from '../../../../utils/ui_trace';
import { useScreenDefinition } from '../../../../hooks/useScreenDefinition';
import { selectOptionsLocation } from '../../../../routes/generateHUQRCodes';
import { getWFProcessScreenLocation } from '../../../../routes/workflow_locations';

const ConfirmOptionScreen = () => {
  const {
    url,
    params: { wfProcessId, activityId, optionIndex },
  } = useRouteMatch();
  const { history } = useScreenDefinition({
    back: selectOptionsLocation,
  });

  const optionInfo = useSelector((state) => getOptionByIndex({ state, wfProcessId, activityId, optionIndex }));
  const [numberOfHUs, setNumberOfHUs] = useState(optionInfo.numberOfHUs);
  const [numberOfCopies, setNumberOfCopies] = useState(1);

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      updateHeaderEntry({
        location: url,
        values: [
          {
            caption: trl('activities.mfg.generateHUQRCodes.packing'),
            value: optionInfo.caption,
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
    if (numberOfCopiesEntered <= 0) {
      return 'numberOfCopies shall be greater than 0';
    }

    return null; // OK
  };

  const onPrintClick = () => {
    uiTrace.putContext({ numberOfHUs, numberOfCopies });

    postGenerateHUQRCodes({
      wfProcessId,
      finishedGoodsReceiveLineId: optionInfo.finishedGoodsReceiveLineId,
      huPackingInstructionsId: optionInfo.packingInstructionsId,
      numberOfHUs,
      numberOfCopies,
    })
      .then(() => history.goTo(getWFProcessScreenLocation)) // back to wfProcess screen
      .catch((axiosError) => toastError({ axiosError }));
  };

  const isValidNumberOfHUs = numberOfHUs != null && numberOfHUs > 0;

  return (
    <>
      <table className="table view-header is-size-6">
        <tbody>
          <tr className="with-border-top">
            <th>{trl('activities.mfg.generateHUQRCodes.numberOfHUs')}</th>
            <td>
              <QtyInputField
                qty={numberOfHUs}
                integerValuesOnly
                validateQtyEntered={validateQtyEntered}
                onQtyChange={({ qty }) => setNumberOfHUs(qtyInfos.toNumberOrString(qty))}
                isRequestFocus={true}
              />
            </td>
          </tr>
          <tr>
            <th>{trl('activities.mfg.generateHUQRCodes.numberOfCopies')}</th>
            <td>
              <QtyInputField
                qty={numberOfCopies}
                integerValuesOnly
                validateQtyEntered={validateNumberOfCopies}
                onQtyChange={({ qty }) => setNumberOfCopies(qtyInfos.toNumberOrString(qty))}
                isRequestFocus={true}
              />
            </td>
          </tr>
        </tbody>
      </table>
      <div className="section pt-2">
        <Button
          caption={trl('activities.mfg.generateHUQRCodes.print')}
          disabled={!isValidNumberOfHUs}
          onClick={onPrintClick}
        />
      </div>
    </>
  );
};

export default ConfirmOptionScreen;
