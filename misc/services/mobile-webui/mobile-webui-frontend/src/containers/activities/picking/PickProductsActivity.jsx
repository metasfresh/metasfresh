import React from 'react';
import PropTypes from 'prop-types';
import * as CompleteStatus from '../../../constants/CompleteStatus';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/buttons/ButtonQuantityProp';
import { pickingLineScreenLocation, pickingScanScreenLocation } from '../../../routes/picking';
import { useHistory } from 'react-router-dom';
import { trl } from '../../../utils/translations';
import { getLinesArrayFromActivity } from '../../../reducers/wfProcesses';
import { isAllowPickingAnyHUForActivity } from '../../../utils/picking';

export const COMPONENTTYPE_PickProducts = 'picking/pickProducts';

const PickProductsActivity = ({ applicationId, wfProcessId, activityId, activity }) => {
  const {
    dataStored: { isUserEditable },
  } = activity;
  const lines = getLinesArrayFromActivity(activity);
  const allowPickingAnyHU = isAllowPickingAnyHUForActivity({ activity });

  const history = useHistory();

  const onScanButtonClick = () => {
    history.push(pickingScanScreenLocation({ applicationId, wfProcessId, activityId }));
  };
  const onLineButtonClick = ({ lineId }) => {
    history.push(pickingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  return (
    <div className="mt-5">
      {allowPickingAnyHU && (
        <ButtonWithIndicator
          caption={trl('activities.picking.scanQRCode')}
          disabled={!isUserEditable}
          onClick={onScanButtonClick}
        />
      )}
      {lines &&
        lines.map((lineItem) => {
          const lineId = lineItem.pickingLineId;
          const { uom, qtyToPick, qtyPicked } = lineItem;

          return (
            <ButtonWithIndicator
              key={lineId}
              caption={lineItem.caption}
              completeStatus={lineItem.completeStatus || CompleteStatus.NOT_STARTED}
              disabled={!isUserEditable}
              onClick={() => onLineButtonClick({ lineId })}
            >
              <ButtonQuantityProp
                qtyCurrent={qtyPicked}
                qtyTarget={qtyToPick}
                uom={uom}
                applicationId={applicationId}
              />
            </ButtonWithIndicator>
          );
        })}
    </div>
  );
};

PickProductsActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  activity: PropTypes.object.isRequired,
};

export default PickProductsActivity;
