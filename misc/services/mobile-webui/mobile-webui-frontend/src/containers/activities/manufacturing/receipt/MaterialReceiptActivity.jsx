import React from 'react';
import PropTypes from 'prop-types';

import * as CompleteStatus from '../../../../constants/CompleteStatus';
import { manufacturingReceiptScreenLocation } from '../../../../routes/manufacturing_receipt';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../../components/buttons/ButtonQuantityProp';
import { useMobileNavigation } from '../../../../hooks/useMobileNavigation';

const MaterialReceiptActivity = (props) => {
  const {
    applicationId,
    wfProcessId,
    activityId,
    activityState: {
      dataStored: { isUserEditable, lines },
    },
  } = props;

  const history = useMobileNavigation();

  const onButtonClick = ({ lineId }) => {
    history.goTo(manufacturingReceiptScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
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
              qtyCurrent={Number(lineItem.qtyReceived)}
              qtyTarget={Number(lineItem.qtyToReceive)}
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
