import React, { useEffect } from 'react';
import { updateHeaderEntry } from '../../../actions/HeaderActions';
import { useDispatch } from 'react-redux';
import WFLaunchersFilters from './WFLaunchersFilters';
import { appLaunchersLocation } from '../../../routes/launchers';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';

const WFLaunchersFiltersScreen = () => {
  const { url, applicationId, history } = useScreenDefinition({
    screenId: 'WFLaunchersFiltersScreen',
    back: appLaunchersLocation,
  });
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(
      updateHeaderEntry({
        location: url,
      })
    );
  }, [url]);

  return (
    <div className="container filters-container">
      <WFLaunchersFilters applicationId={applicationId} onDone={() => history.goBack()} />
    </div>
  );
};

export default WFLaunchersFiltersScreen;
