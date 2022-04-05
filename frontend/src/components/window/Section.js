import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import Separator from '../Separator';
import Column from './Column';
import { INITIALLY_CLOSED, INITIALLY_OPEN } from '../../constants/Constants';

const MAX_ROWS = 12;

const Section = ({
  sectionLayout: { title, columns, closableMode },
  sectionIndex,
  windowId,
  tabId,
  rowId,
  dataId,
  data,
  isDataEntry,
  extendedData,
  isModal,
  isAdvanced,
  isFullScreen,
  onBlurWidget,
  addRefToWidgets,
  requestElementGroupFocus,
  isSectionCollapsed,
  toggleSectionCollapsed,
  disconnected,
}) => {
  const collapsible =
    closableMode === INITIALLY_OPEN || closableMode === INITIALLY_CLOSED;

  const colWidth = Math.floor(MAX_ROWS / columns.length);
  const isFirstSection = sectionIndex === 0;

  return (
    <div className={classnames('section', { collapsed: isSectionCollapsed })}>
      {title && (
        <Separator
          title={title}
          idx={sectionIndex}
          sectionCollapsed={isSectionCollapsed}
          collapsible={collapsible}
          tabId={extendedData.tabId}
          onClick={toggleSectionCollapsed}
        />
      )}
      <div
        className={classnames('row', {
          'collapsible-section': collapsible,
          collapsed: isSectionCollapsed,
        })}
      >
        {columns &&
          columns.map((columnLayout, columnIndex) => {
            const isFirstColumn = isFirstSection && columnIndex === 0;
            return (
              <Column
                key={`col-${columnIndex}`}
                columnLayout={columnLayout}
                columnIndex={columnIndex}
                sectionIndex={sectionIndex}
                colWidth={colWidth}
                windowId={windowId}
                tabId={tabId}
                rowId={rowId}
                dataId={dataId}
                data={data}
                isDataEntry={isDataEntry}
                extendedData={extendedData}
                isFirst={isFirstColumn}
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
    </div>
  );
};

Section.propTypes = {
  sectionLayout: PropTypes.object.isRequired,
  sectionIndex: PropTypes.number.isRequired,
  windowId: PropTypes.string.isRequired,
  tabId: PropTypes.string,
  rowId: PropTypes.string,
  dataId: PropTypes.string,
  data: PropTypes.oneOfType([PropTypes.shape(), PropTypes.array]), // TODO: type here should point to a hidden issue?
  isDataEntry: PropTypes.bool,
  extendedData: PropTypes.object,
  isModal: PropTypes.bool,
  isAdvanced: PropTypes.bool,
  isFullScreen: PropTypes.bool,
  onBlurWidget: PropTypes.func.isRequired,
  addRefToWidgets: PropTypes.func.isRequired,
  requestElementGroupFocus: PropTypes.func.isRequired,
  isSectionCollapsed: PropTypes.bool,
  toggleSectionCollapsed: PropTypes.func.isRequired,
  disconnected: PropTypes.string,
};

export default Section;
