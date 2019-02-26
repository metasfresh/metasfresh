import React, { Component } from 'react';
import classnames from 'classnames';

import MasterWidget from '../widget/MasterWidget';
import { getSizeClass } from '../../utils/tableHelpers';

export default class EntryTable extends Component {
  renderElements = (elements, autoFocus) => {
    const {
      data,
      rowData,
      extendedData,
      addRefToWidgets,
      handleBlurWidget,
      windowId,
      dataId,
      tabIndex,
      fullScreen,
    } = this.props;

    if (rowData && rowData.size) {
      return elements.map((elem, idx) => {
        const fieldName = elem.fields ? elem.fields[0].field : '';
        const widgetData = [rowData.get(0).fieldsByName[fieldName]];
        const relativeDocId = data.ID && data.ID.value;

        return (
          <td
            key={`${fieldName}-cell-${idx}`}
            className={classnames(
              {
                [`text-${widgetData.gridAlign}`]: widgetData.gridAlign,
                'cell-disabled': widgetData[0].readonly,
                'cell-mandatory': widgetData[0].mandatory,
              },
              getSizeClass(widgetData),
              widgetData.widgetType
            )}
          >
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
          </td>
        );
      });
    }

    return null;
  };

  render() {
    const { rows, isFirst } = this.props;

    return (
      <table className="table table-bordered-vertically table-striped js-table layout-fix">
        <tbody>
          {rows.map((cols, idx) => {
            const selected = isFirst && idx === 0;

            return (
              <tr
                key={`entry-row-${idx}`}
                className={classnames({
                  'row-selected': selected,
                  'tr-odd': idx % 2,
                  'tr-even': !(idx % 2),
                })}
              >
                {this.renderElements(cols, selected)}
              </tr>
            );
          })}
        </tbody>
      </table>
    );
  }
}
