import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import update from 'react-addons-update';

import DatetimeRange from './DatetimeRange';
import FiltersItem from './FiltersItem';

import {
	updateFiltersParameters,
	initFiltersParameters,
	deleteFiltersParameters,
	setFilter
} from '../../actions/ListActions';

class Filters extends Component {
	constructor(props) {
		super(props);
		this.state = {
			openDateMenu: false,
			notFrequentListOpen: true,
			filterDataItem: '',
			selectedItem: '',
            frequentFilterOpen: false,
			notFrequentFilterOpen: false
		};
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

	handleClickOutside = (e) => {
		this.closeFilterMenu();
	}

	closeFilterMenu = () => {
		this.setState(Object.assign({}, this.state, {
            notFrequentFilterOpen: false,
			frequentFilterOpen: false
		}))
	}

	toggleFilterMenu = () => {
		const {notFrequentFilterOpen} = this.state;
		this.setState(Object.assign({}, this.state, {
            notFrequentFilterOpen: !notFrequentFilterOpen,
			frequentFilterOpen: false
		}))
	}

	toggleFrequentFilter = (index) => {
		this.setState(Object.assign({}, this.state, {
			frequentFilterOpen: index,
			notFrequentFilterOpen: false
		}))
	}

	showFilter = (filterData) => {
		const {dispatch} = this.props;

		this.setState(Object.assign({}, this.state, {
			filterDataItem: filterData,
			notFrequentListOpen: false,
			frequentFilterOpen: false
		}), () => {
			let parameters = [];

			filterData.parameters.map((item, id) => {
				parameters = update(parameters, {$push: [{
					parameterName: item.parameterName,
					value: null
				}]});
			})

			dispatch(initFiltersParameters(filterData.filterId, parameters));
		})
	}

	applyFilters = () => {
		const {filter, dispatch, updateDocList, windowType} = this.props;
		dispatch(setFilter(filter));
		this.closeFilterMenu();
	}

	hideFilter = () => {
		this.setState(Object.assign({}, this.state, {
			notFrequentListOpen: true,
			filterDataItem: ''
		}))
	}

	clearFilterData = (clearData) => {
		const {windowType, updateDocList, dispatch} = this.props;
		const {filterDataItem} = this.state;

		let parameters = [];
		let data = '';

		if(filterDataItem) {
			data = filterDataItem;
		} else {
			data = clearData;
		}

		data.parameters.map((item, id) => {
			dispatch(updateFiltersParameters(filterDataItem.filterId, '', null));
		})

		dispatch(deleteFiltersParameters());
        dispatch(setFilter(null));
		this.setSelectedItem('', true);
	}


	renderFiltersItem = (item, key) => {
		const {windowType, updateDocList} = this.props;
		const {filterDataItem, selectedItem} = this.state;

		return(
			<FiltersItem
				key={key}
				filterData={item}
				windowType={windowType}
				widgetData={item}
				item={item}
				filterDataItem={filterDataItem}
				updateDocList={updateDocList}
				closeFilterMenu={this.closeFilterMenu}
				setSelectedItem={this.setSelectedItem}
				selectedItem={selectedItem}
				clearFilterData={this.clearFilterData}
				applyFilters={this.applyFilters}
				{...filterDataItem}
			/>
		)
	}

	renderStandardFilter = (filterData) => {
		const {notFrequentListOpen, filterDataItem, notFrequentFilterOpen} = this.state;

		return (
			<div className="filter-wrapper">
				<button
                    onClick={() => this.toggleFilterMenu()}
                    className={
                        "btn btn-filter btn-meta-outline-secondary btn-distance btn-sm" +
                        (notFrequentFilterOpen ? " btn-select": "") +
                        (filterDataItem ? " btn-active" : "")
                    }
                >
					<i className="meta-icon-preview" />
					{ filterDataItem ? 'Filter: ' + filterDataItem.caption : 'No search filters'}
				</button>

				{ notFrequentFilterOpen &&
					<div className="filters-overlay">
						{ notFrequentListOpen ?
							<ul className="filter-menu">
								{filterData.map((item, index) =>
									<li key={index} onClick={() => this.showFilter(item)}>
                                        {item.caption}
                                    </li>
								)}
							</ul>
                            :
							<div>
								{this.renderFiltersItem(filterDataItem)}
							</div>
						}
					</div>
				}
			</div>
		)
	}

    renderFrequentFilterWrapper = (filterData) => {
		const {frequentFilterOpen, selectedItem} = this.state;
		return (
			<div className="filter-wrapper">
				{filterData.map((item, index) =>
					<div className="filter-wrapper" key={index}>
						<button
                            onClick={() => this.toggleFrequentFilter(index)}
                            className={
                                "btn btn-meta-outline-secondary btn-distance btn-sm" +
                                (selectedItem.filterId === item.filterId ? " btn-active": "")
                            }
                        >
							<i className="meta-icon-preview" />
							{ item ? 'Filter: ' + item.caption : 'No search filters'}
						</button>
						{frequentFilterOpen === index && this.renderFiltersItem(item) }
					</div>
				)}
			</div>
		)
	}

	renderWidgetStructure = () => {
		const {filterData} = this.props;

		let freqFilter = [];
		let notFreqFilter = [];

        filterData.map((item) => {
            if(item.frequent){
                freqFilter.push(item);
            }else{
                notFreqFilter.push(item);
            }
        })

		return(
    		<div>
    			{filterData &&
    				<div className="filter-wrapper">
    					<span>Filters </span>
    					<div className="filter-wrapper">
    						{freqFilter && this.renderFrequentFilterWrapper(freqFilter)}
    						{notFreqFilter && this.renderStandardFilter(notFreqFilter)}
    					</div>
    				</div>
    			}
    		</div>
		)
	}

	render() {
		return (
			<div>
				{this.renderWidgetStructure()}
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
