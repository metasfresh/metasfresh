import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import ElementsLine from './ElementsLine';

class ElementGroup extends PureComponent {
  render() {
    const {
      elementGroupLayout,
      shouldBeFocused,
      isModal,
      requestElementGroupFocus,
    } = this.props;

    if (
      elementGroupLayout.elementsLine === undefined ||
      elementGroupLayout.elementsLine.length == 0
    ) {
      return null;
    }

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
        {this.renderElementsLinesArray(elementGroupLayout.elementsLine)}
      </div>
    );
  }

  renderElementsLinesArray = (elementsLinesLayoutArray) => {
    const { windowId, tabId, rowId, dataId } = this.props;
    const { data } = this.props;
    const { shouldBeFocused, tabIndex } = this.props;
    const { isModal, isAdvanced, isFullScreen } = this.props;
    const { addRefToWidgets, onBlurWidget } = this.props;

    return elementsLinesLayoutArray.map(
      (elementsLineLayout, elementsLineIndex) => {
        const isFocused = shouldBeFocused && elementsLineIndex === 0;

        return (
          <ElementsLine
            key={'line' + elementsLineIndex}
            elementsLineLayout={elementsLineLayout}
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
        );
      }
    );
  };
}

ElementGroup.propTypes = {
  elementGroupLayout: PropTypes.object.isRequired,
  //
  windowId: PropTypes.string.isRequired,
  tabId: PropTypes.string,
  rowId: PropTypes.string,
  dataId: PropTypes.string,
  //
  data: PropTypes.oneOfType([PropTypes.shape(), PropTypes.array]), // TODO: type here should point to a hidden issue?
  //
  shouldBeFocused: PropTypes.bool,
  tabIndex: PropTypes.number,
  isModal: PropTypes.bool,
  isAdvanced: PropTypes.bool,
  isFullScreen: PropTypes.bool,
  //
  onBlurWidget: PropTypes.func.isRequired,
  addRefToWidgets: PropTypes.func.isRequired,
  requestElementGroupFocus: PropTypes.func.isRequired,
};

export default ElementGroup;
