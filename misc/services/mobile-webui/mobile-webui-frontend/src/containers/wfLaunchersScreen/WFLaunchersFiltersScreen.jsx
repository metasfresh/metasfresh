import React, { useEffect } from 'react';
import { useRouteMatch } from 'react-router-dom';
import { updateHeaderEntry } from '../../actions/HeaderActions';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import WFLaunchersFilters from './WFLaunchersFilters';
import { setActiveFilters } from '../../actions/LauncherActions';
import { getApplicationLaunchersFacetIds, getApplicationLaunchersFilterByDocumentNo } from '../../reducers/launchers';
import { useApplicationInfo } from '../../reducers/applications';
import { appLaunchersLocation } from '../../routes/launchers';
import { useScreenDefinition } from '../../hooks/useScreenDefinition';

const WFLaunchersFiltersScreen = () => {
  const { history } = useScreenDefinition({ screenId: 'WFLaunchersFiltersScreen', back: appLaunchersLocation });
  const dispatch = useDispatch();

  const {
    url,
    params: { applicationId },
  } = useRouteMatch();

  const { showFilterByDocumentNo } = useApplicationInfo({ applicationId });
  const filterByDocumentNo = useSelector((state) => getApplicationLaunchersFilterByDocumentNo(state, applicationId));
  const activeFacetIds = useSelector((state) => getApplicationLaunchersFacetIds(state, applicationId), shallowEqual);

  useEffect(() => {
    dispatch(
      updateHeaderEntry({
        location: url,
      })
    );
  }, [url]);

  return (
    <div className="container filters-container">
      <WFLaunchersFilters
        applicationId={applicationId}
        showFilterByDocumentNo={showFilterByDocumentNo}
        filterByDocumentNo={filterByDocumentNo}
        activeFacetIds={activeFacetIds}
        onDone={({ facets, filterByDocumentNo }) => {
          dispatch(setActiveFilters({ applicationId, facets, filterByDocumentNo }));
          history.goBack();
        }}
      />
    </div>
  );
};

export default WFLaunchersFiltersScreen;
