import React, { useState } from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { distributionJobsDropAllScreen } from '../../../routes/distribution';
import { trl } from '../../../utils/translations';
import { postPrintMaterialInTransitReport } from '../../../api/distribution';
import { toastErrorFromObj } from '../../../utils/toast';

const DistributionJobsListActions = ({ actions, disabled }) => {
  const history = useMobileNavigation();
  const [isPrintMaterialInTransitReportRunning, setPrintMaterialInTransitReportRunning] = useState(false);

  if (!actions?.length) return null;

  const onDropAll = () => {
    history.goTo(distributionJobsDropAllScreen());
  };

  const onPrintMaterialInTransitReport = () => {
    setPrintMaterialInTransitReportRunning(true);
    postPrintMaterialInTransitReport()
      .catch(toastErrorFromObj)
      .finally(() => setPrintMaterialInTransitReportRunning(false));
  };

  return (
    <>
      {actions.includes('dropAll') && (
        <ButtonWithIndicator
          additionalCssClass="action-button"
          typeFASIconName="fa-solid fa-download"
          caption={trl('activities.distribution.scanDropToLocator')}
          onClick={onDropAll}
          testId="dropAll-button"
          disabled={disabled}
        />
      )}
      {actions.includes('printMaterialInTransitReport') && (
        <ButtonWithIndicator
          additionalCssClass="action-button"
          typeFASIconName="fa-solid fa-print"
          caption={trl('activities.distribution.printMaterialInTransitReport')}
          onClick={onPrintMaterialInTransitReport}
          testId="printMaterialInTransitReport-button"
          disabled={disabled || isPrintMaterialInTransitReportRunning}
        />
      )}
      <br />
    </>
  );
};

DistributionJobsListActions.propTypes = {
  actions: PropTypes.array,
  disabled: PropTypes.bool,
};

export default DistributionJobsListActions;
