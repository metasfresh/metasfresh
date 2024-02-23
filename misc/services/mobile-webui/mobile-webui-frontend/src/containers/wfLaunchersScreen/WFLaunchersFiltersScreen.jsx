import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { pushHeaderEntry } from '../../actions/HeaderActions';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import WFLaunchersFilters from './WFLaunchersFilters';
import { setActiveFacets } from '../../actions/LauncherActions';
import { getApplicationLaunchersFacetIds } from '../../reducers/launchers';

const WFLaunchersFiltersScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();

  const {
    url,
    params: { applicationId },
  } = useRouteMatch();

  const activeFacetIds = useSelector((state) => getApplicationLaunchersFacetIds(state, applicationId), shallowEqual);

  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
      })
    );
  }, [url]);

  return (
    <div className="container filters-container">
      <WFLaunchersFilters
        applicationId={applicationId}
        activeFacetIds={activeFacetIds}
        onDone={(facets) => {
          dispatch(setActiveFacets({ applicationId, facets }));
          history.go(-1);
        }}
      />
    </div>
  );
};

export default WFLaunchersFiltersScreen;
