import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { Map as iMap } from 'immutable';
import _ from 'lodash';
import {
  updateWidgetShown,
  setNewFiltersActive,
  updateActiveFilter,
  clearAllFilters,
  annotateFilters,
} from '../../actions/FiltersActions';

import FiltersFrequent from './FiltersFrequent';
import FiltersNotFrequent from './FiltersNotFrequent';
import deepUnfreeze from 'deep-unfreeze';
import { getEntityRelatedId } from '../../reducers/filters';

/**
 * @file Class based component.
 * @module Filters
 * @extends Component
 */
class Filters extends PureComponent {
  state = {
    activeFilter: null,
    activeFiltersCaptions: null,
    notValidFields: null,
  };

  /**
   * @method UNSAFE_componentWillReceiveProps
   * @summary ToDo: Describe the method
   */
  UNSAFE_componentWillReceiveProps() {
    this.parseActiveFilters();
  }

  /**
   * @method componentDidMount
   * @summary ToDo: Describe the method
   */
  componentDidMount() {
    this.parseActiveFilters();
  }

  /**
   * @method arrangeFilters
   * @summary combines the filters and also removes the other actives ones from same group
   */
  arrangeFilters = (filterData) => {
    let combinedFilters = [];
    for (const [key] of filterData.entries()) {
      let item = filterData.get(key);
      if (typeof item.includedFilters !== 'undefined') {
        combinedFilters.push(...item.includedFilters);
      } else {
        combinedFilters.push(item);
      }
    }
    let mappedFiltersData = new Map();
    combinedFilters.forEach((item) => {
      mappedFiltersData.set(item.filterId, item);
    });
    return mappedFiltersData;
  };

  // PARSING FILTERS ---------------------------------------------------------

  /*
   * parseActiveFilters - this function does three things:
   *  - creates a flat map of existing filter fields to store the widgetType for
        further processing
   *  - creates a local copy of active filters object including filters that
   *    only have defaultValues set. `defaultVal` flag tells us, that this
   *    filter has only defaultValues, and no values set by the user. We need
   *    this to ble able to differentiate between filters that should be
   *    indicated as active on load, or not.
   *  - creates an object with captions of each active parameter per filter
   *
   * So first we traverse all filters data and perform actions in this order:
   *  - if filter is in active filters and parameter has no defaultValue,
   *    or defaultValue has been nullified by user's selection we add it
   *    local active filters and set the `defaultVal` flag to false
   *    (as it obviously was already set).
   *  - if filter is active check if current loop parameter is set in the
   *    active filters. If yes, do nothing as it'll always override the
   *    defaultValue
   *  - otherwise add parameter and filter to local active filters and set
   *    the `defaultVal` to true as apparently there are no values set  
   *  
   *    Update: 10 March 2020, removed the logic to set the default 
   * 
   */
  /**
   * @method parseActiveFilters
   * @summary ToDo: Describe the method
   */
  parseActiveFilters = () => {
    let { filtersActive, filterData, initialValuesNulled } = this.props;
    let activeFilters = _.cloneDeep(filtersActive);

    // make new ES6 Map with the items from combined filters
    let mappedFiltersData = this.arrangeFilters(filterData);
    // put the resulted combined map of filters into the iMap and preserve existing functionality
    let filtersData = iMap(mappedFiltersData);
    const activeFiltersCaptions = {};

    // find any filters with default values first and extend
    // activeFilters with them
    filtersData.forEach((filter, filterId) => {
      if (filter.parameters) {
        outerParameters: for (let parameter of filter.parameters) {
          const { defaultValue, parameterName } = parameter;
          const nulledFilter = initialValuesNulled.get(filterId);

          const isActive = filtersActive.has(filterId);

          if (
            !defaultValue ||
            (nulledFilter && nulledFilter.has(parameterName))
          ) {
            if (isActive) {
              activeFilters = activeFilters.set(filterId, {
                defaultVal: false,
                filterId,
                parameters: activeFilters.get(filterId).parameters,
              });
            }
            continue;
          }

          if (isActive) {
            //look for existing parameterName in parameters array
            // skip if found as they override defaultValue ALWAYS
            const filterActive = activeFilters.get(filterId);

            if (filterActive.parameters) {
              for (let activeParameter of filterActive.parameters) {
                if (activeParameter.parameterName === parameterName) {
                  continue outerParameters;
                }
              }
            }
          }
        }
      }
    });

    if (activeFilters.size) {
      const removeDefault = {};

      activeFilters.forEach((filter, filterId) => {
        let captionsArray = ['', ''];

        if (filter.parameters && filter.parameters.length) {
          filter.parameters.forEach((filterParameter) => {
            const { value, parameterName, defaultValue } = filterParameter;

            if (!defaultValue) {
              // we don't want to show captions, nor show filter button as active
              // for default values
              removeDefault[filterId] = true;

              const parentFilter = filtersData.get(filterId);
              const filterParameter = parentFilter.parameters.find(
                (param) => param.parameterName === parameterName
              );
              let captionName = filterParameter.caption;
              let itemCaption = filterParameter.caption;

              switch (filterParameter.widgetType) {
                case 'Text':
                  captionName = value;

                  if (!value) {
                    captionName = '';
                    itemCaption = '';
                  }
                  break;
                case 'Lookup':
                case 'List':
                  captionName = value && value.caption;
                  break;
                case 'Labels':
                  captionName = value.values.reduce((caption, item) => {
                    return `${caption}, ${item.caption}`;
                  }, '');
                  break;
                case 'YesNo':
                  if (value === null) {
                    captionName = '';
                    itemCaption = '';
                  }
                  break;
                case 'Switch':
                default:
                  if (!value) {
                    captionName = '';
                    itemCaption = '';
                  }
                  break;
              }

              if (captionName) {
                captionsArray[0] = captionsArray[0]
                  ? `${captionsArray[0]}, ${captionName}`
                  : captionName;
              }

              if (itemCaption) {
                captionsArray[1] = captionsArray[1]
                  ? `${captionsArray[1]}, ${itemCaption}`
                  : itemCaption;
              }
            }
          });
        } else {
          const originalFilter = filtersData.get(filterId);
          captionsArray = [originalFilter.caption, originalFilter.caption];
        }

        if (captionsArray.join('').length) {
          activeFiltersCaptions[filterId] = captionsArray;
        }
      });

      // if filter has defaultValues but also some user defined ones,
      // we should still include it in active filters
      if (Object.keys(removeDefault).length) {
        for (let key of Object.keys(removeDefault)) {
          activeFilters = activeFilters.setIn([key, 'defaultVal'], false);
        }
      }

      const cleanActiveFilter = this.cleanupActiveFilter(
        filterData.toIndexedSeq().toArray(),
        activeFilters.toIndexedSeq().toArray()
      );

      this.setState({
        activeFilter: cleanActiveFilter,
        activeFiltersCaptions,
      });
    } else {
      this.setState({
        activeFilter: null,
        activeFiltersCaptions: null,
      });
    }
  };

  cleanupActiveFilter = (allFilters, activeStateFilters) => {
    let filtersToRemove = [];
    activeStateFilters.forEach((activeStateFilter) => {
      allFilters.forEach((filterItem) => {
        if (Array.isArray(filterItem.includedFilters)) {
          let groupFilter = filterItem.includedFilters;
          let foundMatches = groupFilter.filter(
            (gFilterItem) => gFilterItem.filterId === activeStateFilter.filterId
          );
          if (foundMatches.length > 0) {
            filtersToRemove = groupFilter.filter(
              (toRemove) => toRemove.filterId !== activeStateFilter.filterId
            );
          }
        }
      });
    });
    return activeStateFilters.filter(
      (asFilterItem) =>
        !this.isBlacklisted(asFilterItem.filterId, filtersToRemove)
    );
  };

  /**
   * @method isBlackListed
   * @summary Check if the filterId is found among the filters to remove array
   * @param {string filterId
   * @param {array} filtersToRemove
   */
  isBlacklisted = (filterId, filtersToRemove) => {
    let resultValue = false;
    filtersToRemove.forEach((filterItem) => {
      if (filterItem.filterId === filterId) {
        resultValue = true;
      }
    });
    return resultValue;
  };

  /**
   * @method isFilterValid
   * @summary ToDo: Describe the method
   * @param {*} filters
   */
  isFilterValid = (filters) => {
    if (filters.parameters) {
      return !filters.parameters.filter((item) => item.mandatory && !item.value)
        .length;
    }

    return true;
  };

  /**
   * @method parseToPatch
   * @summary ToDo: Describe the method
   * @param {*} params
   */
  parseToPatch = (params) => {
    return params.reduce((acc, param) => {
      if (
        // filters with only defaltValue shouldn't be sent to server
        !param.defaultValue ||
        JSON.stringify(param.defaultValue) !== JSON.stringify(param.value)
      ) {
        acc.push({
          ...param,
          value: param.value === '' ? null : param.value,
        });
      }

      return acc;
    }, []);
  };

  // SETTING FILTERS  --------------------------------------------------------
  /**
   * @method applyFilters
   * @summary This method should update docList
   * @param {*} isActive
   * @param {*} captionValue
   * @param {object} filter
   * @param {*} cb
   */
  // eslint-disable-next-line no-unused-vars
  applyFilters = ({ isActive, captionValue, ...filter }, cb) => {
    const valid = this.isFilterValid(filter);

    this.setState(
      {
        notValidFields: !valid,
      },
      () => {
        if (valid) {
          const parsedFilter = filter.parameters
            ? {
                ...filter,
                parameters: this.parseToPatch(filter.parameters),
              }
            : filter;

          this.setFilterActive(parsedFilter);

          cb && cb();
        }
      }
    );
  };

  /**
   * @method setFilterActive
   * @summary This function updates the active filters we set and then triggers the pre-existing logic from DocList that will fetch the filtered data
   * @param {object} filterToAdd
   */
  setFilterActive = (filterToAdd) => {
    const { updateDocList, filterId, updateActiveFilter } = this.props;
    const { filtersActive: storeActiveFilters } = this.props.filters;

    // we are going to update the active filters from the redux store with the filter passed as param
    const newFiltersActive = setNewFiltersActive({
      storeActiveFilters,
      filterToAdd,
    });

    // update in the store the filters
    updateActiveFilter({ id: filterId, data: newFiltersActive });

    // move on to the logic from the DocList
    updateDocList(newFiltersActive);
  };

  /**
   * @method handleShow
   * @summary Method to lock backdrop, to do not close on click onClickOutside
   *  widgets that are bigger than filter wrapper
   * @param {*} value
   */
  handleShow = (value) => {
    const { filterId, updateWidgetShown } = this.props;
    updateWidgetShown({ id: filterId, data: value });
  };

  /**
   * @method clearFilters
   * @summary Clears all the filters for a specified filter group
   * @param {*} filterToClear - object containing the filters
   */
  clearFilters = (filterToClear) => {
    const { filterId, clearAllFilters, filters, updateDocList } = this.props;
    clearAllFilters({ id: filterId, data: filterToClear });
    // fetch again the doc content after filters were updated into the store
    updateDocList(filters.filtersActive);
  };

  /**
   * @method dropdownToggled
   * @summary ToDo: Describe the method
   */
  dropdownToggled = () => {
    this.setState({
      notValidFields: false,
    });
  };

  // RENDERING FILTERS -------------------------------------------------------
  /**
   * @method render
   * @summary ToDo: Describe the method
   */
  render() {
    const {
      windowType,
      viewId,
      resetInitialValues,
      allowOutsideClick,
      modalVisible,
      filters,
    } = this.props;
    const widgetShown = filters ? filters.widgetShown : false;
    const { notValidFields, activeFiltersCaptions } = this.state;

    if (!filters || !viewId) return false;

    const { filtersActive } = filters;

    const allFilters = annotateFilters({
      unannotatedFilters: filters.filterData,
      filtersActive,
    });

    const flatActiveFilterIds =
      filtersActive !== null ? filtersActive.map((item) => item.filterId) : [];

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
                return dropdownFilterItem;
              });

              // we render the FiltersNotFrequent for normal filters
              // and for those entries that do have includedFilters (we have subfilters)
              // we are rendering FiltersFrequent layout. Note: this is adaptation over previous
              // funtionality that used the 'frequent' flag (was ditched in favour of this one).
              return (
                <FiltersNotFrequent
                  key={item.caption}
                  {...{
                    activeFiltersCaptions,
                    windowType,
                    notValidFields,
                    viewId,
                    widgetShown,
                    resetInitialValues,
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
                />
              );
            }
            if (!item.includedFilters) {
              return (
                <FiltersFrequent
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
                />
              );
            }
          })}
        </div>
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {string} windowType
 * @prop {func} resetInitialValues
 * @prop {string} [viewId]
 * @prop {*} [filtersActive]
 * @prop {*} [filterData]
 * @prop {*} [initialValuesNulled]
 * @prop {*} [updateDocList]
 */
Filters.propTypes = {
  windowType: PropTypes.string.isRequired,
  resetInitialValues: PropTypes.func.isRequired,
  viewId: PropTypes.string,
  updateDocList: PropTypes.any,
  filtersActive: PropTypes.any,
  filterData: PropTypes.any,
  initialValuesNulled: PropTypes.any,
  allowOutsideClick: PropTypes.bool,
  modalVisible: PropTypes.bool,
  filterId: PropTypes.string,
  updateWidgetShown: PropTypes.func,
  updateActiveFilter: PropTypes.func,
  filters: PropTypes.object,
  clearAllFilters: PropTypes.func,
};

const mapStateToProps = (state, ownProps) => {
  const { allowOutsideClick, modal } = state.windowHandler;
  const { viewId, windowType: windowId } = ownProps;
  const filterId = getEntityRelatedId({ windowId, viewId });

  return {
    allowOutsideClick,
    modalVisible: modal.visible,
    filters: windowId && viewId && state.filters ? state.filters[filterId] : {},
    filterId,
  };
};

export default connect(
  mapStateToProps,
  {
    updateWidgetShown,
    updateActiveFilter,
    clearAllFilters,
  }
)(Filters);
