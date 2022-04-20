import PropTypes from 'prop-types';
import React, { useState } from 'react';
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

/**
 * @component
 */
const Attributes = ({
  entity,
  docType,
  dataId,
  tabId,
  rowId,
  viewId,
  disconnected,
  fieldName,
  //
  value,
  attributeType,
  //
  rowIndex,
  tabIndex,
  readonly,
  isModal,
  //
  patch,
  handleBackdropLock,
  updateHeight,
  openModal,
  closeModal,
  setTableNavigation,
}) => {
  const [layout, setLayout] = useState(null);
  const [editingInstanceId, setEditingInstanceId] = useState(null);
  const [fieldsByName, setFieldsByName] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const loadDropdownData = () => {
    const templateId =
      value && value.key
        ? parseInt(value.key, 10) // assume 'value' is a key/buttonCaption lookup value
        : parseInt(value, 10); // assume 'value' is string or int

    const source = computeEditingSource({
      entity,
      docType,
      dataId,
      tabId,
      rowId,
      viewId,
      fieldName,
    });

    setIsLoading(true);
    return api
      .createAttributesEditingInstance(attributeType, templateId, source)
      .then(({ id, layout, fieldsByName }) => {
        setEditingInstanceId(id);
        setLayout(layout.elements);
        setFieldsByName(mergeFieldsByNames({}, fieldsByName));
        setIsDropdownOpen(true);
      })
      .catch((error) =>
        console.error(
          'Failed creating a new editing attributes instance: ',
          error.message
        )
      )
      .finally(() => setIsLoading(false));
  };

  const showHideDropdown = (show) => {
    // Do nothing if readonly. Shall not happen
    if (readonly) {
      return;
    }

    // this is limited to tables only
    if (rowIndex != null && setTableNavigation) {
      const tableId = getTableId({
        windowId: docType,
        viewId,
        docId: dataId,
        tabId,
      });
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

    if (!isLoading) {
      setLayout(null);
      setFieldsByName(null);
      setIsDropdownOpen(false);

      //Method is disabling outside click in parents elements if there is some
      handleBackdropLock && handleBackdropLock(!!show);

      if (show) {
        loadDropdownData();
      }
    }
  };

  const mergeFieldsByNameIntoState = (fieldsByNameToMerge) => {
    setFieldsByName(mergeFieldsByNames(fieldsByName, fieldsByNameToMerge));
  };

  const handleFieldChange = (fieldName, value) => {
    // Add special case of formatting for the case when people input 04.7.2020 to be transformed to 04.07.2020
    value =
      isDropdownOpen && fieldsByName[fieldName].widgetType === 'Date'
        ? formatDateWithZeros(value)
        : value;

    mergeFieldsByNameIntoState({ [fieldName]: { value } });
  };

  const handleFieldPatch = (fieldName, value, editingInstanceId) => {
    if (!isLoading && fieldsByName) {
      mergeFieldsByNameIntoState({ [fieldName]: { value } });

      return api
        .patchAttributes({
          attributeType,
          editingInstanceId,
          fieldName,
          value,
        })
        .then((fieldsByName) => mergeFieldsByNameIntoState(fieldsByName));
    } else {
      return Promise.resolve();
    }
  };

  const handleCompletion = () => {
    if (!isLoading && fieldsByName) {
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
          showHideDropdown(false);
        } else {
          /** the generic case  */
          if (window.confirm('Do you really want to leave?')) {
            showHideDropdown(false);
          }
        }
      } else {
        doCompleteRequest();
        showHideDropdown(false);
      }
    }
  };

  const doCompleteRequest = () => {
    api
      .completeAttributesEditing(attributeType, editingInstanceId, fieldsByName)
      .then((createdLookupValue) => patch(createdLookupValue))
      .then(handleAfterPatchTriggerActions);
  };

  const handleAfterPatchTriggerActions = ({ triggerActions }) => {
    // post PATCH actions if we have `triggerActions` present
    if (triggerActions) {
      closeModal();
      triggerActions.forEach((itemTriggerAction) => {
        const {
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
  };

  const buttonCaption = value?.caption || 'Edit';

  return (
    <div className={classnames('attributes', { 'attributes-in-table': rowId })}>
      <button
        tabIndex={tabIndex}
        onClick={() => showHideDropdown(true)}
        className={classnames(
          'btn btn-block tag tag-lg tag-block tag-secondary pointer',
          {
            'tag-disabled': isDropdownOpen,
            'tag-disabled disabled': readonly,
          }
        )}
      >
        {buttonCaption}
      </button>
      {isDropdownOpen && layout && fieldsByName && (
        <AttributesDropdown
          attributeType={attributeType}
          editingInstanceId={editingInstanceId}
          layout={layout}
          fieldsByName={fieldsByName}
          rowIndex={rowIndex}
          tabIndex={tabIndex}
          isModal={isModal}
          //
          onFieldChange={handleFieldChange}
          onFieldPatch={handleFieldPatch}
          onCompletion={handleCompletion}
        />
      )}
    </div>
  );
};

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

const computeEditingSource = ({
  entity,
  docType,
  dataId,
  tabId,
  rowId,
  fieldName,
  viewId,
}) => {
  if (entity === 'window') {
    return {
      windowId: docType,
      documentId: dataId,
      tabId: tabId,
      rowId: rowId,
      fieldName: fieldName,
    };
  } else if (entity === 'documentView') {
    return {
      viewId: viewId,
      rowId: rowId,
      fieldName: fieldName,
    };
  } else if (entity === 'process') {
    return {
      processId: docType,
      documentId: dataId,
      fieldName: fieldName,
    };
  } else {
    throw 'Unknown entity: ' + entity;
  }
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

export default Attributes;
