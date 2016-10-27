import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import update from 'react-addons-update';

import DatetimeRange from './DatetimeRange';
import FiltersItem from './FiltersItem';
import {
	setFilter
} from '../../actions/ListActions';

import {
	updateFiltersParameters,
	initFiltersParameters,
	deleteFiltersParameters
} from '../../actions/AppActions';

class Filters extends Component {
	constructor(props) {
		super(props);
		this.state = {
			open: false,
			openDateMenu: false,
			openList: true,
			openFilter: false,
			filterDataItem: '',
			selectedItem: '',
			frequentFilterOpen: false
		};
	}


	setSelectedItem = (item, close) => {

		this.setState(Object.assign({}, this.state, {
			selectedItem: item
		}), () => {
			if(close){
				this.setState(Object.assign({}, this.state, {
					openFilter: false,
					openList: true,
					open: false,
					filterDataItem: '',
					frequentFilterOpen: false
				}))
			}
		});
	}

	handleClickOutside = (e) => {
		this.closeFilterMenu();
	}

	closeFilterMenu = () => {
		this.setState(Object.assign({}, this.state, {
			open: false,
			frequentFilterOpen: false
		}))
	}

	toggleFilterMenu = () => {
		const {open} = this.state;

		this.setState(Object.assign({}, this.state, {
			open: !open,
			frequentFilterOpen: false
		}))
	}

	toggleFrequentFilter = (index) => {
		const {frequentFilterOpen} = this.state;
		this.setState(Object.assign({}, this.state, {
			frequentFilterOpen: index,
			open: false

		}))
	}

	showFilter = (filterData) => {

		const {dispatch} = this.props;

		this.setState(Object.assign({}, this.state, {
			openFilter: true,
			filterDataItem: filterData,
			openList: false,
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

		const {filters, dispatch, updateDocList, windowType} = this.props;
		dispatch(setFilter(filters));
		setTimeout(() => { updateDocList('grid', windowType); }, 1)
		this.closeFilterMenu();

	}

	hideFilter = () => {
		this.setState(Object.assign({}, this.state, {
			openFilter: false,
			openList: true,
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
		setTimeout(() => { dispatch(setFilter(null)); }, 1);
		setTimeout(() => { updateDocList('grid', windowType); }, 1);

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

	renderWidgetStructure = () => {
		const {filterData} = this.props;
	
		let freqFilter = false;
		let notFreqFilter = false;

		filterData.map((item) => {
			if(item.frequent){
				freqFilter = true;
			}
			 
			 if(!item.frequent){
				notFreqFilter = true;
			 }
			 
		})

		return(
		<div>
			{filterData && 
				<div className="filter-wrapper">
					<span>Filters </span>
					<div className="filter-wrapper">
						{freqFilter && this.renderFrequentFilterWrapper()}
						{notFreqFilter && this.renderStandardFilter()}
					</div>
				</div>
				
			}
		</div>
		
		)
	}

	renderFrequentFilterWrapper = () => {
		const {filterData} = this.props;
		const {frequentFilterOpen} = this.state;

		let openFilter = true;
		return(
			<div className="filter-wrapper">
				{filterData && filterData.map((item, index) =>
					<div className="filter-wrapper" key={index}>
						{item.frequent &&
							<div>
								<button onClick={() => this.toggleFrequentFilter(index)} className={"btn btn-meta-outline-secondary btn-distance btn-sm" + (open ? " btn-active": "") }>
									<i className="meta-icon-preview" />
									{ item? 'Filter: '+item.caption : 'No search filters'}
								</button>
								{frequentFilterOpen === index && this.renderFiltersItem(item) }
							</div>
							
						}
					</div>
				)}
				
			</div>
			
		)
	}


	renderStandardFilter = () => {
		const {openList, openFilter, filterDataItem, open} = this.state;
		const {filterData} = this.props;

		return (
			<div className="filter-wrapper">
				<button onClick={() => this.toggleFilterMenu()} className={"btn btn-meta-outline-secondary btn-distance btn-sm" + (open ? " btn-active": "") }>
					<i className="meta-icon-preview" />
					{ filterDataItem? 'Filter: '+filterDataItem.caption : 'No search filters'}
				</button>
				{ open &&
					<div className="filters-overlay">
						{ openList &&
							<div className="filter-menu">
								<ul>
									{filterData && filterData.map((item, index) =>
										<div key={index}>
											{!item.frequent &&
												<li onClick={ () => this.showFilter(item) }>{item.caption}</li>
											}
										</div>
									)}
								</ul>
							</div>
						}
						{ openFilter &&
							<div>
								{this.renderFiltersItem(filterDataItem)}
							</div>
						}
					</div>
				}
			</div>
		)
	}

	render() {

		return (
			<div>
				<div>{this.renderWidgetStructure()}</div>
			</div>
		)
	}
}

Filters.propTypes = {
	dispatch: PropTypes.func.isRequired,
	filters: PropTypes.object.isRequired
};

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

Filters = connect(mapStateToProps)(onClickOutside(Filters))

export default Filters
