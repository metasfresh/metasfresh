import React from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { distributionJobsDropAllScreen } from '../../../routes/distribution';
import { trl } from '../../../utils/translations';

const DistributionJobsListActions = ({ launchers, disabled }) => {
  const history = useMobileNavigation();
  const isSomeLaunchersInTransit = isInTransit({ launchers });

  if (!isSomeLaunchersInTransit) {
    return null;
  }

  const onDropAll = () => {
    history.goTo(distributionJobsDropAllScreen());
  };

  return (
    <>
      <ButtonWithIndicator
        additionalCssClass="action-button"
        caption={trl('activities.distribution.scanDropToLocator')}
        onClick={onDropAll}
        testId="dropAll-button"
        disabled={disabled}
      />
      <br />
    </>
  );
};

DistributionJobsListActions.propTypes = {
  launchers: PropTypes.array,
  disabled: PropTypes.bool,
};

export default DistributionJobsListActions;

//
//
// -----------------------------------------------------------------------------
//
//

const isInTransit = ({ launchers }) => {
  if (!launchers.length) {
    return false;
  }

  return launchers.some((launcher) => launcher.wfParameters?.isInTransit);
};
