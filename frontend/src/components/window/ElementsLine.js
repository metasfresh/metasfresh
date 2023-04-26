import React from 'react';
import PropTypes from 'prop-types';

import Element from './Element';

const ElementsLine = ({
  elementsLineLayout,
  elementsLineIndex,
  elementGroupIndex,
  columnIndex,
  sectionIndex,
  windowId,
  tabId,
  rowId,
  dataId,
  isFocused,
  tabIndex,
  isModal,
  isAdvanced,
  isFullScreen,
  onBlurWidget,
  addRefToWidgets,
  disconnected,
}) => {
  if (
    elementsLineLayout.elements === undefined ||
    elementsLineLayout.elements.length === 0
  ) {
    return null;
  }

  return (
    <div className="elements-line">
      {elementsLineLayout.elements.map((elementLayout, elementIndex) => (
        <Element
          key={'element' + elementIndex}
          elementLayout={elementLayout}
          elementIndex={elementIndex}
          elementsLineIndex={elementsLineIndex}
          elementGroupIndex={elementGroupIndex}
          columnIndex={columnIndex}
          sectionIndex={sectionIndex}
          windowId={windowId}
          tabId={tabId}
          rowId={rowId}
          dataId={dataId}
          isFocused={isFocused}
          tabIndex={tabIndex}
          isModal={isModal}
          isAdvanced={isAdvanced}
          isFullScreen={isFullScreen}
          onBlurWidget={onBlurWidget}
          addRefToWidgets={addRefToWidgets}
          disconnected={disconnected}
        />
      ))}
    </div>
  );
};

ElementsLine.propTypes = {
  elementsLineLayout: PropTypes.object.isRequired,
  elementsLineIndex: PropTypes.number,
  elementGroupIndex: PropTypes.number,
  columnIndex: PropTypes.number,
  sectionIndex: PropTypes.number,
  windowId: PropTypes.string.isRequired,
  tabId: PropTypes.string,
  rowId: PropTypes.string,
  dataId: PropTypes.string,
  isFocused: PropTypes.bool,
  tabIndex: PropTypes.number,
  isModal: PropTypes.bool,
  isAdvanced: PropTypes.bool,
  isFullScreen: PropTypes.bool,
  onBlurWidget: PropTypes.func.isRequired,
  addRefToWidgets: PropTypes.func.isRequired,
  disconnected: PropTypes.string,
};

export default ElementsLine;
