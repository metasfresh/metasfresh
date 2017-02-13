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
        const {widgetShown} = this.props;
        if(!widgetShown) {
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

        const {isOpenDropdown, openFilterId} = this.state;

        const openFilter = getItemsByProperty(data, "filterId", openFilterId)[0];
        const activeFilter = active && getItemsByProperty(data, "filterId", active.filterId)[0];
        const isActive = !!activeFilter;

        return (
            <div className="filter-wrapper">
                <button
                    onClick={() => this.toggleDropdown(true)}
                    className={
                        "btn btn-filter btn-meta-outline-secondary btn-distance btn-sm" +
                        (isOpenDropdown ? " btn-select": "") +
                        (isActive ? " btn-active" : "")
                    }
                >
                    <i className="meta-icon-preview" />
                    { isActive ? 'Filter: ' + activeFilter.caption : 'Select other filter'}
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
                                data={openFilter || activeFilter}
                                closeFilterMenu={() => this.toggleDropdown(false)}
                                returnBackToDropdown={() => this.toggleFilter(null)}
                                clearFilters={clearFilters}
                                applyFilters={applyFilters}
                                notValidFields={notValidFields}
                                isActive={isActive}
                                active={active}
                                onShow={() => handleShow(true)}
                                onHide={() => handleShow(false)}
                                viewId={viewId}
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
