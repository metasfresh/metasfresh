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

const mainFilterClasses = `btn btn-filter btn-meta-outline-secondary btn-sm toggle-filters`;

/**
 * @file Class based component.
 * @module FiltersFrequent
 * @extends PureComponent
 */
class FiltersNotIncluded extends PureComponent {
  state = { openFilterId: null };
  deviceType = null;

  constructor(props) {
    super(props);

    this.deviceType = currentDevice.type;
  }

  /**
   * @method toggleFilter
   * @summary Updates in the state the openFilterId with the given filter index (matched from the filtersActive)
   * @param {integer} index - position that corresponds to a filter in the filtersActive
   */
  toggleFilter = (index) => this.setState({ openFilterId: index });

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
    if (allowOutsideClick) {
      !widgetShown && this.toggleFilter(null);
      dropdownToggled();
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
      filterId,
    } = this.props;
    const { openFilterId } = this.state;

    return (
      <div className="filter-wrapper filters-frequent">
        {data.map((item, index) => {
          if (!item.parameters) {
            return false;
          }
          const parameter = item.parameters[0];
          const filterType = parameter.widgetType;
          const dateStepper =
            // keep implied information (e.g. for refactoring)
            item.frequent &&
            item.parameters.length === 1 &&
            parameter.showIncrementDecrementButtons &&
            item.isActive &&
            DATE_FIELD_TYPES.includes(filterType) &&
            !TIME_FIELD_TYPES.includes(filterType);

          const isActive =
            item.isActive && activeFiltersCaptions[item.filterId]
              ? activeFiltersCaptions[item.filterId]
              : null;

          if (item.inlineRenderMode === 'button') {
            return (
              <div className="filter-wrapper filter-inline" key={index}>
                {dateStepper && (
                  <FiltersDateStepper
                    active={this.findActiveFilter(item.filterId)}
                    applyFilters={applyFilters}
                    filter={item}
                  />
                )}

                <button
                  onClick={() => this.toggleFilter(item.filterId)}
                  className={cx(mainFilterClasses, {
                    ['btn-select']: openFilterId === index,
                    ['btn-active']: isActive,
                    ['btn-distance']: !dateStepper,
                  })}
                  tabIndex={modalVisible ? -1 : 0}
                >
                  <i className="meta-icon-preview" />
                  {`${
                    this.deviceType === 'desktop'
                      ? counterpart.translate('window.filters.caption2')
                      : ''
                  }: ${item.caption}`}
                </button>

                {dateStepper && (
                  <FiltersDateStepper
                    active={this.findActiveFilter(item.filterId)}
                    applyFilters={applyFilters}
                    filter={item}
                    next
                  />
                )}

                {openFilterId === item.filterId && (
                  <FiltersItem
                    captionValue={item.captionValue}
                    key={index}
                    windowType={windowType}
                    data={item}
                    closeFilterMenu={this.toggleFilter}
                    clearFilters={clearFilters}
                    applyFilters={applyFilters}
                    notValidFields={notValidFields}
                    isActive={item.isActive}
                    active={active}
                    onShow={this.handleShowTrue}
                    onHide={this.handleShowFalse}
                    viewId={viewId}
                    outsideClick={this.outsideClick}
                    openedFilter={true}
                    filtersWrapper={this.props.filtersWrapper}
                  />
                )}
              </div>
            );
          }

          return (
            <div key={index} className="inline-filters">
              {item.parameters &&
                item.parameters.map((filter, idx) => (
                  <InlineFilterItem
                    captionValue={filter.captionValue}
                    key={idx}
                    id={idx}
                    parentFilter={item}
                    windowType={windowType}
                    data={filter}
                    clearFilters={clearFilters}
                    applyFilters={applyFilters}
                    notValidFields={notValidFields}
                    isActive={filter.isActive}
                    active={active}
                    onShow={this.handleShowTrue}
                    onHide={this.handleShowFalse}
                    viewId={viewId}
                    outsideClick={this.outsideClick}
                    filterId={filterId}
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
};

export default onClickOutside(FiltersNotIncluded);
