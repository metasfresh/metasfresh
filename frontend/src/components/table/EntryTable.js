import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { connect } from 'react-redux';

import { getTableId, getTable } from '../../reducers/tables';

import WidgetTooltip from '../widget/WidgetTooltip';
import MasterWidget from '../widget/MasterWidget';

/**
 * @file Class based component.
 * @module EntryTable
 * @extends Component
 */
class EntryTable extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      tooltipToggled: null,
    };
  }

  /**
   * @method widgetTooltipToggle
   * @summary ToDo: Describe the method
   * @param {*} field
   * @param {*} value
   * @todo Write the documentation
   */
  widgetTooltipToggle = (field, value) => {
    const curVal = this.state.tooltipToggled;
    let newVal = field;

    if (value === false || field === curVal) {
      newVal = null;
    }

    this.setState({
      tooltipToggled: newVal,
    });
  };

  /**
   * @method renderElements
   * @summary ToDo: Describe the method
   * @param {*} elements
   * @param {*} columnsCount
   * @todo Write the documentation
   */
  renderElements = (elements, columnsCount) => {
    const {
      data,
      rows,
      extendedData,
      addRefToWidgets,
      onBlurWidget,
      windowId,
      documentId,
      tabIndex,
      isFullScreen,
    } = this.props;
    const { tooltipToggled } = this.state;
    const renderedArray = [];
    const colWidth = Math.floor(12 / columnsCount);

    if (rows.length) {
      for (let i = 0; i < columnsCount; i += 1) {
        const elem = elements.cols[i];

        if (elem && elem.fields && elem.fields.length) {
          const fieldName = elem.fields ? elem.fields[0].field : '';
          const widgetData = [rows[0].fieldsByName[fieldName]];
          const relativeDocId = data.ID && data.ID.value;
          let tooltipData = null;
          let tooltipWidget = elem.fields
            ? elem.fields.find((field) => {
                if (field.type === 'Tooltip') {
                  tooltipData = rows[0].fieldsByName[field.field];

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
                `field-${widgetData[0].widgetType}`
              )}
            >
              <MasterWidget
                ref={addRefToWidgets}
                entity="window"
                windowType={windowId}
                dataId={documentId}
                dataEntry={true}
                fieldName={fieldName}
                widgetData={widgetData}
                isModal={false}
                tabId={extendedData.tabId}
                rowId={documentId}
                relativeDocId={relativeDocId}
                isAdvanced={false}
                tabIndex={tabIndex}
                fullScreen={isFullScreen}
                onBlurWidget={onBlurWidget}
                {...elem}
              />
              {tooltipWidget && (
                <WidgetTooltip
                  widget={tooltipWidget}
                  data={tooltipData}
                  fieldName={fieldName}
                  isToggled={tooltipToggled === fieldName}
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
    const { columns } = this.props;

    return (
      <table className="table js-table layout-fix">
        <tbody>
          {columns.map((cols, idx) => {
            return (
              <tr className="table-row" key={`entry-row-${idx}`}>
                {this.renderElements(cols, cols.colsCount)}
              </tr>
            );
          })}
        </tbody>
      </table>
    );
  }
}

EntryTable.propTypes = {
  columns: PropTypes.array.isRequired,
  rows: PropTypes.array.isRequired,
  //
  windowId: PropTypes.string.isRequired,
  documentId: PropTypes.string,
  tabId: PropTypes.string,
  //
  data: PropTypes.oneOfType([PropTypes.shape(), PropTypes.array]), // TODO: type here should point to a hidden issue?
  extendedData: PropTypes.any,
  //
  tabIndex: PropTypes.any,
  isFullScreen: PropTypes.bool,
  //
  addRefToWidgets: PropTypes.func.isRequired,
  onBlurWidget: PropTypes.func.isRequired,
};

const mapStateToProps = (state, props) => {
  const { windowId, documentId, tabId } = props;
  const tableId = getTableId({ windowId, tabId, docId: documentId });
  const table = getTable(state, tableId);

  return { rows: table.rows };
};

export default connect(mapStateToProps)(EntryTable);
