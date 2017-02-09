import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    initLayout,
    getData,
    patchRequest
} from '../../actions/GenericActions';

import {
    findRowByPropName
} from '../../actions/WindowActions';

import RawWidget from '../widget/RawWidget';

class SelectionAttributes extends Component {
    constructor(props){
        super(props);
    }

    componentDidUpdate = (prevProps) => {
        const {
            selected, DLWrapperSetData, DLWrapperSetLayout, refresh
        } = this.props;

        if(
            (JSON.stringify(prevProps.selected) !== JSON.stringify(selected)) ||
            (JSON.stringify(prevProps.refresh) !== JSON.stringify(refresh))
        ){
                DLWrapperSetData([], null, () => {
                    DLWrapperSetLayout([], () => {
                        if(selected.length === 1){
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
            windowType, viewId, selected, entity, DLWrapperSetData, DLWrapperSetLayout,
            dispatch
        } = this.props;

        dispatch(initLayout(entity, windowType, selected[0], null, viewId)
            ).then(response => {
                DLWrapperSetLayout(response.data.elements);
                return dispatch(getData(entity, windowType, viewId, selected[0]));
            }).then(response => {
                DLWrapperSetData(response.data.fields, response.data.id);
            });
    }

    focusHUTable = (e) => {
        
        switch(e.key) {
            case "Tab":
                e.preventDefault();
                console.log('Tab preassed');
                console.log(e.key);
                document.getElementsByClassName('js-table')[0].focus();
                window.scrollTo(0,0);
            break;
        }
    }


    render() {
        const {
            windowType, viewId, DLWrapperLayout, DLWrapperData, DLWrapperDataId,
            DLWrapperHandleChange, DLWrapperHandlePatch, entity, setClickOutsideLock
        } = this.props;

        const lastItem = DLWrapperLayout.length-1;

        return (
            <div>
                <div className="attributes-selector-header">
                    Selection attributes
                </div>
                <div tabIndex={1} className="attributes-selector-body js-attributes">
                    {DLWrapperLayout && DLWrapperLayout.map((item, id) =>
                        <RawWidget
                            entity={entity}
                            widgetType={item.widgetType}
                            fields={item.fields}
                            dataId={DLWrapperDataId}
                            windowType={windowType}
                            viewId={viewId}
                            widgetData={item.fields.map(elem => findRowByPropName(DLWrapperData, elem.field))}
                            gridAlign={item.gridAlign}
                            key={id}
                            type={item.type}
                            caption={item.caption}
                            handleFocus={() => setClickOutsideLock(true)}
                            handleBlur={() => setClickOutsideLock(false)}
                            handlePatch={DLWrapperHandlePatch}
                            handleChange={DLWrapperHandleChange}
                            tabIndex={1}
                            focusHUTable={lastItem===id ? this.focusHUTable : ""}
                        />
                    )}
                </div>
            </div>
        );
    }
}

SelectionAttributes = connect()(SelectionAttributes)

export default SelectionAttributes;
