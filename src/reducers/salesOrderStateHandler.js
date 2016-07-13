import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const dataBenchmark = (obj) => {
    let objects = [];
    let i;
    for(i=0; i<50; ++i){
        let tmp = Object.assign({}, obj);
        tmp.id = i;
        objects.push(tmp);
    }
    return objects;
}

const initialState = {
    purchaser: {
        recent: [],
        purchaser: '',
        invoice: '',
        unloading: ''
    },
    products: {
        products: dataBenchmark({
            id: 0,
            name: 'Convenience Salad 250g',
            amount: 15,
            packing: 'ICO 6410 x 10 Stk',
            quantity: 150,
            price: 2.0,
            priceFor: 'Each',
            discount: 0
        }),
        containers: [{id: 123}]
    },
    productEdited: {
        property: '',
        id: 0,
        value: ''
    },
    selectedProducts: [],
    isSubheaderShow: false,
    autocomplete: {
        query: "",
        results: [{
            name: 'Jazzy Innovations',
            address: 'Tracka 18, Gliwice, Poland',
            vat: '541-141-56-23'
        }]
    },
    recentPartners: [{
        id: 1,
        name: 'Jazzy Innovations',
        address: 'Tracka 18, Gliwice, Poland',
        vat: '541-141-56-23'
    },{
        id: 2,
        name: 'Innovations',
        address: 'Jazzy 18, Gliwice, Poland',
        vat: '541-141-56-23'
    },{
        id: 3,
        name: 'Jazzy',
        address: 'Innovation 18, Gliwice, Poland',
        vat: '541-141-56-23'
    }]
}

export default function salesOrderStateHandler(state = initialState, action) {
    switch(action.type){
        case types.INVOICE_CHANGED:
            return update(state, {
                purchaser: {
                    invoice: {$set: action.value}
                }
            })
        case types.UNLOADING_CHANGED:
            return update(state, {
                purchaser: {
                    unloading: {$set: action.value}
                }
            })
        case types.EDIT_PRODUCT_PROPERTY:
            return update(state, {
                productEdited: {
                    property: {$set: action.property}
                }
            })
        case types.CHANGE_PRODUCT_PROPERTY:
            return update(state, {
                productEdited: {
                    value: {$set: action.value}
                }
            })
        case types.SAVE_PRODUCT_PROPERTY:
            return Object.assign({}, state, {
                products: Object.assign({}, state.products, {
                    products: state.products.products.map(product => {
                        if( product.id === action.id ){
                            product[action.property] = action.value;
                            return product;
                        }else{
                            return product;
                        }
                    })
                }),
                productEdited: {}
            })
        case types.SELECT_PRODUCT:
            return update(state, {
                selectedProducts: {$push: [action.product]}
            })
        case types.SELECT_ONE_PRODUCT:
            return update(state, {
                selectedProducts: {$set: [action.product]}
            })
        case types.SELECT_RANGE_PRODUCT:
            return update(state, {
                selectedProducts: {$set: action.ids}
            })
        case types.DESELECT_PRODUCT:
            return update(state, {
                selectedProducts: {$splice: [[action.product, 1]]}
            })
        case types.DESELECT_ALL_PRODUCTS:
            return update(state, {
                selectedProducts: {$set: []}
            })
        case types.SHOW_SUBHEADER:
            return Object.assign({}, state, {
                isSubheaderShow: true
            })
        case types.HIDE_SUBHEADER:
            return Object.assign({}, state, {
                isSubheaderShow: false
            })
        case types.DELETE_SELECTED_PRODUCTS:
            return Object.assign({}, state, {
                products: Object.assign({}, state.products, {
                    products: state.products.products.map(product => {
                        return product;
                    })
                })
            })
        case types.AUTOCOMPLETE:
            return Object.assign({}, state, {
                autocomplete: Object.assign({}, state.autocomplete, {
                    query: action.query
                })
            })

        default:
            return state
    }
}
