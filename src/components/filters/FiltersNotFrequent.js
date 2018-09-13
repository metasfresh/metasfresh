import counterpart from 'counterpart';
import React, { Component, Fragment } from 'react';
import PropTypes from 'prop-types';
import onClickOutside from 'react-onclickoutside';
import classnames from 'classnames';
import { connect } from 'react-redux';

import { getItemsByProperty } from '../../actions/WindowActions';
import FiltersItem from './FiltersItem';

class FiltersNotFrequent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isOpenDropdown: false,
      openFilterId: null,
    };
  }

  handleClickOutside = ({ target }) => {
    const { widgetShown, dropdownToggled, allowOutsideClick } = this.props;

    if (target.classList && target.classList.contains('input-dropdown-list')) {
      return;
    }

    if (allowOutsideClick && !widgetShown) {
      dropdownToggled();
      this.toggleDropdown(false);
      this.toggleFilter(null);
    }
  };

  toggleDropdown = value => {
    this.setState({
      isOpenDropdown: value,
    });
  };

  toggleFilter = index => {
    this.setState({
      openFilterId: index,
    });
  };

  render() {
    const {
      data,
      windowType,
      notValidFields,
      viewId,
      handleShow,
      applyFilters,
      clearFilters,
      active,
      modalVisible,
      activeFiltersCaptions,
      resetInitialValues,
    } = this.props;

    const { isOpenDropdown, openFilterId } = this.state;
    const openFilter = getItemsByProperty(data, 'filterId', openFilterId)[0];
    const activeFilters = data.filter(filter => filter.isActive);
    const activeFilter = activeFilters.length === 1 && activeFilters[0];

    const captions =
      (activeFilter && activeFiltersCaptions[activeFilter.filterId]) || [];
    let panelCaption = activeFilter.isActive ? activeFilter.caption : '';
    let buttonCaption = activeFilter.isActive ? activeFilter.caption : 'Filter';

    if (captions.length) {
      buttonCaption = captions[0];
      panelCaption = captions[1];
    }

    return (
      <div className="filter-wrapper">
        <button
          onClick={() => this.toggleDropdown(true)}
          className={classnames(
            'btn btn-filter btn-meta-outline-secondary',
            'btn-distance btn-sm',
            {
              'btn-select': isOpenDropdown,
              'btn-active': captions.length,
            }
          )}
          title={buttonCaption}
          tabIndex={modalVisible ? -1 : 0}
        >
          <i className="meta-icon-preview" />
          {activeFilter ? (
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
            'Filter'
          )}
        </button>

        {isOpenDropdown && (
          <div className="filters-overlay">
            {!openFilterId && !activeFilter ? (
              <ul className="filter-menu">
                {data.map((item, index) => (
                  <li
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
                  resetInitialValues,
                  applyFilters,
                  clearFilters,
                }}
                captionValue={activeFilter.captionValue}
                data={activeFilter.isActive ? activeFilter : openFilter}
                closeFilterMenu={() => this.toggleDropdown(false)}
                returnBackToDropdown={() => this.toggleFilter(null)}
                notValidFields={notValidFields}
                isActive={activeFilter.isActive}
                onShow={() => handleShow(true)}
                onHide={() => handleShow(false)}
                outsideClick={this.outsideClick}
                openedFilter={true}
                filtersWrapper={this.props.filtersWrapper}
              />
            )}
          </div>
        )}
      </div>
    );
  }
}

FiltersNotFrequent.propTypes = {
  allowOutsideClick: PropTypes.bool.isRequired,
  resetInitialValues: PropTypes.func.isRequired,
  modalVisible: PropTypes.bool.isRequired,
  filtersWrapper: PropTypes.any,
  activeFiltersCaptions: PropTypes.object,
};

const mapStateToProps = ({ windowHandler }) => {
  const { allowOutsideClick, modal } = windowHandler;

  return {
    allowOutsideClick,
    modalVisible: modal.visible,
  };
};

export default connect(mapStateToProps)(onClickOutside(FiltersNotFrequent));
