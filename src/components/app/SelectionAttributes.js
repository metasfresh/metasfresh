import React, { Component } from 'react';
import {connect} from 'react-redux';
import counterpart from 'counterpart';

import {
    initLayout,
    getData
} from '../../actions/GenericActions';

import RawWidget from '../widget/RawWidget';

class SelectionAttributes extends Component {
    constructor(props){
        super(props);
    }

    componentDidUpdate = (prevProps) => {
        const {
            selected, DLWrapperSetData, DLWrapperSetLayout, refresh,
            shouldNotUpdate
        } = this.props;

        if(shouldNotUpdate){
            return;
        }

        if(
            (JSON.stringify(prevProps.selected) !== JSON.stringify(selected)) ||
            (JSON.stringify(prevProps.refresh) !== JSON.stringify(refresh))
        ){
            DLWrapperSetData([], null, () => {
                DLWrapperSetLayout([], () => {
                    if(selected && selected.length === 1){
                        if(selected[0] == 0){
                            return;
                        }
                        this.fetchActions();
                    }
                })
            })
        }
    }

    fetchActions = () => {
        const {
            windowType, viewId, selected, entity, DLWrapperSetData,
            DLWrapperSetLayout
        } = this.props;

        initLayout(entity, windowType, selected[0], null, viewId)
            .then(response => {
                DLWrapperSetLayout(response.data.elements);
                return getData(entity, windowType, viewId, selected[0]);
            }).then(response => {
                DLWrapperSetData(response.data.fieldsByName, response.data.id);
            }).catch(() => {});
    }

    moveToDevice = (e) => {
        switch(e.key) {
            case 'Shift':
                e.preventDefault();
                //TO DO
            break;
        }
    }

    getTabId = (item) => {
        return item && item[0].readonly ? -1 : 1;

    }

    selectTable = () => {
        document.getElementsByClassName('js-table')[0].focus();
    }

    render() {
        const {
            windowType, viewId, DLWrapperLayout, DLWrapperData, DLWrapperDataId,
            DLWrapperHandleChange, DLWrapperHandlePatch, entity,
            setClickOutsideLock
        } = this.props;

        return (
            <div className="js-not-unselect">
                <div className="attributes-selector-header">
                    {counterpart.translate(
                        'window.selectionAttributes.caption')}
                </div>
                <div
                    tabIndex={1}
                    className="attributes-selector-body js-attributes"
                >
                    {DLWrapperLayout && DLWrapperLayout.map((item, id) =>
                        <RawWidget
                            entity={entity}
                            attribute={true}
                            widgetType={item.widgetType}
                            fields={item.fields}
                            dataId={DLWrapperDataId}
                            windowType={windowType}
                            viewId={viewId}
                            widgetData={item.fields.map(elem =>
                                DLWrapperData[elem.field] || -1
                            )}
                            gridAlign={item.gridAlign}
                            key={id}
                            type={item.type}
                            caption={item.caption}
                            handleFocus={() => setClickOutsideLock(true)}
                            handleBlur={() => setClickOutsideLock(false)}
                            handlePatch={DLWrapperHandlePatch}
                            handleChange={DLWrapperHandleChange}
                            tabIndex={this.getTabId(
                                item.fields.map(elem =>
                                    DLWrapperData[elem.field] || -1
                                )
                            )}
                        />
                    )}
                    {DLWrapperLayout && !DLWrapperLayout.length &&
                        <i>
                            {counterpart.translate(
                                'window.selectionAttributes.callToAction'
                            )}
                        </i>
                    }
                </div>
                <div
                    className="focusHandler"
                    tabIndex={1}
                    onFocus={this.selectTable}
                />
            </div>
        );
    }
}

SelectionAttributes = connect()(SelectionAttributes)

export default SelectionAttributes;
