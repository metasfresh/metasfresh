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
            selected: [],
            contextMenu: {
                open: false,
                x: 0,
                y: 0
            }
        }
    }

    selectProduct = (id) => {
        this.setState(Object.assign({}, this.state, {
            selected: this.state.selected.concat([id])
        }))
    }

    selectRangeProduct = (ids) => {
        this.setState(Object.assign({}, this.state, {
            selected: ids
        }))
    }

    selectOneProduct = (id) => {
        this.setState(Object.assign({}, this.state, {
            selected: [id]
        }))
    }

    deselectProduct = (id) => {
        this.setState(update(this.state, {
            selected: {$splice: [[id, 1]]}
        }))
    }

    deselectAllProducts = () => {
        this.setState(Object.assign({}, this.state, {
            selected: []
        }))
    }


    handleClickOutside = (event) => {
      if(this.state.selected.length>0){
        const {dispatch} = this.props
        // dispatch(deselectAllProducts());
        this.deselectAllProducts();
      }
    }

    closeContextMenu = (event) => {
      this.setState(Object.assign({}, this.state, {
            contextMenu: Object.assign({}, this.state.contextMenu, {
                open: false
            })
        }))
    }

    handleClick = (e, id) => {
        e.preventDefault();

        const {dispatch} = this.props;
        const {selected} = this.state;
        const selectMore = e.nativeEvent.metaKey || e.nativeEvent.ctrlKey;
        const selectRange = e.shiftKey;
        const isSelected = selected.indexOf(id) > -1;
        const isAnySelected = selected.length > 0;
        const isMoreSelected = selected.length > 1;

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
        // const {selected} = this.state;
        // if(selected.length < 1) {
        //   this.selectProduct(id);
        // }
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
            Object.keys(rowData[tabid]).findIndex(x => x === this.state.selected[0])
        ];
        selected.sort((a,b) => a - b);
        return Object.keys(rowData[tabid]).slice(selected[0], selected[1]+1);
    }
    openModal = (windowType, tabId, rowId) => {
        const {dispatch} = this.props;
        dispatch(openModal("Add new", windowType, tabId, rowId));
    }
    renderTableBody = () => {
        const {rowData, tabid, cols, type, docId} = this.props;
        const {selected} = this.state;
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
                        isSelected={selected.indexOf(item[key].rowId) > -1}
                        onClick={(e) => this.handleClick(e, item[key].rowId)}
                        onContextMenu={(e) => this.handleRightClick(e)}
                    />
                );
            }

            return ret;
        }
    }

    renderEmptyInfo = () => {
        const {emptyText, emptyHint} = this.props;
        return (
            <div className="empty-info-text">
                <div>
                    <h5>{emptyText}</h5>
                    <p>{emptyHint}</p>
                </div>
            </div>
        )
    }

    render() {
        const {cols, type, docId, rowData, tabid} = this.props;
        const {x,y,contextMenu,selected} = this.state;

        return (
            <div className="row">
                <div className="col-xs-12">
                    <TableContextMenu
                        x={contextMenu.x}
                        y={contextMenu.y}
                        isDisplayed={contextMenu.open}
                        blur={() => this.closeContextMenu()}
                        type={type}
                        tabId={tabid}
                        selected={selected}
                    />
                    <div className="row">
                        <div className="col-xs-12">
                            <button className="btn btn-meta-outline-secondary btn-distance btn-sm pull-xs-left" onClick={() => this.openModal(type, tabid, "NEW")}>Add new</button>
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
