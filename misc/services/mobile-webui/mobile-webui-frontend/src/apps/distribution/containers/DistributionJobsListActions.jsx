import React from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { distributionJobsDropAllScreen } from '../../../routes/distribution';

const DistributionJobsListActions = ({ launchers, disabled }) => {
  const history = useMobileNavigation();
  const isSomeLaunchersInTransit = isInTransit({ launchers });

  const onDropAll = () => {
    history.goTo(distributionJobsDropAllScreen());
  };

  return (
    <>
      {isSomeLaunchersInTransit && (
        <ButtonWithIndicator
          caption={'DROP ALL' /* TODO TRL */}
          onClick={onDropAll}
          testId="dropAll-button"
          disabled={disabled}
        />
      )}
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
