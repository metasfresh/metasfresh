import React from 'react';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { massPrintingScanScreenFromListLocation } from '../../../routes/picking';
import { useApplicationInfoParameters } from '../../../reducers/applications';
import { APPLICATION_ID_Picking } from '../index';

const PickingJobsListActions = () => {
  const history = useMobileNavigation();
  const { massPrinting } = useApplicationInfoParameters({ applicationId: APPLICATION_ID_Picking });

  if (!massPrinting) return null;

  return (
    <>
      <ButtonWithIndicator
        captionKey="activities.picking.massPrinting.triggerButton"
        testId="massPrinting-button"
        onClick={() => history.push(massPrintingScanScreenFromListLocation())}
        additionalCssClass="action-button"
      />
      <br />
    </>
  );
};

export default PickingJobsListActions;
