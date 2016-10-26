import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import FilterWidget from '../FilterWidget';
import update from 'react-addons-update';
import Moment from 'moment';
import DateRangePicker from 'react-bootstrap-daterangepicker';

import DatetimeRange from './DatetimeRange';
import FiltersItem from './FiltersItem';
import {
	setFilter,
	setFilterRequest
} from '../../actions/ListActions';

import {
	updateFiltersParameters,
	initFiltersParameters,
	deleteFiltersParameters
} from '../../actions/AppActions';

import {
	getRootBreadcrumb
} from '../../actions/MenuActions';

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
			startDate: null,
			endDate: null,
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

	toggleFilterMenu = (standardFilter) => {
		const {open, openDateMenu, openFilter, filterDataItem, frequentFilterOpen} = this.state;

		console.log('frequentFilterOpen' + ' ' + frequentFilterOpen);

		if(standardFilter){
			this.setState(Object.assign({}, this.state, {
				open: !open,
				frequentFilterOpen: false
			}), () => {
				this.setState(Object.assign({}, this.state, {
					openDateMenu: false
				}))
			})
		} else {
			this.setState(Object.assign({}, this.state, {
				openDateMenu: !openDateMenu
			}), () => {
				this.setState(Object.assign({}, this.state, {
					open: false
				}))
			})
		}

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

		const {openFilter} = this.state;
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

	deleteFilters = () => {

	}

	applyFilters = () => {

		const {filters, dispatch, updateDocList, windowType} = this.props;

		dispatch(setFilter(filters));

		setTimeout(() => { updateDocList('grid', windowType); }, 1)

		this.closeFilterMenu();

	}

	hideFilter = () => {
		const {openFilter} = this.state;
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
		setTimeout(() => { dispatch(setFilter(null)); }, 1)
		setTimeout(() => { updateDocList('grid', windowType); }, 1)

		this.setSelectedItem('', true);
		
	}


	// renderFilterWidget = (item, index) => {
	// 	const {dispatch, filterData, windowType, updateDocList} = this.props;
	// 	const {openList, openFilter, filterDataItem, open, selectedItem} = this.state;

	// 	return(
	// 		<FilterWidget
	// 		key={index}
	// 		id={index}
	// 		windowType={windowType}
	// 		widgetData={item}
	// 		item={item}
	// 		widgetType={item.widgetType}
	// 		updateDocList={updateDocList}
	// 		closeFilterMenu={this.closeFilterMenu}
	// 		setSelectedItem={this.setSelectedItem}
	// 		selectedItem={selectedItem}
	// 		{...filterDataItem} />

	// 	)


	// }

	renderFiltersItem = (item, key) => {
		const {windowType, updateDocList} = this.props;
		const {openList, openFilter, filterDataItem, open, selectedItem} = this.state;
		console.log(this.state);

		return(

			<FiltersItem 
				key={key}
				filterData={item}
				windowType={windowType}
				widgetData={item}
				item={item}
				widgetType={item.widgetType}
				updateDocList={updateDocList}
				closeFilterMenu={this.closeFilterMenu}
				setSelectedItem={this.setSelectedItem}
				selectedItem={selectedItem}
				clearFilterData={this.clearFilterData}
				{...filterDataItem} 
			/>
		)


	}

	renderWidgetStructure = () => {
		const {filterData} = this.props;
		console.log(filterData);
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
		const {openList, openFilter, filterDataItem, open, selectedItem} = this.state;
		const {filterData, windowType, updateDocList} = this.props;
		console.log(filterData);
		return (
			<div className="filter-wrapper">
				<button onClick={() => this.toggleFilterMenu(true)} className={"btn btn-meta-outline-secondary btn-distance btn-sm" + (open ? " btn-active": "") }>
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

	handleEvent = (event, picker) => {

		//tmp
		// <button onClick={() => this.toggleFilterMenu(false)} className={"btn btn-meta-outline-secondary btn-distance btn-sm" + (openDateMenu ? " btn-active": "")}>
  //                   <i className="meta-icon-calendar" />
  //                       { filterDataItem? 'Filter: '+filterDataItem.caption : 'No data filters'}
  //               </button>

		// 		{ openDateMenu &&
		// 			<div className="filters-overlay">

		// 				{ openList &&
		// 					<div className="filter-menu">
		// 						<ul>
		// 							{filterData && filterData.map((item, index) => 
		// 								<div key={index}> 
		// 									{item.frequent &&	
		// 										<li>
		// 											<DateRangePicker
		// 												startDate={Moment(new Date('1/1/2014'))}
		// 								                endDate={Moment(new Date('3/1/2014'))}
		// 								                ranges={ranges}
		// 								                alwaysShowCalendars={true}
		// 								                onApply={this.handleEvent}
		// 											>
		// 												<span className="">
		// 							                        {!!startDate && !!endDate ?
		// 							                            " " + Moment(startDate).format('L') + " - " + Moment(endDate).format('L') :
		// 							                            item.caption
		// 							                        }
		// 								                </span>
		// 											</DateRangePicker>


		// 										</li>
		// 									}
		// 								</div>
		// 							)}
		// 						</ul>
		// 					</div>
		// 				}
		// 				{ openFilter &&
		// 					<div className="filter-menu filter-widget">
		// 						<div>Active filter: <span className="filter-active">{filterDataItem.caption}</span> <span className="filter-clear" onClick={() => { this.clearFilterData()}}>Clear filter <i className="meta-icon-trash"></i></span> </div>
		// 						<div className="form-group row filter-content">
		// 							<div className="col-sm-12">
		// 								{filterDataItem.parameters && filterDataItem.parameters.map((item, index) =>
		// 									this.renderFilterWidget(item, index)
		// 								)}
		// 							</div>
		// 						</div>
		// 						<div className="filter-btn-wrapper">
		// 							<button className="applyBtn btn btn-sm btn-success" onClick={() => this.applyFilters() }>Apply</button>
		// 						</div>
		// 					</div>
		// 				}
		// 			</div>
		// 		}
	   
	}

	renderDateFilter = () => {
		const {openList, openFilter, filterDataItem, openDateMenu, selectedItem, startDate, endDate} = this.state;
		const {filterData, windowType, updateDocList} = this.props;
		const ranges = {
			'Today': [Moment(), Moment()],
			'Yesterday': [Moment().subtract(1, 'days'), Moment().subtract(1, 'days')],
			'Last 7 Days': [Moment().subtract(6, 'days'), Moment()],
			'Last 30 Days': [Moment().subtract(29, 'days'), Moment()],
			'This Month': [Moment().startOf('month'), Moment().endOf('month')],
			'Last Month': [Moment().subtract(1, 'month').startOf('month'), Moment().subtract(1, 'month').endOf('month')]
		}
		return (
			<div>

				<DateRangePicker
					startDate={Moment(new Date('1/1/2014'))}
					endDate={Moment(new Date('3/1/2014'))}
					ranges={ranges}
					alwaysShowCalendars={true}
					onApply={this.handleEvent}
				>
					<button onClick={() => this.toggleFilterMenu(false)} className={"btn btn-meta-outline-secondary btn-distance btn-sm" + (openDateMenu ? " btn-active": "")}>
						<i className="meta-icon-calendar" />
						{ 'No data filters'}
					</button>
				</DateRangePicker>




				
			</div>
		)
	}

	componentDidMount() {
		const {dispatch} = this.props;
		const {filterDataItem} = this.state;


	}

	render() {
		const {openList, openFilter, filterDataItem, open, selectedItem} = this.state;
		const {filterData, windowType, updateDocList} = this.props;

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
