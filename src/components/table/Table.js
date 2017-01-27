import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import update from 'react-addons-update';

import {
    openModal,
    selectTableItems,
    deleteLocal
} from '../../actions/WindowActions';

import {
    deleteRequest
} from '../../actions/GenericActions';

import Prompt from '../app/Prompt';

import TableFilter from './TableFilter';
import TableItemWrapper from './TableItemWrapper';
import TablePagination from './TablePagination';
import TableHeader from './TableHeader';
import TableContextMenu from './TableContextMenu';
import MasterWidget from '../widget/MasterWidget';

import keymap from '../../keymap.js';
import DocumentListContextShortcuts from '../shortcuts/DocumentListContextShortcuts';
import TableContextShortcuts from '../shortcuts/TableContextShortcuts';
import { ShortcutManager } from 'react-shortcuts';
const shortcutManager = new ShortcutManager(keymap);


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
            },
            promptOpen: false,
            isBatchEntry: false
        }
    }

    getChildContext = () => {
        return { shortcuts: shortcutManager }
    }

    componentWillUpdate(nextProps, nextState) {
        const {dispatch} = this.props;

        if(JSON.stringify(nextState.selected) !== JSON.stringify(this.state.selected)){
            dispatch(selectTableItems(nextState.selected));
        }
    }

    componentDidUpdate() {
        const {mainTable, open} = this.props;
        if(mainTable && open){
            this.table.focus();
        }
    }

    changeListen = (listenOnKeys) => {
        this.setState(Object.assign({}, this.state, {
            listenOnKeys: !!listenOnKeys
        }))
    }

    getSelectedItems = () => {
        const {selected} = this.state;
        return selected;
    }

    selectProduct = (id, idFocused, idFocusedDown) => {
        const {dispatch} = this.props;

        this.setState(prevState => Object.assign({}, this.state, {
            selected: prevState.selected.concat([id])
        }), () => {
            dispatch(selectTableItems(this.state.selected))
            this.triggerFocus(idFocused, idFocusedDown);
        })
    }

    selectRangeProduct = (ids) => {
        this.setState(Object.assign({}, this.state, {
            selected: ids
        }))
    }

    selectAll = () => {
        const {rowData, tabid, keyProperty} = this.props;
        const property = keyProperty ? keyProperty : "rowId";
        const toSelect = rowData[tabid].map((item, index) => item[property]);

        this.selectRangeProduct(toSelect);
    }

    selectOneProduct = (id, idFocused, idFocusedDown, cb) => {
        this.setState(Object.assign({}, this.state, {
            selected: [id]
        }), () => {
            this.triggerFocus(idFocused, idFocusedDown);
            cb && cb();
        })
    }

    deselectProduct = (id) => {
        const index = this.state.selected.indexOf(id);
        this.setState(update(this.state, {
            selected: {$splice: [[index, 1]]}
        }))
    }

    deselectAllProducts = (cb) => {
        this.setState(Object.assign({}, this.state, {
            selected: []
        }), cb && cb());
    }

    triggerFocus = (idFocused, idFocusedDown) => {
        const rowSelected = document.getElementsByClassName('row-selected');
        if(rowSelected.length > 0){
            if(typeof idFocused == "number"){
                rowSelected[0].children[idFocused].focus();
            }
            if(typeof idFocusedDown == "number"){
                rowSelected[rowSelected.length-1].children[idFocusedDown].focus();
            }
        }
    }

    handleClickOutside = (event) => {
        const item = event.path;
        for(let i = 0; i < item.length; i++){
            if(item[i].classList && item[i].classList.contains('js-not-unselect')){
                return;
            }
        }

        this.deselectAllProducts();
    }

    handleKeyDown = (e) => {
        const {rowData, tabid, listenOnKeys} = this.props;
        const item = rowData[tabid];
        const {selected} = this.state;
        const selectRange = e.shiftKey;

        const nodeList = Array.prototype.slice.call(document.activeElement.parentElement.children);
        const idActive = nodeList.indexOf(document.activeElement);

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
                        this.selectOneProduct(Object.keys(rowData[tabid])[newId], idFocused, false);
                    } else {
                        this.selectProduct(Object.keys(rowData[tabid])[newId], idFocused, false);
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
            case "Tab":
                if(e.shiftKey){
                    //passing focus over table cells backwards
                    this.table.focus();
                }else{
                    //passing focus over table cells
                    this.tfoot.focus();
                }
                break;
        }
    }

    handleKeyDownDocList = (e) => {
        const {selected} = this.state;
        const {rowData, tabid, listenOnKeys, onDoubleClick, closeOverlays, open} = this.props;
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
                if(selected.length <= 1) {
                   onDoubleClick(selected[selected.length-1]);
                }

                if(open) {
                    closeOverlays();
                }
                break;
            case "Escape":
                if(open){
                    closeOverlays();
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
                this.selectOneProduct(id);
            }
        }
    }

    handleRightClick = (e, id) => {
        const {selected} = this.state;
        const {clientX, clientY} = e;
        e.preventDefault();

        if(selected.indexOf(id) > -1){
            this.setContextMenu(clientX, clientY);
        }else{
            this.selectOneProduct(id, null, null, () => {
                this.setContextMenu(clientX, clientY);
            });
        }
    }

    setContextMenu = (clientX, clientY) => {
        this.setState(Object.assign({}, this.state, {
            contextMenu: Object.assign({}, this.state.contextMenu, {
                x: clientX,
                y: clientY,
                open: true
            })
        }));
    }

    handleFocus = () => {
        const {rowData, tabid} = this.props;
        const {selected} = this.state;

        if(selected.length <= 0){
            const firstId = Object.keys(rowData[tabid])[0];
            this.selectOneProduct(firstId, 0);
        }
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

    handleBatchEntryToggle = () => {
        const {isBatchEntry} = this.state;

        this.setState(Object.assign({}, this.state, {
            isBatchEntry: !isBatchEntry
        }));
    }

    openModal = (windowType, tabId, rowId) => {
        const {dispatch} = this.props;
        dispatch(openModal("Add new", windowType, "window", tabId, rowId));
    }

    renderTableBody = () => {
        const {
            rowData, tabid, cols, type, docId, readonly, keyProperty,
            onDoubleClick, mainTable, newRow, tabIndex, entity, closeOverlays
        } = this.props;

        const {selected} = this.state;

        if(!!rowData && rowData[tabid]){
            let keys = Object.keys(rowData[tabid]);
            const item = rowData[tabid];
            let ret = [];
            for(let i=0; i < keys.length; i++) {
                const key = keys[i];
                const index = keyProperty ? keyProperty : "rowId";

                ret.push(
                    <TableItemWrapper
                        key={i}
                        odd={i & 1}
                        item={item[key]}
                        entity={entity}
                        tabId={tabid}
                        cols={cols}
                        type={type}
                        docId={docId}
                        tabIndex={tabIndex}
                        readonly={readonly}
                        mainTable={mainTable}
                        selected={selected}
                        onDoubleClick={() => onDoubleClick && onDoubleClick(item[key][index])}
                        handleClick={this.handleClick}
                        onContextMenu={(e) => this.handleRightClick(e, item[key][index])}
                        changeListenOnTrue={() => this.changeListen(true)}
                        changeListenOnFalse={() => this.changeListen(false)}
                        newRow={i === keys.length-1 ? newRow : false}
                        handleSelect={this.selectProduct}
                    />
                );
            }

            return ret;
        }
    }

    renderEmptyInfo = (data, tabId) => {
        const {emptyText, emptyHint} = this.props;

        if(
            (data && data[tabId] && Object.keys(data[tabId]).length === 0) ||
            (!data[tabId])
        ){
            return (
                <div className="empty-info-text">
                    <div>
                        <h5>{emptyText}</h5>
                        <p>{emptyHint}</p>
                    </div>
                </div>
            )
        }else{
            return false;
        }
    }

    handleAdvancedEdit = (type, tabId, selected) => {
        const {dispatch} = this.props;

        dispatch(openModal("Advanced edit", type, "window", tabId, selected[0], true));
    }

    handleOpenNewTab = (selected) => {
        const {type} = this.props;
        for(let i = 0; i < selected.length; i++){
            window.open("/window/" + type + "/" + selected[i], "_blank");
        }
    }

    handleDelete = () => {
        this.setState(Object.assign({}, this.state, {
            promptOpen: true
        }))
    }

    handlePromptCancelClick = () => {
        this.setState(
            Object.assign({}, this.state, {
                promptOpen: false
            })
        )
    }

    handlePromptSubmitClick = (selected) => {
        const {
            dispatch, type, docId, mainTable, updateDocList, entity, tabid
        } = this.props;

        this.setState(Object.assign({}, this.state, {
            promptOpen: false,
            selected: []
        }), () => {
            dispatch(deleteRequest("window", type, docId ? docId : null, docId ? tabid : null, selected))
            .then(() => {
                if(docId){
                    dispatch(deleteLocal(tabid, selected, "master"))
                } else {
                    updateDocList();
                }
            });
        });
    }

    handleKey = (e) => {
        const {readonly, mainTable} = this.props;
        const {listenOnKeys} = this.state;

        if(listenOnKeys){
            if(!readonly){
                this.handleKeyDown(e);
            }else if(mainTable){
                this.handleKeyDownDocList(e)
            }
        }
    }

    render() {
        const {
            cols, type, docId, rowData, tabid, readonly, size, handleChangePage,
            pageLength, page, mainTable, updateDocList, sort, orderBy, toggleFullScreen,
            fullScreen, tabIndex
        } = this.props;

        const {
            contextMenu, selected, listenOnKeys, promptOpen, isBatchEntry
        } = this.state;

        return (
            <div className="table-flex-wrapper">
                <div className="table-flex-wrapper">
                    {contextMenu.open && <TableContextMenu
                        x={contextMenu.x}
                        y={contextMenu.y}
                        blur={() => this.closeContextMenu()}
                        docId={docId}
                        type={type}
                        tabId={tabid}
                        selected={selected}
                        deselect={() => this.deselectAllProducts()}
                        mainTable={mainTable}
                        updateDocList={updateDocList}
                        handleAdvancedEdit={() => this.handleAdvancedEdit(type, tabid, selected)}
                        handleOpenNewTab={() => this.handleOpenNewTab(selected)}
                        handleDelete={() => this.handleDelete()}
                    />}
                    {!readonly && <div className="row">
                        <div className="col-xs-12">
                            <TableFilter
                                openModal={() => this.openModal(type, tabid, "NEW")}
                                toggleFullScreen={toggleFullScreen}
                                fullScreen={fullScreen}
                                docType={type}
                                docId={docId}
                                tabId={tabid}
                                tabIndex={tabIndex}
                                isBatchEntry={isBatchEntry}
                                handleBatchEntryToggle={this.handleBatchEntryToggle}
                            />
                        </div>
                    </div>}

                    <div
                        className="panel panel-primary panel-bordered panel-bordered-force table-flex-wrapper"
                    >
                        <table
                            className={
                                "table table-bordered-vertically table-striped " +
                                (readonly ? "table-read-only" : "")
                            }
                            onKeyDown={this.handleKey}
                            tabIndex={tabIndex}
                            onFocus={this.handleFocus}
                            ref={c => this.table = c}
                        >
                            <thead>
                                <TableHeader
                                    cols={cols}
                                    mainTable={mainTable}
                                    sort={sort}
                                    orderBy={orderBy}
                                    deselect={this.deselectAllProducts}
                                    page={page}
                                />
                            </thead>
                            {this.renderTableBody()}
                            <tfoot
                                ref={c => this.tfoot = c}
                                tabIndex={tabIndex}
                            />
                        </table>

                        {this.renderEmptyInfo(rowData, tabid)}
                    </div>
                </div>
                {page && pageLength &&
                    <div className="row">
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
                    </div>
                }
                {
                    promptOpen &&
                    <Prompt
                        title={"Delete"}
                        text={"Are you sure?"}
                        buttons={{submit: "Delete", cancel: "Cancel"}}
                        onCancelClick={this.handlePromptCancelClick}
                        onSubmitClick={() => this.handlePromptSubmitClick(selected)}
                    />
                }
                <DocumentListContextShortcuts
                    handleAdvancedEdit={selected.length > 0 ? () => this.handleAdvancedEdit(type, tabid, selected) : ''}
                    handleOpenNewTab={selected.length > 0 && mainTable ? () => this.handleOpenNewTab(selected) : ''}
                    handleDelete={selected.length > 0 ? () => this.handleDelete() : ''}
                />

                {!readonly &&
                    <TableContextShortcuts
                        handleToggleQuickInput={this.handleBatchEntryToggle}
                        handleToggleExpand={() => toggleFullScreen(!fullScreen)}
                    />
                }
            </div>
        )
    }
}

Table.childContextTypes = {
    shortcuts: PropTypes.object.isRequired
}

Table.propTypes = {
    dispatch: PropTypes.func.isRequired
}

Table = connect(false, false, false, { withRef: true })(onClickOutside(Table))

export default Table
