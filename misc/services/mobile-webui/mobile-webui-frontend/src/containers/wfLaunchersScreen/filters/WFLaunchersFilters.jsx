import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { trl } from '../../../utils/translations';
import GetDocumentNoDialog from './GetDocumentNoDialog';
import { useApplicationInfo } from '../../../reducers/applications';
import { useDispatch } from 'react-redux';
import { useFacetIds, useFilters } from '../../../reducers/launchers';
import { FacetGroup } from './FacetGroup';
import { computeActiveFacetsFromGroups } from './utils';
import { clearActiveFilters, setActiveFilters } from '../../../actions/LauncherActions';
import { useGroups } from './hooks/useGroups';
import { useResultsCount } from './hooks/useResultsCount';

const WFLaunchersFilters = ({ applicationId, onDone }) => {
  const dispatch = useDispatch();
  const { showFilterByDocumentNo, showFilterByQtyAvailableAtPickFromLocator } = useApplicationInfo({ applicationId });
  const activeFacetIdsInitial = useFacetIds({ applicationId });

  const { filters, setFilterByDocumentNo, toggleFilterByQtyAvailableAtPickFromLocator } = useFiltersModel({
    applicationId,
  });
  const [isFilterByDocumentNoModalDisplayed, setIsFilterByDocumentNoModalDisplayed] = useState(false);

  const { groups, groupsLoading, toggleActiveFacet } = useGroups({
    applicationId,
    activeFacetIdsInitial,
    filters,
  });
  const { resultsCountLoading, resultsCount } = useResultsCount({
    applicationId,
    groupsLoading,
    groups,
    filters,
  });

  const [isApplyFilters, setApplyFilters] = useState(false);
  useEffect(() => {
    if (isApplyFilters) {
      setApplyFilters(false);
      onApplyFilters();
    }
  }, [isApplyFilters]);

  const onFilterByDocumentNoChanged = (filterByDocumentNoNew) => {
    setFilterByDocumentNo(filterByDocumentNoNew);
    setApplyFilters(true);
  };
  const onFilterByDocumentNoCleared = () => {
    setFilterByDocumentNo('');
    setIsFilterByDocumentNoModalDisplayed(false);
  };

  const onFilterByQtyAvailableAtPickFromLocatorClicked = () => {
    toggleFilterByQtyAvailableAtPickFromLocator();
  };

  const onFacetClicked = ({ facetId }) => {
    toggleActiveFacet({ facetId });
  };

  const onApplyFilters = () => {
    dispatch(
      setActiveFilters({
        applicationId,
        filters,
        facets: computeActiveFacetsFromGroups(groups),
      })
    );

    onDone();
  };
  const onClearFilters = () => {
    dispatch(clearActiveFilters({ applicationId }));
    onDone();
  };

  return (
    <div className="filters">
      {showFilterByDocumentNo && (
        <>
          <ButtonWithIndicator
            id="filterByDocumentNo-button"
            caption={filters?.filterByDocumentNo ? filters.filterByDocumentNo : trl('general.DocumentNo')}
            onClick={() => setIsFilterByDocumentNoModalDisplayed(true)}
          />
          <br />
        </>
      )}
      {isFilterByDocumentNoModalDisplayed && (
        <GetDocumentNoDialog
          documentNo={filters?.filterByDocumentNo}
          onOK={onFilterByDocumentNoChanged}
          onClear={() => onFilterByDocumentNoCleared()}
        />
      )}
      {showFilterByQtyAvailableAtPickFromLocator && (
        <ButtonWithIndicator
          id="filterByQtyAvailableAtPickFromLocator-button"
          caption={trl('general.filter.filterByQtyAvailableAtPickFromLocator')}
          typeFASIconName={filters?.filterByQtyAvailableAtPickFromLocator ? 'fa-check' : null}
          onClick={() => onFilterByQtyAvailableAtPickFromLocatorClicked()}
        />
      )}
      {groups?.map((group) => (
        <FacetGroup key={group.groupId} caption={group.caption} facets={group.facets} onClick={onFacetClicked} />
      ))}
      {groupsLoading && (
        <div className="loading">
          <i className="loading-icon fas fa-solid fa-spinner fa-spin" />
        </div>
      )}
      <div className="bottom-buttons">
        {!groupsLoading && resultsCount > 0 && (
          <ButtonWithIndicator
            id="showResults"
            caption={trl('general.filter.showResults', { count: resultsCount })}
            typeFASIconName={resultsCountLoading ? 'fa-spinner fa-spin' : null}
            disabled={resultsCountLoading}
            onClick={onApplyFilters}
            data-hitcount={resultsCount}
          />
        )}
        <ButtonWithIndicator id="clearFilters" captionKey="general.filter.clearFilters" onClick={onClearFilters} />
      </div>
    </div>
  );
};

WFLaunchersFilters.propTypes = {
  applicationId: PropTypes.string.isRequired,
  onDone: PropTypes.func.isRequired,
};

export default WFLaunchersFilters;

//
//
//
//
//

const useFiltersModel = ({ applicationId }) => {
  const filtersInitial = useFilters({ applicationId });
  const [filters, setFilters] = useState(filtersInitial);

  const setFilterByDocumentNo = (filterByDocumentNoNew) => {
    setFilters((filters) => ({ ...filters, filterByDocumentNo: filterByDocumentNoNew }));
  };

  const toggleFilterByQtyAvailableAtPickFromLocator = () => {
    setFilters((filters) => ({
      ...filters,
      filterByQtyAvailableAtPickFromLocator: !filters.filterByQtyAvailableAtPickFromLocator,
    }));
  };

  return {
    filters,
    setFilterByDocumentNo,
    toggleFilterByQtyAvailableAtPickFromLocator,
  };
};
