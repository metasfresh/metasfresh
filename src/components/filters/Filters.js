import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';

import { filtersToMap } from '../../utils/documentListHelper';
import TableCell from '../table/TableCell';
import FiltersFrequent from './FiltersFrequent';
import FiltersNotFrequent from './FiltersNotFrequent';

class Filters extends Component {
  state = {
    activeFilter: null,
    activeFiltersCaptions: {},
    notValidFields: null,
    widgetShown: false,
  };

  componentWillReceiveProps() {
    this.parseActiveFilters();
  }

  componentDidMount() {
    this.parseActiveFilters();
  }

  // PARSING FILTERS ---------------------------------------------------------
  parseActiveFilters = () => {
    let { filtersActive, filterData } = this.props;
    const activeFiltersCaptions = {};

    // find any filters with default values first and extend
    // activeFilters with them
    filterData.forEach((filter, filterId) => {
      filter.parameters &&
        filter.parameters.forEach(({ defaultValue, parameterName }) => {
          if (defaultValue) {
            const isActive = filtersActive.has(filterId);

            if (!isActive) {
              filtersActive = filtersActive.set(filterId, {
                filterId,
                parameters: [],
              });
            }

            const singleActiveFilter = filtersActive.get(filterId);
            const paramsArray = singleActiveFilter.parameters;
            let length = paramsArray.length,
              extendedParams = [],
              seen = new Set();

            if (!paramsArray.length) {
              extendedParams.push({
                parameterName,
                value: defaultValue,
              });
              seen.add(parameterName)
            }

            outer: for (let index = 0; index < length; index += 1) {
              let name = paramsArray[index].parameterName;

              if (seen.has(name) || !paramsArray[index].defaultValue) {
                continue outer;
              }

              seen.add(name);
              extendedParams.push({
                parameterName,
                value: defaultValue,
              });
            }

            filtersActive = filtersActive.set(filterId, {
              filterId,
              parameters: extendedParams,
            });
          }
        });
    });

    if (filtersActive.size) {
      filtersActive.forEach((filter, filterId) => {
        const captionsArray = ['', ''];

        filter.parameters.forEach(({ value, parameterName }) => {
          const parentFilter = filterData.get(filterId);
          const filterParameter = parentFilter.parameters.find(
            param => param.parameterName === parameterName
          );
          let captionName = filterParameter.caption;
          let itemCaption = filterParameter.caption;

          switch (filterParameter.widgetType) {
            case 'Text':
              captionName = value;
              break;
            case 'List':
              captionName = value.caption;
              break;
            case 'Labels':
              captionName = value.values.reduce((caption, item) => {
                return `${caption}, ${item.caption}`;
              }, '');
              break;
            case 'YesNo':
            case 'Switch':
            default:
              break;
          }
          captionsArray[0] = captionsArray[0]
            ? `${captionsArray[0]}, ${captionName}`
            : captionName;
          captionsArray[1] = captionsArray[1]
            ? `${captionsArray[1]}, ${itemCaption}`
            : itemCaption;
        });

        activeFiltersCaptions[filterId] = captionsArray;
      });

      this.setState({
        activeFilter: filtersActive.toIndexedSeq().toArray(),
        activeFiltersCaptions,
      });
    }
  };

  sortFilters = data => {
    return {
      frequentFilters: this.annotateFilters(
        data
          .filter(filter => filter.frequent)
          .toIndexedSeq()
          .toArray()
      ),
      notFrequentFilters: this.annotateFilters(
        data
          .filter(filter => !filter.frequent && !filter.static)
          .toIndexedSeq()
          .toArray()
      ),
      staticFilters: this.annotateFilters(data.filter(filter => filter.static))
        .toIndexedSeq()
        .toArray(),
    };
  };

  isFilterValid = filters => {
    if (filters.parameters) {
      return !filters.parameters.filter(item => item.mandatory && !item.value)
        .length;
    }

    return true;
  };

  isFilterActive = filterId => {
    const { activeFilter } = this.state;

    if (activeFilter) {
      const active = activeFilter.find(item => item.filterId === filterId);

      return typeof active !== 'undefined';
    }

    return false;
  };

  parseToPatch = params => {
    return params.map(param => ({
      ...param,
      value: param.value === '' ? null : param.value,
    }));
  };

  // SETTING FILTERS  --------------------------------------------------------

  /*
     *   This method should update docList
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

  setFilterActive = filterToAdd => {
    const { updateDocList } = this.props;
    const { activeFilter } = this.state;
    let newFilter;

    if (activeFilter) {
      newFilter = activeFilter.filter(
        item => item.filterId !== filterToAdd.filterId
      );
      newFilter.push(filterToAdd);
    } else {
      newFilter = [filterToAdd];
    }

    const filtersMap = filtersToMap(newFilter);

    this.setState(
      {
        activeFilter: newFilter,
      },
      () => {
        updateDocList(filtersMap);
      }
    );
  };

  /*
     *  Method to lock backdrop, to do not close on click onClickOutside
     *  widgets that are bigger than filter wrapper
     */
  handleShow = value => {
    this.setState({
      widgetShown: value,
    });
  };

  clearFilters = filterToClear => {
    const { updateDocList } = this.props;
    const { activeFilter } = this.state;

    if (activeFilter) {
      let newFilter = activeFilter.filter(
        item => item.filterId !== filterToClear.filterId
      );

      this.setState(
        {
          activeFilter: newFilter,
        },
        () => {
          updateDocList(newFilter);
        }
      );
    }
  };

  dropdownToggled = () => {
    this.setState({
      notValidFields: false,
    });
  };

  annotateFilters = unannotatedFilters => {
    const { activeFilter } = this.state;

    return unannotatedFilters.map(unannotatedFilter => {
      const parameter =
        unannotatedFilter.parameters && unannotatedFilter.parameters[0];
      const filterType = parameter && parameter.widgetType;
      const isActive = this.isFilterActive(unannotatedFilter.filterId);
      const currentFilter = activeFilter
        ? activeFilter.find(f => f.filterId === unannotatedFilter.filterId)
        : null;
      const activeParameter =
        parameter && isActive && currentFilter && currentFilter.parameters[0];
      const captionValue = activeParameter
        ? TableCell.fieldValueToString(
            activeParameter.valueTo
              ? [activeParameter.value, activeParameter.valueTo]
              : activeParameter.value,
            filterType
          )
        : '';

      return {
        ...unannotatedFilter,
        captionValue,
        isActive,
      };
    });
  };

  // RENDERING FILTERS -------------------------------------------------------

  render() {
    const { filterData, windowType, viewId } = this.props;
    const { frequentFilters, notFrequentFilters } = this.sortFilters(
      filterData
    );
    const {
      notValidFields,
      widgetShown,
      activeFilter,
      activeFiltersCaptions,
    } = this.state;

    return (
      <div
        className="filter-wrapper js-not-unselect"
        ref={c => (this.filtersWrapper = c)}
      >
        <span className="filter-caption">
          {`${counterpart.translate('window.filters.caption')}: `}
        </span>
        <div className="filter-wrapper">
          {!!frequentFilters.length && (
            <FiltersFrequent
              {...{
                activeFiltersCaptions,
                windowType,
                notValidFields,
                viewId,
                widgetShown,
              }}
              data={frequentFilters}
              handleShow={this.handleShow}
              applyFilters={this.applyFilters}
              clearFilters={this.clearFilters}
              active={activeFilter}
              dropdownToggled={this.dropdownToggled}
              filtersWrapper={this.filtersWrapper}
            />
          )}
          {!!notFrequentFilters.length && (
            <FiltersNotFrequent
              {...{
                activeFiltersCaptions,
                windowType,
                notValidFields,
                viewId,
                widgetShown,
              }}
              data={notFrequentFilters}
              handleShow={this.handleShow}
              applyFilters={this.applyFilters}
              clearFilters={this.clearFilters}
              active={activeFilter}
              dropdownToggled={this.dropdownToggled}
              filtersWrapper={this.filtersWrapper}
            />
          )}
        </div>
      </div>
    );
  }
}

Filters.propTypes = {
  windowType: PropTypes.string.isRequired,
};

export default Filters;
