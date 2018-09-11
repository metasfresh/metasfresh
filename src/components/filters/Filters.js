import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';

import TableCell from '../table/TableCell';
import FiltersFrequent from './FiltersFrequent';
import FiltersNotFrequent from './FiltersNotFrequent';

class Filters extends Component {
  state = {
    filter: null,
    notValidFields: null,
    widgetShown: false,
  };

  componentWillReceiveProps(props) {
    const { filtersActive } = props;

    this.init(filtersActive ? filtersActive : null);
  }

  componentDidMount() {
    const { filtersActive } = this.props;
    filtersActive && this.init(filtersActive);
  }

  init = filter => {
    this.setState({
      filter: filter,
    });
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
            ? Object.assign({}, filter, {
                parameters: this.parseToPatch(filter.parameters),
              })
            : filter;

          this.setFilterActive(parsedFilter);

          cb && cb();
        }
      }
    );
  };

  setFilterActive = filterToAdd => {
    const { updateDocList } = this.props;
    const { filter } = this.state;
    let newFilter;

    if (filter) {
      newFilter = filter.filter(item => item.filterId !== filterToAdd.filterId);
      newFilter.push(filterToAdd);
    } else {
      newFilter = [filterToAdd];
    }

    this.setState(
      {
        filter: newFilter,
      },
      () => {
        updateDocList(newFilter);
      }
    );
  };

  /*
     *  Mehod to lock backdrop, to do not close on click onClickOutside
     *  widgets that are bigger than filter wrapper
     */
  handleShow = value => {
    this.setState({
      widgetShown: value,
    });
  };

  clearFilters = filterToClear => {
    const { updateDocList } = this.props;
    const { filter } = this.state;

    if (filter) {
      let newFilter = filter.filter(
        item => item.filterId !== filterToClear.filterId
      );

      this.setState(
        {
          filter: newFilter,
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
    const { filter } = this.state;

    return unannotatedFilters.map(unannotatedFilter => {
      const parameter =
        unannotatedFilter.parameters && unannotatedFilter.parameters[0];
      const filterType = parameter && parameter.widgetType;
      const isActive = this.isFilterActive(unannotatedFilter.filterId);
      const currentFilter = filter
        ? filter.find(f => f.filterId === unannotatedFilter.filterId)
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

  // PARSING FILTERS ---------------------------------------------------------

  sortFilters = data => {
    return {
      frequentFilters: this.annotateFilters(
        data.filter(filter => filter.frequent)
      ),
      notFrequentFilters: this.annotateFilters(
        data.filter(filter => !filter.frequent && !filter.static)
      ),
      staticFilters: this.annotateFilters(data.filter(filter => filter.static)),
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
    const { filter } = this.state;

    if (filter) {
      const activeFilter = filter.find(item => item.filterId === filterId);
      return typeof activeFilter !== 'undefined';
    }

    return false;
  };

  parseToPatch = params => {
    return params.map(param =>
      Object.assign({}, param, {
        value: param.value === '' ? null : param.value,
      })
    );
  };

  // RENDERING FILTERS -------------------------------------------------------

  render() {
    const { filterData, windowType, viewId } = this.props;
    const { frequentFilters, notFrequentFilters } = this.sortFilters(
      filterData
    );
    const { notValidFields, widgetShown, filter } = this.state;

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
              active={filter}
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
              active={filter}
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
