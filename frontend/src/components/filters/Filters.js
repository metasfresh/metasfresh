import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
// -- using iMap from immutable
import { Map as iMap } from 'immutable';
import _ from 'lodash';

import { DATE_FIELDS } from '../../constants/Constants';
import {
  generateMomentObj,
  getFormatForDateField,
} from '../widget/RawWidgetHelpers';
import { parseDateWithCurrentTimezone } from '../../utils/documentListHelper';
import { fieldValueToString } from '../../utils/tableHelpers';
import FiltersFrequent from './FiltersFrequent';
import FiltersNotFrequent from './FiltersNotFrequent';

/**
 * @method parseDateToReadable
 * @summary ToDo: Describe the method
 * @param {*} widgetType
 * @param {*} value
 * @todo Write the documentation
 */
export function parseDateToReadable(widgetType, value) {
  if (DATE_FIELDS.indexOf(widgetType) > -1) {
    return parseDateWithCurrentTimezone(value, widgetType);
  }
  return value;
}

/**
 * @file Class based component.
 * @module Filters
 * @extends Component
 */
class Filters extends PureComponent {
  state = {
    activeFilter: null,
    activeFiltersCaptions: null,
    flatFiltersMap: null,
    notValidFields: null,
    widgetShown: false,
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
    const flatFiltersMap = {};
    const activeFiltersCaptions = {};

    // find any filters with default values first and extend
    // activeFilters with them
    filtersData.forEach((filter, filterId) => {
      if (filter.parameters) {
        outerParameters: for (let parameter of filter.parameters) {
          const { defaultValue, parameterName, widgetType } = parameter;
          const nulledFilter = initialValuesNulled.get(filterId);

          flatFiltersMap[`${filterId}-${parameterName}`] = {
            widgetType,
          };

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
        flatFiltersMap,
      });
    } else {
      this.setState({
        activeFilter: null,
        activeFiltersCaptions: null,
        flatFiltersMap,
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
   * @method sortFilters
   * @summary ToDo: Describe the method
   * @param {array} data
   */
  sortFilters = (data) => {
    return {
      frequentFilters: this.annotateFilters(
        data
          .filter((filter) => filter.frequent)
          .toIndexedSeq()
          .toArray()
      ),
      notFrequentFilters: this.annotateFilters(
        data
          .filter((filter) => !filter.frequent && !filter.static)
          .toIndexedSeq()
          .toArray()
      ),
      staticFilters: this.annotateFilters(
        data.filter((filter) => filter.static)
      )
        .toIndexedSeq()
        .toArray(),
    };
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
   * @method isFilterActive
   * @summary ToDo: Describe the method
   * @param {*} filterId
   */
  isFilterActive = (filterId) => {
    const { activeFilter } = this.state;

    if (activeFilter) {
      // filters with only defaultValues shouldn't be set to active
      const active = activeFilter.find(
        (item) => item.filterId === filterId && !item.defaultVal
      );

      return typeof active !== 'undefined';
    }

    return false;
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
   * @summary This function merges new filters that are to be activated with the existing
   *  active filters. Additionally we format date fields accordingly so that the backend
   *  accepts them.
   * @param {object} filterToAdd
   */
  setFilterActive = (filterToAdd) => {
    const { updateDocList } = this.props;
    let { filtersActive } = this.props;
    const { flatFiltersMap } = this.state;
    let activeFilters = iMap(filtersActive);

    activeFilters = activeFilters.filter(
      (item, id) => id !== filterToAdd.filterId
    );
    activeFilters = activeFilters.set(filterToAdd.filterId, filterToAdd);

    if (flatFiltersMap) {
      activeFilters = activeFilters.map((filter, filterId) => {
        filter.parameters &&
          filter.parameters.forEach((parameter) => {
            const { value, valueTo, parameterName } = parameter;
            const singleFilter = flatFiltersMap[`${filterId}-${parameterName}`];

            if (
              singleFilter &&
              DATE_FIELDS.indexOf(singleFilter.widgetType) > -1
            ) {
              const format = getFormatForDateField(singleFilter.widgetType);

              if (value) {
                parameter.value = generateMomentObj(value, format);
              }
              if (valueTo) {
                parameter.valueTo = generateMomentObj(valueTo, format);
              }
            }
          });

        return filter;
      });
    }

    updateDocList(activeFilters);
  };

  /**
   * @method handleShow
   * @summary Method to lock backdrop, to do not close on click onClickOutside
   *  widgets that are bigger than filter wrapper
   * @param {*} value
   */
  handleShow = (value) => {
    this.setState({
      widgetShown: value,
    });
  };

  /**
   * @method clearFilters
   * @summary ToDo: Describe the method
   * @param {*} filterToClear
   */
  clearFilters = (filterToClear, propertyName) => {
    const { updateDocList } = this.props;
    let { filtersActive } = this.props;
    let activeFilters = iMap(filtersActive);

    if (filtersActive.size) {
      activeFilters = activeFilters.filter((item, id) => {
        if (id === filterToClear.filterId) {
          if (propertyName && item.parameters && item.parameters.length) {
            const parametersCopy = item.parameters.filter(
              (param) => param.parameterName !== propertyName
            );

            if (parametersCopy.length > 0) {
              item.parameters = parametersCopy;

              return item;
            }
            return false;
          }
          return false;
        }
        return item;
      });
      updateDocList(activeFilters);
    }
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

  /**
   * @method annotateFilters
   * @summary I think it creates caption for active filters to show when the widget is closed - Kuba
   * @param {array} unannotatedFilters
   */
  annotateFilters = (unannotatedFilters) => {
    const { activeFilter } = this.state;

    return unannotatedFilters.map((unannotatedFilter) => {
      const parameter =
        unannotatedFilter.parameters && unannotatedFilter.parameters[0];
      const isActive = this.isFilterActive(unannotatedFilter.filterId);
      const currentFilter = activeFilter
        ? activeFilter.find((f) => f.filterId === unannotatedFilter.filterId)
        : null;
      const activeParameter =
        parameter && isActive && currentFilter && currentFilter.parameters[0];

      const filterType =
        unannotatedFilter.parameters && activeParameter
          ? unannotatedFilter.parameters.find(
              (filter) => filter.parameterName === activeParameter.parameterName
            )
          : parameter && parameter.widgetType;

      const captionValue = activeParameter
        ? fieldValueToString({
            fieldValue: activeParameter.valueTo
              ? [activeParameter.value, activeParameter.valueTo]
              : activeParameter.value,
            fieldType: filterType,
          })
        : '';

      return {
        ...unannotatedFilter,
        captionValue,
        isActive,
      };
    });
  };

  // RENDERING FILTERS -------------------------------------------------------
  /**
   * @method render
   * @summary ToDo: Describe the method
   */
  render() {
    const {
      filterData,
      windowType,
      viewId,
      resetInitialValues,
      allowOutsideClick,
      modalVisible,
    } = this.props;

    const {
      notValidFields,
      widgetShown,
      activeFilter,
      activeFiltersCaptions,
    } = this.state;

    const allFilters = this.annotateFilters(
      filterData.toIndexedSeq().toArray()
    );
    const flatActiveFilterIds =
      activeFilter !== null ? activeFilter.map((item) => item.filterId) : [];

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
                  active={activeFilter}
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
                  active={activeFilter}
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

  // this should be an immutable Map
  filtersActive: PropTypes.any,
  filterData: PropTypes.any,
  initialValuesNulled: PropTypes.any,
  allowOutsideClick: PropTypes.bool,
  modalVisible: PropTypes.bool,
};

const mapStateToProps = (state) => {
  const { allowOutsideClick, modal } = state.windowHandler;

  return {
    allowOutsideClick,
    modalVisible: modal.visible,
  };
};

export default connect(mapStateToProps)(Filters);
