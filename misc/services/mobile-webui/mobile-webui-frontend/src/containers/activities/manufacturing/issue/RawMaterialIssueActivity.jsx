import React from 'react';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';

import * as CompleteStatus from '../../../../constants/CompleteStatus';
import { manufacturingLineScreenLocation } from '../../../../routes/manufacturing_issue';

import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../../components/buttons/ButtonQuantityProp';

const RawMaterialIssueActivity = (props) => {
  const {
    applicationId,
    wfProcessId,
    activityId,
    activityState: {
      dataStored: { isUserEditable, lines },
    },
  } = props;

  const history = useHistory();
  const onButtonClick = (lineId) => {
    history.push(manufacturingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  const showHazardsAndAllergens =
    lines && lines.some((lineItem) => lineItem?.hazardSymbols?.length > 0 || lineItem?.allergens?.length > 0);

  return (
    <div className="mt-5">
      {lines && lines.length > 0
        ? lines.map((lineItem, lineIndex) => {
            const lineId = '' + lineIndex;
            //console.log('line', { lineItem });

            return (
              <ButtonWithIndicator
                key={lineId}
                typeFASIconName="fa-arrow-right-to-bracket"
                caption={lineItem.productName}
                hazardSymbols={showHazardsAndAllergens ? lineItem.hazardSymbols : null}
                allergens={showHazardsAndAllergens ? lineItem.allergens : null}
                completeStatus={lineItem.completeStatus || CompleteStatus.NOT_STARTED}
                disabled={!isUserEditable}
                onClick={() => onButtonClick(lineId)}
              >
                <ButtonQuantityProp
                  qtyCurrent={lineItem.qtyIssued}
                  qtyTarget={lineItem.qtyToIssue}
                  uom={lineItem.uom}
                  applicationId={applicationId}
                  subtypeId="issues"
                />
              </ButtonWithIndicator>
            );
          })
        : null}
    </div>
  );
};

RawMaterialIssueActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
};

export default RawMaterialIssueActivity;
