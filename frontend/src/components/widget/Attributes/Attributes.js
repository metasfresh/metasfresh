import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import classnames from 'classnames';

import * as api from '../../../api/attributes';
import { formatDateWithZeros } from '../../../utils/documentListHelper';
import {
  DROPDOWN_OFFSET_BIG,
  DROPUP_OFFSET_SMALL,
  DROPUP_START,
} from '../../../constants/Constants';
import { getTableId } from '../../../reducers/tables';

import AttributesDropdown from './AttributesDropdown';

export default class Attributes extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      layout: null,
      editingInstanceId: null,
      fieldsByName: null,
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
      value,
      entity,
    } = this.props;

    const templateId =
      value && value.key
        ? parseInt(value.key, 10) // assume 'value' is a key/caption lookup value
        : parseInt(value, 10); // assume 'value' is string or int

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
      return api
        .createAttributesEditingInstance(attributeType, templateId, source)
        .then((response) => {
          const { id, layout, fieldsByName } = response.data;
          this.setState({
            editingInstanceId: id,
            layout: layout.elements,
            fieldsByName: mergeFieldsByNames({}, fieldsByName),
            isDropdownOpen: true,
          });
        })
        .catch((error) =>
          console.error(
            'Failed creating a new editing attributes instance: ',
            error.message
          )
        )
        .finally(() => this.setState({ loading: false }));
    });
  };

  showHideDropdown = (show) => {
    const {
      readonly,
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

    // Do nothing if readonly. Shall not happen
    if (readonly) {
      return;
    }

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
      updateHeight(DROPUP_OFFSET_SMALL);

    if (!loading) {
      this.setState(
        {
          layout: null,
          fieldsByName: null,
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

  mergeFieldsByNameIntoState = (fieldsByNameToMerge, callback = null) => {
    this.setState(
      (prevState) => ({
        fieldsByName: mergeFieldsByNames(
          prevState.fieldsByName,
          fieldsByNameToMerge
        ),
      }),
      () => {
        callback && callback();
      }
    );
  };

  handleFieldChange = (fieldName, value) => {
    const { isDropdownOpen, fieldsByName } = this.state;
    // Add special case of formatting for the case when people input 04.7.2020 to be transformed to 04.07.2020
    value =
      isDropdownOpen && fieldsByName[fieldName].widgetType === 'Date'
        ? formatDateWithZeros(value)
        : value;

    this.mergeFieldsByNameIntoState({ [fieldName]: { value } });
  };

  handleFieldPatch = (fieldName, value, editingInstanceId, callback = null) => {
    const { attributeType } = this.props;
    const { fieldsByName, loading } = this.state;

    if (!loading && fieldsByName) {
      this.mergeFieldsByNameIntoState({ [fieldName]: { value } });

      return api
        .patchAttributes({
          attributeType,
          instanceId: editingInstanceId,
          fieldName,
          value,
        })
        .then((response) => {
          if (response.data && response.data.length) {
            const fieldsByName = response.data[0].fieldsByName;
            this.mergeFieldsByNameIntoState(fieldsByName, callback);

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
    const { fieldsByName, loading } = this.state;
    const { disconnected } = this.props;

    if (!loading && fieldsByName) {
      const mandatoryFieldNames = Object.keys(fieldsByName).filter(
        (fieldName) => fieldsByName[fieldName].mandatory
      );
      const valid = !mandatoryFieldNames.filter(
        (fieldName) => !fieldsByName[fieldName].value
      ).length;

      //there are required values that are not set. just close
      if (mandatoryFieldNames.length && !valid) {
        /** we are treating the inlineTab differently - we don't show this confirmation dialog  */
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
    const { editingInstanceId, fieldsByName } = this.state;

    api
      .completeAttributesEditing(attributeType, editingInstanceId, fieldsByName)
      .then((response) => {
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

  renderDropdownIfNeeded = () => {
    const { isDropdownOpen } = this.state;
    if (!isDropdownOpen) {
      return null;
    }

    const { attributeType, tabIndex, rowIndex, isModal } = this.props;

    const { layout, editingInstanceId, fieldsByName } = this.state;

    return (
      <AttributesDropdown
        attributeType={attributeType}
        editingInstanceId={editingInstanceId}
        layout={layout}
        fieldsByName={fieldsByName}
        rowIndex={rowIndex}
        tabIndex={tabIndex}
        isModal={isModal}
        //
        onFieldChange={this.handleFieldChange}
        onFieldPatch={this.handleFieldPatch}
        onCompletion={this.handleCompletion}
      />
    );
  };

  render() {
    const { value, rowId, tabIndex, readonly } = this.props;

    const { isDropdownOpen } = this.state;
    const caption = value?.caption || '';

    return (
      <div
        className={classnames('attributes', { 'attributes-in-table': rowId })}
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
          {caption ? caption : 'Edit'}
        </button>
        {this.renderDropdownIfNeeded()}
      </div>
    );
  }
}

Attributes.propTypes = {
  entity: PropTypes.string.isRequired, // i.e. window, documentView, process
  docType: PropTypes.oneOfType([PropTypes.string, PropTypes.number]), // i.e. windowId or processId
  dataId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  tabId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  rowId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  viewId: PropTypes.string,
  disconnected: PropTypes.any, // this is used to differentiate in which type of parent widget we are rendering the SubSection elements (ie. `inlineTab`)
  fieldName: PropTypes.string,
  //
  value: PropTypes.oneOfType([PropTypes.object, PropTypes.string]), // lookup value, e.g. { key: 1234, caption: 'ASI description' }, { key: 333, caption: 'Location Description'  }, ""
  attributeType: PropTypes.string.isRequired,
  //
  rowIndex: PropTypes.number, // used for knowing the row index within the Table (used on AttributesDropdown component)
  tabIndex: PropTypes.number,
  readonly: PropTypes.bool,
  isModal: PropTypes.bool,
  //
  patch: PropTypes.func.isRequired,
  handleBackdropLock: PropTypes.func,
  updateHeight: PropTypes.func, // adjusts the table container with a given height from a child component when child exceeds visible area
  openModal: PropTypes.func,
  closeModal: PropTypes.func,
  setTableNavigation: PropTypes.func,
};

const mergeFieldsByNames = (existingFieldsByName, fieldsByNameToMerge) => {
  const result = existingFieldsByName ? { ...existingFieldsByName } : {};

  Object.keys(fieldsByNameToMerge).forEach((fieldName) => {
    // Skip pseudo-field "ID". We already have editingInstanceId in our state.
    if (fieldName === 'ID') {
      return;
    }

    const fieldData = fieldsByNameToMerge[fieldName];
    result[fieldName] = {
      ...result[fieldName],
      ...fieldData,
    };
  });

  return result;
};
