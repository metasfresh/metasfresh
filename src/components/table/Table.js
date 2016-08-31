import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    selectProduct,
    selectRangeProduct,
    selectOneProduct,
    deselectProduct,
    deselectAllProducts,
} from '../../actions/AppActions';

import {
    openModal,
    closeModal
} from '../../actions/WindowActions';

import TableFilter from './TableFilter';
import TableHeader from './TableHeader';
import TableContextMenu from './TableContextMenu';
import TableItem from './TableItem';
import Widget from '../Widget';

class Table extends Component {
    constructor(props) {
        super(props);
        this.state = {
            contextMenu: {
                open: false,
                x: 0,
                y: 0
            }
        }
    }
    handleClick = (e, id, index) => {
        // e.preventDefault();
        //
        // const {selectedProducts, dispatch} = this.props
        // const selectMore = e.nativeEvent.metaKey || e.nativeEvent.ctrlKey;
        // const selectRange = e.shiftKey;
        // const isSelected = index > -1;
        // const isAnySelected = selectedProducts.length > 0;
        // const isMoreSelected = selectedProducts.length > 1;
        //
        // if(selectMore){
        //     if(isSelected){
        //         dispatch(deselectProduct(index));
        //     }else{
        //         dispatch(selectProduct(id));
        //     }
        // }else if(selectRange){
        //     if(isAnySelected){
        //         const idsToSelect = this.getProductRange(id);
        //         dispatch(selectRangeProduct(idsToSelect));
        //     }else{
        //         dispatch(selectOneProduct(id));
        //     }
        // }else{
        //     if(isSelected){
        //         if(isMoreSelected){
        //             dispatch(selectOneProduct(id));
        //         }else{
        //             dispatch(deselectAllProducts());
        //         }
        //     }else{
        //         dispatch(selectOneProduct(id));
        //     }
        // }
    }
    handleRemoveSelected = () => {
        // this.props.dispatch(deleteSelectedProducts(this.props.selectedProducts));
    }
    sumProperty = (items, prop) => {
        return items.reduce((a, b) => {
            return b[prop] == null ? a : a + b[prop];
        }, 0);
    }
    getProductRange = (id) => {
        // const {products, selectedProducts} = this.props;
        // let selected = [
        //     products.products.findIndex(x => x.id === id),
        //     products.products.findIndex(x => x.id === selectedProducts[0])
        // ];
        // selected.sort((a,b) => a - b);
        // return products.products.slice(selected[0], selected[1]+1).map(p => {
        //     return p.id
        // });
    }
    openModal = () => {
        this.props.dispatch(openModal());
    }
    renderTableBody = () => {
        const {rowData, tabid, cols, type, docId} = this.props;
        if(rowData[tabid]){
            let keys = Object.keys(rowData[tabid]);
            const item = rowData[tabid];
            let ret = [];
            for(let i=0; i < keys.length; i++) {
                const key = keys[i];
                ret.push(
                    <TableItem
                        fields={item[key].fields}
                        key={i}
                        rowId={item[key].rowId}
                        tabId={tabid}
                        cols={cols}
                        type={type}
                        docId={docId}
                        // onClick={(e) => this.handleClick(e, product.id, selectedProducts.indexOf(product.id))}
                    />
                );
            }

            return ret;
        }
    }

    renderEmptyInfo = () => {
            return (
                <div className="empty-info-text">
                    <div>
                        <h5>There are no pricing rules assigned to this product.</h5>
                        <p>You can define them in "Pricing systems"</p>
                    </div>
                </div>
            )
    }

    render() {
        const {cols, windowType, docId, rowData, tabid} = this.props;
        const buttonLayout = {
            fields: [{
                field: "example"
            }],
            rowId: 'NEW',
            tabId: 1,
            dataId: docId,

        }
        const buttonData = {
            displayed: true,
            value: {"P": "New order line"}
        }
        console.log(rowData);
        return (
            <div className="row">
                <div className="col-xs-12">
                    <TableFilter />
                    <TableContextMenu />
                    <div className="panel panel-primary panel-bordered panel-bordered-force">
                        {/* Temporary button for adding new row*/}
                        <Widget
                            widgetType="Button"
                            windowType={143}
                            widgetData={buttonData}
                            type={"primary"}
                            noLabel={true}
                            key={'tmpButton'}
                            {...buttonLayout}
                        />
                        <button className="btn btn-meta-primary btn-sm" onClick={this.openModal}>Advanced edit</button>

                        <table className="table table-bordered-vertically table-striped">
                            <thead>
                                <TableHeader cols={cols} />
                            </thead>
                            <tbody>
                            {this.renderTableBody()}
                            {/*rowData[tabid].length > 0 ? this.renderTableBody() : this.renderEmptyInfo()*/}
                            </tbody>
                            <tfoot>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        )
    }
}


Table.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { windowHandler } = state;
    const {
        rowData
    } = windowHandler || {
        rowData: {}
    }

    return {
        rowData
    }
}

Table = connect(mapStateToProps)(Table)

export default Table
