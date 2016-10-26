import React, { Component } from 'react';
import { connect } from 'react-redux';

import FilterWidget from '../FilterWidget';

class FiltersItem extends Component {
	constructor(props) {
		super(props);
	}

    renderFilterWidget = (item, index) => {
        const {filterData, filterDataItem, windowType, updateDocList, closeFilterMenu, setSelectedItem, selectedItem, filters} = this.props;
        console.log(item);

        return(
            <FilterWidget
                key={index}
                id={index}
                windowType={windowType}
                widgetData={item}
                item={item}
                widgetType={item.widgetType}
                updateDocList={updateDocList}
                closeFilterMenu={closeFilterMenu}
                setSelectedItem={setSelectedItem}
                selectedItem={selectedItem}
                {...filterDataItem} 
            />
        )


    }

	render() {

        const {filterData, clearFilterData} = this.props;

        return (
            <div className="filter-menu filter-widget">
                <div>Active filter: <span className="filter-active">{filterData.caption}</span> <span className="filter-clear" onClick={() => { clearFilterData(filterData)}}>Clear filter <i className="meta-icon-trash"></i></span> </div>
                    <div className="form-group row filter-content">
                        <div className="col-sm-12">
                            {filterData.parameters && filterData.parameters.map((item, index) =>
                                this.renderFilterWidget(filterData, index)
                            )}
                    </div>
                </div>
                <div className="filter-btn-wrapper">
                    <button className="applyBtn btn btn-sm btn-success" onClick={() => this.applyFilters() }>Apply</button>
                </div>
            </div>
        )
	}
}

function mapStateToProps(state) {
    const {appHandler} = state;
    const {
        filters
    } = appHandler || {
        filters: []
    }

    return {
        filters
    }
}

FiltersItem = connect(mapStateToProps)(FiltersItem)

export default FiltersItem
