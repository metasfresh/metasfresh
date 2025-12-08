import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import ElementsLine from './ElementsLine';

const ElementGroup = ({
  elementGroupLayout,
  elementGroupIndex,
  columnIndex,
  sectionIndex,
  windowId,
  tabId,
  rowId,
  dataId,
  shouldBeFocused,
  tabIndex,
  isModal,
  isAdvanced,
  isFullScreen,
  onBlurWidget,
  addRefToWidgets,
  requestElementGroupFocus,
  disconnected,
}) => {
  if (
    elementGroupLayout.elementsLine === undefined ||
    elementGroupLayout.elementsLine.length === 0
  ) {
    return null;
  }

  const elementsLinesArray = elementGroupLayout.elementsLine;

  return (
    <div
      ref={(c) => {
        if (c && isModal && shouldBeFocused) {
          requestElementGroupFocus(c);
        }
      }}
      className={classnames('panel panel-spaced panel-distance', {
        'panel-bordered panel-primary': elementGroupLayout.type === 'primary',
        'panel-secondary': elementGroupLayout.type !== 'primary',
      })}
    >
      {elementsLinesArray.map((elementsLineLayout, elementsLineIndex) => (
        <ElementsLine
          key={'line' + elementsLineIndex}
          elementsLineLayout={elementsLineLayout}
          elementsLineIndex={elementsLineIndex}
          elementGroupIndex={elementGroupIndex}
          columnIndex={columnIndex}
          sectionIndex={sectionIndex}
          windowId={windowId}
          tabId={tabId}
          rowId={rowId}
          dataId={dataId}
          isFocused={shouldBeFocused && elementsLineIndex === 0}
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

ElementGroup.propTypes = {
  elementGroupLayout: PropTypes.object.isRequired,
  elementGroupIndex: PropTypes.number,
  columnIndex: PropTypes.number,
  sectionIndex: PropTypes.number,
  windowId: PropTypes.string.isRequired,
  tabId: PropTypes.string,
  rowId: PropTypes.string,
  dataId: PropTypes.string,
  shouldBeFocused: PropTypes.bool,
  tabIndex: PropTypes.number,
  isModal: PropTypes.bool,
  isAdvanced: PropTypes.bool,
  isFullScreen: PropTypes.bool,
  onBlurWidget: PropTypes.func.isRequired,
  addRefToWidgets: PropTypes.func.isRequired,
  requestElementGroupFocus: PropTypes.func.isRequired,
  disconnected: PropTypes.string,
};

export default ElementGroup;
