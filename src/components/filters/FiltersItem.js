import React, { Component } from 'react';
import PropTypes from 'prop-types';
import RawWidget from '../widget/RawWidget';
import OverlayField from '../app/OverlayField';
import counterpart from 'counterpart';
import keymap from '../../keymap.js';
import ModalContextShortcuts from '../shortcuts/ModalContextShortcuts';
import { ShortcutManager } from 'react-shortcuts';
import Tooltips from '../tooltips/Tooltips.js';
const shortcutManager = new ShortcutManager(keymap);

class FiltersItem extends Component {
    constructor(props) {
        super(props);

        this.state = {
            filter: props.data,
            isTooltipShow: false
        }

    }

    componentWillMount() {
        this.init();
    }

    componentWillReceiveProps(props) {
        const {active} = this.props;

        if(
            JSON.stringify(active) !==
            JSON.stringify(props.active)
        ){
            this.init();
        }
    }

    init = () => {
        const {active} = this.props;
        const {filter} = this.state;

        if(
            filter.parameters && active && active.parameters &&
            (active.filterId === filter.filterId)
        ){
            active.parameters.map(item => {
                this.mergeData(
                    item.parameterName,
                    item.value != null ? item.value : '',
                    item.valueTo != null ? item.valueTo : ''
                );
            })
        }else if(filter.parameters){
            filter.parameters.map(item => {
                this.mergeData(
                    item.parameterName,
                    ''
                );
            })
        }
    }

    getChildContext = () => {
        return { shortcuts: shortcutManager }
    }

    setValue = (property, value, id, valueTo) => {
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

    mergeData = (property, value, valueTo) => {
        this.setState(prevState => ({
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
        }))
    }

    handleApply = () => {
        const {applyFilters, closeFilterMenu} = this.props;
        const {filter} = this.state;

        if(
            filter &&
            filter.parametersLayoutType === 'singleOverlayField' &&
            !filter.parameters[0].value
        ){
            return this.handleClear();
        }

        applyFilters(filter, () => {
            closeFilterMenu();
        });
    }

    handleClear = () => {
        const {
            clearFilters, closeFilterMenu, returnBackToDropdown
        } = this.props;

        clearFilters();
        closeFilterMenu();
        returnBackToDropdown && returnBackToDropdown();
    }

    toggleTooltip = (visible) => {
        this.setState({
            isTooltipShow: visible
        });
    }

    render() {
        const {
            data, notValidFields, isActive, windowType, onShow, onHide, viewId,
            outsideClick
        } = this.props;

        const {
            filter, isTooltipShow
        } = this.state;

        return (
            <div>
                {
                    data.parametersLayoutType === 'singleOverlayField' ?
                    <OverlayField
                        type={windowType}
                        filter={true}
                        layout={filter}
                        handlePatch={this.setValue}
                        handleChange={this.setValue}
                        closeOverlay={outsideClick}
                        handleSubmit={this.handleApply}
                        {...{windowType, onShow, onHide, viewId}}
                    /> :
                    <div className="filter-menu filter-widget">
                        <div>{counterpart.translate('window.activeFilter.caption')}:
                            <span className="filter-active">
                                {data.caption}
                            </span>
                            {isActive &&
                                <span
                                    className="filter-clear"
                                    onClick={() => this.handleClear()}
                                >
                                    {counterpart.translate('window.clearFilter.caption')}
                                    <i className="meta-icon-trash" />
                            </span>
                        }
                    </div>
                    <div className="form-group row filter-content">
                        <div className="col-sm-12">
                            {filter.parameters &&
                            filter.parameters.map((item, index) =>
                                <RawWidget
                                    entity="documentView"
                                    subentity="filter"
                                    subentityId={filter.filterId}
                                    handlePatch={this.setValue}
                                    handleChange={this.setValue}
                                    widgetType={item.widgetType}
                                    fields={[item]}
                                    type={item.type}
                                    widgetData={[item]}
                                    key={index}
                                    id={index}
                                    range={item.range}
                                    caption={item.caption}
                                    noLabel={false}
                                    filterWidget={true}
                                    {...{viewId, windowType, onShow, onHide}}
                                />
                            )}
                        </div>
                        <div className="col-sm-12 text-xs-right">
                            {notValidFields &&
                                <div className="input-error">
                                    {counterpart.translate('window.noMandatory.caption')}
                                </div>
                            }
                        </div>
                    </div>
                    <div className="filter-btn-wrapper">
                        <button
                            className="applyBtn btn btn-sm btn-success"
                            onClick={this.handleApply}
                            onMouseEnter={() =>
                                this.toggleTooltip(true)
                            }
                            onMouseLeave={() => this.toggleTooltip(false)}
                        >
                            {counterpart.translate('window.apply.caption')}
                            {isTooltipShow &&
                                <Tooltips
                                    name={keymap.MODAL_CONTEXT.APPLY}
                                    action={counterpart.translate('window.apply.caption')}
                                    type={''}
                                />
                            }
                        </button>
                    </div>
                </div>
                }
                <ModalContextShortcuts
                    apply={this.handleApply}
                />
            </div>
    )}
}

FiltersItem.childContextTypes = {
    shortcuts: PropTypes.object.isRequired
}

export default FiltersItem
