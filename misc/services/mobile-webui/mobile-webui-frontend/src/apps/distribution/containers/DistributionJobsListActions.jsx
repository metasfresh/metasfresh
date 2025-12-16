import React from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { distributionJobsDropAllScreen } from '../../../routes/distribution';
import { trl } from '../../../utils/translations';

const DistributionJobsListActions = ({ actions, disabled }) => {
  const history = useMobileNavigation();

  if (!actions?.length) return null;

  const onDropAll = () => {
    history.goTo(distributionJobsDropAllScreen());
  };

  return (
    <>
      {actions.includes('dropAll') && (
        <ButtonWithIndicator
          additionalCssClass="action-button"
          caption={trl('activities.distribution.scanDropToLocator')}
          onClick={onDropAll}
          testId="dropAll-button"
          disabled={disabled}
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
