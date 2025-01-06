import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { pushHeaderEntry } from '../../actions/HeaderActions';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import WFLaunchersFilters from './WFLaunchersFilters';
import { setActiveFilters } from '../../actions/LauncherActions';
import { getApplicationLaunchersFacetIds, getApplicationLaunchersFilterByDocumentNo } from '../../reducers/launchers';
import { useApplicationInfo } from '../../reducers/applications';

const WFLaunchersFiltersScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();

  const {
    url,
    params: { applicationId },
  } = useRouteMatch();

  const { showFilterByDocumentNo } = useApplicationInfo({ applicationId });
  const filterByDocumentNo = useSelector((state) => getApplicationLaunchersFilterByDocumentNo(state, applicationId));
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
        showFilterByDocumentNo={showFilterByDocumentNo}
        filterByDocumentNo={filterByDocumentNo}
        activeFacetIds={activeFacetIds}
        onDone={({ facets, filterByDocumentNo }) => {
          dispatch(setActiveFilters({ applicationId, facets, filterByDocumentNo }));
          history.go(-1);
        }}
      />
    </div>
  );
};

export default WFLaunchersFiltersScreen;
