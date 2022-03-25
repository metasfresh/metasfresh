import React from 'react';
import PropTypes from 'prop-types';
import { forEach } from 'lodash';
import * as CompleteStatus from '../../../constants/CompleteStatus';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/buttons/ButtonQuantityProp';
import { pickingLineScreenLocation } from '../../../routes/picking';
import { useHistory } from 'react-router-dom';

const computeLineQuantities = (line) => {
  let picked = 0;
  let toPick = 0;
  let uom = '';

  forEach(line.steps, (step) => {
    const { qtyToPick, uom: stepUom } = step;

    picked += computeStepQtyPickedTotal(step);
    toPick += qtyToPick;
    uom = stepUom;
  });

  return { picked, toPick, uom };
};

const computeStepQtyPickedTotal = (step) => {
  let qtyPickedTotal = 0;

  if (step.mainPickFrom.qtyPicked) {
    qtyPickedTotal += step.mainPickFrom.qtyPicked;
  }

  if (step.pickFromAlternatives) {
    const qtyPickedInAltSteps = Object.values(step.pickFromAlternatives).reduce(
      (accum, pickFromAlternative) => accum + pickFromAlternative.qtyPicked,
      0
    );

    qtyPickedTotal += qtyPickedInAltSteps;
  }

  return qtyPickedTotal;
};

const PickProductsActivity = ({ applicationId, wfProcessId, activityId, activityState }) => {
  const {
    dataStored: { lines, completeStatus, isUserEditable },
  } = activityState;

  const history = useHistory();
  const onButtonClick = ({ lineId }) => {
    history.push(pickingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  return (
    <div className="mt-5">
      {lines && lines.length > 0
        ? lines.map((lineItem, lineIndex) => {
            const lineId = '' + lineIndex;
            const { picked, toPick, uom } = computeLineQuantities(lineItem);

            return (
              <ButtonWithIndicator
                key={lineId}
                caption={lineItem.caption}
                completeStatus={completeStatus || CompleteStatus.NOT_STARTED}
                disabled={!isUserEditable}
                onClick={() => onButtonClick({ lineId })}
              >
                <ButtonQuantityProp qtyCurrent={picked} qtyTarget={toPick} uom={uom} applicationId={applicationId} />
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
