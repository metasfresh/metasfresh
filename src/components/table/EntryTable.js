import React, { Component } from 'react';
import classnames from 'classnames';

import MasterWidget from '../widget/MasterWidget';

export default class EntryTable extends Component {
  renderElements = (elements, columnsCount) => {
    const {
      data,
      rowData,
      extendedData,
      addRefToWidgets,
      handleBlurWidget,
      layout,
      dataId,
      tabIndex,
      fullScreen,
    } = this.props;
    const renderedArray = [];
    const colWidth = Math.floor(12 / columnsCount);

    if (rowData && rowData.size) {
      for (let i = 0; i < columnsCount; i += 1) {
        const elem = elements.cols[i];

        if (elem) {
          const fieldName = elem.fields ? elem.fields[0].field : '';
          const widgetData = [rowData.get(0).fieldsByName[fieldName]];
          const relativeDocId = data.ID && data.ID.value;

          renderedArray.push(
            <td
              key={`${fieldName}-cell-${i}`}
              className={classnames(
                `col-sm-${colWidth}`,
                {
                  [`text-${widgetData.gridAlign}`]: widgetData.gridAlign,
                  'cell-disabled': widgetData[0].readonly,
                  'cell-mandatory': widgetData[0].mandatory,
                },
                widgetData.widgetType
              )}
            >
              <MasterWidget
                ref={addRefToWidgets}
                entity="window"
                windowType={layout.windowId}
                dataId={dataId}
                widgetData={widgetData}
                isModal={false}
                tabId={extendedData.tabId}
                rowId={dataId}
                relativeDocId={relativeDocId}
                isAdvanced={false}
                tabIndex={tabIndex}
                fullScreen={fullScreen}
                onBlurWidget={() => handleBlurWidget(fieldName)}
                {...elem}
              />
            </td>
          );
        } else {
          renderedArray.push(<td key={`__-cell-${i}`} />);
        }
      }
      return renderedArray;
    }

    return null;
  };

  render() {
    const { rows } = this.props;

    return (
      <table className="table js-table layout-fix">
        <tbody>
          {rows.map((cols, idx) => {
            return (
              <tr key={`entry-row-${idx}`}>
                {this.renderElements(cols, cols.colsCount)}
              </tr>
            );
          })}
        </tbody>
      </table>
    );
  }
}
