import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import RawWidget from '../widget/RawWidget';

import {
    findRowByPropName,
    parseToDisplay,
    addNewRow
} from '../../actions/WindowActions';

import {
    initLayout,
    getData,
    patchRequest,
    createInstance,
    completeRequest
} from '../../actions/GenericActions';

import {
    addNotification
} from '../../actions/AppActions';

class TableQuickInput extends Component {
    // promise with patching for queuing form submission after patch is done
    patchPromise;

    constructor(props) {
        super(props);

        this.state = {
            layout: null,
            data: null,
            id: null,
            editedField: 0
        }
    }

    componentDidMount() {
        this.initQuickInput();
    }

    componentDidUpdate(prevProps, prevState) {
        const {data, layout, editedField} = this.state;
        if(data){
            for(let i = 0; i < layout.length; i++){
                const item = layout[i].fields.map(elem => findRowByPropName(data, elem.field));
                if(!item[0].value){
                    if(editedField !== i){
                        this.setState(Object.assign({}, this.state, {
                            editedField: i
                        }));
                    }

                    break;
                }
            }
        }
    }

    initQuickInput = () => {
        const {dispatch, docType, docId, tabId} = this.props;
        const {layout} = this.state;

        this.setState(Object.assign({}, this.state, {
            data: null
        }), () => {
            dispatch(createInstance('window', docType, docId, tabId, 'quickInput')).then(instance => {
                this.setState(Object.assign({}, this.state, {
                    data: parseToDisplay(instance.data.fields),
                    id: instance.data.id,
                    editedField: 0
                }));
            }).catch(err => {

            });

            !layout && dispatch(initLayout('window', docType, tabId, 'quickInput', docId)).then(layout => {
                this.setState(Object.assign({}, this.state, {
                    layout: layout.data.elements
                }))
            });
        });
    }

    handleChange = (field, value) => {
        const {data} = this.state;

        this.setState(Object.assign({}, this.state, {
            data: data.map(item => {
                if(field === 'all' || item.field === field){
                    return Object.assign({}, item, {
                        value: value
                    })
                }else{
                    return item;
                }
            })
        }))
    }

    handlePatch = (prop, value, callback) => {
        const {dispatch, docType, docId, tabId} = this.props;
        const {id} = this.state;

        this.patchPromise = new Promise(resolve => {
            dispatch(patchRequest('window', docType, docId, tabId, null, prop, value, 'quickInput', id))
                .then(response => {
                    response.data[0] && response.data[0].fields.map(item => {
                        this.setState({
                            data: this.state.data.map(field => {
                                if (field.field !== item.field){
                                    return field;
                                }

                                if(callback){
                                    callback();
                                }

                                resolve();
                                return {
                                    ...field,
                                    ...item
                                };
                            })
                        });
                    })
                })
        });
    }

    renderFields = (layout, data, dataId, attributeType, quickInputId) => {
        const {tabId, docType} = this.props;
        const {editedField} = this.state;
        if(layout){
            return layout.map((item, id) => {
                const widgetData = item.fields.map(elem => findRowByPropName(data, elem.field));
                return (<RawWidget
                    entity={attributeType}
                    subentity='quickInput'
                    subentityId={quickInputId}
                    tabId={tabId}
                    windowType={docType}
                    widgetType={item.widgetType}
                    fields={item.fields}
                    dataId={dataId}
                    widgetData={widgetData}
                    gridAlign={item.gridAlign}
                    key={id}
                    caption={item.caption}
                    handlePatch={(prop, value, callback) => this.handlePatch(prop,value, callback)}
                    handleFocus={() => {}}
                    handleChange={this.handleChange}
                    type="secondary"
                    autoFocus={id === 0}
                />)
            })
        }
    }

    onSubmit = (e) => {
        const {dispatch, docType, docId, tabId} = this.props;
        const {id,data} = this.state;
        e.preventDefault();

        document.activeElement.blur();

        if(!this.validateForm(data)){
            return dispatch(addNotification("Error", 'Mandatory fields are not filled!', 5000, "error"))
        }

        this.patchPromise
            .then(() => {
                console.log('submit');
                return dispatch(completeRequest('window', docType, docId, tabId, null, 'quickInput', id))
            })
            .then(response => {
                console.log('submitEnd');
                this.initQuickInput();
                dispatch(addNewRow(response.data, tabId, response.data.rowId, "master"))
            });
    }

    validateForm = (data) => {
        const {dispatch} = this.props;
        let ret = true;
        data.map(item => {
            if(!item.value){
                ret = false;
            }
        });
        return ret;
    }

    render() {
        const {
            docId
        } = this.props;

        const {
            data, layout, id
        } = this.state;

        return (
            <form
                onSubmit={this.onSubmit}
                className="quick-input-container"
                ref={c => this.form = c}
            >
                {this.renderFields(layout, data, docId, 'window', id)} <div className="hint">(Press 'Enter' to add)</div>
                <button type="submit" className="hidden-xs-up"></button>
            </form>
        )
    }
}

TableQuickInput.propTypes = {
    dispatch: PropTypes.func.isRequired
}

TableQuickInput = connect()(TableQuickInput);

export default TableQuickInput;
