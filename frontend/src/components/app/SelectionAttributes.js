import counterpart from 'counterpart';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import {
  patchViewAttributes,
  setViewAttributesData,
} from '../../actions/IndependentWidgetsActions';

import WidgetWrapper from '../../containers/WidgetWrapper';

/**
 * @file Class based component.
 * @module DocumentList
 * @extends PureComponent
 */
class SelectionAttributes extends PureComponent {
  /*
   * @method handleChange
   * @summary Run action creator changing field value in the store
   *
   * @param {string} field
   * @param {any} value
   */
  handleChange = (field, value) => {
    const { setViewAttributesData } = this.props;

    setViewAttributesData({ field, value });
  };

  /*
   * @method handlePatch
   * @summary Run action creator sending the change to the backend
   *
   * @param {string} prop - field name
   * @param {any} value
   * @param {function} cb - callback function
   */
  handlePatch = (prop, value, cb) => {
    const {
      windowId,
      viewId,
      patchViewAttributes,
      attributes: { dataId },
    } = this.props;

    patchViewAttributes({ windowId, viewId, rowId: dataId, prop, value }).then(
      () => {
        cb && cb();
      }
    );
  };

  moveToDevice(e) {
    switch (e.key) {
      case 'Shift':
        e.preventDefault();
        //TO DO
        break;
    }
  }

  getTabId(item) {
    return item && item[0].readonly ? -1 : 1;
  }

  selectTable() {
    document.getElementsByClassName('js-table')[0].focus();
  }

  handleFocus = () => {
    this.props.setClickOutsideLock(true);
  };

  handleBlur = () => {
    this.props.setClickOutsideLock(false);
  };

  render() {
    const { windowId, viewId, attributes, entity } = this.props;
    const { fields, elements, dataId } = attributes;

    return (
      <div className="table-flex-wrapper attributes-selector js-not-unselect">
        <div className="attributes-selector-header">
          {counterpart.translate('window.selectionAttributes.caption')}
        </div>
        <div tabIndex={1} className="attributes-selector-body js-attributes">
          {elements &&
            elements.map((item, id) => (
              <WidgetWrapper
                key={id}
                dataSource="selection-attributes"
                type={item.type}
                entity={entity}
                windowId={windowId}
                dataId={dataId}
                attribute={true}
                widgetType={item.widgetType}
                fields={item.fields}
                windowType={windowId}
                viewId={viewId}
                widgetData={item.fields.map((elem) => fields[elem.field] || -1)}
                gridAlign={item.gridAlign}
                caption={item.caption}
                handleFocus={this.handleFocus}
                handleBlur={this.handleBlur}
                handlePatch={this.handlePatch}
                handleChange={this.handleChange}
                tabIndex={this.getTabId(
                  item.fields.map((elem) => fields[elem.field] || -1)
                )}
              />
            ))}
          {elements && !elements.length && (
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
  return {
    attributes: state.widgetHandler.attributes,
  };
};

SelectionAttributes.propTypes = {
  windowId: PropTypes.string,
  selected: PropTypes.any,
  viewId: PropTypes.string,
  setViewAttributesData: PropTypes.func,
  patchViewAttributes: PropTypes.func,
  attributes: PropTypes.object,
  DLWrapperHandlePatch: PropTypes.func,
  setClickOutsideLock: PropTypes.func,
  entity: PropTypes.string,
  DLWrapperLayout: PropTypes.array,
  supportAttribute: PropTypes.bool,
};

export default connect(mapStateToProps, {
  patchViewAttributes,
  setViewAttributesData,
})(SelectionAttributes);
