import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import FilterWidget from '../FilterWidget';
import update from 'react-addons-update';

import {
	setFilter,
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
			openList: true,
			openFilter: false,
			filterDataItem: '',
			selectedItem: ''
		};
	}

	setSelectedItem = (item, close) => {
		let th = this;
		this.setState(Object.assign({}, this.state, {
			selectedItem: item
		}), () => {
			if(close){
				th.hideFilter();
			}
		});

		
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

		const {dispatch} = this.props;

		const {openFilter} = this.state;
		this.setState(Object.assign({}, this.state, {
			openFilter: true,
			filterDataItem: filterData,
			openList: false
		}), () => {
				// console.log('filterData');
				// console.log(filterData);

				let parameters = [];

				filterData.parameters.map((item, id) => {
					parameters = update(parameters, {$push: [{
						parameterName: item.parameterName,
						value: null
					}]});

				})

				// console.log(parameters);

				dispatch(initFiltersParameters(filterData.filterId, parameters));

		})


	}

	applyFilters = () => {
		// console.log('apply filters');

		const {filters, dispatch, updateDocList, windowType} = this.props;

		// console.log(filters);

		// let filter =
  //         [{
  //           filterId: "default",
  //           parameters: [
  //             {
  //               parameterName: "C_BPartner_ID",
  //               value: {
  //               	2156425:"G0001_Musterfrau, Lisasssder"
  //               }
  //             },{
  //               parameterName: "BPartnerAddress",
  //               value: "qweqweqwe"
  //             },{
  //               parameterName: "C_DocType_ID",
  //               value: {
  //               	1000007:"Abgleich Rechnung"
  //               }
  //             }
  //           ]
  //         }]

//   let filter =
//           [{
//             filterId: "default",
//             parameters: [
// {
//                 parameterName: "C_DocType_ID",
//                 value: {
//                 	2156425:"G0001_Musterfrau, Lisasssder"
//                 }
//               }
//             ]
//           }]

		dispatch(setFilter([filters]));
		updateDocList('grid', windowType);
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

	clearFilterData = () => {

		const {windowType, updateDocList, dispatch} = this.props;
		const {filterDataItem} = this.state;

		let parameters = [];

		filterDataItem.parameters.map((item, id) => {
			// console.log(item);
			dispatch(updateFiltersParameters(filterDataItem.filterId, '', null));
		})

		
		
		dispatch(setFilter([]));
		updateDocList('grid', windowType);
		this.setSelectedItem('', true);
		
	}

	componentDidMount() {
		// console.log('mounted');
		const {filterData, dispatch} = this.props;

		// let filterItem = {
		// 	filterId: "",
		// 	parameters: [
		// 		parameterName: "",
		// 		value: {}
		// 	]

		// };


		filterData.map((item, index) => {



			// if(!item.frequent){
			// 	dispatch(updateFiltersParameters({
			// 		filterId: item.filterId,
			// 		parameters: item.parameters
			// 	}));
			// }

			// if(!item.frequent){
			// 	dispatch(updateFiltersParameters({
			// 		filterId: item.filterId,
			// 		parameters: item.parameters.map((it, id) => {
			// 			parameterName: it.parameterName
			// 		})
			// 	}));
			// }



			// console.log(item);

		});

		// dispatch(updateFiltersParameters(filterData));
	}

	renderFilterWidget = (item, index) => {
		const {dispatch, filterData, windowType, updateDocList} = this.props;
		const {openList, openFilter, filterDataItem, open, selectedItem} = this.state;

		// console.log('filterData');
		// console.log(filterDataItem);

		

		return(
			<FilterWidget
			key={index}
			id={index}
			windowType={windowType}
			widgetData={item}
			item={item}
			widgetType={item.widgetType}
			updateDocList={updateDocList}
			closeFilterMenu={this.closeFilterMenu}
			setSelectedItem={this.setSelectedItem}
			selectedItem={selectedItem}
			{...filterDataItem} />

		)

		
	}

	componentDidMount() {
		const {dispatch} = this.props;
		const {filterDataItem} = this.state;

		
	}

	render() {
		const {openList, openFilter, filterDataItem, open, selectedItem} = this.state;
		const {filterData, windowType, updateDocList} = this.props;

		// console.log(filterData);

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
							<div className="filter-menu filter-widget">
								<div>Active filter: <span className="filter-active">{filterDataItem.caption}</span> <span className="filter-clear" onClick={() => { this.clearFilterData()}}>Clear filter <i className="meta-icon-trash"></i></span> </div>
								<div className="form-group row filter-content">
									<div className="col-sm-12">
										{filterDataItem.parameters && filterDataItem.parameters.map((item, index) => 
											this.renderFilterWidget(item, index)
										)}
									</div>
								</div>
								<div className="filter-btn-wrapper">
									<button className="applyBtn btn btn-sm btn-success" onClick={() => this.applyFilters() }>Apply</button>
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
