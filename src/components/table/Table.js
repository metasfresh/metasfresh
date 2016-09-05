import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import update from 'react-addons-update';

import {
    openModal
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
            selectedProducts: [],
            contextMenu: {
                open: false,
                x: 0,
                y: 0
            }
        }
    }

    selectProduct = (id) => {
        this.setState({
            selectedProducts: this.state.selectedProducts.concat([id])
        })
    }

    selectRangeProduct = (ids) => {
        this.setState({
            selectedProducts: ids
        })
    }

    selectOneProduct = (id) => {
        this.setState({
            selectedProducts: [id]
        })
    }

    deselectProduct = (id) => {
        this.setState(update(this.state, {
            selectedProducts: {$splice: [[id, 1]]}
        }))
    }

    deselectAllProducts = () => {
        this.setState({
            selectedProducts: []
        })
    }


    handleClickOutside = (event) => {
      if(this.state.selectedProducts.length>0){
        const {dispatch} = this.props
        // dispatch(deselectAllProducts());
        this.deselectAllProducts();
      }
    }

    closeContextMenu = (event) => {
      this.setState({
            contextMenu: {
                open: false
            }
        });
    }

    handleClick = (e, id) => {
        e.preventDefault();

        const {dispatch} = this.props;
        const {selectedProducts} = this.state;
        const selectMore = e.nativeEvent.metaKey || e.nativeEvent.ctrlKey;
        const selectRange = e.shiftKey;
        const isSelected = selectedProducts.indexOf(id) > -1;
        const isAnySelected = selectedProducts.length > 0;
        const isMoreSelected = selectedProducts.length > 1;

        if(selectMore){
            if(isSelected){
                this.deselectProduct(id);
            }else{
                this.selectProduct(id);
            }
        }else if(selectRange){
            if(isAnySelected){
                const idsToSelect = this.getProductRange(id);
                this.selectRangeProduct(idsToSelect);
            }else{
                this.selectOneProduct(id);
            }
        }else{
            if(isSelected){
                if(isMoreSelected){
                    this.selectOneProduct(id);
                }else{
                    // this.deselectAllProducts();
                }
            }else{
                this.selectOneProduct(id);
            }
        }
    }
    handleRightClick = (e) => {
        e.preventDefault();
        this.setState({
            contextMenu: {
                x: e.clientX,
                y: e.clientY,
                open: true
            }
        });
    }
    handleRemoveSelected = () => {

    }
    sumProperty = (items, prop) => {
        return items.reduce((a, b) => {
            return b[prop] == null ? a : a + b[prop];
        }, 0);
    }
    getProductRange = (id) => {
        const {rowData, tabid} = this.props;
        let selected = [
            Object.keys(rowData[tabid]).findIndex(x => x === id),
            Object.keys(rowData[tabid]).findIndex(x => x === this.state.selectedProducts[0])
        ];
        selected.sort((a,b) => a - b);
        return Object.keys(rowData[tabid]).slice(selected[0], selected[1]+1);
    }
    openModal = (windowType, tabId, rowId) => {
        const {dispatch} = this.props;
        dispatch(openModal(windowType, tabId, rowId));
    }
    renderTableBody = () => {
        const {rowData, tabid, cols, type, docId} = this.props;
        const {selectedProducts} = this.state;
        if(!!rowData && rowData[tabid]){
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
                        isSelected={selectedProducts.indexOf(item[key].rowId) > -1}
                        onClick={(e) => this.handleClick(e, item[key].rowId)}
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
        const {cols, type, docId, rowData, tabid} = this.props;
        const buttonLayout = {
            fields: [{
                field: "example"
            }],
            rowId: 'NEW',
            tabId: 1,
            dataId: docId
        }
        const buttonData = {
            displayed: true,
            value: {"P": "New order line"}
        }

        return (
            <div className="row">
                <div className="col-xs-12">
                    <TableContextMenu
                        x={this.state.contextMenu.x}
                        y={this.state.contextMenu.y}
                        isDisplayed={this.state.contextMenu.open}
                        blur={() => this.closeContextMenu()}
                    />
                    <div className="row">
                        <div className="col-xs-12">
                            <button className="btn btn-meta-outline-secondary btn-distance btn-sm pull-xs-left" onClick={() => this.openModal(type, tabid, "NEW")}>Add new</button>
                            <button className="btn btn-meta-outline-secondary btn-distance btn-sm pull-xs-left" onClick={() => this.openModal(type + "&advanced=true")}>Advanced edit</button>
                            <div className="pull-xs-right">
                                <TableFilter />
                            </div>
                        </div>
                    </div>

                    <div className="panel panel-primary panel-bordered panel-bordered-force">
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

                        {rowData && rowData[tabid] && Object.keys(rowData[tabid]).length === 0 && this.renderEmptyInfo()}
                    </div>
                    {/* Temporary button for adding new row*/}
                    <Widget
                        widgetType="Button"
                        windowType={143}
                        widgetData={[buttonData]}
                        type={"primary"}
                        noLabel={true}
                        key={'tmpButton'}
                        {...buttonLayout}
                    />
                </div>
            </div>
        )
    }
}

Table.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Table = connect()(onClickOutside(Table))

export default Table
