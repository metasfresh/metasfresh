// import update from 'immutability-helper';
// import { is } from 'immutable';
// import * as _ from 'lodash';
import React, { Component } from 'react';
// import onClickOutside from 'react-onclickoutside';
// import classnames from 'classnames';
import MasterWidget from '../widget/MasterWidget';

export default class EntryTable extends Component {
  renderElements = elements => {
    const {
      data,
      rowData,
      extendedData,
      isFirst,
      addRefToWidgets,
      handleBlurWidget,
      windowId,
      dataId,
      tabIndex,
      fullScreen,
    } = this.props;

    console.log('ROWDATA222: ', rowData );

    if (rowData && rowData.size) {
      return elements.map((elem, idx) => {
        const autoFocus = isFirst && idx === 0;
        const widgetData = rowData.fieldsByName.get(extendedData.tabId);
        const fieldName = elem.fields ? elem.fields[0].field : '';
        const relativeDocId = data.ID && data.ID.value;
        // tabIndex

        console.log(' renderElements: ', autoFocus, idx, ', fieldName: ', fieldName, ', rowData: ', rowData);

        return (
          <td key={`${fieldName}-cell-${idx}`}>
            <MasterWidget
              ref={addRefToWidgets}
              entity="window"
              windowType={windowId}
              dataId={dataId}
              widgetData={widgetData}
              isModal={false}
              tabId={extendedData.tabId}
              rowId={dataId}
              relativeDocId={relativeDocId}
              isAdvanced={false}
              tabIndex={tabIndex}
              autoFocus={autoFocus}
              fullScreen={fullScreen}
              onBlurWidget={() => handleBlurWidget(fieldName)}
              {...elem}
            />
            {fieldName}
          </td>
        );
      });
    }

    return null;
  };

  render() {
    const { rows } = this.props;

    return (
      <table>
        <tbody>
          {rows.map(cols => (
            <tr>{this.renderElements(cols)}</tr>
          ))}
        </tbody>
      </table>
    );
  }
}
