import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import ElementGroup from './ElementGroup';
import EntryTable from '../table/EntryTable';

class Column extends PureComponent {
  constructor(props) {
    super(props);

    if (props.isModal) {
      this.tabIndex = {
        firstColumn: 0,
        secondColumn: 0,
      };
    } else {
      this.tabIndex = {
        firstColumn: 1,
        secondColumn: 2,
      };
    }
  }

  render() {
    const { columnLayout, colWidth } = this.props;
    const { isDataEntry } = this.props;
    const elementGroups = columnLayout.elementGroups;

    if (isDataEntry) {
      return (
        <div className="col-sm-12">{this.renderEntryTable(elementGroups)}</div>
      );
    } else {
      if (elementGroups === undefined || elementGroups.length == 0) {
        return null;
      }

      return (
        <div className={`col-sm-${colWidth}`}>
          {elementGroups && this.renderElementGroups(elementGroups)}
        </div>
      );
    }
  }

  renderElementGroups = (groups) => {
    const { windowId, tabId, rowId, dataId } = this.props;
    const { data } = this.props;
    const { isFirst, isModal, isAdvanced, isFullScreen } = this.props;
    const {
      onBlurWidget,
      addRefToWidgets,
      requestElementGroupFocus,
    } = this.props;

    return groups.map((elementGroupLayout, elementGroupIndex) => {
      const shouldBeFocused = isFirst && elementGroupIndex === 0;
      const tabIndex =
        elementGroupLayout.type === 'primary'
          ? this.tabIndex.firstColumn
          : this.tabIndex.secondColumn;

      return (
        <ElementGroup
          key={'elemGroups' + elementGroupIndex}
          //
          elementGroupLayout={elementGroupLayout}
          //
          windowId={windowId}
          tabId={tabId}
          rowId={rowId}
          dataId={dataId}
          //
          data={data}
          //
          shouldBeFocused={shouldBeFocused}
          tabIndex={tabIndex}
          isModal={isModal}
          isAdvanced={isAdvanced}
          isFullScreen={isFullScreen}
          //
          onBlurWidget={onBlurWidget}
          addRefToWidgets={addRefToWidgets}
          requestElementGroupFocus={requestElementGroupFocus}
        />
      );
    });
  };

  renderEntryTable = (groups) => {
    const rows = groups.reduce((rowsArray, group) => {
      const cols = [];
      group.elementsLine.forEach((line) => {
        if (line && line.elements && line.elements.length) {
          cols.push(line.elements[0]);
        }
      });

      rowsArray.push({
        cols,
        colsCount: group.columnCount,
      });

      return rowsArray;
    }, []);

    const { windowId, dataId } = this.props;
    const { data, extendedData, rowData, isFullScreen } = this.props;
    const { addRefToWidgets, onBlurWidget } = this.props;

    return (
      <div
        className={classnames(
          'panel panel-primary panel-bordered',
          'panel-bordered-force table-flex-wrapper',
          'document-list-table js-not-unselect'
        )}
      >
        <EntryTable
          rows={rows}
          //
          windowId={windowId}
          dataId={dataId}
          //
          data={data}
          rowData={rowData}
          extendedData={extendedData}
          //
          isFullScreen={isFullScreen}
          //
          addRefToWidgets={addRefToWidgets}
          onBlurWidget={onBlurWidget}
        />
      </div>
    );
  };
}

Column.propTypes = {
  columnLayout: PropTypes.object.isRequired,
  colWidth: PropTypes.number,
  //
  windowId: PropTypes.string.isRequired,
  tabId: PropTypes.string,
  rowId: PropTypes.string,
  dataId: PropTypes.string,
  //
  data: PropTypes.oneOfType([PropTypes.shape(), PropTypes.array]), // TODO: type here should point to a hidden issue?
  isDataEntry: PropTypes.bool,
  extendedData: PropTypes.object,
  rowData: PropTypes.object,
  //
  isFirst: PropTypes.bool,
  isModal: PropTypes.bool,
  isAdvanced: PropTypes.bool,
  isFullScreen: PropTypes.bool,
  //
  onBlurWidget: PropTypes.func.isRequired,
  addRefToWidgets: PropTypes.func.isRequired,
  requestElementGroupFocus: PropTypes.func.isRequired,
};

export default Column;
