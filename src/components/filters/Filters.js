import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {push} from 'react-router-redux';
import counterpart from 'counterpart';
import FiltersFrequent from './FiltersFrequent';
import FiltersNotFrequent from './FiltersNotFrequent';
import FiltersStatic from './FiltersStatic';

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
                const parsedFilter = filter.parameters ?
                    Object.assign({}, filter, {
                        parameters: this.parseToPatch(filter.parameters)
                    }) : filter;
                this.setFilterActive([parsedFilter]);
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
            notFrequentFilters: data.filter(filter =>
                !filter.frequent && !filter.static),
            staticFilters: data.filter(filter => filter.static)
        }
    }

    isFilterValid = (filters) => {
        if(filters.parameters){
            return !(filters.parameters.filter(
                item => item.mandatory && !item.value
            ).length);
        }else{
            return true;
        }
    }

    parseToPatch = (params) => {
        return params.map(param =>
            Object.assign({}, param, {
                value: param.value === '' ? null : param.value
            })
        )
    }

    // RENDERING FILTERS -------------------------------------------------------

    render() {
        const {filterData, windowType, viewId, clearStaticFilters} = this.props;
        const {
            frequentFilters, notFrequentFilters, staticFilters
        } = this.sortFilters(filterData);
        const {notValidFields, widgetShown, filter} = this.state;

        return (
            <div className="filter-wrapper js-not-unselect">
                <span>{counterpart.translate(
                    'window.filters.caption'
                )}: </span>
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
                    {
                        //TODO: temporary solution to refactor.
                        // Structure data/layout corrupted.
                    }
                    {filter && filter.static && (
                        <FiltersStatic
                            data={[filter]}
                            clearFilters={clearStaticFilters}
                        />
                    )}
                </div>
            </div>
        )
    }
}

Filters.propTypes = {
    windowType: PropTypes.string.isRequired
}

export default Filters;
