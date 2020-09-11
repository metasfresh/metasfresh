import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
// import MasterWidget from '../widget/MasterWidget';
import Widget from '../../containers/WidgetWrapper';

class Element extends PureComponent {
  render() {
    const {
      windowId,
      tabId,
      rowId,
      dataId,
      elementLayout,
      elementIndex,
      isFocused,
      tabIndex,
      data,
      isModal,
      isAdvanced,
      isFullScreen,
      addRefToWidgets,
      onBlurWidget,
    } = this.props;

    const autoFocus = isFocused && elementIndex === 0;
    const fieldName = elementLayout.fields ? elementLayout.fields[0].field : '';
    const relativeDocId = data.ID && data.ID.value;

    return (
      <Widget
        renderMaster={true}
        dataSource="element"

        ref={addRefToWidgets}
        entity="window"
        key={'element' + elementIndex}
        windowType={windowId}
        dataId={dataId}
        isModal={!!isModal}
        tabId={tabId}
        rowId={rowId}
        relativeDocId={relativeDocId}
        isAdvanced={isAdvanced}
        tabIndex={tabIndex}
        autoFocus={!isModal && autoFocus}
        fullScreen={isFullScreen}
        fieldName={fieldName}
        onBlurWidget={onBlurWidget}
        {...elementLayout}
      />
    );
  }
}

Element.propTypes = {
  elementLayout: PropTypes.object.isRequired,
  elementIndex: PropTypes.number.isRequired,
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
