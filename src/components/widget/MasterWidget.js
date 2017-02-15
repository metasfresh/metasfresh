import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    patch,
    updateProperty,
    findRowByPropName
} from '../../actions/WindowActions';

import RawWidget from './RawWidget';

class MasterWidget extends Component {
    constructor(props) {
        super(props);

        this.state = {
            updated: false,
            edited: false
        }
    }

    componentWillReceiveProps(nextProps) {
        const {widgetData} = this.props;
        const {edited, editedProp} = this.state;

        if(JSON.stringify(widgetData[0].value) !== JSON.stringify(nextProps.widgetData[0].value)) {
            if(!edited) {
                this.setState(
                    Object.assign({}, this.state, {
                        updated: true
                    }), () => {
                        this.timeout = setTimeout(() => {
                            this.setState(Object.assign({}, this.state, {
                                updated: false
                            }))
                        }, 1000);
                    }
                )
            }else{
                this.setState(Object.assign({}, this.state, {
                    edited: false
                }));
            }
        }
    }

    componentWillUnmount() {
        clearTimeout(this.timeout);
    }

    handlePatch = (property, value) => {
        const {
            isModal, widgetType, widgetData, dataId, windowType, dispatch,
            rowId, tabId, onChange, relativeDocId, isAdvanced = false, entity
        } = this.props;

        let currRowId = rowId;
        let ret = null;

        if(rowId === 'NEW'){
            currRowId = relativeDocId;
        }

        if(widgetType !== 'Button'){
            dispatch(updateProperty(property, value, tabId, currRowId, isModal));
        }

        ret = dispatch(patch(
            entity, windowType, dataId, tabId, currRowId, property, value, isModal,
            isAdvanced
        ));

        //callback
        if(onChange){
            onChange();
        }

        return ret;
    }
    //
    // This method may looks like a redundant for this one above,
    // but is need to handle controlled components if
    // they patch on other event than onchange
    //
    handleChange = (property, val) => {
        const {
            dispatch, tabId, rowId, isModal, relativeDocId, precision, widgetType
        } = this.props;

        let currRowId = rowId;

        const dateParse = ['Date', 'DateTime', 'Time'];

        this.setState(Object.assign({}, this.state, {
            edited: true
        }), () => {
            if(dateParse.indexOf(widgetType) === -1 && !this.validatePrecision(val)){
                return;
            }

            if(rowId === 'NEW'){
                currRowId = relativeDocId;
            }

            dispatch(updateProperty(property, val, tabId, currRowId, isModal));

        });
    }

    setEditedFlag = (edited) => {
        this.setState(Object.assign({}, this.state, {
            edited: edited
        }));
    }

    validatePrecision = (value) => {
        const {widgetType, precision} = this.props;
        let precisionProcessed = precision;

        if(
            widgetType === 'Integer' ||
            widgetType === 'Quantity'
        ){
            precisionProcessed = 0;
        }

        if(precisionProcessed < (value.split('.')[1] || []).length){
            return false;
        }else{
            return true;
        }
    }


    render() {
        const {
            caption, widgetType, description, fields, windowType, type, noLabel,
            widgetData, dataId, rowId, tabId, icon, gridAlign, isModal, entity,
            handleBackdropLock, tabIndex, dropdownOpenCallback, autoFocus, fullScreen
        } = this.props;

        const {updated, edited} = this.state;

        return (
            <RawWidget
                entity={entity}
                widgetType={widgetType}
                fields={fields}
                windowType={windowType}
                dataId={dataId}
                widgetData={widgetData}
                rowId={rowId}
                tabId={tabId}
                icon={icon}
                gridAlign={gridAlign}
                handlePatch={this.handlePatch}
                handleChange={this.handleChange}
                updated={updated}
                isModal={isModal}
                setEditedFlag={this.setEditedFlag}
                noLabel={noLabel}
                type={type}
                caption={caption}
                handleBackdropLock={handleBackdropLock}
                tabIndex={tabIndex}
                dropdownOpenCallback={dropdownOpenCallback}
                autoFocus={autoFocus}
                fullScreen={fullScreen}
            />
        )
    }
}

MasterWidget.propTypes = {
    dispatch: PropTypes.func.isRequired
};

MasterWidget = connect()(MasterWidget)

export default MasterWidget
