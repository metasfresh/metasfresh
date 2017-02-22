import React, { Component, PropTypes } from 'react';

import FiltersFrequent from './FiltersFrequent';
import FiltersNotFrequent from './FiltersNotFrequent';

class Filters extends Component {
    constructor(props) {
        super(props);

        this.state = {
            filter: null,
            notValidFields: null,
            widgetShown: false
        }
    }

    componentWillReceiveProps(props) {
        const {filtersActive} = props;
        
        this.init(filtersActive ? filtersActive[0] : null);
    }

    componentDidMount() {
        const {filtersActive} = this.props;
        filtersActive && this.init(filtersActive[0]);
    }

    init = (filter) => {
        this.setState({
            filter: filter
        })
    }

    // SETTING FILTERS  --------------------------------------------------------

    /*
     *   This method should update docList
     */
    applyFilters = (filter, cb) => {
        const valid = this.isFilterValid(filter);
        
        this.setState({
            notValidFields: !valid
        }, () => {
            if (valid){
                this.setFilterActive([filter]);
                cb && cb();
            }
        });
    }

    setFilterActive = (filter) => {
        const {updateDocList} = this.props;
        this.setState({
            filter: filter
        }, () => {
            updateDocList(filter);
        })
    }

    /*
     *  Mehod to lock backdrop, to do not close on click onClickOutside
     *  widgets that are bigger than filter wrapper
     */
    handleShow = (value) => {
        this.setState({
            widgetShown: value
        })
    }

    clearFilters = () => {
        this.setFilterActive(null)
    }
    
    dropdownToggled = () => {
        this.setState({
            notValidFields: false
        })
    }

    // PARSING FILTERS ---------------------------------------------------------

    sortFilters = (data) => {
        return {
            frequentFilters: data.filter(filter => filter.frequent),
            notFrequentFilters: data.filter(filter => !filter.frequent)
        }
    }

    isFilterValid = (filters) => {
        if(filters.parameters){
            return !(filters.parameters.filter(
                item => item.mandatory && !item.value
            ).length);
        }else{
            return false;
        }
    }

    getFiltersStructure = (filterData) => {
        return filterData.parameters.map((item) => {
            item.value = null;
            return item;
        });
    }

    // RENDERING FILTERS -------------------------------------------------------

    render() {
        const {filterData, windowType, viewId} = this.props;
        const {frequentFilters, notFrequentFilters} = this.sortFilters(filterData);
        const {notValidFields, widgetShown, filter} = this.state;
        return (
            <div className="filter-wrapper js-not-unselect">
                <span>Filters: </span>
                <div className="filter-wrapper">
                    {!!frequentFilters.length &&
                        <FiltersFrequent
                            windowType={windowType}
                            data={frequentFilters}
                            notValidFields={notValidFields}
                            viewId={viewId}
                            handleShow={this.handleShow}
                            widgetShown={widgetShown}
                            applyFilters={this.applyFilters}
                            clearFilters={this.clearFilters}
                            active={filter}
                            dropdownToggled={this.dropdownToggled}
                        />
                    }
                    {!!notFrequentFilters.length &&
                        <FiltersNotFrequent
                            windowType={windowType}
                            data={notFrequentFilters}
                            notValidFields={notValidFields}
                            viewId={viewId}
                            handleShow={this.handleShow}
                            widgetShown={widgetShown}
                            applyFilters={this.applyFilters}
                            clearFilters={this.clearFilters}
                            active={filter}
                            dropdownToggled={this.dropdownToggled}
                        />
                    }
                </div>
            </div>
        )
    }
}

Filters.propTypes = {
    windowType: PropTypes.number.isRequired
}

export default Filters;
