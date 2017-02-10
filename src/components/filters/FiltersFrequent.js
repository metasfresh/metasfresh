import React, { Component } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import FiltersItem from './FiltersItem';

import {
    getItemsByProperty
} from '../../actions/WindowActions';

class FiltersFrequent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            openFilterId: null
        }
    }

    toggleFilter = (index, item) => {
        this.setState({
            openFilterId: index
        })
    }

    handleClickOutside = () => {
        const {widgetShown} = this.props;
        !widgetShown && this.toggleFilter(null, null);
    }

    render() {
        const {
            data, windowType, notValidFields, viewId, handleShow,
            applyFilters, clearFilters, active
        } = this.props;

        const {openFilterId} = this.state;

        return (
            <div className="filter-wrapper">
                {data.map((item, index) => {
                    const isActive = active && (active.filterId == item.filterId);
                    return (
                        <div className="filter-wrapper" key={index}>
                            <button
                                onClick={() => this.toggleFilter(index, item)}
                                className={
                                    "btn btn-filter btn-meta-outline-secondary btn-distance btn-sm " +
                                    (openFilterId === index ? "btn-select ": "") +
                                    (isActive ? "btn-active ": "")
                                }
                            >
                                <i className="meta-icon-preview" />
                                { isActive ? 'Filter: ' + item.caption : 'Filter by ' + item.caption}
                            </button>

                            {openFilterId === index &&
                                <FiltersItem
                                    key={index}
                                    windowType={windowType}
                                    data={item}
                                    closeFilterMenu={() => this.toggleFilter()}
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
                    )
                })}
            </div>
        );
    }
}

FiltersFrequent = connect()(onClickOutside(FiltersFrequent));

export default FiltersFrequent;
