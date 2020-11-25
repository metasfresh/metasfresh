import counterpart from 'counterpart';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import {
  getViewAttributes,
  getViewAttributesLayout,
} from '../../actions/ViewAttributesActions';
import { allowShortcut, disableShortcut } from '../../actions/WindowActions';

import RawWidget from '../widget/RawWidget';

/**
 * @file Class based component.
 * @module DocumentList
 * @extends PureComponent
 */
class SelectionAttributes extends PureComponent {
  componentDidMount = () => {
    this.shouldFetchActions();
  };

  componentDidUpdate = (prevProps) => {
    this.shouldFetchActions(prevProps);
  };

  shouldFetchActions = (prevProps) => {
    const {
      selected,
      DLWrapperSetData,
      DLWrapperSetLayout,
      shouldNotUpdate,
      supportAttribute,
    } = this.props;

    if (shouldNotUpdate) {
      return;
    }

    if (
      !prevProps ||
      (prevProps &&
        JSON.stringify(prevProps.selected) !== JSON.stringify(selected))
    ) {
      DLWrapperSetData([], null, () => {
        DLWrapperSetLayout([], () => {
          if (supportAttribute && selected.length === 1) {
            if (selected[0] == 0) {
              return;
            }

            this.fetchActions();
          }
        });
      });
    }
  };

  fetchActions = () => {
    const {
      windowId,
      viewId,
      selected,
      DLWrapperSetData,
      DLWrapperSetLayout,
    } = this.props;
    getViewAttributesLayout(windowId, viewId, selected[0])
      .then((response) => {
        DLWrapperSetLayout(response.data.elements);
        return getViewAttributes(windowId, viewId, selected[0]);
      })
      .then((response) => {
        DLWrapperSetData(response.data.fieldsByName, response.data.id);
      })
      .catch(() => {});
  };

  moveToDevice = (e) => {
    switch (e.key) {
      case 'Shift':
        e.preventDefault();
        //TO DO
        break;
    }
  };

  getTabId = (item) => {
    return item && item[0].readonly ? -1 : 1;
  };

  selectTable = () => {
    document.getElementsByClassName('js-table')[0].focus();
  };

  render() {
    const {
      windowId,
      viewId,
      DLWrapperLayout,
      DLWrapperData,
      DLWrapperDataId,
      DLWrapperHandleChange,
      DLWrapperHandlePatch,
      entity,
      setClickOutsideLock,
      modalVisible,
      timeZone,
      allowShortcut,
      disableShortcut,
    } = this.props;

    return (
      <div className="js-not-unselect">
        <div className="attributes-selector-header">
          {counterpart.translate('window.selectionAttributes.caption')}
        </div>
        <div tabIndex={1} className="attributes-selector-body js-attributes">
          {DLWrapperLayout &&
            DLWrapperLayout.map((item, id) => (
              <RawWidget
                entity={entity}
                attribute={true}
                widgetType={item.widgetType}
                fields={item.fields}
                dataId={DLWrapperDataId}
                windowType={windowId}
                viewId={viewId}
                widgetData={item.fields.map(
                  (elem) => DLWrapperData[elem.field] || -1
                )}
                gridAlign={item.gridAlign}
                key={id}
                type={item.type}
                caption={item.caption}
                handleFocus={() => setClickOutsideLock(true)}
                handleBlur={() => setClickOutsideLock(false)}
                handlePatch={DLWrapperHandlePatch}
                handleChange={DLWrapperHandleChange}
                tabIndex={this.getTabId(
                  item.fields.map((elem) => DLWrapperData[elem.field] || -1)
                )}
                {...{
                  modalVisible,
                  timeZone,
                  allowShortcut,
                  disableShortcut,
                }}
              />
            ))}
          {DLWrapperLayout && !DLWrapperLayout.length && (
            <i>
              {counterpart.translate('window.selectionAttributes.callToAction')}
            </i>
          )}
        </div>
        <div className="focusHandler" tabIndex={1} onFocus={this.selectTable} />
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  const { appHandler, windowHandler } = state;

  return {
    modalVisible: windowHandler.modal.visible,
    timeZone: appHandler.me.timeZone,
  };
};

SelectionAttributes.propTypes = {
  windowId: PropTypes.string,
  selected: PropTypes.any,
  viewId: PropTypes.string,
  DLWrapperSetLayout: PropTypes.func,
  DLWrapperSetData: PropTypes.func,
  shouldNotUpdate: PropTypes.bool,
  DLWrapperData: PropTypes.any,
  DLWrapperDataId: PropTypes.string,
  DLWrapperHandleChange: PropTypes.func,
  DLWrapperHandlePatch: PropTypes.func,
  setClickOutsideLock: PropTypes.func,
  entity: PropTypes.any,
  DLWrapperLayout: PropTypes.array,
  supportAttribute: PropTypes.bool,
  allowShortcut: PropTypes.func.isRequired,
  disableShortcut: PropTypes.func.isRequired,
  modalVisible: PropTypes.bool.isRequired,
  timeZone: PropTypes.string.isRequired,
};

export default connect(
  mapStateToProps,
  {
    allowShortcut,
    disableShortcut,
  }
)(SelectionAttributes);
