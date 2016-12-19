import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import update from 'react-addons-update';

import DatetimeRange from '../widget/DatetimeRange';
import FiltersItem from './FiltersItem';

import {
    updateFiltersParameters,
    initFiltersParameters,
    deleteFiltersParameters,
    setFilter
} from '../../actions/ListActions';

import {
    getItemsByProperty
} from '../../actions/WindowActions';

class Filters extends Component {
    constructor(props) {
        super(props);

        this.state = {
            openDateMenu: false,
            notFrequentListOpen: true,
            selectedItem: '',
            frequentFilterOpen: false,
            notFrequentFilterOpen: false,
            notValidFields: null,
            active: null,
            widgetShown: false
        }
    }

    componentDidMount() {
        const {dispatch, filtersActive, windowType} = this.props;

        if(filtersActive) {
            this.setFilter(filtersActive[0], windowType);
        }
    }

    setSelectedItem = (item, close) => {
        this.setState(Object.assign({}, this.state, {
            selectedItem: item
        }), () => {
            close && this.setState(Object.assign({}, this.state, {
                notFrequentListOpen: true,
                filterDataItem: '',
                notFrequentFilterOpen: false,
                frequentFilterOpen: false
            }))
        });
    }

    handleShow = (show) => {
        this.setState(Object.assign({}, this.state, {
            widgetShown: !!show
        }));
    }

    handleClickOutside = (e) => {
        const {widgetShown} = this.state;
        !widgetShown && this.closeFilterMenu();
    }

    closeFilterMenu = () => {
        this.setState(Object.assign({}, this.state, {
            notFrequentFilterOpen: false,
            frequentFilterOpen: false,
            notFrequentListOpen: true
        }))
    }

    toggleFilterMenu = (filterData) => {
        const {dispatch} = this.props;
        const {notFrequentFilterOpen, active} = this.state;
        const activeItem = getItemsByProperty(filterData, "filterId", active)[0];

        if(activeItem){
            dispatch(initFiltersParameters(activeItem.filterId, activeItem.parameters));
            this.initActiveFilters();
            this.setState(Object.assign({}, this.state, {
                filterDataItem: activeItem,
                notFrequentFilterOpen: !notFrequentFilterOpen,
                frequentFilterOpen: false,
                notValidFields: null
            }))
        }else{
            this.setState(Object.assign({}, this.state, {
                notFrequentFilterOpen: !notFrequentFilterOpen,
                frequentFilterOpen: false,
                notValidFields: null
            }))
        }
    }

    getFiltersStructure = (filterData) => {
        return filterData.parameters.map((item) => {
            item.value = null;
            return item;
        });
    }

    initActiveFilters = () => {
        const {dispatch, filtersActive} = this.props;
        if(filtersActive){
            filtersActive[0].parameters.map(item => {
                dispatch(updateFiltersParameters(
                    filtersActive[0].filterId, item.parameterName, item.value, item.valueTo
                ))
            })
        }
    }

    toggleFrequentFilter = (index, filterData) => {
        const {dispatch, filter, filtersActive} = this.props;
        if(filter.parameters.length === 0 || filter.filterId !== filterData.filterId){
            dispatch(initFiltersParameters(filterData.filterId, this.getFiltersStructure(filterData)));
            this.initActiveFilters();
        }
        this.setState(Object.assign({}, this.state, {
            frequentFilterOpen: index,
            notFrequentFilterOpen: false,
            notFrequentListOpen: true,
            filterDataItem: filterData,
            notValidFields: null
        }));
    }

    showFilter = (filterData) => {
        const {dispatch} = this.props;
        dispatch(initFiltersParameters(filterData.filterId, this.getFiltersStructure(filterData)));
        this.setState(Object.assign({}, this.state, {
            filterDataItem: filterData,
            notFrequentListOpen: false,
            frequentFilterOpen: false,
            notValidFields: null
        }));
    }

    isFilterValid = (filters) => {
        return filters.parameters.filter(item => {
            return item.mandatory && !item.value;
        })
    }

    applyFilters = () => {
        const {filter, dispatch, windowType} = this.props;
        const notValid = this.isFilterValid(filter);
        
        if (notValid.length) {
            this.setState(Object.assign({}, this.state, {
                notValidFields: notValid
            }));
        } else {
            this.setFilter(filter, windowType)
            this.closeFilterMenu();
        }
    }

    setFilter = (filter, windowType) => {
        const {dispatch} = this.props;

        this.setState(Object.assign({}, this.state, {
            active: filter.filterId
        }), () => {
            dispatch(setFilter(filter, windowType));
        })
    }

    hideFilter = () => {
        this.setState(Object.assign({}, this.state, {
            notFrequentListOpen: true,
            filterDataItem: ''
        }))
    }

    clearFilterData = (clearData) => {
        const {windowType, dispatch} = this.props;
        const {filterDataItem} = this.state;

        let data = '';

        if(filterDataItem) {
            data = filterDataItem;
        } else {
            data = clearData;
        }

        data.parameters.map((item, id) => {
            dispatch(updateFiltersParameters(filterDataItem.filterId, '', null));
        });

        dispatch(deleteFiltersParameters());
        dispatch(setFilter(null, null));
        this.setSelectedItem('', true);
    }

    renderFiltersItem = (item, key, isActive) => {
        const {windowType} = this.props;
        const {filterDataItem, selectedItem, notValidFields} = this.state;
        if(item){
            return (
                <FiltersItem
                    key={key}
                    filterData={item}
                    windowType={windowType}
                    widgetData={item}
                    item={item}
                    filterDataItem={filterDataItem}
                    closeFilterMenu={this.closeFilterMenu}
                    setSelectedItem={this.setSelectedItem}
                    selectedItem={selectedItem}
                    clearFilterData={this.clearFilterData}
                    applyFilters={this.applyFilters}
                    notValidFields={notValidFields}
                    isActive={isActive}
                    isShown={() => this.handleShow(true)}
                    isHidden={() => this.handleShow(false)}
                />
            )
        }
    }

    renderStandardFilter = (filterData) => {
        const {filters} = this.props;
        const {notFrequentListOpen, filterDataItem, notFrequentFilterOpen} = this.state;

        //have to check wheter any of standard filters is active
        //once it is rendered
        const active = filters[0] ? getItemsByProperty(filterData, "filterId", filters[0].filterId)[0] : null;
        const isActive = active ? !!active : false;
        return (
            <div className="filter-wrapper">
                <button
                    onClick={() => this.toggleFilterMenu(filterData)}
                    className={
                        "btn btn-filter btn-meta-outline-secondary btn-distance btn-sm" +
                        (notFrequentFilterOpen ? " btn-select": "") +
                        (isActive ? " btn-active" : "")
                    }
                >
                <i className="meta-icon-preview" />
                    { isActive ? 'Filter: ' + active.caption : 'Select other filter'}
                </button>

                { notFrequentFilterOpen &&
                    <div className="filters-overlay">
                        { (notFrequentListOpen && !isActive) ?
                            <ul className="filter-menu">
                            {filterData.map((item, index) =>
                                <li key={index} onClick={() => this.showFilter(item)}>
                                    {item.caption}
                                </li>
                            )}
                            </ul>
                            :
                            <div>
                                {this.renderFiltersItem(filterDataItem, 0, isActive)}
                            </div>
                        }
                    </div>
                }
            </div>
        )
    }

    renderFrequentFilterWrapper = (filterData) => {
        const {filters} = this.props;
        const {frequentFilterOpen, selectedItem, active} = this.state;
        return (
            <div className="filter-wrapper">
            {filterData.map((item, index) => {
                const isActive = !!getItemsByProperty(filters, "filterId", item.filterId).length;
                return (
                    <div className="filter-wrapper" key={index}>
                        <button
                            onClick={() => this.toggleFrequentFilter(index, item)}
                            className={
                                "btn btn-filter btn-meta-outline-secondary btn-distance btn-sm" +
                                (isActive ? " btn-active": "")
                            }
                        >
                        <i className="meta-icon-preview" />
                        { isActive ? 'Filter: ' + item.caption : 'Filter by ' + item.caption}
                        </button>
                        {frequentFilterOpen === index && this.renderFiltersItem(item, index, isActive) }
                    </div>
                )
            })}
            </div>
        )
    }

    render() {
        const {filterData, filtersActive} = this.props;

        let freqFilter = [];
        let notFreqFilter = [];

        filterData && filterData.map((item) => {
            if(item.frequent){
                freqFilter.push(item);
            }else{
                notFreqFilter.push(item);
            }
        })

        return (
            <div>
            {filterData && !!filterData.length &&
                <div className="filter-wrapper js-not-unselect">
                <span>Filters: </span>
                <div className="filter-wrapper">
                {!!freqFilter.length && this.renderFrequentFilterWrapper(freqFilter)}
                {!!notFreqFilter.length && this.renderStandardFilter(notFreqFilter)}
                </div>
                </div>
            }
            </div>
        )
    }
}

Filters.propTypes = {
    dispatch: PropTypes.func.isRequired,
    filters: PropTypes.array.isRequired,
    filter: PropTypes.object.isRequired,
};

function mapStateToProps(state) {
    const {listHandler} = state;
    const {
        filters,
        filter
    } = listHandler || {
        filters: [],
        filter: {}
    }

    return {
        filters,
        filter
    }
}

Filters = connect(mapStateToProps)(onClickOutside(Filters))

export default Filters
