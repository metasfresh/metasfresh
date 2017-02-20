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
import TableItem from './TableItem';
import TablePagination from './TablePagination';
import TableHeader from './TableHeader';
import TableContextMenu from './TableContextMenu';

import keymap from '../../keymap.js';
import DocumentListContextShortcuts from '../shortcuts/DocumentListContextShortcuts';
import TableContextShortcuts from '../shortcuts/TableContextShortcuts';
import { ShortcutManager } from 'react-shortcuts';
const shortcutManager = new ShortcutManager(keymap);


class Table extends Component {
    constructor(props) {
        super(props);

        const {defaultSelected} = this.props;

        this.state = {
            selected: defaultSelected || [null],
            listenOnKeys: true,
            contextMenu: {
                open: false,
                x: 0,
                y: 0
            },
            promptOpen: false,
            isBatchEntry: false,
            rows: []
        }
    }

    componentDidMount(){
        this.getIndentData(true);
    }

    componentWillUpdate(nextProps, nextState) {
        const {dispatch} = this.props;

        if((JSON.stringify(nextState.selected) !== JSON.stringify(this.state.selected))
        ){
            dispatch(selectTableItems(nextState.selected));
        }
    }

    componentDidUpdate(prevProps) {
        const {mainTable, open, rowData} = this.props;
        if(mainTable && open){
            this.table.focus();
        }

        if(
            JSON.stringify(prevProps.rowData) !=
            JSON.stringify(rowData)
        ){
            this.getIndentData();
        }
    }

    getChildContext = () => {
        return { shortcuts: shortcutManager }
    }

    getIndentData = (selectFirst) => {
        const {rowData, tabid, indentSupported} = this.props;

        if(indentSupported){
            let rowsData = [];

            rowData[tabid].map(item => {
                rowsData = rowsData.concat(this.mapIncluded(item));
            })

            this.setState({
                rows: rowsData
            }, () => {
                if(selectFirst){
                    this.selectOneProduct(this.state.rows[0].id);
                    document.getElementsByClassName('js-table')[0].focus();
                }

            })
        } else {
            this.setState({
                rows: rowData[tabid]
            });
        }
    }

    getAllLeafs = () => {
        const {rows, selected} = this.state;
        let leafs = [];
        let leafsIds = [];

        rows.map( item => {
            if(item.id == selected[0]){
                leafs = this.mapIncluded(item);
            }
        });

        leafs.map(item => {
            leafsIds = leafsIds.concat(item.id);
        });

        this.selectRangeProduct(leafsIds);
    }

    changeListen = (listenOnKeys) => {
        this.setState({
            listenOnKeys: !!listenOnKeys
        })
    }

    selectProduct = (id, idFocused, idFocusedDown) => {
        const {dispatch} = this.props;

        this.setState(prevState => {
            selected: prevState.selected.concat([id])
        }, () => {
            dispatch(selectTableItems(this.state.selected))
            this.triggerFocus(idFocused, idFocusedDown);
        })
    }

    selectRangeProduct = (ids) => {
        this.setState({
            selected: ids
        });
    }

    selectAll = () => {
        const {keyProperty} = this.props;
        const {rows} = this.state;
        const property = keyProperty ? keyProperty : 'rowId';
        const toSelect = rows.map((item) => item[property]);

        this.selectRangeProduct(toSelect);
    }

    selectOneProduct = (id, idFocused, idFocusedDown, cb) => {
        this.setState({
            selected: [id]
        }, () => {
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
        this.setState({
            selected: []
        }, cb && cb());
    }

    triggerFocus = (idFocused, idFocusedDown) => {
        const rowSelected = document.getElementsByClassName('row-selected');
        if(rowSelected.length > 0){
            if(typeof idFocused == 'number'){
                rowSelected[0].children[idFocused].focus();
            }
            if(typeof idFocusedDown == 'number'){
                rowSelected[rowSelected.length-1].children[idFocusedDown].focus();
            }
        }
    }

    handleClickOutside = (event) => {
        if(event.target.parentNode !== document) {
            const item = event.path;
            for(let i = 0; i < item.length; i++){
                if(item[i].classList && item[i].classList.contains('js-not-unselect')){
                    return;
                }
            }

            this.deselectAllProducts();
        }   
    }

    handleKeyDown = (e) => {
        const {selected, rows} = this.state;
        const selectRange = e.shiftKey;

        const nodeList = Array.prototype.slice.call(document.activeElement.parentElement.children);
        const idActive = nodeList.indexOf(document.activeElement);

        let idFocused = null;
        if(idActive > -1) {
            idFocused = idActive;
        }

        switch(e.key) {
            case 'ArrowDown':
                e.preventDefault();

                const actualId = Object.keys(rows).findIndex(x => x === selected[selected.length-1])

                if(actualId < Object.keys(rows).length-1 ){
                    let newId = actualId+1;

                    if(!selectRange) {
                        this.selectOneProduct(Object.keys(rows)[newId], false, idFocused);
                    } else {
                        this.selectProduct(Object.keys(rows)[newId], false, idFocused);
                    }
                }
                break;
            case 'ArrowUp':
                e.preventDefault();

                const actual = Object.keys(rows).findIndex(x => x === selected[selected.length-1])

                if(actual > 0 ){
                    let newId = actual-1;

                    if(!selectRange) {
                        this.selectOneProduct(Object.keys(rows)[newId], idFocused, false);
                    } else {
                        this.selectProduct(Object.keys(rows)[newId], idFocused, false);
                    }
                }
                break;
            case 'ArrowLeft':
                e.preventDefault();
                if(document.activeElement.previousSibling){
                    document.activeElement.previousSibling.focus();
                }
                break;
            case 'ArrowRight':
                e.preventDefault();
                if(document.activeElement.nextSibling){
                    document.activeElement.nextSibling.focus();
                }
                break;
            case 'Tab':
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

    mapIncluded = (node, indent, isParentLastChild = false) => {
        let ind = indent ? indent : [];
        let result = [];

        const nodeCopy = Object.assign({}, node, {
            indent: ind
        });

        result = result.concat([nodeCopy]);

        if(isParentLastChild){
            ind[ind.length - 2] = false;
        }

        if(node.includedDocuments){
            for(let i = 0; i < node.includedDocuments.length; i++){
                let copy = node.includedDocuments[i];
                if(i === node.includedDocuments.length - 1){
                    copy = Object.assign({}, copy, {
                        lastChild: true
                    });
                }

                result = result.concat(
                    this.mapIncluded(copy, ind.concat([true]), node.lastChild)
                )
            }
        }

        return result;
    }

    handleKeyDownDocList = (e) => {
        const {selected, rows} = this.state;

        const {
            onDoubleClick, closeOverlays, open
        } = this.props;

        const selectRange = e.shiftKey;

        switch(e.key) {
            case 'ArrowDown':
                e.preventDefault();

                const array = rows.map((item) => {
                    return item.id
                });

                const actualId = array.findIndex(x => x === selected[selected.length-1]);

                if(actualId < array.length-1 ){
                    let newId = actualId+1;

                    if(!selectRange) {
                        this.selectOneProduct(array[newId]);
                    } else {
                        this.selectProduct(array[newId]);
                    }
                }
                break;
            case 'ArrowUp':
                e.preventDefault();

                const arrays = rows.map((item) => {
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
            case 'Enter':
                e.preventDefault();
                if(selected.length <= 1) {
                   onDoubleClick(selected[selected.length-1]);
                }

                if(open) {
                    closeOverlays();
                }
                break;
            case 'Escape':
                if(open){
                    closeOverlays();
                }
                break;
            case 'Tab':
                e.preventDefault();
                const focusedElem = document.getElementsByClassName('js-attributes')[0];
                if(focusedElem){
                    focusedElem.getElementsByTagName('input')[0].focus();
                }
                break;
        }
    }

    closeContextMenu = () => {
        this.setState({
            contextMenu: Object.assign({}, this.state.contextMenu, {
                open: false
            })
        })
    }

    handleClick = (e, id) => {
        if(e.button === 0){
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
        this.setState({
            contextMenu: Object.assign({}, this.state.contextMenu, {
                x: clientX,
                y: clientY,
                open: true
            })
        });
    }

    handleFocus = () => {
        const {rowData, tabid} = this.props;
        const {selected} = this.state;

         if(selected.length <= 0){
            const firstId = Object.keys(rowData[tabid])[0];
            this.selectOneProduct(firstId, 0);
        }
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

        this.setState({
            isBatchEntry: !isBatchEntry
        });
    }

    openModal = (windowType, tabId, rowId) => {
        const {dispatch} = this.props;
        dispatch(openModal('Add new', windowType, 'window', tabId, rowId));
    }

    handleAdvancedEdit = (type, tabId, selected) => {
        const {dispatch} = this.props;

        dispatch(openModal('Advanced edit', type, 'window', tabId, selected[0], true));
    }

    handleOpenNewTab = (selected) => {
        const {type} = this.props;
        for(let i = 0; i < selected.length; i++){
            window.open('/window/' + type + '/' + selected[i], '_blank');
        }
    }

    handleDelete = () => {
        this.setState({
            promptOpen: true
        })
    }

    handlePromptCancelClick = () => {
        this.setState({
            promptOpen: false
        })
    }

    handlePromptSubmitClick = (selected) => {
        const {
            dispatch, type, docId, updateDocList, tabid
        } = this.props;

        this.setState({
            promptOpen: false,
            selected: []
        }, () => {
            dispatch(deleteRequest('window', type, docId ? docId : null, docId ? tabid : null, selected))
            .then(() => {
                if(docId){
                    dispatch(deleteLocal(tabid, selected, 'master'))
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

    renderTableBody = () => {
        const {
            tabid, cols, type, docId, readonly, keyProperty, onDoubleClick, 
            mainTable, newRow, tabIndex, entity, indentSupported
        } = this.props;

        const {selected, rows} = this.state;
        const keyProp = keyProperty ? keyProperty : 'rowId';

        if(rows){
            let keys = Object.keys(rows);
            const item = rows;
            let ret = [];
            for(let i=0; i < keys.length; i++) {
                const key = keys[i];
                ret.push(
                    <tbody key={i}>
                        <TableItem
                            key={i}
                            odd={i & 1}
                            item={item[key]}
                            entity={entity}
                            fields={item[key].fields}
                            rowId={item[key][keyProp]}
                            tabId={tabid}
                            cols={cols}
                            type={type}
                            docId={docId}
                            tabIndex={tabIndex}
                            readonly={readonly}
                            mainTable={mainTable}
                            selected={selected}
                            keyProperty={keyProp}
                            onDoubleClick={() => onDoubleClick && onDoubleClick(item[key][keyProp])}
                            onMouseDown={(e) => this.handleClick(e, item[key][keyProp])}
                            handleRightClick={(e) => this.handleRightClick(e, item[key][keyProp])}
                            changeListenOnTrue={() => this.changeListen(true)}
                            changeListenOnFalse={() => this.changeListen(false)}
                            newRow={i === keys.length-1 ? newRow : false}
                            isSelected={selected.indexOf(item[key][keyProp]) > -1}
                            handleSelect={this.selectRangeProduct}
                            indentSupported={indentSupported}
                            indent={item[key].indent}
                            includedDocuments={item[key].includedDocuments}
                            lastSibling={item[key].lastChild}
                            contextType={item[key].type}
                        />
                    </tbody>
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

    render() {
        const {
            cols, type, docId, rowData, tabid, readonly, size, handleChangePage,
            pageLength, page, mainTable, updateDocList, sort, orderBy, toggleFullScreen,
            fullScreen, tabIndex, indentSupported
        } = this.props;

        const {
            contextMenu, selected, promptOpen, isBatchEntry
        } = this.state;

        return (
            <div className="table-flex-wrapper">
                <div className={'table-flex-wrapper ' +
                        (mainTable ? 'table-flex-wrapper-row ' : '')
                    }
                >
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
                                openModal={() => this.openModal(type, tabid, 'NEW')}
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
                        className={
                            'panel panel-primary panel-bordered panel-bordered-force table-flex-wrapper document-list-table ' +
                            ((
                                (rowData && rowData[tabid] &&
                                Object.keys(rowData[tabid]).length === 0) ||
                                (!rowData[tabid])
                            ) ? 'table-content-empty ' : '')
                        }
                    >
                        <table
                            className={
                                'table table-bordered-vertically table-striped js-table ' +
                                (readonly ? 'table-read-only' : '')
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
                                    indentSupported={indentSupported}
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

                    {
                        // Other 'table-flex-wrapped' components
                        // like selection attributes
                        this.props.children
                    }
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
                {promptOpen &&
                    <Prompt
                        title="Delete"
                        text="Are you sure?"
                        buttons={{submit: 'Delete', cancel: 'Cancel'}}
                        onCancelClick={this.handlePromptCancelClick}
                        onSubmitClick={() => this.handlePromptSubmitClick(selected)}
                    />
                }

                <DocumentListContextShortcuts
                    handleAdvancedEdit={selected.length > 0 ? () => this.handleAdvancedEdit(type, tabid, selected) : ''}
                    handleOpenNewTab={selected.length > 0 && mainTable ? () => this.handleOpenNewTab(selected) : ''}
                    handleDelete={selected.length > 0 ? () => this.handleDelete() : ''}
                    getAllLeafs={this.getAllLeafs}
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
