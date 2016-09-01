import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';

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
        this.handleClickOutside = this.handleClickOutside.bind(this);
        this.state = {
            contextMenu: {
                open: false,
                x: 0,
                y: 0
            }
        }
    }


    handleClickOutside = (event) => {
      console.log('ssss');
    }

    handleClick = (e, id, selectedProd) => {
        e.preventDefault();

        const {selectedProducts, dispatch} = this.props
        const selectMore = e.nativeEvent.metaKey || e.nativeEvent.ctrlKey;
        const selectRange = e.shiftKey;
        const isSelected = selectedProd.indexOf(id) > -1;
        const isAnySelected = selectedProducts.length > 0;
        const isMoreSelected = selectedProducts.length > 1;

        console.log(id);
        console.log(selectedProd.indexOf(id));

        if(selectMore){
          console.log('select more');
            if(isSelected){
                dispatch(deselectProduct(id));
            }else{
                dispatch(selectProduct(id));
            }
        }else if(selectRange){
            console.log('select range');
            if(isAnySelected){
                const idsToSelect = this.getProductRange(id);
                dispatch(selectRangeProduct(idsToSelect));
            }else{
                dispatch(selectOneProduct(id));
            }
        }else{
            if(isSelected){
                if(isMoreSelected){
                    dispatch(selectOneProduct(id));
                }else{
                    // dispatch(deselectAllProducts());
                }
            }else{
                dispatch(selectOneProduct(id));
            }
        }
    }
    handleRightClick = (e) => {
        e.preventDefault();
        console.log('right click');
        // this.setState({x: e.clientX, y: e.clientY, contextMenuDisplayed: true})
        this.state.contextMenu.x = e.clientX;
        this.state.contextMenu.y = e.clientY;
        this.state.contextMenu.contextMenuDisplayed = true;
        console.log(this);

    }
    handleRemoveSelected = () => {
        this.props.dispatch(deleteSelectedProducts(this.props.selectedProducts));
    }
    sumProperty = (items, prop) => {
        return items.reduce((a, b) => {
            return b[prop] == null ? a : a + b[prop];
        }, 0);
    }
    getProductRange = (id) => {
        const {products, selectedProducts} = this.props;
        let selected = [
            products.products.findIndex(x => x.id === id),
            products.products.findIndex(x => x.id === selectedProducts[0])
        ];
        selected.sort((a,b) => a - b);
        return products.products.slice(selected[0], selected[1]+1).map(p => {
            return p.id
        });
    }
    openModal = () => {
        this.props.dispatch(openModal());
    }
    renderTableBody = () => {
        const {rowData, tabid, cols, type, docId} = this.props;
        console.log(rowData.length);
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
                        onClick={(e) => this.handleClick(e, item[key].rowId, this.props.selectedProducts)}
                        onContextMenu={(e) => this.handleRightClick(e)}
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
                    <TableContextMenu x={this.state.contextMenu.x} y={this.state.contextMenu.y} isDisplayed={this.state.contextMenu.contextMenuDisplayed} />
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

                        <table className="table table-bordered-vertically table-striped" onContextMenu={(e) => this.handleRightClick(e)}>
                            <thead>
                                <TableHeader cols={cols} />
                            </thead>
                            <tbody>
                            {this.renderTableBody()}
                            </tbody>
                            <tfoot>
                            </tfoot>
                        </table>

                        {console.log("position x")}
                        {console.log(this.state.contextMenu.x)}

                        { this.props.rowData[this.props.tabid] ? ((Object.keys(this.props.rowData[this.props.tabid]).length > 0) ? "" :  this.renderEmptyInfo()) : "null" }

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

    const selectedProducts = state.appHandler.selectedProducts;
    return {
        rowData, selectedProducts
    }
}

Table = connect(mapStateToProps)(Table)

export default Table
// export default onClickOutside(Table)
