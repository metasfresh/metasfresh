import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import update from 'react-addons-update';

import {
    openModal
} from '../../actions/WindowActions';

import TableFilter from './TableFilter';
import TablePagination from './TablePagination';
import TableHeader from './TableHeader';
import TableContextMenu from './TableContextMenu';
import TableItem from './TableItem';
import MasterWidget from '../MasterWidget';


class Table extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selected: [],
            listenOnKeys: true,
            contextMenu: {
                open: false,
                x: 0,
                y: 0
            }
        }
    }

    changeListenOnTrue = () => {
        this.setState(Object.assign({}, this.state, {
            listenOnKeys: true
        }))
    }

    changeListenOnFalse = () => {
        this.setState(Object.assign({}, this.state, {
            listenOnKeys: false
        }))
    }

    selectAll = () => {
        const {rowData, tabid, keyProperty} = this.props;
        const property = keyProperty ? keyProperty : "rowId";
        const toSelect = rowData[tabid].map((item, index) => item[property]);
        this.selectRangeProduct(toSelect);
    }

    getSelectedItems = () => {
        const {selected} = this.state;
        return selected;
    }

    selectProduct = (id, idFocused, idFocusedDown) => {
        this.setState(Object.assign({}, this.state, {
            selected: this.state.selected.concat([id])
        }), ()=> {
            if(idFocused){
                document.getElementsByClassName('row-selected')[0].children[idFocused].focus();
            }
            if(idFocusedDown){
                document.getElementsByClassName('row-selected')[document.getElementsByClassName('row-selected').length-1].children[idFocusedDown].focus();
            }

        })
    }

    selectRangeProduct = (ids) => {
        this.setState(Object.assign({}, this.state, {
            selected: ids
        }))
    }

    selectOneProduct = (id, idFocused, idFocusedDown) => {
        this.setState(Object.assign({}, this.state, {
            selected: [id]
        }), ()=> {
            if(idFocused){
                document.getElementsByClassName('row-selected')[0].children[idFocused].focus();
            }
            if(idFocusedDown){
                document.getElementsByClassName('row-selected')[document.getElementsByClassName('row-selected').length-1].children[idFocusedDown].focus();
            }

        })

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
        if(this.state.selected.length > 0){
            this.deselectAllProducts();
        }

    }

    handleKeyDown = (e) => {

        const {rowData, tabid, listenOnKeys} = this.props;
        const item = rowData[tabid];
        const {selected} = this.state;
        const selectRange = e.shiftKey;

        let nodeList = Array.prototype.slice.call( document.activeElement.parentElement.children);
        let idActive = nodeList.indexOf(document.activeElement);

        let idFocused = null;
        if(idActive > -1) {
            idFocused = idActive;
        }


        switch(e.key) {
            case "ArrowDown":
                e.preventDefault();

                const actualId = Object.keys(rowData[tabid]).findIndex(x => x === selected[selected.length-1])

                if(actualId < Object.keys(rowData[tabid]).length-1 ){
                    let newId = actualId+1;
                    // this.state.selected = [Object.keys(rowData[tabid])[newId]];

                    if(!selectRange) {
                        this.selectOneProduct(Object.keys(rowData[tabid])[newId], false, idFocused);
                    } else {
                        this.selectProduct(Object.keys(rowData[tabid])[newId], false, idFocused);
                    }
                }
                break;
            case "ArrowUp":
                e.preventDefault();

                const actual = Object.keys(rowData[tabid]).findIndex(x => x === selected[selected.length-1])

                if(actual > 0 ){
                    let newId = actual-1;

                    if(!selectRange) {
                        this.selectOneProduct(Object.keys(rowData[tabid])[newId], idFocused);

                    } else {
                        this.selectProduct(Object.keys(rowData[tabid])[newId], idFocused);
                    }
                }
                break;
            case "ArrowLeft":
                e.preventDefault();
                if(document.activeElement.previousSibling){
                    document.activeElement.previousSibling.focus();
                }
                break;
            case "ArrowRight":
                e.preventDefault();
                if(document.activeElement.nextSibling){
                   document.activeElement.nextSibling.focus();
                }
                break;
        }

    }

    handleKeyDownDocList = (e) => {
        const {selected} = this.state;


        const {rowData, tabid, listenOnKeys, onDoubleClick} = this.props;
        const item = rowData[tabid];
        const selectRange = e.shiftKey;



        switch(e.key) {
            case "ArrowDown":
                e.preventDefault();


                const array = (rowData[tabid]).map((item, id) => {
                    return item.id
                });


                const actualId = array.findIndex(x => x === selected[selected.length-1])

                if(actualId < array.length-1 ){
                    let newId = actualId+1;
                    // this.state.selected = [Object.keys(rowData[tabid])[newId]];

                    if(!selectRange) {
                        this.selectOneProduct(array[newId]);
                    } else {
                        this.selectProduct(array[newId]);
                    }
                }
                break;
            case "ArrowUp":
                e.preventDefault();

                const arrays = (rowData[tabid]).map((item, id) => {
                    return item.id
                });

                const actual = arrays.findIndex(x => x === selected[selected.length-1])


                if(actual > 0 ){
                    let newId = actual-1;

                    if(!selectRange) {
                        this.selectOneProduct(arrays[newId]);

                    } else {
                        this.selectProduct(arrays[newId]);
                    }
                }
                break;
            case "Enter":
                e.preventDefault();
                if(selected.length > 1) {

                } else {
                   onDoubleClick(selected[selected.length-1]);
                }

                break;
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

        if(e.button === 0){
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


    }
    handleRightClick = (e, id) => {
        const {selected} = this.state;
        const isAnySelected = selected.length > 0;

        if(!isAnySelected){
            this.selectProduct(id);
        } else if(selected.length === 1){
            this.deselectAllProducts();
            let t = this;
            setTimeout(function(){
                t.selectProduct(id);
            }, 1);

        }

        e.preventDefault();
        this.setState({
            contextMenu: {
                x: e.clientX,
                y: e.clientY,
                open: true
            }
        });
    }
    sumProperty = (items, prop) => {
        return items.reduce((a, b) => {
            return b[prop] == null ? a : a + b[prop];
        }, 0);
    }
    getProductRange = (id) => {
        const {rowData, tabid, keyProperty} = this.props;
        let arrayIndex;

        let selectIdA;
        let selectIdB;

        if(keyProperty === 'id'){
            arrayIndex = rowData[tabid].map(function(item){return item.id});
            selectIdA = arrayIndex.findIndex(x => x === id)
            selectIdB = arrayIndex.findIndex(x => x === this.state.selected[0])
        }else {
            selectIdA = Object.keys(rowData[tabid]).findIndex(x => x === id)
            selectIdB = Object.keys(rowData[tabid]).findIndex(x => x === this.state.selected[0])
        }

        let selected = [
            selectIdA,
            selectIdB
        ];
        selected.sort((a,b) => a - b);
            if(keyProperty === 'id'){
                return arrayIndex.slice(selected[0], selected[1]+1);
            }else {
                return Object.keys(rowData[tabid]).slice(selected[0], selected[1]+1);
            }
    }
    openModal = (windowType, tabId, rowId) => {
        const {dispatch} = this.props;
        dispatch(openModal("Add new", windowType, tabId, rowId));
    }

    renderTableBody = () => {
        const {rowData, tabid, cols, type, docId, readonly, keyProperty, onDoubleClick, mainTable, updatedRow} = this.props;
        const {selected} = this.state;
        if(!!rowData && rowData[tabid]){
            let keys = Object.keys(rowData[tabid]);
            const item = rowData[tabid];
            let ret = [];
            for(let i=0; i < keys.length; i++) {
                const key = keys[i];
                const index = keyProperty ? keyProperty : "rowId";
                ret.push(
                    <TableItem
                        fields={item[key].fields}
                        key={i}
                        rowId={item[key].rowId}
                        tabId={tabid}
                        cols={cols}
                        type={type}
                        docId={docId}
                        isSelected={selected.indexOf(item[key][index]) > -1}
                        onDoubleClick={() => onDoubleClick && onDoubleClick(item[key][index])}
                        onMouseDown={(e) => this.handleClick(e, item[key][index])}
                        onContextMenu={(e) => this.handleRightClick(e, item[key][index])}
                        changeListenOnTrue={() => this.changeListenOnTrue()}
                        changeListenOnFalse={() => this.changeListenOnFalse()}
                        readonly={readonly}
                        mainTable={mainTable}
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
        const {cols, type, docId, rowData, tabid, readonly, size, handleChangePage, pageLength, page, mainTable, updateDocList, sort, orderBy} = this.props;
        const {x, y, contextMenu, selected, listenOnKeys} = this.state;

        return (
            <div>
                <div className="row">
                    <div className="col-xs-12">
                        <TableContextMenu
                            x={contextMenu.x}
                            y={contextMenu.y}
                            isDisplayed={contextMenu.open}
                            blur={() => this.closeContextMenu()}
                            docId={docId}
                            type={type}
                            tabId={tabid}
                            selected={selected}
                            deselect={() => this.deselectAllProducts()}
                            mainTable={mainTable}
                            updateDocList={updateDocList}
                        />
                        {!readonly && <div className="row">
                            <div className="col-xs-12">
                                <button className="btn btn-meta-outline-secondary btn-distance btn-sm pull-xs-left" onClick={() => this.openModal(type, tabid, "NEW")}>Add new</button>
                                <div className="pull-xs-right">
                                    {/*<TableFilter />*/}
                                </div>
                            </div>
                        </div>}

                        <div className="panel panel-primary panel-bordered panel-bordered-force">
                            <table
                                className={
                                    "table table-bordered-vertically table-striped " +
                                    (readonly ? "table-read-only" : "")
                                }
                                onKeyDown = { listenOnKeys && !readonly ? (e) => this.handleKeyDown(e) : (listenOnKeys && mainTable) ? (e) => this.handleKeyDownDocList(e) : ''}
                            >
                                <thead>
                                    <TableHeader cols={cols} mainTable={mainTable} sort={sort} orderBy={orderBy} deselect={this.deselectAllProducts} />
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
                {page && pageLength && <div className="row">
                    <div className="col-xs-12">
                        <TablePagination
                            handleChangePage={handleChangePage}
                            handleSelectAll={this.selectAll}
                            pageLength={pageLength}
                            size={size}
                            selected={selected}
                            page={page}
                            orderBy={orderBy}
                            deselect={this.deselectAllProducts}
                        />
                    </div>
                </div>}
            </div>
        )
    }
}

Table.propTypes = {
    dispatch: PropTypes.func.isRequired
}

Table = connect(false, false, false, { withRef: true })(onClickOutside(Table))

export default Table
