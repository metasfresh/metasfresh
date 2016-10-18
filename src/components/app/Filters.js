import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import FilterWidget from '../FilterWidget';

import {
    setFilter,
} from '../../actions/ListActions';

class Filters extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open: false,
            openList: true,
            openFilter: false,
            filterDataItem: ''
        };
    }

    handleClickOutside = (e) => {
        this.closeFilterMenu();
    }

    closeFilterMenu = () => {
        this.setState(Object.assign({}, this.state, {
            open: false
        }))
    }

    toggleFilterMenu = () => {
        const {open} = this.state;
        this.setState(Object.assign({}, this.state, {
            open: !open
        }))
    }

    showFilter = (filterData) => {

        const {openFilter} = this.state;
        this.setState(Object.assign({}, this.state, {
            openFilter: true,
            filterDataItem: filterData,
            openList: false
        }))
    }

    hideFilter = () => {
        const {openFilter} = this.state;
        this.setState(Object.assign({}, this.state, {
            openFilter: false,
            openList: true,
            filterDataItem: ''
        }))
    }

    clearFilterData = () => {
        const {windowType, updateDocList, dispatch} = this.props;
        dispatch(setFilter(null));
        updateDocList('grid', windowType);
    }

    render() {
        const {openList, openFilter, filterDataItem, open} = this.state;
        const {filterData, windowType, updateDocList} = this.props;

        return (
            <div className="filter-wrapper">
                <button onClick={this.toggleFilterMenu} className={"btn btn-meta-outline-secondary btn-distance btn-sm" + (open ? " btn-active": "") }>
                    <i className="meta-icon-preview" />
                    { filterDataItem? 'Filter: '+filterDataItem.caption : 'No search filters'}
                </button>
                { open &&
                    <div className="filters-overlay">
                        { openList &&
                            <div className="filter-menu">
                                <ul>
                                    {filterData && filterData.map((item, index) => 
                                        <li key={index} onClick={ () => this.showFilter(item) }>{item.caption}</li>
                                    )}
                                </ul>
                            </div>
                        }
                        { openFilter &&
                            <div className="filter-menu filter-widget">
                                <div>Active filter: <span className="filter-active">{filterDataItem.caption}</span> <span className="filter-clear" onClick={() => {this.hideFilter(); this.clearFilterData()}}>Clear filter <i className="meta-icon-trash"></i></span> </div>
                                <div className="form-group row filter-content">
                                    <div className="col-sm-12">
                                        <FilterWidget
                                            windowType={windowType}
                                            widgetData={filterDataItem.parameters}
                                            widgetType={filterDataItem.parameters[0].widgetType}
                                            updateDocList={updateDocList}
                                            {...filterDataItem} />
                                    </div>
                                </div>
                            </div>
                        }
                    </div>

                }
                
                
                
                
            </div>

        )
    }
}

Filters.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Filters = connect()(onClickOutside(Filters))

export default Filters
