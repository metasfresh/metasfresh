import counterpart from 'counterpart';
import React, { PureComponent, Fragment } from 'react';
import PropTypes from 'prop-types';
import onClickOutside from 'react-onclickoutside';
import classnames from 'classnames';
import { getItemsByProperty } from '../../utils';
import FiltersItem from './FiltersItem';

/**
 * @file Class based component.
 * @module FiltersIncluded
 * @extends Component
 */
class FiltersIncluded extends PureComponent {
  state = { isOpenDropdown: false, openFilterId: null };

  /**
   * @method handleClickOutside
   * @summary As the name suggests this is a handler for the case when user clicks outside the component
   *          No action is done in case the target container contains input-dropdown-list
   * @param {object} target element
   */
  handleClickOutside = ({ target }) => {
    const { widgetShown, allowOutsideClick } = this.props;
    const { isOpenDropdown } = this.state;

    if (target.classList && target.classList.contains('input-dropdown-list')) {
      return;
    }

    if (allowOutsideClick && !widgetShown && isOpenDropdown) {
      this.toggleDropdown(false);
      this.toggleFilter(null);
    }
  };

  /**
   * @method toggleDropdown
   * @summary Toggles the dropdown. Executed when you click on a filter header and the dropdown should show up
   *          This also updates the openFilterId content after it does some checks
   * @param {boolean} isOpenDropdown
   */
  toggleDropdown = (isOpenDropdown) => {
    const { active, data, dropdownToggled } = this.props;
    const toCheckAgainst = data.map((item) => item.filterId);
    let openFilterIdValue = null;

    if (active !== null) {
      const foundInActive = active.filter((activeItem) =>
        toCheckAgainst.includes(activeItem.filterId)
      );
      openFilterIdValue =
        foundInActive.length && active ? active[0].filterId : null;
    }

    // If we are asked to open the dropdown and there is no filter found to be opened and if we have only one filter,
    // then open it
    if (isOpenDropdown && !openFilterIdValue) {
      if (data && data.length === 1 && data[0].filterId) {
        openFilterIdValue = data[0].filterId;
      }
    }

    // when hiding the dropdown invalidate the filter fields in the store
    if (!isOpenDropdown) {
      dropdownToggled();
    }

    this.setState({
      isOpenDropdown,
      openFilterId: openFilterIdValue,
    });
  };

  /**
   * @method toggleFilter
   * @summary Updates in the state the openFilterId with the given filter name (matched from the filtersActive)
   * @param {string} id - name of the in the filtersActive array
   */
  toggleFilter = (id) => this.setState({ openFilterId: id });

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

    const { isOpenDropdown, openFilterId } = this.state;
    const openFilter = getItemsByProperty(data, 'filterId', openFilterId)[0];
    const activeFilters = data.filter((filter) => filter.isActive);
    const activeFilter = activeFilters.length && activeFilters[0];

    const captions =
      (activeFilter &&
        activeFiltersCaptions &&
        activeFiltersCaptions[activeFilter.filterId]) ||
      [];
    let panelCaption = activeFilter.isActive ? activeFilter.caption : '';
    let buttonCaption = activeFilter.isActive
      ? activeFilter.caption
      : counterpart.translate('window.filters.noActiveFilter.caption', {
          fallback: 'Filter',
        });

    if (captions.length) {
      buttonCaption = captions[0];
      panelCaption = captions[1];
    }

    return (
      <div className="filter-wrapper filters-not-frequent">
        <button
          onClick={() => this.toggleDropdown(!isOpenDropdown)}
          className={classnames(
            'btn btn-filter btn-meta-outline-secondary toggle-filters',
            'btn-distance btn-sm',
            {
              'btn-select': isOpenDropdown,
              'btn-active':
                activeFilter && !allChildFiltersCleared
                  ? activeFilter.isActive
                  : false,
            }
          )}
          title={buttonCaption}
          tabIndex={modalVisible ? -1 : 0}
        >
          <i className="meta-icon-preview" />
          {activeFilter && !allChildFiltersCleared ? (
            activeFilter.parameters &&
            activeFilter.parameters.length === 1 &&
            activeFilter.captionValue ? (
              <Fragment>
                {`${activeFilter.caption}: `}
                {activeFilter.captionValue}
              </Fragment>
            ) : (
              `${counterpart.translate(
                'window.filters.caption2'
              )}: ${buttonCaption}`
            )
          ) : (
            `${counterpart.translate('window.filters.noActiveFilter.caption', {
              fallback: 'Filter',
            })}`
          )}
        </button>

        {isOpenDropdown && (
          <div className="filters-overlay">
            {!openFilterId ? (
              <ul className="filter-menu">
                {data.map((item, index) => (
                  <li
                    className={`filter-option-${item.filterId}`}
                    key={index}
                    onClick={() => this.toggleFilter(item.filterId)}
                  >
                    {item.caption}
                  </li>
                ))}
              </ul>
            ) : (
              <FiltersItem
                {...{
                  panelCaption,
                  windowType,
                  active,
                  viewId,
                  applyFilters,
                  clearFilters,
                }}
                captionValue={activeFilter.captionValue}
                data={
                  activeFilter.isActive && !Array.isArray(activeFilter.isActive)
                    ? activeFilter
                    : openFilter
                }
                closeFilterMenu={() => this.toggleDropdown(false)}
                returnBackToDropdown={() => this.toggleFilter(null)}
                notValidFields={notValidFields}
                isActive={activeFilter.isActive}
                onShow={this.handleShowTrue}
                onHide={this.handleShowFalse}
                openedFilter={true}
                filtersWrapper={this.props.filtersWrapper}
                allChildFiltersCleared={allChildFiltersCleared}
              />
            )}
          </div>
        )}
      </div>
    );
  }
}

FiltersIncluded.propTypes = {
  allowOutsideClick: PropTypes.bool.isRequired,
  modalVisible: PropTypes.bool.isRequired,
  filtersWrapper: PropTypes.any,
  activeFiltersCaptions: PropTypes.object,
  data: PropTypes.any,
  windowType: PropTypes.any,
  notValidFields: PropTypes.any,
  viewId: PropTypes.any,
  handleShow: PropTypes.any,
  applyFilters: PropTypes.any,
  clearFilters: PropTypes.any,
  active: PropTypes.any,
  widgetShown: PropTypes.any,
  dropdownToggled: PropTypes.any,
  allChildFiltersCleared: PropTypes.bool, // indicator used to know when all the child filters of a filter were cleared
};

export default onClickOutside(FiltersIncluded);
