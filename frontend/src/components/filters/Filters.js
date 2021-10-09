import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import deepUnfreeze from 'deep-unfreeze';
import { FILTERS_TYPE_NOT_INCLUDED } from '../../constants/Constants';
import { getEntityRelatedId, getCachedFilter } from '../../reducers/filters';
import {
  updateFilterWidgetShown,
  updateActiveFilters,
  clearAllFilters,
  updateNotValidFields,
} from '../../actions/FiltersActions';
import {
  setNewFiltersActive,
  annotateFilters,
  isFilterValid,
  normalizeFilterValue,
} from '../../utils/filterHelpers';

import FiltersNotIcluded from './FiltersNotIncluded';
import FiltersIncluded from './FiltersIncluded';

const EMPTY_ARRAY = [];

/**
 * @file Class based component.
 * @module Filters
 * @extends PureComponent
 */
class Filters extends PureComponent {
  /**
   * @method applyFilters
   * @summary This method should update filters in the store and DL reacts automatically to the changes
   * @param {object} filter
   * @param {function} cb - executed if filter is valid and after it was applied
   */
  applyFilters = ({ ...filter }, cb) => {
    const valid = isFilterValid(filter);
    const { updateNotValidFields, filterId } = this.props;

    updateNotValidFields({ filterId, data: !valid });
    if (valid) {
      const parsedFilter = filter.parameters
        ? {
            ...filter,
            parameters: normalizeFilterValue(filter.parameters),
          }
        : filter;

      this.setFilterActive(parsedFilter);

      cb && cb();
    }
  };

  /**
   * @method setFilterActive
   * @summary This function updates the active filters we set and then triggers the pre-existing
   *          logic from DocList that will fetch the filtered data
   * @param {object} filterToAdd
   */
  setFilterActive = (filterToAdd) => {
    const { updateDocList, filterId, updateActiveFilters } = this.props;
    const { filtersActive: storeActiveFilters } = this.props.filters;

    // updating the active filters from the redux store with the filter passed as param
    const newFiltersActive = setNewFiltersActive({
      storeActiveFilters,
      filterToAdd,
    });

    updateActiveFilters({ filterId, data: newFiltersActive }); // update in the store the filters
    updateDocList(newFiltersActive); // move on and update the page with the new filters via DocList
  };

  /**
   * @method handleShow
   * @summary Method to lock backdrop, to do not close on click onClickOutside
   *          widgets that are bigger than filter wrapper
   * @param {*} value
   */
  handleShow = (value) => {
    const { filterId, updateFilterWidgetShown } = this.props;
    updateFilterWidgetShown({ filterId, data: value });
  };

  /**
   * @method clearFilters
   * @summary Clears all the filters for a specified filter group
   * @param {object} filterToClear - object containing the filters
   * @param {boolean} noUpdate - flag defining if filters should be re-fetched on clearing.
   *                             Used when clearing happens through `apply`
   */
  clearFilters = (filterToClear, noUpdate) => {
    const { filterId, clearAllFilters, filters, updateDocList } = this.props;

    clearAllFilters({ filterId, data: filterToClear });

    // fetch again the doc content after filters were updated into the store
    if (!noUpdate) {
      updateDocList(filters.filtersActive);
    }
  };

  /**
   * @method dropdownToggled
   * @summary Resets notValidFields flag to false
   */
  dropdownToggled = () => {
    const { updateNotValidFields, filterId } = this.props;
    updateNotValidFields({ filterId, data: false });
  };

  /**
   * @method checkClearedFilters
   * @summary verifies if the active filter has all the parameters (filters) already cleared
   * @param {object} { activeFilterId, filtersActive, filterType }
   *                 activeFilterId - the active filter ID to check
   *                 filtersActive  - the array contining the current active filters
   *                 filterTyope    - the type of the filter applied
   */
  checkClearedFilters = ({ activeFilterId, filtersActive, filterType }) => {
    if (!filtersActive || filtersActive.length === 0) return false;
    let mainFilter = filtersActive.filter(
      (item) => item.filterId === activeFilterId
    );
    if (mainFilter.length) {
      const { parameters } = mainFilter[0];
      if (parameters) {
        return parameters.every((filterItem) => {
          return filterType === FILTERS_TYPE_NOT_INCLUDED &&
            filterItem.value &&
            filterItem.value.values &&
            !filterItem.value.values.length
            ? true
            : filterItem.value === null;
        });
      }
    }
    return false;
  };

  /**
   * @method render
   * @summary Main render function - renders the filters
   */
  render() {
    const {
      windowType,
      viewId,
      allowOutsideClick,
      modalVisible,
      filters,
      filterId,
      allFilters,
      flatActiveFilterIds,
    } = this.props;

    const widgetShown = filters ? filters.widgetShown : false;
    const notValidFields = filters ? filters.notValidFields : false;

    if (!filters || !viewId || !filters.filterData || !allFilters.length)
      return false;
    const { filtersActive, filtersCaptions: activeFiltersCaptions } = filters;
    let activeFilterId = null;
    let allChildFiltersCleared = false;

    return (
      <div
        className="filter-wrapper js-not-unselect"
        ref={(c) => (this.filtersWrapper = c)}
      >
        <span className="filter-caption">
          {`${counterpart.translate('window.filters.caption')}: `}
        </span>
        <div className="filter-wrapper">
          {allFilters.map((item) => {
            // iterate among the existing filters
            if (item.includedFilters) {
              let dropdownFilters = item.includedFilters;
              dropdownFilters = deepUnfreeze(dropdownFilters);
              dropdownFilters.map((dropdownFilterItem) => {
                dropdownFilterItem.isActive = flatActiveFilterIds.includes(
                  dropdownFilterItem.filterId
                );
                if (dropdownFilterItem.isActive) {
                  activeFilterId = dropdownFilterItem.filterId;
                }

                return dropdownFilterItem;
              });

              allChildFiltersCleared = this.checkClearedFilters({
                activeFilterId,
                filtersActive,
              });

              // we render the FiltersNotFrequent for normal filters
              // and for those entries that do have includedFilters (we have subfilters)
              // we are rendering FiltersFrequent layout. Note: this is adaptation over previous
              // funtionality that used the 'frequent' flag (was ditched in favour of this one).
              return (
                <FiltersIncluded
                  key={item.caption}
                  {...{
                    activeFiltersCaptions,
                    windowType,
                    notValidFields,
                    viewId,
                    widgetShown,
                    allowOutsideClick,
                    modalVisible,
                  }}
                  data={dropdownFilters}
                  handleShow={this.handleShow}
                  applyFilters={this.applyFilters}
                  clearFilters={this.clearFilters}
                  active={filtersActive}
                  dropdownToggled={this.dropdownToggled}
                  filtersWrapper={this.filtersWrapper}
                  allChildFiltersCleared={allChildFiltersCleared}
                />
              );
            }
            if (!item.includedFilters) {
              allChildFiltersCleared = this.checkClearedFilters({
                activeFilterId: item.filterId,
                filtersActive,
                filterType: FILTERS_TYPE_NOT_INCLUDED,
              });

              return (
                <FiltersNotIcluded
                  {...{
                    activeFiltersCaptions,
                    windowType,
                    notValidFields,
                    viewId,
                    widgetShown,
                    allowOutsideClick,
                    modalVisible,
                  }}
                  data={[item]}
                  handleShow={this.handleShow}
                  applyFilters={this.applyFilters}
                  clearFilters={this.clearFilters}
                  active={filtersActive}
                  dropdownToggled={this.dropdownToggled}
                  filtersWrapper={this.filtersWrapper}
                  key={item.filterId}
                  filterId={filterId}
                  allChildFiltersCleared={allChildFiltersCleared}
                />
              );
            }
          })}
        </div>
      </div>
    );
  }
}

Filters.propTypes = {
  windowType: PropTypes.string.isRequired,
  viewId: PropTypes.string,
  updateDocList: PropTypes.any,
  filtersActive: PropTypes.any,
  filterData: PropTypes.any,
  allowOutsideClick: PropTypes.bool,
  modalVisible: PropTypes.bool,
  filterId: PropTypes.string,
  updateFilterWidgetShown: PropTypes.func,
  updateActiveFilters: PropTypes.func,
  filters: PropTypes.object,
  clearAllFilters: PropTypes.func,
  updateNotValidFields: PropTypes.func,
  allFilters: PropTypes.array,
  flatActiveFilterIds: PropTypes.array,
};

const mapStateToProps = (state, ownProps) => {
  const { allowOutsideClick, modal } = state.windowHandler;
  const { viewId, windowType: windowId } = ownProps;
  const filterId = getEntityRelatedId({ windowId, viewId });

  const stateFilter =
    windowId && viewId ? getCachedFilter(state, filterId) : null;

  const allFilters =
    stateFilter && stateFilter.filterData
      ? annotateFilters({
          unannotatedFilters: stateFilter.filterData,
          filtersActive: stateFilter.filtersActive,
        })
      : EMPTY_ARRAY;

  const flatActiveFilterIds =
    stateFilter && stateFilter.filtersActive
      ? stateFilter.filtersActive.map((item) => item.filterId)
      : EMPTY_ARRAY;

  return {
    allowOutsideClick,
    modalVisible: modal.visible,
    filters: stateFilter,
    filterId,
    allFilters,
    flatActiveFilterIds,
  };
};

export default connect(mapStateToProps, {
  updateFilterWidgetShown,
  updateActiveFilters,
  clearAllFilters,
  updateNotValidFields,
})(Filters);
