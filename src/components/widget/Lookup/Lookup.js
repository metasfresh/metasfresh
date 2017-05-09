import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import update from 'immutability-helper';

import {
    autocompleteRequest,
    dropdownRequest
} from '../../../actions/GenericActions';

import {
    getItemsByProperty,
    openModal
} from '../../../actions/WindowActions';

import RawLookup from './RawLookup';

import LookupList from './LookupList';
import List from '../List/List';

class Lookup extends Component {
    constructor(props) {
        super(props);

        const {properties} = this.props;

        this.state = {
            isInputEmpty: true,
            propertiesCopy: getItemsByProperty(properties, 'source', 'list'),
            showNextDropdown: false,
            property: ''
        }

        // console.log(this.state.propertiesCopy);
        // console.log(properties);
    }

    componentDidMount() {
        const {defaultValue, properties} = this.props;
        console.log('defaultValue component did mount');
        console.log(defaultValue);
        console.log(properties);

        // this.setState({
        //     property: properties[0].field
        // })

        this.checkIfDefaultValue();
    }

    componentDidUpdate(prevProps) {
        const {defaultValue, properties} = this.props;
        const {property} = this.state;
         
        //  console.log('property');
        //  console.log(property);

        // if(JSON.stringify(prevProps.defaultValue)!==JSON.stringify(defaultValue)){

        //     defaultValue.map((item, index)=>{
        //         const prevIndex = index-1;
        //         if(prevIndex>-1 && defaultValue[prevIndex].field === property){
        //             console.log(properties[index].field);
        //             this.setState({
        //                 property: properties[index].field
        //             })
        //         }
                
        //     })
        // }
        // console.log('defaultValue');
        // console.log(defaultValue);

        
     
    }

    handleInputEmptyStatus = (isEmpty) => {
        this.setState({
            isInputEmpty: isEmpty
        })
    }

    // getNextDropdown=(state)=> {
    //     this.setState({
    //         showNextDropdown: state
    //     });
    // }

    setNextProperty = (property)=> {
        const {defaultValue, properties} = this.props;
        console.log('setNextProperty');
        console.log(property);
        // property && this.setState({
        //     property: property
        // });


        defaultValue.map((item, index)=>{
                const nextIndex = index+1;
                if(nextIndex<defaultValue.length && defaultValue[index].field === property){
                    console.log(properties[nextIndex].field);
                    this.setState({
                        property: properties[nextIndex].field
                    })
                    return;
                } else if(defaultValue[defaultValue.length-1].field === property){
                    console.log('else-----------------------------');
                    this.setState({
                        property: ''
                    })
                }
                
            })
        
        
    }

    checkIfDefaultValue = () => {
        const {defaultValue} = this.props;
        defaultValue.map(item => {
            if(item.value){
                this.setState({
                    isInputEmpty: false
                })
                // console.log(item.value);
            }
            
        });
    }

    // componentDidUpdate(prevProps) {
    //     const {defaultValue} = this.props;
    //     console.log(prevProps.defaultValue);

    //     if(JSON.stringify(prevProps.defaultValue) !==
    //         JSON.stringify(defaultValue)){
    //         this.checkIfDefaultValue();
    //     }
        

    //     // defaultValue.map(item => {
    //     //     if(item.value){
    //     //         this.setState({
    //     //             isInputEmpty: false
    //     //         })
    //     //         console.log(item.value);
    //     //     }
            
    //     // });
    // }

    handleClear = (e) => {
        // console.log('handleClear');
        const {onChange, properties} = this.props;
        onChange(properties, null, false);
        this.setState({
            isInputEmpty: true,
            property: properties[0].field,
            property: ''
        });
    }

    render() {
        const {
            rank, readonly, defaultValue, placeholder, align, isModal, updated,
            filterWidget, mandatory, rowId, tabIndex, validStatus, recent, onChange,
            newRecordCaption, properties, windowType, parameterName, entity, dataId, tabId,
            subentity, subentityId, viewId
        } = this.props;

        const {isInputEmpty, property} = this.state;

        return (
            <div
                onKeyDown={this.handleKeyDown}
                ref={(c) => this.dropdown = c}
                className={
                    'input-dropdown-container lookup-wrapper input-' +
                    (rank ? rank : 'primary') +
                    (updated ? ' pulse-on' : ' pulse-off') +
                    (filterWidget ? ' input-full' : '')+
                    (mandatory && (
                        (validStatus.initialValue && !validStatus.valid)) ?
                        ' input-mandatory ' : '') +
                    ((validStatus &&
                        (
                            (!validStatus.valid && !validStatus.initialValue)
                        )
                    ) ? ' input-error ' : '')
                }
            >

            {
                properties && properties.map((item, index) => {
                    {/*console.log(item);*/}
                    {
                        if(item.source === 'lookup'){
                            return <RawLookup
                                key={index}
                                newRecordCaption={newRecordCaption}
                                defaultValue={getItemsByProperty(
                                            defaultValue, 'field', item.field)
                                            [0].value}
                                mainProperty={[item]}
                                placeholder={placeholder}
                                readonly={readonly}
                                tabIndex={tabIndex}
                                windowType={windowType}
                                parameterName={parameterName}
                                entity={entity}
                                dataId={dataId}
                                isModal={isModal}
                                recent={recent}
                                rank={rank}
                                updated={updated}
                                filterWidget={filterWidget}
                                mandatory={mandatory}
                                validStatus={validStatus}
                                align={align}
                                onChange={onChange}
                                item={item}
                                handleInputEmptyStatus={this.handleInputEmptyStatus}
                                setNextProperty={this.setNextProperty}
                            />

                        } else if (item.source === 'list') {

                            const objectValue = getItemsByProperty(
                                defaultValue, 'field', item.field
                            )[0].value;

                            {/*console.log('----');
                            console.log(item.field);
                            console.log(property);*/}


                            return <div className="raw-lookup-wrapper raw-lookup-wrapper-bcg" key={index}>
                                    <List
                                        dataId={dataId}
                                        entity={entity}
                                        windowType={windowType}
                                        filterWidget={filterWidget}
                                        properties={[item]}
                                        tabId={tabId}
                                        rowId={rowId}
                                        subentity={subentity}
                                        subentityId={subentityId}
                                        viewId={viewId}
                                        onChange={onChange}
                                        lookupList={true}
                                        autofocus={item.field == property ? true : false}
                                        defaultValue={objectValue[Object.keys(objectValue)[0]]}
                                        isInputEmpty={isInputEmpty}
                                        setNextProperty={this.setNextProperty}
                                        mainProperty={[item]}
                                    />
                                </div>
                        }
                        
                        
                    }
                })
            }

    

            {isInputEmpty ?
                <div className="input-icon input-icon-lg raw-lookup-wrapper">
                    <i className="meta-icon-preview" />
                </div> :
                <div className="input-icon input-icon-lg raw-lookup-wrapper">
                    {!readonly && <i
                        onClick={this.handleClear}
                        className="meta-icon-close-alt"
                    />}
                </div>
            }
                      
            </div>
        )
    }
}

export default Lookup
