import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import ElementGroup from './ElementGroup';
import EntryTable from '../table/EntryTable';

const extractEntryTableColumnsArray = (elementGroupsArray) => {
  return elementGroupsArray.reduce((columnsArray, elementGroup) => {
    const cols = [];
    elementGroup.elementsLine.forEach((line) => {
      if (line && line.elements && line.elements.length) {
        cols.push(line.elements[0]);
      }
    });

    columnsArray.push({
      cols,
      colsCount: elementGroup.columnCount,
    });

    return columnsArray;
  }, []);
};

const Column = ({
  columnLayout,
  colWidth,
  columnIndex,
  sectionIndex,
  windowId,
  tabId,
  rowId,
  dataId,
  data,
  isDataEntry,
  extendedData,
  isFirst,
  isModal,
  isAdvanced,
  isFullScreen,
  onBlurWidget,
  addRefToWidgets,
  requestElementGroupFocus,
  disconnected,
}) => {
  const tabIndexSpec = isModal
    ? { firstColumn: 0, secondColumn: 0 }
    : { firstColumn: 1, secondColumn: 2 };

  const elementGroups = columnLayout.elementGroups;
  if (isDataEntry) {
    return (
      <div className="col-sm-12">
        {
          <div
            className={classnames(
              'panel panel-primary panel-bordered',
              'panel-bordered-force table-flex-wrapper',
              'document-list-table js-not-unselect'
            )}
          >
            <EntryTable
              columns={extractEntryTableColumnsArray(elementGroups)}
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
        }
      </div>
    );
  } else {
    if (elementGroups === undefined || elementGroups.length === 0) {
      return null;
    }

    return (
      <div className={`col-sm-${colWidth}`}>
        {elementGroups &&
          elementGroups.map((elementGroupLayout, elementGroupIndex) => {
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
                shouldBeFocused={isFirst && elementGroupIndex === 0}
                tabIndex={
                  elementGroupLayout.type === 'primary'
                    ? tabIndexSpec.firstColumn
                    : tabIndexSpec.secondColumn
                }
                isModal={isModal}
                isAdvanced={isAdvanced}
                isFullScreen={isFullScreen}
                onBlurWidget={onBlurWidget}
                addRefToWidgets={addRefToWidgets}
                requestElementGroupFocus={requestElementGroupFocus}
                disconnected={disconnected}
              />
            );
          })}
      </div>
    );
  }
};

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
