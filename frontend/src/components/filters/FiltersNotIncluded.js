import cx from 'classnames';
import counterpart from 'counterpart';
import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import onClickOutside from 'react-onclickoutside';
import currentDevice from 'current-device';
import FiltersDateStepper from './FiltersDateStepper';
import FiltersItem from './FiltersItem';
import InlineFilterItem from './InlineFilterItem';
import { DATE_FIELD_TYPES, TIME_FIELD_TYPES } from '../../constants/Constants';
import { find } from 'lodash';

const mainFilterClasses = `btn btn-filter btn-meta-outline-secondary btn-sm toggle-filters`;

/**
 * @file Class based component.
 * @module FiltersFrequent
 * @extends PureComponent
 */
class FiltersNotIncluded extends PureComponent {
  state = { openFilterIdx: -1 };
  deviceType = null;

  constructor(props) {
    super(props);

    this.deviceType = currentDevice.type;
  }

  /**
   * @method toggleFilter
   * @summary Updates in the state the openFilterIdx with the given filter index (matched from the filtersActive)
   * @param index - position that corresponds to a filter in the filtersActive
   */
  toggleFilter = (index) => this.setState({ openFilterIdx: index });

  /**
   * @method findActiveFilter
   * @summary Returns the active object filter from the array of active filters by the given id passed as parameter
   * @param {*} id
   */
  findActiveFilter = (id) => {
    const { active } = this.props;

    return active ? active.find((item) => item.filterId === id) : null;
  };

  /**
   * @method handleClickOutside
   * @summary closes the dropdown in case some click outside event happen
   */
  handleClickOutside = () => {
    const { widgetShown, dropdownToggled, allowOutsideClick } = this.props;
    const { openFilterIdx } = this.state;

    if (allowOutsideClick) {
      if (openFilterIdx > -1) {
        !widgetShown && this.toggleFilter(-1);
        dropdownToggled();
      }
    }
  };

  // wrappers around props.handleShow to skip creating anonymous functions on render
  handleShowTrue = () => this.props.handleShow(true);
  handleShowFalse = () => this.props.handleShow(false);

  render() {
    const {
      data,
      windowType,
      notValidFields,
      viewId,
      applyFilters,
      clearFilters,
      active,
      modalVisible,
      activeFiltersCaptions,
      allChildFiltersCleared,
    } = this.props;
    const { openFilterIdx } = this.state;

    return (
      <div className="filter-wrapper filters-frequent">
        {data.map((filter, index) => {
          if (!filter.parameters) {
            return false;
          }
          const firstParameter = filter.parameters[0];
          const filterType = firstParameter.widgetType;
          const dateStepper =
            // keep implied information (e.g. for refactoring)
            filter.frequent &&
            filter.parameters.length === 1 &&
            firstParameter.showIncrementDecrementButtons &&
            filter.isActive &&
            DATE_FIELD_TYPES.includes(filterType) &&
            !TIME_FIELD_TYPES.includes(filterType);

          const isActive =
            filter.isActive && activeFiltersCaptions[filter.filterId]
              ? activeFiltersCaptions[filter.filterId]
              : null;

          if (filter.inlineRenderMode === 'button') {
            return (
              <div className="filter-wrapper filter-inline" key={index}>
                {dateStepper && (
                  <FiltersDateStepper
                    active={this.findActiveFilter(filter.filterId)}
                    applyFilters={applyFilters}
                    filter={filter}
                  />
                )}

                <button
                  onClick={() => this.toggleFilter(index)}
                  className={cx(mainFilterClasses, {
                    ['btn-select']: openFilterIdx === index,
                    ['btn-active']: isActive && !allChildFiltersCleared,
                    ['btn-distance']: !dateStepper,
                  })}
                  tabIndex={modalVisible ? -1 : 0}
                >
                  <i className="meta-icon-preview" />
                  {`${
                    this.deviceType === 'desktop'
                      ? counterpart.translate('window.filters.caption2')
                      : ''
                  }: ${filter.caption}`}
                </button>

                {dateStepper && (
                  <FiltersDateStepper
                    active={this.findActiveFilter(filter.filterId)}
                    applyFilters={applyFilters}
                    filter={filter}
                    next
                  />
                )}

                {openFilterIdx === index && (
                  <FiltersItem
                    captionValue={filter.captionValue}
                    key={index}
                    windowType={windowType}
                    data={filter}
                    closeFilterMenu={this.toggleFilter}
                    clearFilters={clearFilters}
                    applyFilters={applyFilters}
                    notValidFields={notValidFields}
                    isActive={filter.isActive}
                    active={active}
                    onShow={this.handleShowTrue}
                    onHide={this.handleShowFalse}
                    viewId={viewId}
                    outsideClick={this.outsideClick}
                    openedFilter={true}
                    filtersWrapper={this.props.filtersWrapper}
                    allChildFiltersCleared={allChildFiltersCleared}
                  />
                )}
              </div>
            );
          }

          return (
            <div key={index} className="inline-filters">
              {filter.parameters &&
                filter.parameters.map((parameter, idx) => (
                  <InlineFilterItem
                    key={`${idx}_${filter.filterId}`}
                    id={idx}
                    //rootFilterId={filterId}
                    filter={filter}
                    filterParameter={parameter}
                    filterData={
                      active && active.length
                        ? find(active, { filterId: filter.filterId })
                        : null
                    }
                    filtersActive={active}
                    windowId={windowType}
                    viewId={viewId}
                    clearFilters={clearFilters}
                    applyFilters={applyFilters}
                    onShow={this.handleShowTrue}
                    onHide={this.handleShowFalse}
                  />
                ))}
            </div>
          );
        })}
      </div>
    );
  }
}

FiltersNotIncluded.propTypes = {
  allowOutsideClick: PropTypes.bool.isRequired,
  modalVisible: PropTypes.bool.isRequired,
  active: PropTypes.array,
  filtersWrapper: PropTypes.any,
  activeFiltersCaptions: PropTypes.object,
  data: PropTypes.any,
  windowType: PropTypes.any,
  notValidFields: PropTypes.any,
  viewId: PropTypes.any,
  handleShow: PropTypes.any,
  applyFilters: PropTypes.any,
  clearFilters: PropTypes.any,
  widgetShown: PropTypes.any,
  dropdownToggled: PropTypes.any,
  filterId: PropTypes.string,
  allChildFiltersCleared: PropTypes.bool, // indicator used to know when all the child filters of a filter were cleared
};

export default onClickOutside(FiltersNotIncluded);
