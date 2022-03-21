import PropTypes from 'prop-types';
import React from 'react';
import classnames from 'classnames';
import FocusTrap from 'focus-trap-react';

import { DROPUP_START } from '../../../constants/Constants';
import { useOnClickOutsideWithControls } from '../../../hooks/useOnClickOutsideWithControls';

import WidgetWrapper from '../../../containers/WidgetWrapper';
import { shallowEqual, useSelector } from 'react-redux';

/**
 * @component
 */
const AttributesDropdown = ({
  attributeType,
  editingInstanceId,
  layout,
  fieldsByName,
  rowIndex,
  tabIndex,
  isModal,
  //
  onFieldChange,
  onFieldPatch,
  onCompletion,
}) => {
  const { allowOutsideClick } = useSelector(
    (state) => getPropsFromState({ state }),
    shallowEqual
  );

  const {
    clickOutsideComponentRef,
    enableOnClickOutside,
    disableOnClickOutside,
  } = useOnClickOutsideWithControls(() => {
    allowOutsideClick && onCompletion();
  });

  const handleKeyDown = (event) => {
    if ((event.key === 'Enter' && event.altKey) || event.key === 'Escape') {
      event.stopPropagation();
      event.preventDefault();
      onCompletion();
    }
  };

  function extractWidgetData(elementLayout) {
    return elementLayout.fields.map(
      (fieldLayout) => fieldsByName[fieldLayout.field] || -1
    );
  }

  return (
    <FocusTrap
      focusTrapOptions={{
        // NOTE: we have to allowOutsideClick=true
        // because else clicking on date picker calendar navigation buttons won't work
        allowOutsideClick: true,
        escapeDeactivates: false,
      }}
    >
      <div
        ref={clickOutsideComponentRef}
        className={classnames(
          'attributes-dropdown panel-shadowed panel-primary panel-bordered panel-spaced',
          { 'attributes-dropup': rowIndex > DROPUP_START }
        )}
        onKeyDown={handleKeyDown}
      >
        {layout.map((elementLayout, elementIndex) => (
          <WidgetWrapper
            key={elementIndex}
            dataSource="attributes-dropdown"
            entity={attributeType}
            type={elementLayout.type}
            caption={elementLayout.caption}
            widgetType={elementLayout.widgetType}
            fields={elementLayout.fields}
            dataId={editingInstanceId}
            widgetData={extractWidgetData(elementLayout)}
            gridAlign={elementLayout.gridAlign}
            autoFocus={elementIndex === 0}
            handlePatch={(fieldName, value) => {
              onFieldPatch(fieldName, value, editingInstanceId);
            }}
            handleChange={onFieldChange}
            enableOnClickOutside={enableOnClickOutside}
            disableOnClickOutside={disableOnClickOutside}
            attributeWidget={true}
            tabIndex={tabIndex}
            isModal={isModal}
          />
        ))}
      </div>
    </FocusTrap>
  );
};

/**
 * @typedef {object} Props Component props
 *
 * @prop {string} [attributeType] i.e. pattribute, address
 * @prop {string|number} [editingInstanceId] the ID of the temporary editing instance
 * @prop {array} [layout] array of element layouts
 * @prop {object} [fieldsByName]
 * @prop {number} [rowIndex] row index within the table
 * @prop {number} [tabIndex]
 * @prop {bool} [isModal]
 *
 * @prop {func} [onFieldChange]
 * @prop {func} [onFieldPatch]
 * @prop {func} [onCompletion]
 */
AttributesDropdown.propTypes = {
  attributeType: PropTypes.string.isRequired,
  editingInstanceId: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
    .isRequired,
  layout: PropTypes.array.isRequired,
  fieldsByName: PropTypes.object.isRequired,
  rowIndex: PropTypes.number,
  tabIndex: PropTypes.number,
  isModal: PropTypes.bool,
  //
  onFieldChange: PropTypes.func.isRequired,
  onFieldPatch: PropTypes.func.isRequired,
  onCompletion: PropTypes.func.isRequired,
};

const getPropsFromState = ({ state }) => {
  return {
    allowOutsideClick: !!state.windowHandler.allowOutsideClick,
  };
};

export default AttributesDropdown;
