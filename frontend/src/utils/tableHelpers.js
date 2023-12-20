import React from 'react';
import currentDevice from 'current-device';
import PropTypes from 'prop-types';
import numeral from 'numeral';
import Moment from 'moment-timezone';

import {
  AMOUNT_FIELD_FORMATS_BY_PRECISION,
  AMOUNT_FIELD_TYPES,
  SPECIAL_FIELD_TYPES,
  DATE_FIELD_FORMATS,
  DATE_FIELD_TYPES,
  TIME_FIELD_TYPES,
  TIME_REGEX_TEST,
  TIME_FORMAT,
  VIEW_EDITOR_RENDER_MODES_ALWAYS,
  VIEW_EDITOR_RENDER_MODES_ON_DEMAND,
} from '../constants/Constants';
import { getCurrentActiveLocale } from './locale';
import { getSettingFromStateAsBoolean } from './settings';

export const containerPropTypes = {
  // from <DocumentList>
  // TODO: This needs to be fixed in all child components
  // windowId: PropTypes.number,
  // docId: PropTypes.number,
  viewId: PropTypes.string,
  tabId: PropTypes.string,
  autofocus: PropTypes.bool,
  rowEdited: PropTypes.bool,
  onSelectionChanged: PropTypes.func,
  defaultSelected: PropTypes.array,
  limitOnClickOutside: PropTypes.bool,
  supportOpenRecord: PropTypes.bool,
  onSortTable: PropTypes.func,
  parentView: PropTypes.object,

  // from redux
  rows: PropTypes.array.isRequired,
  columns: PropTypes.array.isRequired,
  selected: PropTypes.array.isRequired,
  collapsedParentRows: PropTypes.array.isRequired,
  collapsedRows: PropTypes.array.isRequired,
  allowShortcut: PropTypes.bool,
  allowOutsideClick: PropTypes.bool,
  modalVisible: PropTypes.bool,
  isGerman: PropTypes.bool,
  activeSort: PropTypes.bool,

  // action creators
  collapseTableRow: PropTypes.func.isRequired,
  deselectTableRows: PropTypes.func.isRequired,
  openModal: PropTypes.func.isRequired,
  updateTableSelection: PropTypes.func.isRequired,
};

// TableWrapper props
export const componentPropTypes = {
  ...containerPropTypes,
  onSelect: PropTypes.func.isRequired,
  onGetAllLeaves: PropTypes.func.isRequired,
  onSelectAll: PropTypes.func.isRequired,
  onDeselectAll: PropTypes.func.isRequired,
  onDeselect: PropTypes.func.isRequired,
};

const userSettings_defaultPrecisionByFieldType = {};

/**
 * @method getAmountFormatByPrecisiont
 * @param {string} precision
 **/
export function getAmountFormatByPrecision(precision) {
  return precision &&
    precision >= 0 &&
    precision < AMOUNT_FIELD_FORMATS_BY_PRECISION.length
    ? AMOUNT_FIELD_FORMATS_BY_PRECISION[precision]
    : null;
}

function getDefaultPrecisionByFieldType(fieldType) {
  return userSettings_defaultPrecisionByFieldType[fieldType];
}

export function updateDefaultPrecisionsFromUserSettings(userSettings) {
  userSettings_defaultPrecisionByFieldType['Quantity'] =
    extractDefaultPrecisionFromUserSettings(userSettings, 'Quantity');
  userSettings_defaultPrecisionByFieldType['Amount'] =
    extractDefaultPrecisionFromUserSettings(userSettings, 'Amount');
  userSettings_defaultPrecisionByFieldType['CostPrice'] =
    extractDefaultPrecisionFromUserSettings(userSettings, 'CostPrice');
  userSettings_defaultPrecisionByFieldType['Number'] =
    extractDefaultPrecisionFromUserSettings(userSettings, 'Number');

  console.debug('Updated default precision from user settings', {
    userSettings_defaultPrecisionByFieldType,
    userSettings,
  });
}

function extractDefaultPrecisionFromUserSettings(userSettings, fieldType) {
  const precision =
    userSettings?.[`widget.${fieldType}.defaultPrecision`] ?? null;
  if (
    precision == null ||
    precision === '-' ||
    precision === '' ||
    precision < 0
  ) {
    return null;
  } else {
    return Number(precision);
  }
}

/**
 * @method getDateFormat
 * @param {string} fieldType
 * @summary get the date format according to the given fieldType provided parameter
 *   <FieldType> =====> <stringFormat> correspondence:
 *   Date: 'L',
 *   ZonedDateTime: 'L LTS',
 *   DateTime: 'L LTS',
 *   Time: 'LT',
 *   Timestamp: 'L HH:mm:ss',
 */
export function getDateFormat(fieldType) {
  return DATE_FIELD_FORMATS[fieldType];
}

/**
 * @method getSizeClass
 * @param {object} col
 * @summary get the class size dinamically (for Bootstrap ) for the col obj given as param
 */
export function getSizeClass(col) {
  const { widgetType, size } = col;
  const lg = ['List', 'Lookup', 'LongText', 'Date', 'DateTime', 'Time'];
  const md = ['Text', 'Address', 'ProductAttributes'];

  if (size) {
    switch (size) {
      case 'S':
        return 'td-sm';
      case 'M':
        return 'td-md';
      case 'L':
        return 'td-lg';
      case 'XL':
        return 'td-xl';
      case 'XXL':
        return 'td-xxl';
    }
  } else {
    if (lg.indexOf(widgetType) > -1) {
      return 'td-lg';
    } else if (md.indexOf(widgetType) > -1) {
      return 'td-md';
    } else {
      return 'td-sm';
    }
  }
}

/**
 * @method getIconClassName
 * @param {object} huType
 * @summary returns the icon type for the indent row depending on the given parameter
 */
export function getIconClassName(huType) {
  switch (huType) {
    case 'LU':
      return 'meta-icon-pallete';
    case 'TU':
      return 'meta-icon-package';
    case 'CU':
      return 'meta-icon-product';
    case 'PP_Order_Receive':
      return 'meta-icon-receipt';
    case 'PP_Order_Issue':
      return 'meta-icon-issue';
    case 'PP_Order_Issue_Service':
      return 'meta-icon-issue-service';
    case 'M_Picking_Slot':
      // https://github.com/metasfresh/metasfresh/issues/2298
      return 'meta-icon-beschaffung';
  }
}

/**
 * @method createDate
 * @param {object} containing the fieldValue and fieldType
 * @summary creates a Date using Moment lib formatting it with the locale passed as param
 */
export function createDate({ fieldValue, fieldType }) {
  const activeLocale = getCurrentActiveLocale();
  const languageKey = activeLocale ? activeLocale : null;

  if (fieldValue) {
    return !Moment.isMoment(fieldValue) && fieldValue.match(TIME_REGEX_TEST)
      ? Moment.utc(Moment.duration(fieldValue).asMilliseconds())
          .locale(languageKey)
          .format(TIME_FORMAT)
      : Moment(fieldValue).locale(languageKey).format(getDateFormat(fieldType));
  }

  return '';
}

/**
 * @method createAmount
 * @param {string} fieldValue
 * @param {string} precision
 * @param {boolean} isGerman
 * @summary creates an amount for a given value with the desired precision and it provides special formatting
 *          for the case when german language is set
 */
export function createAmount(fieldValue, precision, isGerman) {
  if (fieldValue) {
    const fieldValueAsNum = numeral(parseFloat(fieldValue));
    const numberFormat = getAmountFormatByPrecision(precision);
    const returnValue = numberFormat
      ? fieldValueAsNum.format(numberFormat)
      : fieldValueAsNum.format();

    // For German natives we want to show numbers with comma as a value separator
    // https://github.com/metasfresh/me03/issues/1822
    if (isGerman && parseFloat(returnValue) != null) {
      const commaRegexp = /,/g;
      commaRegexp.test(returnValue);
      const lastIdx = commaRegexp.lastIndex;

      if (lastIdx) {
        return returnValue;
      }

      return `${returnValue}`.replace('.', ',');
    }

    return returnValue;
  }

  return '';
}

/**
 * @method createSpecialField
 * @param {string}  fieldType
 * @param {string}  fieldValue
 * @summary For the special case of fieldType being of type 'Color' it will show a circle in the TableCell
 *          with the hex value given - fieldValue
 *          More details on : https://github.com/metasfresh/metasfresh-webui-frontend-legacy/issues/1603
 */
export function createSpecialField(fieldType, fieldValue) {
  switch (fieldType) {
    case 'Color': {
      const style = {
        backgroundColor: fieldValue,
      };
      return <span className="widget-color-display" style={style} />;
    }
    default:
      return fieldValue;
  }
}

/**
 * @method fieldValueToString
 * @param {string} fieldValue
 * @param {string} fieldType
 * @param {string} precision
 * @param {boolean} isGerman
 * @summary This is a patch function to mangle the desired output used at table level within TableCell, Filters components
 */
export function fieldValueToString({
  fieldValue,
  fieldType = 'Text',
  precision = null,
  isGerman,
}) {
  if (fieldValue === null) {
    return '';
  }

  switch (typeof fieldValue) {
    /**
     * Case when fieldValue is passed as an array - this is used to show date intervals within filters
     * as dd.mm.yyyy - dd.mm.yyyy for example
     */
    case 'object': {
      if (Array.isArray(fieldValue)) {
        return fieldValue
          .map((value) => fieldValueToString(value, fieldType))
          .join(' - ');
      }

      return DATE_FIELD_TYPES.includes(fieldType) ||
        TIME_FIELD_TYPES.includes(fieldType)
        ? createDate({ fieldValue, fieldType })
        : fieldValue.caption;
    }
    case 'boolean': {
      return fieldValue ? (
        <i className="meta-icon-checkbox-1" />
      ) : (
        <i className="meta-icon-checkbox" />
      );
    }
    case 'string': {
      if (
        DATE_FIELD_TYPES.includes(fieldType) ||
        TIME_FIELD_TYPES.includes(fieldType)
      ) {
        return createDate({ fieldValue, fieldType });
      } else if (AMOUNT_FIELD_TYPES.includes(fieldType)) {
        const precisionEffective =
          precision != null
            ? precision
            : getDefaultPrecisionByFieldType(fieldType);
        return createAmount(fieldValue, precisionEffective, isGerman);
      } else if (SPECIAL_FIELD_TYPES.includes(fieldType)) {
        return createSpecialField(fieldType, fieldValue);
      }
      return fieldValue;
    }
    default: {
      return fieldValue;
    }
  }
}

export function handleCopy(e) {
  e.preventDefault();

  const cell = e.target;
  const textValue = cell.value || cell.textContent;

  e.clipboardData.setData('text/plain', textValue);
}

export function handleOpenNewTab({ windowId, rowIds }) {
  if (!rowIds) {
    return;
  }

  rowIds.forEach((rowId) => {
    window.open(`/window/${windowId}/${rowId}`, '_blank');
  });
}

export function shouldRenderColumn(column) {
  if (
    !column.restrictToMediaTypes ||
    column.restrictToMediaTypes.length === 0
  ) {
    return true;
  }

  const deviceType = currentDevice.type;
  let mediaType = 'tablet';

  if (deviceType === 'mobile') {
    mediaType = 'phone';
  } else if (deviceType === 'desktop') {
    mediaType = 'screen';
  }

  return column.restrictToMediaTypes.indexOf(mediaType) !== -1;
}

/**
 * @method isEditableOnDemand
 * @summary Check if field is editable on demand
 *
 * @param {object} item - single widget data
 * @param {object} cells - map of data's fields by name
 */
export function isEditableOnDemand(item, cells) {
  const property = item.fields ? item.fields[0].field : item.field;

  return (
    (cells &&
      cells[property] &&
      cells[property].viewEditorRenderMode ===
        VIEW_EDITOR_RENDER_MODES_ON_DEMAND) ||
    item.viewEditorRenderMode === VIEW_EDITOR_RENDER_MODES_ON_DEMAND
  );
}

/**
 * @method isCellEditable
 * @summary Checks if field is always editable
 *
 * @param {object} item - single widget data
 * @param {object} cells - map of data's fields by name
 */
export function isCellEditable(item, cells) {
  const property = item.fields[0].field;
  let isEditable =
    (cells &&
      cells[property] &&
      cells[property].viewEditorRenderMode ===
        VIEW_EDITOR_RENDER_MODES_ALWAYS) ||
    item.viewEditorRenderMode === VIEW_EDITOR_RENDER_MODES_ALWAYS;

  isEditable = item.widgetType === 'Color' ? false : isEditable;

  return isEditable;
}

/**
 * @method prepareWidgetData
 * @summary Create data for the widget
 *
 * @param {object} item - single widget data
 * @param {object} cells - map of data's fields by name
 */
export function prepareWidgetData(item, cells) {
  return item.fields.map((prop) => cells[prop.field]);
}

/**
 * @method getWidgetData
 * @summary prepare data for the widget
 *
 * @param {object} cells - row's `fieldsByName` that hold the field value/type
 * @param {object} item - widget data object
 * @param {boolean} isEditable - flag if cell is editable
 * @param {boolean} supportFieldEdit - flag if selected cell can be editable
 */
export function getCellWidgetData(cells, item, isEditable, supportFieldEdit) {
  const widgetData = item.fields.reduce((result, prop) => {
    if (cells) {
      let cellWidget = cells[prop.field] || null;

      if (isEditable || (supportFieldEdit && typeof cellWidget === 'object')) {
        cellWidget = {
          ...cellWidget,
          widgetType: item.widgetType,
          displayed: true,
          readonly: false,
        };
      } else {
        cellWidget = {
          ...cellWidget,
          readonly: true,
        };
      }

      if (cellWidget) {
        result.push(cellWidget);
      }
    }
    return result;
  }, []);

  if (widgetData.length) {
    return widgetData;
  }

  return [{}];
}

/**
 * @method getTdValue
 * @summary Get the content of the table divider based on the widgetData provided
 *
 * @param {array} widgetData
 * @param {object} item
 * @param {bool} isEdited
 * @param {bool} isGerman
 */
export function getTdValue({ widgetData, item, isEdited, isGerman }) {
  return !isEdited
    ? fieldValueToString({
        fieldValue: widgetData[0].value,
        fieldType: item.widgetType,
        precision: widgetData[0].precision,
        isGerman,
      })
    : null;
}

/**
 * @method getDescription
 * @summary Get the description based on the widgetData and table divider value provided
 *
 * @param {array} widgetData
 * @param {string|null} tdValue
 */
export function getDescription({ widgetData, tdValue }) {
  return widgetData[0].value && widgetData[0].value.description
    ? widgetData[0].value.description
    : tdValue;
}

/**
 * @method getTdTitle
 * @summary Get the table divider title based on item content and provided description
 *
 * @param {object} item
 * @param {string} desciption
 */
export function getTdTitle({ item, description }) {
  return item.widgetType === 'YesNo' ||
    item.widgetType === 'Switch' ||
    item.widgetType === 'Color'
    ? ''
    : description;
}

/**
 * @method checkIfDateField
 * @summary check if it's a date field or not
 *
 * @param {object} item
 */
export function checkIfDateField({ item }) {
  return DATE_FIELD_FORMATS[item.widgetType]
    ? getDateFormat(item.widgetType)
    : false;
}

/**
 * @method nestedSelect
 * @summary Recursive fn to get row and it's descendants
 *
 * @param {array} elem - row element
 */
export function nestedSelect(elem) {
  let res = [];

  elem &&
    elem.map((item) => {
      res = res.concat([item.id]);

      if (item.includedDocuments) {
        res = res.concat(nestedSelect(item.includedDocuments));
      }
    });

  return res;
}

/**
 * @method getTooltipWidget
 * @summary check if cell has a tooltip and selects it's data if needed
 *
 * @param {object} item - row element
 * @param {array} widgetData
 */
export function getTooltipWidget(item, widgetData) {
  let tooltipData = null;
  let tooltipWidget =
    item.fields && item.widgetType === 'Lookup'
      ? item.fields.find((field, idx) => {
          if (field.type === 'Tooltip') {
            tooltipData = widgetData[idx];

            if (tooltipData && tooltipData.value) {
              return field;
            }
          }
          return false;
        })
      : null;

  return { tooltipData, tooltipWidget };
}

export const computeNumberOfPages = (size, pageLength) => {
  if (pageLength > 0) {
    return size ? Math.ceil(size / pageLength) : 0;
  } else {
    return size ? 1 : 0;
  }
};

export const isShowCommentsMarker = (state) =>
  getSettingFromStateAsBoolean(state, 'view.showCommentsMarker', true);
