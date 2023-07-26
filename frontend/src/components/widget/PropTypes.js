import PropTypes from 'prop-types';

/**
 * @typedef {object} Props Component props
 * @prop {func} allowShortcut
 * @prop {func} disableShortcut
 * @prop {bool} inProgress
 * @prop {bool} autoFocus
 * @prop {bool} textSelected
 * @prop {bool} listenOnKeys
 * @prop {func} listenOnKeysFalse
 * @prop {func} listenOnKeysTrue
 * @prop {array} widgetData
 * @prop {func} handleFocus
 * @prop {func} handlePatch
 * @prop {func} handleBlur
 * @prop {func} handleProcess
 * @prop {func} handleChange
 * @prop {func} hanndleBackdropLock
 * @prop {func} handleZoomInto
 * @prop {string} tabId
 * @prop {string} viewId
 * @prop {string} rowId
 * @prop {string|number} dataId
 * @prop {string} windowType
 * @prop {string} caption
 * @prop {string} gridAlign
 * @prop {string} type
 * @prop {bool} updated
 * @prop {bool} isModal
 * @prop {bool} modalVisible
 * @prop {bool} filterWidget
 * @prop {string} filterId
 * @prop {number} id
 * @prop {bool} range
 * @prop {func} onShow
 * @prop {func} onHide
 * @prop {string} subentity
 * @prop {string} subentityId
 * @prop {number} tabIndex
 * @prop {func} dropdownOpenCallback
 * @prop {bool} fullScreen
 * @prop {string} widgetType
 * @prop {array} fields
 * @prop {string} icon
 * @prop {string} entity
 * @prop {*} data
 * @prop {func} closeTableField
 * @prop {bool} attribute
 * @prop {bool} allowShowPassword
 * @prop {string} buttonProcessId
 * @prop {func} onBlurWidget
 * @prop {string|array} defaultValue
 * @prop {bool} noLabel
 * @prop {bool} isOpenDatePicker
 * @prop {number} forceHeight
 * @prop {bool} dataEntry
 * @prop {bool} lastFormField
 */
export const RawWidgetPropTypes = {
  allowShortcut: PropTypes.func.isRequired,
  disableShortcut: PropTypes.func.isRequired,
  inProgress: PropTypes.bool,
  autoFocus: PropTypes.bool,
  textSelected: PropTypes.bool,
  listenOnKeys: PropTypes.bool,
  listenOnKeysFalse: PropTypes.func,
  listenOnKeysTrue: PropTypes.func,
  widgetData: PropTypes.array,
  handleFocus: PropTypes.func,
  handlePatch: PropTypes.func,
  handleBlur: PropTypes.func,
  handleProcess: PropTypes.func,
  handleChange: PropTypes.func,
  handleBackdropLock: PropTypes.func,
  handleZoomInto: PropTypes.func,
  tabId: PropTypes.string,
  viewId: PropTypes.string,
  rowId: PropTypes.string,
  dataId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  windowType: PropTypes.string,
  caption: PropTypes.string,
  gridAlign: PropTypes.string,
  type: PropTypes.string,
  updated: PropTypes.bool,
  isModal: PropTypes.bool,
  modalVisible: PropTypes.bool.isRequired,
  filterWidget: PropTypes.bool,
  filterId: PropTypes.string,
  id: PropTypes.number,
  range: PropTypes.bool,
  onShow: PropTypes.func,
  onHide: PropTypes.func,
  subentity: PropTypes.string,
  subentityId: PropTypes.string,
  tabIndex: PropTypes.number,
  dropdownOpenCallback: PropTypes.func,
  fullScreen: PropTypes.bool,
  widgetType: PropTypes.string,
  fields: PropTypes.array,
  icon: PropTypes.string,
  entity: PropTypes.string,
  data: PropTypes.any,
  closeTableField: PropTypes.func,
  attribute: PropTypes.bool,
  allowShowPassword: PropTypes.bool, // NOTE: looks like this wasn't used
  buttonProcessId: PropTypes.string, // NOTE: looks like this wasn't used
  onBlurWidget: PropTypes.func,
  defaultValue: PropTypes.oneOfType([PropTypes.string, PropTypes.array]),
  noLabel: PropTypes.bool,
  isOpenDatePicker: PropTypes.bool,
  forceHeight: PropTypes.number,
  dataEntry: PropTypes.bool,
  lastFormField: PropTypes.bool,
};

export const RawWidgetDefaultProps = {
  tabIndex: 0,
  handleZoomInto: () => {},
};

export const WidgetRendererPropTypes = {
  allowShortcut: PropTypes.func.isRequired,
  disableShortcut: PropTypes.func.isRequired,
  inProgress: PropTypes.bool,
  autoFocus: PropTypes.bool,
  textSelected: PropTypes.bool,
  listenOnKeys: PropTypes.bool,
  listenOnKeysFalse: PropTypes.func,
  listenOnKeysTrue: PropTypes.func,
  widgetData: PropTypes.array,
  handleFocus: PropTypes.func,
  handlePatch: PropTypes.func,
  handleBlur: PropTypes.func,
  handleProcess: PropTypes.func,
  handleChange: PropTypes.func,
  handleBackdropLock: PropTypes.func,
  handleZoomInto: PropTypes.func,
  tabId: PropTypes.string,
  viewId: PropTypes.string,
  rowId: PropTypes.string,
  dataId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  windowType: PropTypes.string,
  caption: PropTypes.string,
  gridAlign: PropTypes.string,
  type: PropTypes.string,
  updated: PropTypes.bool,
  isModal: PropTypes.bool,
  modalVisible: PropTypes.bool.isRequired,
  filterWidget: PropTypes.bool,
  filterId: PropTypes.string,
  id: PropTypes.number,
  range: PropTypes.bool,
  onShow: PropTypes.func,
  onHide: PropTypes.func,
  subentity: PropTypes.string,
  subentityId: PropTypes.string,
  tabIndex: PropTypes.number,
  dropdownOpenCallback: PropTypes.func,
  fullScreen: PropTypes.bool,
  widgetType: PropTypes.string,
  fields: PropTypes.array,
  icon: PropTypes.string,
  entity: PropTypes.string,
  data: PropTypes.any,
  closeTableField: PropTypes.func,
  attribute: PropTypes.bool,
  allowShowPassword: PropTypes.bool, // NOTE: looks like this wasn't used
  buttonProcessId: PropTypes.string, // NOTE: looks like this wasn't used
  onBlurWidget: PropTypes.func,
  defaultValue: PropTypes.oneOfType([PropTypes.string, PropTypes.array]),
  noLabel: PropTypes.bool,
  isOpenDatePicker: PropTypes.bool,
  forceHeight: PropTypes.number,
  dataEntry: PropTypes.bool,
  lastFormField: PropTypes.bool,

  //from RawWidget
  isMultiselect: PropTypes.bool,
  widgetField: PropTypes.string,
  widgetProperties: PropTypes.object.isRequired,
  showErrorBorder: PropTypes.bool,
  isFocused: PropTypes.bool,
  charsTyped: PropTypes.number,
  readonly: PropTypes.bool,
  onPatch: PropTypes.func.isRequired,
  onListFocus: PropTypes.func.isRequired,
  onBlurWithParams: PropTypes.func.isRequired,
  onSetWidgetType: PropTypes.func.isRequired,
  onHandleProcess: PropTypes.func.isRequired,
  forwardedRef: PropTypes.any,
};
