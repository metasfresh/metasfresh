import counterpart from 'counterpart';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {
  getViewAttributes,
  getViewAttributesLayout,
} from '../../actions/ViewAttributesActions';
import RawWidget from '../widget/RawWidget';

/**
 * @file Class based component.
 * @module DocumentList
 * @extends PureComponent
 */
class SelectionAttributes extends PureComponent {
  componentDidUpdate = (prevProps) => {
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

    if (JSON.stringify(prevProps.selected) !== JSON.stringify(selected)) {
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
      windowType,
      viewId,
      selected,
      DLWrapperSetData,
      DLWrapperSetLayout,
    } = this.props;
    getViewAttributesLayout(windowType, viewId, selected[0])
      .then((response) => {
        DLWrapperSetLayout(response.data.elements);
        return getViewAttributes(windowType, viewId, selected[0]);
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
      windowType,
      viewId,
      DLWrapperLayout,
      DLWrapperData,
      DLWrapperDataId,
      DLWrapperHandleChange,
      DLWrapperHandlePatch,
      entity,
      setClickOutsideLock,
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
                windowType={windowType}
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

SelectionAttributes.propTypes = {
  windowType: PropTypes.string,
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
};

export default connect()(SelectionAttributes);
