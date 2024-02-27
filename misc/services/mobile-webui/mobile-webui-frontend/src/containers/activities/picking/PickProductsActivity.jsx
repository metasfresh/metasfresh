import React from 'react';
import PropTypes from 'prop-types';
import * as CompleteStatus from '../../../constants/CompleteStatus';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/buttons/ButtonQuantityProp';
import { pickingLineScreenLocation } from '../../../routes/picking';
import { useHistory } from 'react-router-dom';

const PickProductsActivity = ({ applicationId, wfProcessId, activityId, activityState }) => {
  const {
    dataStored: { lines: linesById, isUserEditable },
  } = activityState;
  const lines = Object.values(linesById);

  const history = useHistory();
  const onButtonClick = ({ lineId }) => {
    history.push(pickingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  return (
    <div className="mt-5">
      {lines && lines.length > 0
        ? lines.map((lineItem) => {
            const lineId = lineItem.pickingLineId;
            const { uom, qtyToPick, qtyPicked } = lineItem;

            return (
              <ButtonWithIndicator
                key={lineId}
                caption={lineItem.caption}
                completeStatus={lineItem.completeStatus || CompleteStatus.NOT_STARTED}
                disabled={!isUserEditable}
                onClick={() => onButtonClick({ lineId })}
              >
                <ButtonQuantityProp
                  qtyCurrent={qtyPicked}
                  qtyTarget={qtyToPick}
                  uom={uom}
                  applicationId={applicationId}
                />
              </ButtonWithIndicator>
            );
          })
        : null}
    </div>
  );
};

PickProductsActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
};

export default PickProductsActivity;
