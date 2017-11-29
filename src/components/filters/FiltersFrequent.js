import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import counterpart from 'counterpart';

import FiltersItem from './FiltersItem';
import { TableCell } from '../table/TableCell';

class FiltersFrequent extends Component {
    state = { openFilterId: null };

    toggleFilter = (index) => {
        this.setState({
            openFilterId: index
        })
    }

    handleClickOutside = () => {
        this.outsideClick();
    }

    outsideClick = () => {
        const {widgetShown, dropdownToggled} = this.props;
        !widgetShown && this.toggleFilter(null, null);
        dropdownToggled();
    }

    isActive(filterId) {
        const { active } = this.props;
        let result = false;

        if (active) {
            let activeFilter = active.find( (item) => item.filterId === filterId );
            result = (typeof activeFilter !== 'undefined') && activeFilter;
        }

        return result;
    }

    render() {
        const {
            data, windowType, notValidFields, viewId, handleShow,
            applyFilters, clearFilters, active
        } = this.props;

        const { openFilterId } = this.state;

        return (
            <div className="filter-wrapper">
                {data.map((item, index) => {
                    const isActive = this.isActive(item.filterId);

                    return (
                        <div className="filter-wrapper" key={index}>
                            <button
                                onClick={() => this.toggleFilter(index, item)}
                                className={
                                    'btn btn-filter ' +
                                    'btn-meta-outline-secondary btn-distance ' +
                                    'btn-sm ' +
                                    (openFilterId === index ?
                                        'btn-select ': ''
                                    ) +
                                    (isActive ? 'btn-active ': '')
                                }
                            >
                                <i className="meta-icon-preview" />
                                {isActive
                                    ? `${item.caption}: ${
                                        TableCell.fieldValueToString(
                                            active[index].parameters[0].value,
                                            item.parameters[0].widgetType
                                        )}`
                                    : `${counterpart.translate(
                                            'window.filters.caption2'
                                        )}: ${item.caption}`
                                }
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
                                    outsideClick={this.outsideClick}
                                />
                            }
                        </div>
                    )
                })}
            </div>
        );
    }
}

export default onClickOutside(FiltersFrequent);
