import React, { Component } from 'react';
import { connect } from 'react-redux';

import FilterWidget from '../FilterWidget';

class FiltersItem extends Component {
	constructor(props) {
		super(props);
	}

    renderFilterWidget = (item, index) => {
        const {
            filterDataItem, windowType, updateDocList, closeFilterMenu,
            setSelectedItem, selectedItem
        } = this.props;

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
        const {filterData, clearFilterData, applyFilters, notValidFields, isActive} = this.props;
        return (
            <div className="filter-menu filter-widget">
                <div>Active filter:
                    <span className="filter-active"> {filterData.caption}</span>
                    {isActive &&
                        <span
                            className="filter-clear"
                            onClick={() => { clearFilterData(filterData)}}
                        >
                            Clear filter <i className="meta-icon-trash" />
                        </span>
                    }
                </div>
                <div className="form-group row filter-content">
                    <div className="col-sm-12">
                        {filterData.parameters && filterData.parameters.map((item, index) =>
                            this.renderFilterWidget(item, index)
                        )}
                    </div>
                    <div className="col-sm-12 text-xs-right">
                        {notValidFields && <div className="input-error">Mandatory filters are not filled!</div>}
                    </div>
                </div>
                <div className="filter-btn-wrapper">
                    <button className="applyBtn btn btn-sm btn-success" onClick={() => applyFilters() }>Apply</button>
                </div>
            </div>
        )
	}
}


FiltersItem = connect()(FiltersItem)

export default FiltersItem
