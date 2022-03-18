import PropTypes from 'prop-types';
import React, { Component } from 'react';
import classnames from 'classnames';

import {
  getAttributesInstance,
  getLayout,
  patchRequest,
  completeRequest,
} from '../../../api';
import {
  parseToDisplay,
  formatDateWithZeros,
} from '../../../utils/documentListHelper';
import AttributesDropdown from './AttributesDropdown';
import {
  DROPUP_START,
  DROPDOWN_OFFSET_BIG,
  DROPDOWN_OFFSET_SMALL,
} from '../../../constants/Constants';
import { getTableId } from '../../../reducers/tables';

/**
 * @file Class based component.
 * @module Attributes
 * @extends Component
 */
export default class Attributes extends Component {
  constructor(props) {
    super(props);

    this.state = {
      layout: null,
      data: null,
      loading: false,
      isDropdownOpen: false,
    };
  }

  loadDropdownData = () => {
    const {
      docType,
      dataId,
      tabId,
      rowId,
      viewId,
      fieldName,
      attributeType,
      widgetData,
      entity,
    } = this.props;

    const templateId =
      widgetData.value && widgetData.value.key
        ? parseInt(widgetData.value.key, 10) // assume 'value' is a key/caption lookup value
        : parseInt(widgetData.value, 10); // assume 'value' is string or int

    let source;
    if (entity === 'window') {
      source = {
        windowId: docType,
        documentId: dataId,
        tabId: tabId,
        rowId: rowId,
        fieldName: fieldName,
      };
    } else if (entity === 'documentView') {
      source = {
        viewId: viewId,
        rowId: rowId,
        fieldName: fieldName,
      };
    } else if (entity === 'process') {
      source = {
        processId: docType,
        documentId: dataId,
        fieldName: fieldName,
      };
    } else {
      console.error('Unknown entity: ', entity);
    }

    this.setState({ loading: true }, () => {
      return getAttributesInstance(attributeType, templateId, source)
        .then((response) => {
          const { id, fieldsByName } = response.data;
          this.setState({ data: parseToDisplay(fieldsByName) });
          return getLayout(attributeType, id);
        })
        .then((response) => {
          const { elements } = response.data;
          this.setState({ layout: elements });
        })
        .then(() => this.setState({ loading: false, isDropdownOpen: true }))
        .catch((error) =>
          console.error('Attributes handleInit error: ', error.message)
        )
        .finally(() => this.setState({ loading: false }));
    });
  };

  showHideDropdown = (show) => {
    const {
      handleBackdropLock,
      updateHeight,
      rowIndex,
      isModal,
      setTableNavigation,
      docType,
      dataId,
      tabId,
    } = this.props;
    const { loading, isDropdownOpen } = this.state;

    // this is limited to tables only
    if (rowIndex != null) {
      const tableId = getTableId({ windowId: docType, docId: dataId, tabId });
      setTableNavigation(tableId, !show);
    }

    !isDropdownOpen &&
      !isModal &&
      rowIndex < DROPUP_START &&
      updateHeight(DROPDOWN_OFFSET_BIG);
    isDropdownOpen &&
      !isModal &&
      rowIndex < DROPUP_START &&
      updateHeight(DROPDOWN_OFFSET_SMALL);

    if (!loading) {
      this.setState(
        {
          layout: null,
          data: null,
          isDropdownOpen: false,
        },
        () => {
          //Method is disabling outside click in parents
          //elements if there is some
          handleBackdropLock && handleBackdropLock(!!show);

          if (show) {
            this.loadDropdownData();
          }
        }
      );
    }
  };

  handleKeyDown = (e) => {
    console.log('handleKeyDown', { key: e.key, altKey: e.altKey, e });
    if (e.key === 'Enter' && e.altKey) {
      e.stopPropagation();
      e.preventDefault();
      this.handleCompletion();
    } else if (e.key === 'Escape') {
      e.stopPropagation();
      e.preventDefault();
      this.handleCompletion();
    }
  };

  handleFieldChange = async (field, value) => {
    const { isDropdownOpen, data } = this.state;
    // Add special case of formatting for the case when people input 04.7.2020 to be transformed to 04.07.2020
    value =
      isDropdownOpen && data[field].widgetType === 'Date'
        ? await formatDateWithZeros(value)
        : value;

    this.setState((prevState) => ({
      data: Object.assign({}, prevState.data, {
        [field]: Object.assign({}, prevState.data[field], { value }),
      }),
    }));
  };

  handleFieldPatch = (fieldName, value, attrId, callback) => {
    const { attributeType } = this.props;
    const { data, loading } = this.state;

    if (!loading && data) {
      return patchRequest({
        entity: attributeType,
        docType: null,
        docId: attrId,
        property: fieldName,
        value,
      }).then((response) => {
        if (response.data && response.data.length) {
          const fields = response.data[0].fieldsByName;

          Object.keys(fields).map((fieldName) => {
            this.setState(
              (prevState) => ({
                data: {
                  ...prevState.data,
                  [fieldName]: {
                    ...prevState.data[fieldName],
                    value,
                  },
                },
              }),
              () => {
                callback && callback();
              }
            );
          });
          return Promise.resolve(true);
        } else {
          return Promise.resolve(false);
        }
      });
    } else {
      return Promise.resolve(true);
    }
  };

  handleCompletion = () => {
    const { data, loading } = this.state;
    const { disconnected } = this.props;

    if (!loading && data) {
      const mandatory = Object.keys(data).filter(
        (fieldName) => data[fieldName].mandatory
      );
      const valid = !mandatory.filter((field) => !data[field].value).length;

      //there are required values that are not set. just close
      if (mandatory.length && !valid) {
        /** we are treating the inlineTab differently - we don't show this confirm dialog  */
        if (disconnected === 'inlineTab') {
          /** TODO: here we might use a prompt explaining that the settings were not saved */
          this.showHideDropdown(false);
        } else {
          /** the generic case  */
          if (window.confirm('Do you really want to leave?')) {
            this.showHideDropdown(false);
          }
        }
      } else {
        this.doCompleteRequest();
        this.showHideDropdown(false);
      }
    }
  };

  doCompleteRequest = () => {
    const { attributeType, patch, openModal, closeModal } = this.props;
    const { data } = this.state;
    const attrId = data && data.ID ? data.ID.value : -1;

    completeRequest(attributeType, attrId).then((response) => {
      patch(response.data).then(({ triggerActions }) => {
        // post PATCH actions if we have `triggerActions` present
        if (triggerActions) {
          closeModal();
          triggerActions.forEach((itemTriggerAction) => {
            let {
              selectedDocumentPath: { documentId },
              processId,
            } = itemTriggerAction;

            openModal({
              windowId: processId,
              modalType: 'process',
              viewDocumentIds: [`${documentId}`],
            });
          });
        }
      });
    });
  };

  render() {
    const {
      widgetData,
      rowId,
      attributeType,
      tabIndex,
      readonly,
      rowIndex,
      isModal,
    } = this.props;

    const { isDropdownOpen, data, layout } = this.state;
    const { value } = widgetData;
    const label = value ? value.caption : '';
    const attrId = data && data.ID ? data.ID.value : -1;

    return (
      <div
        onKeyDown={this.handleKeyDown}
        className={classnames('attributes', {
          'attributes-in-table': rowId,
        })}
      >
        <button
          tabIndex={tabIndex}
          onClick={() => this.showHideDropdown(true)}
          className={classnames(
            'btn btn-block tag tag-lg tag-block tag-secondary pointer',
            {
              'tag-disabled': isDropdownOpen,
              'tag-disabled disabled': readonly,
            }
          )}
        >
          {label ? label : 'Edit'}
        </button>
        {isDropdownOpen && (
          <AttributesDropdown
            attributeType={attributeType}
            attrId={attrId}
            layout={layout}
            rowIndex={rowIndex}
            tabIndex={tabIndex}
            data={data}
            isModal={isModal}
            //
            onFieldChange={this.handleFieldChange}
            onFieldPatch={this.handleFieldPatch}
            onCompletion={this.handleCompletion}
          />
        )}
      </div>
    );
  }
}

Attributes.propTypes = {
  entity: PropTypes.string.isRequired,
  docType: PropTypes.oneOfType([PropTypes.string, PropTypes.number]), // i.e. windowId or processId
  dataId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  tabId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  rowId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  viewId: PropTypes.string,
  disconnected: PropTypes.any, // this is used to differentiate in which type of parent widget we are rendering the SubSection elements (ie. `inlineTab`)
  //
  fieldName: PropTypes.string,
  attributeType: PropTypes.string.isRequired,
  widgetType: PropTypes.string,
  widgetData: PropTypes.object,
  //
  rowIndex: PropTypes.number, // used for knowing the row index within the Table (used on AttributesDropdown component)
  tabIndex: PropTypes.number,
  readonly: PropTypes.bool,
  isModal: PropTypes.bool,
  //
  patch: PropTypes.func.isRequired,
  handleBackdropLock: PropTypes.func,
  onBlur: PropTypes.func,
  updateHeight: PropTypes.func, // adjusts the table container with a given height from a child component when child exceeds visible area
  openModal: PropTypes.func,
  closeModal: PropTypes.func,
  setTableNavigation: PropTypes.func,
};
