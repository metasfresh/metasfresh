import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import counterpart from 'counterpart';

import FiltersDateStepper from './FiltersDateStepper';
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
                    const parameter = item.parameters[0];
                    const filterType = parameter.widgetType;
                    const isActive = this.isActive(item.filterId);
                    const dateStepper = (
                        // keep implied information (e.g. for refactoring)
                        item.frequent &&

                        item.parameters.length === 1 &&
                        parameter.showIncrementDecrementButtons &&
                        isActive &&
                        TableCell.DATE_FIELD_TYPES.includes(filterType) &&
                        !TableCell.TIME_FIELD_TYPES.includes(filterType)
                    );
                    const activeParameter = (
                        isActive && active[index].parameters[0]
                    );
                    const caption = isActive && TableCell.fieldValueToString(
                        activeParameter.valueTo
                            ? [activeParameter.value, activeParameter.valueTo]
                            : activeParameter.value,
                        filterType
                    );

                    return (
                        <div className="filter-wrapper" key={index}>
                            {dateStepper && (
                                <FiltersDateStepper
                                    active={active[index]}
                                    applyFilters={applyFilters}
                                    filter={item}
                                />
                            )}

                            <button
                                onClick={() => this.toggleFilter(index, item)}
                                className={
                                    'btn btn-filter ' +
                                    'btn-meta-outline-secondary ' +
                                    'btn-sm ' +
                                    (openFilterId === index ?
                                        'btn-select ': ''
                                    ) +
                                    (isActive ? 'btn-active ': '')
                                }
                            >
                                <i className="meta-icon-preview" />
                                {isActive
                                    ? `${item.caption}: ${caption}`
                                    : `${counterpart.translate(
                                            'window.filters.caption2'
                                        )}: ${item.caption}`
                                }
                            </button>

                            {dateStepper && (
                                <FiltersDateStepper
                                    active={active[index]}
                                    applyFilters={applyFilters}
                                    filter={item}
                                    next
                                />
                            )}

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
