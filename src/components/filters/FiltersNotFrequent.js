import React, { Component } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import FiltersItem from './FiltersItem';

import {
    getItemsByProperty
} from '../../actions/WindowActions';

class FiltersNotFrequent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isOpenDropdown: false,
            openFilterId: null
        }
    }

    handleClickOutside = () => {
       this.outsideClick();
    }

    outsideClick = () => {
        const {widgetShown, dropdownToggled} = this.props;
        if(!widgetShown) {
            dropdownToggled();
            this.toggleDropdown(false);
            this.toggleFilter(null);
        }
    }

    toggleDropdown = (value) => {
        this.setState({
            isOpenDropdown: value
        })
    }

    toggleFilter = (index) => {
        this.setState({
            openFilterId: index
        })
    }

    render() {
        const {
            data, windowType, notValidFields, viewId, handleShow,
            applyFilters, clearFilters, active
        } = this.props;

        const { isOpenDropdown, openFilterId } = this.state;

        const openFilter =
            getItemsByProperty(data, 'filterId', openFilterId)[0];

        let activeFilter, activeFilterModel;
        let activeFilterCaption = openFilter && openFilter.caption;
        if (active && data) {
            activeFilter = active.find( (item) => {
                return data.find( (dataItem) => {
                    if (dataItem.filterId === item.filterId) {
                        activeFilterModel = dataItem;
                        activeFilterCaption = dataItem.caption;
                    }

                    return (dataItem.filterId === item.filterId);
                });
            });
        }
        const isActive = !!activeFilter;

        return (
            <div className="filter-wrapper">
                <button
                    onClick={() => this.toggleDropdown(true)}
                    className={
                        'btn btn-filter btn-meta-outline-secondary ' +
                        'btn-distance btn-sm' +
                        (isOpenDropdown ? ' btn-select': '') +
                        (isActive ? ' btn-active' : '')
                    }
                >
                    <i className="meta-icon-preview" />
                    { activeFilter ? 'Filter: ' + activeFilterCaption : 'Filter'}
                </button>

                { isOpenDropdown &&
                    <div className="filters-overlay">
                        { (!openFilterId && !isActive) ?
                            <ul className="filter-menu">
                                {data.map((item, index) =>
                                    <li
                                        key={index}
                                        onClick={() =>
                                            this.toggleFilter(item.filterId)
                                        }
                                    >
                                        {item.caption}
                                    </li>
                                )}
                            </ul>
                            :
                            <FiltersItem
                                windowType={windowType}
                                data={activeFilterModel || openFilter}
                                closeFilterMenu={() =>
                                    this.toggleDropdown(false)
                                }
                                returnBackToDropdown={() =>
                                    this.toggleFilter(null)
                                }
                                clearFilters={clearFilters}
                                applyFilters={applyFilters}
                                notValidFields={notValidFields}
                                isActive={isActive}
                                active={active}
                                onShow={() => handleShow(true)}
                                onHide={() => handleShow(false)}
                                viewId={viewId}
                                outsideClick={this.outsideClick}
                            />
                        }
                    </div>
                }
            </div>
        );
    }
}

FiltersNotFrequent = connect()(onClickOutside(FiltersNotFrequent));

export default FiltersNotFrequent;
