// import update from 'immutability-helper';
// import { is } from 'immutable';
// import * as _ from 'lodash';
import React, { Component } from 'react';
// import onClickOutside from 'react-onclickoutside';
// import { connect } from 'react-redux';
// import classnames from 'classnames';
// import currentDevice from 'current-device';
import MasterWidget from '../widget/MasterWidget';
// import { deleteRequest } from '../../actions/GenericActions';
// import {
//   deleteLocal,
//   getZoomIntoWindow,
//   openModal,
//   selectTableItems,
//   deselectTableItems,
// } from '../../actions/WindowActions';
// import Prompt from '../app/Prompt';
// import DocumentListContextShortcuts from '../keyshortcuts/DocumentListContextShortcuts';
// import TableContextShortcuts from '../keyshortcuts/TableContextShortcuts';
// import TableContextMenu from './TableContextMenu';
// import TableFilter from './TableFilter';
// import TableHeader from './TableHeader';
// import TableItem from './TableItem';
// import TablePagination from './TablePagination';
// import {
//   getSizeClass,
//   handleCopy,
//   handleOpenNewTab,
//   propTypes,
//   constructorFn,
// } from '../../utils/tableHelpers';
// import {
//   getRowsData,
//   mapIncluded,
//   collapsedMap,
// } from '../../utils/documentListHelper';


    // return elements.map((elem, id) => {
    //   const autoFocus = isFocused && id === 0;
    //   const widgetData = elem.fields.map(item => data[item.field] || -1);
    //   const fieldName = elem.fields ? elem.fields[0].field : '';
    //   const relativeDocId = data.ID && data.ID.value;
      // return (
      //   <MasterWidget
      //     ref={c => {
      //       if (c) {
      //         this.widgets.push(c);
      //       }
      //     }}
      //     entity="window"
      //     key={'element' + id}
      //     windowType={windowId}
      //     dataId={dataId}
      //     widgetData={widgetData}
      //     isModal={!!modal}
      //     tabId={tabId || extendedData.tabId}
      //     rowId={extendedData.tabId ? dataId : rowId}
      //     relativeDocId={relativeDocId}
      //     isAdvanced={isAdvanced}
      //     tabIndex={tabIndex}
      //     autoFocus={!modal && autoFocus}
      //     fullScreen={fullScreen}
      //     onBlurWidget={this.handleBlurWidget.bind(this, fieldName)}
      //     {...elem}
      //   />
      // );

export default class EntryTable extends Component {
  renderElements = elements => {
    const { data, rowData } = this.props;

    return elements.map((elem, id) => {
      // const autoFocus = isFocused && id === 0;
      // const widgetData = elem.fields.map(item => data[item.field] || -1);
      const fieldName = elem.fields ? elem.fields[0].field : '';
      // const relativeDocId = data.ID && data.ID.value;

      return (
        <td>
{/*          <MasterWidget
            entity="window"
            key={'element' + id}
            windowType={windowId}
            dataId={dataId}
            widgetData={widgetData}
            isModal={!!modal}
            tabId={tabId || extendedData.tabId}
            rowId={extendedData.tabId ? dataId : rowId}
            relativeDocId={relativeDocId}
            isAdvanced={isAdvanced}
            tabIndex={tabIndex}
            autoFocus={!modal && autoFocus}
            fullScreen={fullScreen}
            onBlurWidget={this.handleBlurWidget.bind(this, fieldName)}
            {...elem}
          />*/}
          {fieldName}
        </td>
     );
    });
  }

  render() {
    const { rows } = this.props;

    console.log('this: ', this.renderElements)

    return (
      <table>
        <tbody>
          {rows.map(row => (
            <tr>
              {this.renderElements(row.elements)}
            </tr>
          ))}
        </tbody>
      </table>
    );
  }
}
