import React from 'react';
import { useHistory } from 'react-router-dom';
import PropTypes from 'prop-types';

import * as CompleteStatus from '../../../../constants/CompleteStatus';
import { manufacturingReceiptScreenLocation } from '../../../../routes/manufacturing_receipt';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../../components/buttons/ButtonQuantityProp';

const MaterialReceiptActivity = (props) => {
  const {
    applicationId,
    wfProcessId,
    activityId,
    activityState: {
      dataStored: { isUserEditable, lines },
    },
  } = props;

  const history = useHistory();

  const onButtonClick = ({ lineId }) => {
    const location = manufacturingReceiptScreenLocation({ applicationId, wfProcessId, activityId, lineId });
    history.push(location);
  };

  const linesArray = lines ? Object.values(lines) : [];
  const showHazardsAndAllergens = linesArray.some(
    (lineItem) => lineItem?.hazardSymbols?.length > 0 || lineItem?.allergens?.length > 0
  );

  return (
    <div className="mt-5">
      {linesArray.map((lineItem) => {
        const lineId = lineItem.id;

        return (
          <ButtonWithIndicator
            key={lineId}
            caption={lineItem.productName}
            typeFASIconName={lineItem.coproduct ? 'fa-retweet' : 'fa-arrow-right-from-bracket'}
            hazardSymbols={showHazardsAndAllergens ? lineItem.hazardSymbols : null}
            allergens={showHazardsAndAllergens ? lineItem.allergens : null}
            completeStatus={lineItem.completeStatus || CompleteStatus.NOT_STARTED}
            disabled={!isUserEditable}
            onClick={() => onButtonClick({ lineId })}
          >
            <ButtonQuantityProp
              qtyCurrent={lineItem.qtyReceived}
              qtyTarget={lineItem.qtyToReceive}
              uom={lineItem.uom}
              applicationId={applicationId}
              subtypeId="receipts"
            />
          </ButtonWithIndicator>
        );
      })}
    </div>
  );
};

MaterialReceiptActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  activityState: PropTypes.object,
};

export default MaterialReceiptActivity;
