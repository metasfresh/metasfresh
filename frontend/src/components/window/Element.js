import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import MasterWidget from '../widget/MasterWidget';

class Element extends PureComponent {
  render() {
    const { windowId, tabId, rowId, dataId } = this.props;
    const { elementLayout, elementIndex } = this.props;
    const { isFocused, tabIndex } = this.props;
    const { data } = this.props;
    const { isModal, isAdvanced, isFullScreen } = this.props;
    const { addRefToWidgets, onBlurWidget } = this.props;

    const autoFocus = isFocused && elementIndex === 0;
    const widgetData = elementLayout.fields.map(
      (item) => data[item.field] || -1
    );
    const fieldName = elementLayout.fields ? elementLayout.fields[0].field : '';
    const relativeDocId = data.ID && data.ID.value;

    return (
      <MasterWidget
        ref={addRefToWidgets}
        entity="window"
        key={'element' + elementIndex}
        windowType={windowId}
        dataId={dataId}
        widgetData={widgetData}
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
  //
  windowId: PropTypes.string.isRequired,
  tabId: PropTypes.string,
  rowId: PropTypes.string,
  dataId: PropTypes.string,
  //
  data: PropTypes.oneOfType([PropTypes.shape(), PropTypes.array]), // TODO: type here should point to a hidden issue?
  //
  isFocused: PropTypes.bool,
  isModal: PropTypes.bool,
  isAdvanced: PropTypes.bool,
  isFullScreen: PropTypes.bool,
  tabIndex: PropTypes.number,
  //
  onBlurWidget: PropTypes.func.isRequired,
  addRefToWidgets: PropTypes.func.isRequired,
};

export default Element;
