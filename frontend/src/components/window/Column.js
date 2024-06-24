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
    const { columnLayout, colWidth, isDataEntry } = this.props;
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
    const {
      windowId,
      tabId,
      rowId,
      dataId,
      isFirst,
      isModal,
      isAdvanced,
      isFullScreen,
      onBlurWidget,
      addRefToWidgets,
      requestElementGroupFocus,
      columnIndex,
      sectionIndex,
      disconnected,
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
          elementGroupLayout={elementGroupLayout}
          elementGroupIndex={elementGroupIndex}
          columnIndex={columnIndex}
          sectionIndex={sectionIndex}
          windowId={windowId}
          tabId={tabId}
          rowId={rowId}
          dataId={dataId}
          shouldBeFocused={shouldBeFocused}
          tabIndex={tabIndex}
          isModal={isModal}
          isAdvanced={isAdvanced}
          isFullScreen={isFullScreen}
          onBlurWidget={onBlurWidget}
          addRefToWidgets={addRefToWidgets}
          requestElementGroupFocus={requestElementGroupFocus}
          disconnected={disconnected}
        />
      );
    });
  };

  renderEntryTable = (groups) => {
    const columns = groups.reduce((columnsArray, group) => {
      const cols = [];
      group.elementsLine.forEach((line) => {
        if (line && line.elements && line.elements.length) {
          cols.push(line.elements[0]);
        }
      });

      columnsArray.push({
        cols,
        colsCount: group.columnCount,
      });

      return columnsArray;
    }, []);

    const {
      windowId,
      dataId,
      tabId,
      data,
      extendedData,
      isFullScreen,
      addRefToWidgets,
      onBlurWidget,
    } = this.props;

    return (
      <div
        className={classnames(
          'panel panel-primary panel-bordered',
          'panel-bordered-force table-flex-wrapper',
          'document-list-table js-not-unselect'
        )}
      >
        <EntryTable
          columns={columns}
          windowId={windowId}
          documentId={dataId}
          tabId={tabId}
          data={data}
          extendedData={extendedData}
          isFullScreen={isFullScreen}
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
  columnIndex: PropTypes.number,
  sectionIndex: PropTypes.number,
  windowId: PropTypes.string.isRequired,
  tabId: PropTypes.string,
  rowId: PropTypes.string,
  dataId: PropTypes.string,
  data: PropTypes.oneOfType([PropTypes.shape(), PropTypes.array]), // TODO: type here should point to a hidden issue?
  isDataEntry: PropTypes.bool,
  extendedData: PropTypes.object,
  isFirst: PropTypes.bool,
  isModal: PropTypes.bool,
  isAdvanced: PropTypes.bool,
  isFullScreen: PropTypes.bool,
  onBlurWidget: PropTypes.func.isRequired,
  addRefToWidgets: PropTypes.func.isRequired,
  requestElementGroupFocus: PropTypes.func.isRequired,
  disconnected: PropTypes.string,
};

export default Column;
