import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

import Element from './Element';

class ElementsLine extends PureComponent {
  render() {
    const { elementsLineLayout } = this.props;

    if (
      elementsLineLayout.elements === undefined ||
      elementsLineLayout.elements.length == 0
    ) {
      return null;
    }

    return (
      <div className="elements-line">
        {this.renderElements(elementsLineLayout.elements)}
      </div>
    );
  }

  renderElements = (elements) => {
    const { windowId, tabId, rowId, dataId } = this.props;
    const { data } = this.props;
    const { tabIndex, isFocused } = this.props;
    const { isModal, isAdvanced, isFullScreen } = this.props;
    const { addRefToWidgets, onBlurWidget } = this.props;

    return elements.map((elementLayout, elementIndex) => (
      <Element
        key={'element' + elementIndex}
        //
        elementLayout={elementLayout}
        elementIndex={elementIndex}
        //
        windowId={windowId}
        tabId={tabId}
        rowId={rowId}
        dataId={dataId}
        //
        data={data}
        //
        isFocused={isFocused}
        tabIndex={tabIndex}
        isModal={isModal}
        isAdvanced={isAdvanced}
        isFullScreen={isFullScreen}
        //
        onBlurWidget={onBlurWidget}
        addRefToWidgets={addRefToWidgets}
      />
    ));
  };
}

ElementsLine.propTypes = {
  elementsLineLayout: PropTypes.object.isRequired,
  //
  windowId: PropTypes.string.isRequired,
  tabId: PropTypes.string,
  rowId: PropTypes.string,
  dataId: PropTypes.string,
  //
  data: PropTypes.oneOfType([PropTypes.shape(), PropTypes.array]), // TODO: type here should point to a hidden issue?
  //
  isFocused: PropTypes.bool,
  tabIndex: PropTypes.number,
  isModal: PropTypes.bool,
  isAdvanced: PropTypes.bool,
  isFullScreen: PropTypes.bool,
  //
  onBlurWidget: PropTypes.func.isRequired,
  addRefToWidgets: PropTypes.func.isRequired,
};

export default ElementsLine;
