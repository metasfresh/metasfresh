import React, { Component, PropTypes } from 'react';

import FiltersFrequent from './FiltersFrequent';
import FiltersNotFrequent from './FiltersNotFrequent';

class Filters extends Component {
    constructor(props) {
        super(props);

        this.state = {
            filter: null,

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

    componentWillReceiveProps(props) {
        const {filtersActive} = props;
        filtersActive && this.init(filtersActive[0]);
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

    // SETTING FILTERS  --------------------------------------------------------

    /*
     *   This method should update docList
     */
    applyFilters = (filter) => {
        const notValid = this.isFilterValid(filter);

        if (notValid.length) {
            this.setState({
                notValidFields: notValid
            });
        } else {
            this.setFilterActive([filter]);
        }
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

    // PARSING FILTERS ---------------------------------------------------------

    sortFilters = (data) => {
        return {
            frequentFilters: data.filter(filter => filter.frequent),
            notFrequentFilters: data.filter(filter => !filter.frequent)
        }
    }

    isFilterValid = (filters) => {
        return filters.parameters.filter(item => {
            return item.mandatory && !item.value;
        })
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
