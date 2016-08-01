import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const dataBenchmark = (obj, count) => {
    let objects = [];
    let i;
    for(i=0; i<count; ++i){
        let tmp = Object.assign({}, obj);
        tmp.id = i;
        objects.push(tmp);
    }
    return objects;
}

const initialState = {
    salesOrderWindow: {},
    salesOrderTable: {},
    orderStatus: 0,
    purchaser: {
        purchaser: null,
        invoice: null,
        unloading: null
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
        }, 400),
        containers: [{id: 123}]
    },
    productEdited: {
        property: '',
        id: 0,
        value: ''
    },
    selectedProducts: [],
    autocomplete: {
        query: "",
        selected: null,
        results: [],
        property: ""
    },
    recentPartners: [{
        id: 1,
        n: 'Jazzy Innovations'
    }],
    recentProducts: [{
        id: 1,
        n: 'Grüne Paloxe_Grüne Paloxe'
    }],
    orderList: dataBenchmark({
        id: 0,
        purchaser: 'Jazzy Innovations',
        amount: 123,
        ordered: "23.12.15",
        status: "WIP"
    }, 10),
    isSubheaderShow: false,
    isProductOrderSummaryShow: false,
    isOrderListShow: false
}

export default function salesOrderStateHandler(state = initialState, action) {
    switch(action.type){
        case types.PURCHASER_CHANGED:
            return update(state, {
                purchaser: {
                    purchaser: {$set: action.purchaser}
                }
            })
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
        case types.TOGGLE_ORDER_LIST:
            return Object.assign({}, state, {
                isOrderListShow: action.value
            })
        case types.TOGGLE_PRODUCT_ORDER_SUMMARY:
            return Object.assign({}, state, {
                isProductOrderSummaryShow: action.value
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
        case types.AUTOCOMPLETE_SUCCESS:
            return Object.assign({}, state, {
                autocomplete: Object.assign({}, state.autocomplete, {
                    results: action.results,
                    property: action.key
                })
            })
        case types.AUTOCOMPLETE_SELECT:
            return Object.assign({}, state, {
                autocomplete: Object.assign({}, state.autocomplete, {
                    selected: action.id
                })
            })
        case types.NEW_SALES_ORDER_CREATED:
            return Object.assign({}, state, {
                salesOrderWindow: action.response.Auftrag.childDescriptors,
                salesOrderTable: action.response.Orderline_includedTab
            })
        case types.CHANGE_ORDER_STATUS:
            return Object.assign({}, state, {
                orderStatus: action.value
            })

        default:
            return state
    }
}
