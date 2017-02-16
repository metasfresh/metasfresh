import React, { Component } from 'react';

import RawWidget from '../widget/RawWidget';

class FiltersItem extends Component {
    constructor(props) {
        super(props);

        this.state = {
            filter: props.data
        }
    }

    componentWillMount() {
        this.init();
    }

    componentWillReceiveProps(props) {
        if(JSON.stringify(this.props.active) !== JSON.stringify(props.active)){
            this.init();
        }
    }

    init = () => {
        const {active} = this.props;
        active && active.parameters && active.parameters.map(item => {
            this.mergeData(item.parameterName, item.value, item.valueTo);
        })
    }

    setValue = (property, value, valueTo) => {
        //TODO: LOOKUPS GENERATE DIFFERENT TYPE OF PROPERTY parameters
        // IT HAS TO BE UNIFIED
        //
        // OVERWORKED WORKAROUND
        if(Array.isArray(property)){
            property.map(item => {
                this.mergeData(item.parameterName, value, valueTo);
            })
        }else{
            this.mergeData(property, value, valueTo);
        }
    }

    mergeData = (property, value, valueTo = null) => {
        this.setState(prevState => {
            return {
                filter: Object.assign({}, prevState.filter, {
                    parameters: prevState.filter.parameters.map(param => {
                        if(param.parameterName === property){
                            return Object.assign({}, param, 
                                valueTo ? {
                                    value,
                                    valueTo
                                } : {
                                    value
                                }
                            )
                        }else{
                            return param;
                        }
                    })
                })
            }
        })
    }

    handleApply = () => {
        const {applyFilters, closeFilterMenu} = this.props;
        const {filter} = this.state;

        applyFilters(filter);
        closeFilterMenu();
    }

    handleClear = () => {
        const {clearFilters, closeFilterMenu, returnBackToDropdown} = this.props;

        clearFilters();
        closeFilterMenu();
        returnBackToDropdown && returnBackToDropdown();
    }

    render() {
        const {
            data, notValidFields, isActive, windowType, onShow, onHide, viewId
        } = this.props;

        const {
            filter
        } = this.state;

        return (
            <div className="filter-menu filter-widget">
                <div>Active filter:
                    <span className="filter-active"> {data.caption}</span>
                    {isActive &&
                        <span
                            className="filter-clear"
                            onClick={() => this.handleClear()}
                        >
                            Clear filter <i className="meta-icon-trash" />
                    </span>
                }
            </div>
            <div className="form-group row filter-content">
                <div className="col-sm-12">
                    {filter.parameters && filter.parameters.map((item, index) =>
                        <RawWidget
                            entity="documentView"
                            subentity="filter"
                            subentityId={filter.filterId}
                            handlePatch={this.setValue}
                            handleChange={this.setValue}
                            widgetType={item.widgetType}
                            fields={[item]}
                            windowType={windowType}
                            type={filter.type}
                            widgetData={[item]}
                            key={index}
                            id={index}
                            range={item.range}
                            onShow={onShow}
                            onHide={onHide}
                            caption={item.caption}
                            noLabel={false}
                            filterWidget={true}
                            viewId={viewId}
                        />
                    )}
                </div>
                <div className="col-sm-12 text-xs-right">
                    {notValidFields &&
                        <div className="input-error">
                            Mandatory filters are not filled!
                        </div>
                    }
                </div>
            </div>
            <div className="filter-btn-wrapper">
                <button
                    className="applyBtn btn btn-sm btn-success"
                    onClick={this.handleApply}
                >
                    Apply
                </button>
            </div>
        </div>
    )}
}

export default FiltersItem
