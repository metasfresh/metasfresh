import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { omit } from 'lodash';

import WidgetWrapper from '../../containers/WidgetWrapper';

class Element extends PureComponent {
  render() {
    const {
      windowId,
      tabId,
      rowId,
      dataId,
      elementLayout,
      isFocused,
      tabIndex,
      isModal,
      isAdvanced,
      isFullScreen,
      addRefToWidgets,
      onBlurWidget,
      elementIndex,
      elementsLineIndex,
      elementGroupIndex,
      sectionIndex,
      columnIndex,
    } = this.props;

    const autoFocus = isFocused && elementIndex === 0;
    const fieldName = elementLayout.fields ? elementLayout.fields[0].field : '';
    const layoutId = `${sectionIndex}_${columnIndex}_${elementGroupIndex}_${elementsLineIndex}_${elementIndex}`;
    const element = omit(elementLayout, ['fields']);
    const dataSource = isModal ? 'modal' : 'element';

    return (
      <WidgetWrapper
        renderMaster={true}
        dataSource={dataSource}
        layoutId={layoutId}
        ref={addRefToWidgets}
        entity="window"
        key={'element' + elementIndex}
        windowId={windowId}
        dataId={dataId}
        isModal={!!isModal}
        tabId={tabId}
        rowId={rowId}
        isAdvanced={isAdvanced}
        tabIndex={tabIndex}
        autoFocus={!isModal && autoFocus}
        fullScreen={isFullScreen}
        fieldName={fieldName}
        onBlurWidget={onBlurWidget}
        {...element}
      />
    );
  }
}

Element.propTypes = {
  elementLayout: PropTypes.object.isRequired,
  elementIndex: PropTypes.number.isRequired,
  elementsLineIndex: PropTypes.number,
  elementGroupIndex: PropTypes.number,
  columnIndex: PropTypes.number,
  sectionIndex: PropTypes.number,
  windowId: PropTypes.string.isRequired,
  tabId: PropTypes.string,
  rowId: PropTypes.string,
  dataId: PropTypes.string,
  data: PropTypes.oneOfType([PropTypes.shape(), PropTypes.array]), // TODO: type here should point to a hidden issue?
  isFocused: PropTypes.bool,
  isModal: PropTypes.bool,
  isAdvanced: PropTypes.bool,
  isFullScreen: PropTypes.bool,
  tabIndex: PropTypes.number,
  onBlurWidget: PropTypes.func.isRequired,
  addRefToWidgets: PropTypes.func.isRequired,
};

export default Element;
