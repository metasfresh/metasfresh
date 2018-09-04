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

  componentWillReceiveProps(props) {
    this.init(props.filtersActive);
  }

  componentDidMount() {
    const { filtersActive } = this.props;
    filtersActive && this.init(filtersActive);
  }

  init = filter => {
    if (filter) {
      this.parseActiveFilters();
    } else {
      this.setState({
        activeFilter: null,
        activeFiltersCaptions: {},
      });
    }
  };

  // PARSING FILTERS ---------------------------------------------------------
  parseActiveFilters = () => {
    const { filtersActive, filterData } = this.props;
    const activeFiltersCaptions = {};

    if (filtersActive.size) {
      filtersActive.forEach((filter, filterId) => {
        const captionsArray = ['', ''];

        console.log('active params ?: ', filter, filterId)

        filter.parameters.forEach(({ value, parameterName}) => {
          const parentFilter = filterData.get(filterId);
          const filterParameter = parentFilter.parameters.find(
            param => param.parameterName === parameterName
          );
          let captionName = filterParameter.caption;
          let itemCaption = filterParameter.caption;

          console.log('HE ?: ', value, parameterName, filterParameter)

          switch (filterParameter.widgetType) {
            case 'Text':
              captionName = value;
              // itemCaption = filterParameter.caption;
              break;
            case 'List':
              captionName = value.caption;
              // itemCaption = filterParameter.caption;
              break;
            case 'Labels':
            // values array, { key:, caption: }
              captionName = value.values.reduce((caption, item) => {
                return `${caption}, ${item.caption}`;
              }, '');
              // itemCaption = filterParameter.caption;
              break;
            case 'YesNo':
            case 'Switch':
            default:
              // captionName = filterParameter.caption;
              // itemCaption = filterParameter.caption;
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

      console.log('filtersactive: ', filtersActive.toIndexedSeq().toArray(), activeFiltersCaptions);

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

    console.log('APPLY: ', filter);

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

    console.log('new filter: ', { ...newFilter });

    const filtersMap = filtersToMap(newFilter);

    this.setState(
      {
        activeFilter: filtersMap,
        // activeFilter: filtersToMap(newFilter),
      },
      () => {
        // updateDocList(newFilter);
        updateDocList(filtersMap);
      }
    );
  };

    // const activeFilters = data.filter(filter => filter.isActive);
    // const activeFilter = activeFilters.length === 1 && activeFilters[0];

    // let caption = activeFilter ? activeFilter.caption : 'Filter';
    // if (activeFilter.captionValue && activeFilter.captionValue.length) {
    //   caption = activeFilter.captionValue;
    // }

    // // buttonCaption meta, foo, Active,
    //   // for textFields value
    //   // for switch 
    // // panelCaption Name, Search Key, Active

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
    const { notValidFields, widgetShown, activeFilter } = this.state;

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
              windowType={windowType}
              data={frequentFilters}
              notValidFields={notValidFields}
              viewId={viewId}
              handleShow={this.handleShow}
              widgetShown={widgetShown}
              applyFilters={this.applyFilters}
              clearFilters={this.clearFilters}
              active={activeFilter}
              dropdownToggled={this.dropdownToggled}
              filtersWrapper={this.filtersWrapper}
            />
          )}
          {!!notFrequentFilters.length && (
            <FiltersNotFrequent
              windowType={windowType}
              data={notFrequentFilters}
              notValidFields={notValidFields}
              viewId={viewId}
              handleShow={this.handleShow}
              widgetShown={widgetShown}
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
