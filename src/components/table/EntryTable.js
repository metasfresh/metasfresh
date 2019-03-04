import React, { Component } from 'react';
import classnames from 'classnames';

import WidgetTooltip from '../widget/WidgetTooltip';
import MasterWidget from '../widget/MasterWidget';

export default class EntryTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      tooltipToggled: false,
    };
  }

  widgetTooltipToggle = (field, value) => {
    const curVal = this.state.tooltipToggled;
    const newVal = value != null ? value : !curVal;

    this.setState({
      tooltipToggled: newVal,
    });
  };

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
    const { tooltipToggled } = this.state;
    const renderedArray = [];
    const colWidth = Math.floor(12 / columnsCount);

    if (rowData && rowData.size) {
      for (let i = 0; i < columnsCount; i += 1) {
        const elem = elements.cols[i];

        if (elem) {
          const fieldName = elem.fields ? elem.fields[0].field : '';
          const widgetData = [rowData.get(0).fieldsByName[fieldName]];
          const relativeDocId = data.ID && data.ID.value;
          let tooltipData = null;
          let tooltipWidget = elem.fields
            ? elem.fields.find((field, idx) => {
                if (field.type === 'Tooltip') {
                  tooltipData = rowData.get(0).fieldsByName[field.field];

                  if (tooltipData && tooltipData.value) {
                    return field;
                  }
                }
                return false;
              })
            : null;

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
                fieldName={fieldName}
                widgetData={widgetData}
                isModal={false}
                tabId={extendedData.tabId}
                rowId={dataId}
                relativeDocId={relativeDocId}
                isAdvanced={false}
                tabIndex={tabIndex}
                fullScreen={fullScreen}
                onBlurWidget={handleBlurWidget}
                {...elem}
              />
              {tooltipWidget && (
                <WidgetTooltip
                  widget={tooltipWidget}
                  data={tooltipData}
                  fieldName={fieldName}
                  isToggled={tooltipToggled}
                  onToggle={this.widgetTooltipToggle}
                />
              )}
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
