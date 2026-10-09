import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import TetherComponent from 'react-tether';
import ReactDOM from 'react-dom';
import classnames from 'classnames';
import { debounce } from 'throttle-debounce';
import counterpart from 'counterpart';
import { LOOKUP_SHOW_MORE_PIXEL_NO } from '../../../constants/Constants';

import {
  autocompleteModalRequest,
  autocompleteRequest,
} from '../../../actions/GenericActions';
import { getViewAttributeTypeahead } from '../../../api';
import { openModal } from '../../../actions/WindowActions';
import SelectionDropdown from '../SelectionDropdown';
import { isBlank } from '../../../utils';
import { getViewFieldTypeahead } from '../../../api/view';

const KEY_None = null;
const KEY_New = 'NEW';
const KEY_AdvancedSearch = 'SEARCH';

const isNoneItem = (keyCaptionItem) => {
  return !keyCaptionItem || keyCaptionItem.key === KEY_None;
};

const computeInputTextFromSelectedItem = (
  keyCaptionItem,
  fallbackTextIfNullOrNone = ''
) => {
  return keyCaptionItem && !isNoneItem(keyCaptionItem)
    ? keyCaptionItem.caption
    : fallbackTextIfNullOrNone;
};

const executeAfterPromise = (promise, afterCallback) => {
  if (promise) {
    promise.then(afterCallback);
  } else {
    afterCallback();
  }
};

/**
 * Play a short beep sound using the Web Audio API.
 * Used for error feedback in fast-entry scenarios where the user doesn't watch the screen.
 */
let beepAudioCtx = null;
const playBeep = () => {
  try {
    if (!beepAudioCtx) {
      beepAudioCtx = new (window.AudioContext || window.webkitAudioContext)();
    }

    // Resume suspended context (Chrome autoplay policy)
    if (beepAudioCtx.state === 'suspended') {
      beepAudioCtx.resume().catch(() => {}); // best-effort
    }

    const gainNode = beepAudioCtx.createGain();
    gainNode.connect(beepAudioCtx.destination);
    gainNode.gain.value = 0.3;

    const oscillator = beepAudioCtx.createOscillator();
    oscillator.type = 'sine';
    oscillator.frequency.setValueAtTime(800, beepAudioCtx.currentTime);
    oscillator.connect(gainNode);

    oscillator.start();
    oscillator.stop(beepAudioCtx.currentTime + 0.08);
  } catch (e) {
    // eslint-disable-next-line no-console
    console.warn('Could not play beep sound:', e);
  }
};

/**
 * Simple lookup field, part of a composed lookup field (see Lookup.js).
 */
export class RawLookup extends Component {
  constructor(props) {
    super(props);

    this.state = {
      query: '',
      list: [],
      isInputEmpty: true,
      selected: null,
      loading: false,
      oldValue: '',
      shouldBeFocused: true,
      isFocused: false,
    };

    const debounceTime = props.item.lookupSearchStartDelayMillis || 100;
    this.minQueryLength = props.item.lookupSearchStringMinLength || 0;

    this.typeaheadRequest = this.typeaheadRequest.bind(this);
    this.autocompleteSearchDebounced = debounce(
      debounceTime,
      this.typeaheadRequest
    );
  }

  componentDidMount() {
    // console.debug('componentDidMount', {
    //   idValue: this.props.idValue,
    //   props: this.props,
    // });

    this.handleValueChanged();

    const { defaultValue, initialFocus } = this.props;
    if (defaultValue) {
      this.inputSearch.value = computeInputTextFromSelectedItem(defaultValue);
    }
    if (initialFocus && !this.inputSearch.value) {
      this.inputSearch.focus();
    }
  }

  componentDidUpdate(prevProps) {
    this.handleValueChanged();

    const {
      autoFocus,
      defaultValue,
      handleInputEmptyStatus,
      filterWidget,
      lookupEmpty,
      localClearing,
      fireDropdownList,
    } = this.props;
    const { shouldBeFocused } = this.state;

    if (localClearing && !defaultValue) {
      this.inputSearch.value = '';
    }

    if (autoFocus && !this.inputSearch.value && shouldBeFocused) {
      this.inputSearch.focus();
      this.setState({ shouldBeFocused: false });
    }

    if (
      defaultValue &&
      (prevProps.defaultValue == null ||
        (prevProps.defaultValue &&
          prevProps.defaultValue.caption !== defaultValue.caption))
    ) {
      handleInputEmptyStatus && handleInputEmptyStatus(false);
    }

    if (filterWidget && lookupEmpty && defaultValue === null) {
      this.inputSearch.value = '';
    }

    if (fireDropdownList && prevProps.fireDropdownList !== fireDropdownList) {
      this.handleInputTextChange('', true);
    }

    this.checkIfComponentOutOfFilter();
  }

  checkIfComponentOutOfFilter = () => {
    if (!this.lookupList) return;
    // eslint-disable-next-line react/no-find-dom-node
    let element = ReactDOM.findDOMNode(this.lookupList);
    const { top } = element.getBoundingClientRect();
    const { filter, isOpen } = this.props;

    if (
      isOpen &&
      filter.visible &&
      (top + 20 > filter.boundingRect.bottom ||
        top - 20 < filter.boundingRect.top)
    ) {
      this.fireOnDropdownListToggle(false);
    }
  };

  handleSelect = (selectedItem, isMouseEvent = false) => {
    this.setState({ selected: null });

    if (selectedItem?.key === KEY_New) {
      this.handleSelect_AddNew();
    } else if (selectedItem?.key === KEY_AdvancedSearch) {
      this.handleSelect_AdvancedSearch();
    } else {
      this.handleSelect_RegularItem(selectedItem, isMouseEvent);
    }
  };

  handleSelect_AddNew = () => {
    const {
      dispatch,
      newRecordWindowId,
      newRecordCaption,
      filterWidget,
      parameterName,
      mainProperty,
    } = this.props;

    this.handleDropdownBlur();

    dispatch(
      openModal({
        title: newRecordCaption,
        windowId: newRecordWindowId,
        modalType: 'window',
        dataId: KEY_New,
        triggerField: filterWidget ? parameterName : mainProperty.field,
      })
    );
  };

  handleDropdownBlur = (isMouseEvent) => {
    this.setState(
      { isFocused: false }, //
      () => this.fireOnDropdownListToggle(false, isMouseEvent)
    );
  };

  handleSelect_AdvancedSearch = () => {
    const {
      dispatch,
      advSearchCaption,
      advSearchWindowId,
      filterWidget,
      parameterName,
      mainProperty,
      windowType,
      dataId,
      item,
    } = this.props;

    this.handleDropdownBlur();

    dispatch(
      openModal({
        title: advSearchCaption,
        windowId: advSearchWindowId,
        modalType: 'window',
        dataId: KEY_AdvancedSearch,
        triggerField: filterWidget ? parameterName : mainProperty.field,
        parentWindowId: windowType,
        parentDocumentId: dataId,
        parentFieldId: item.field,
      })
    );
  };

  handleSelect_RegularItem = (selectedItemParam, isMouseEvent = false) => {
    const {
      onChange,
      handleInputEmptyStatus,
      mainProperty,
      setNextProperty,
      filterWidget,
      updateItems,
    } = this.props;

    const selectedItemNorm = !isNoneItem(selectedItemParam)
      ? selectedItemParam
      : null;

    const fieldName = filterWidget
      ? mainProperty.parameterName
      : mainProperty.field;

    // shouldKeepFocus: controls whether to call this.focus() after onChange completes.
    // - Async onChange (regular documents): onChange returns a Promise; its .then()
    //   callback is a microtask and cannot run before the current synchronous stack
    //   unwinds. The `if (shouldKeepFocus)` check below runs synchronously, so
    //   shouldKeepFocus is still true — preserving the original "always refocus" behavior.
    // - Sync onChange (quick input): onChange returns undefined; executeAfterPromise
    //   runs the callback inline. If setNextProperty reports no next sub-field,
    //   shouldKeepFocus is set to false before `if (shouldKeepFocus)` is checked —
    //   correct: Lookup.focusNextFormField handles focus advance instead.
    // - Enter-key path in quick input: goes through resolveAndSelectOnEnter →
    //   handleAutoSelectAndAdvance → focusNextFieldInForm, bypassing this code path
    //   entirely.
    let shouldKeepFocus = true;

    executeAfterPromise(onChange(fieldName, selectedItemNorm), () => {
      const hasNextSubField = setNextProperty(fieldName);
      if (!hasNextSubField) {
        shouldKeepFocus = false;
      }
    });

    // see FiltersItem.updateItems
    updateItems &&
      updateItems({
        widgetField: fieldName,
        value: selectedItemNorm,
      });

    this.inputSearch.value = computeInputTextFromSelectedItem(selectedItemNorm);
    this.setState({ inputTextOnFocus: this.inputSearch.value });

    handleInputEmptyStatus && handleInputEmptyStatus(false);

    if (shouldKeepFocus) {
      this.focus();
    }

    this.handleDropdownBlur(isMouseEvent);
  };

  handleInputTextClick = () => {
    const { mandatory } = this.props;

    if (this.state.isFocused) {
      this.handleDropdownBlur(true);
    } else {
      this.setState(
        { isFocused: true }, //
        () => {
          if (!mandatory) {
            this.fireOnDropdownListToggle(true);
          }
        }
      );
    }
  };

  handleInputTextFocus = () => {
    this.setState({ inputTextOnFocus: this.inputSearch.value });
  };

  handleInputTextBlur = () => {
    if (!this.inputSearch) {
      return;
    }

    const { defaultValue } = this.props;
    const { inputTextOnFocus } = this.state;
    const inputTextNow = this.inputSearch.value;

    // console.log('handleInputTextBlur', {
    //   idValue: this.props.idValue,
    //   inputTextNow,
    //   inputTextOnFocus,
    //   defaultValue,
    //   props: this.props,
    //   state: this.state,
    // });

    if (inputTextOnFocus !== inputTextNow) {
      if (isBlank(inputTextNow) && !isBlank(inputTextOnFocus) && defaultValue) {
        //console.log('handleInputTextBlur - SET TO NULL');
        this.handleSelect(null);
      } else {
        this.inputSearch.value = computeInputTextFromSelectedItem(
          defaultValue,
          inputTextOnFocus
        );
        //console.log(`handleInputTextBlur - RESTORED value to "${this.inputSearch.value}"`);
      }
    }
  };

  fireOnDropdownListToggle = (isDropdownListOpen, isMouseEvent = false) => {
    const { item } = this.props;
    const { onDropdownListToggle } = this.props;

    onDropdownListToggle &&
      onDropdownListToggle(isDropdownListOpen, item.field, isMouseEvent);
  };

  handleInputTextKeyDown = (e) => {
    const { isOpen, subentity } = this.props;

    // ArrowDown on a closed dropdown: open it (same behaviour as clicking the field)
    if (e.key === 'ArrowDown') {
      if (!isOpen) {
        e.preventDefault();
        e.stopPropagation();
        this.handleInputTextChange();
      }
    }

    // Quick input: handle Enter for immediate product resolution
    if (e.key === 'Enter' && subentity === 'quickInput') {
      e.preventDefault();
      e.stopPropagation();

      this.resolveAndSelectOnEnter();
    }
  };

  /**
   * @method resolveAndSelectOnEnter
   * @summary Quick input: when Enter is pressed, immediately resolve the lookup value
   * and auto-select the first match. If no match found, play a beep sound.
   * After selection, advance focus to the next field in the quick input form.
   */
  resolveAndSelectOnEnter = () => {
    const { list, loading } = this.state;

    // If typeahead results are already loaded, use them
    if (!loading && list.length > 0) {
      const regularItems = list.filter(
        (item) =>
          item.key !== KEY_New &&
          item.key !== KEY_AdvancedSearch &&
          !isNoneItem(item)
      );
      this.resolveItems(regularItems);
      return;
    }

    const query = this.inputSearch.value;
    if (!query || !query.trim()) {
      return;
    }

    // Fire an immediate typeahead request (bypass debounce)
    this.buildTypeaheadRequestForQuery(query)
      .then((response) => {
        const values = response.data.values || [];
        this.resolveItems(values);
      })
      .catch(() => {
        playBeep();
      });
  };

  /**
   * @method resolveItems
   * @summary Given the typeahead results, auto-select if exactly one match,
   * beep if no match, or beep and keep dropdown open if multiple matches.
   */
  resolveItems = (items) => {
    if (items.length === 1) {
      this.handleAutoSelectAndAdvance(items[0]);
    } else if (items.length > 1) {
      // Select the highlighted item (arrow-key navigated) or the first one
      const selected = this.state.selected || items[0];
      this.handleAutoSelectAndAdvance(selected);
    } else {
      // No match
      playBeep();
    }
  };

  /**
   * @method _buildTypeaheadPromise
   * @summary Core routing for typeahead requests: selects the correct API based on
   * entity type, attribute flag, and filterWidget context. Returns the axios promise.
   * Shared by buildTypeaheadRequestForQuery (Enter-key path) and typeaheadRequest
   * (debounced path) so the routing logic is not duplicated.
   */
  _buildTypeaheadPromise = (query) => {
    const {
      windowType,
      dataId,
      filterWidget,
      attribute,
      parameterName,
      tabId,
      rowId,
      entity,
      subentity,
      subentityId,
      viewId,
      mainProperty,
      typeaheadSupplier,
    } = this.props;

    const params = {
      entity,
      docType: windowType,
      docId: filterWidget ? viewId : dataId,
      propertyName: filterWidget ? parameterName : mainProperty.field,
      query: query || ' ',
      rowId,
      tabId,
    };

    if (typeaheadSupplier) {
      return typeaheadSupplier({ ...params, subentity, subentityId });
    } else if (entity === 'documentView' && attribute) {
      return getViewAttributeTypeahead(
        windowType,
        viewId,
        dataId,
        mainProperty.field,
        params.query
      );
    } else if (entity === 'documentView' && !attribute) {
      return getViewFieldTypeahead({
        windowId: windowType,
        viewId,
        rowId,
        fieldName: mainProperty.field,
        query: params.query,
      });
    } else if (viewId && !filterWidget) {
      return autocompleteModalRequest({
        ...params,
        entity: 'documentView',
        viewId,
      });
    } else {
      return autocompleteRequest({ ...params, subentity, subentityId });
    }
  };

  /**
   * @method buildTypeaheadRequestForQuery
   * @summary Build and fire a typeahead request for the given query string.
   * Returns the axios promise. This is a non-debounced version of typeaheadRequest.
   */
  buildTypeaheadRequestForQuery = (query) => this._buildTypeaheadPromise(query);

  /**
   * @method handleAutoSelectAndAdvance
   * @summary Select the given item and advance focus to the next field in the quick input form.
   * Unlike handleSelect_RegularItem, this does NOT refocus the current lookup input.
   */
  handleAutoSelectAndAdvance = (selectedItem) => {
    const { onChange, handleInputEmptyStatus, mainProperty, filterWidget } =
      this.props;

    const fieldName = filterWidget
      ? mainProperty.parameterName
      : mainProperty.field;

    this.inputSearch.value = computeInputTextFromSelectedItem(selectedItem);
    this.setState({ inputTextOnFocus: this.inputSearch.value });

    handleInputEmptyStatus?.(false);

    this.handleDropdownBlur();

    // Call onChange (PATCH) and advance focus AFTER the server responds
    // and React re-renders. We intentionally skip setNextProperty here
    // because it manages focus within composed lookups (e.g. BPartner →
    // Location → Contact) and would call onBlurWidget, stealing focus.
    executeAfterPromise(onChange(fieldName, selectedItem), () =>
      this.focusNextFieldInForm()
    );
  };

  /**
   * @method focusNextFieldInForm
   * @summary Find the next focusable input in the parent form and focus it.
   * Used after auto-selecting a product in quick input to advance to the quantity field.
   * Retries up to 10 times (every 100ms) because the PATCH response triggers a React
   * re-render that may make the next field focusable asynchronously.
   */
  focusNextFieldInForm = (retriesLeft = 10) => {
    if (!this.inputSearch) {
      return;
    }

    const form = this.inputSearch.closest('form');
    if (!form) {
      // eslint-disable-next-line no-console
      console.warn(
        'RawLookup.focusNextFieldInForm: no parent <form> found. Focus advance requires the quick input to be wrapped in a <form> element (see TableQuickInput).'
      );
      return;
    }

    // Include disabled inputs — the next field (e.g. Qty) may be temporarily disabled
    // during a callout/re-render after product selection. Excluding it would skip straight
    // to the field after it. We detect that case and retry below.
    const inputs = Array.from(
      form.querySelectorAll('input:not([type="hidden"]):not([readonly])')
    );
    const idx = inputs.indexOf(this.inputSearch);
    if (idx < 0) {
      // eslint-disable-next-line no-console
      console.warn(
        'RawLookup.focusNextFieldInForm: current input not found in form input list. This may indicate a DOM structure issue.'
      );
      return; // no point retrying — the input won't appear in the list on next attempt
    }
    if (idx < inputs.length - 1) {
      const nextInput = inputs[idx + 1];
      if (nextInput.disabled) {
        // Next field not yet enabled (callout pending) — retry
        if (retriesLeft > 0) {
          setTimeout(() => this.focusNextFieldInForm(retriesLeft - 1), 100);
        }
        return;
      }
      nextInput.focus();

      if (document.activeElement === nextInput) {
        return;
      }
    }

    // Retry: the next field may not be focusable yet (React re-render pending)
    if (retriesLeft > 0) {
      setTimeout(() => this.focusNextFieldInForm(retriesLeft - 1), 100);
    }
  };

  typeaheadRequest = () => {
    const inputValue = this.inputSearch.value;
    this.typeaheadQuery = inputValue;

    this._buildTypeaheadPromise(inputValue).then((response) => {
      if (this.typeaheadQuery && this.typeaheadQuery === inputValue) {
        this.populateTypeaheadData(response.data);
      }
    });
  };

  populateTypeaheadData = (responseData) => {
    const { isModal, mandatory } = this.props;

    let values = responseData.values || [];
    const isAlwaysDisplayNewBPartner =
      !!responseData.isAlwaysDisplayNewBPartner;
    const hasMoreResults = !!responseData.hasMoreResults;
    const newState = { loading: false };

    const optionNew = this.getNewRecordKeyCaptionIfAvailable();
    let list;
    if (values.length === 0 && !isModal) {
      list = [];
      optionNew && list.push(optionNew);

      newState.forceEmpty = true;
      newState.selected = optionNew;
    } else {
      list = values;
      isAlwaysDisplayNewBPartner && optionNew && list.unshift(optionNew);

      newState.forceEmpty = false;
      newState.selected = values[0];
    }

    const optionAdvSearch = this.getAdvancedSearchKeyCaptionIfAvailable();
    if (optionAdvSearch) {
      list.unshift(optionAdvSearch);
    }

    if (!mandatory) {
      list.push(this.getPlaceholderKeyCaption());
    }

    newState.list = [...list];
    newState.hasMoreResults = hasMoreResults;

    this.setState({ ...newState });
  };

  handleInputTextChange = (handleChangeOnFocus, allowEmpty) => {
    const { handleInputEmptyStatus, enableAutofocus, isOpen } = this.props;

    enableAutofocus();

    if (this.props.localClearing) {
      this.props.resetLocalClearing();
    }

    const inputValue = this.inputSearch.value;

    if (inputValue || allowEmpty) {
      !allowEmpty && handleInputEmptyStatus && handleInputEmptyStatus(false);

      if (!isOpen) {
        this.fireOnDropdownListToggle(true);
      }

      this.setState(
        { isInputEmpty: false, loading: true, query: inputValue },
        () => {
          const query = this.state.query;
          if (query.length >= this.minQueryLength) {
            this.autocompleteSearchDebounced();
          }
        }
      );
    } else {
      this.setState({ isInputEmpty: true, query: inputValue, list: [] });

      handleInputEmptyStatus && handleInputEmptyStatus(true);
    }
  };

  // TODO: improve code quality
  // This method is called on componentDidMount and componentDidUpdate
  handleValueChanged = () => {
    if (!this.inputSearch) {
      return;
    }

    const { defaultValue, filterWidget, mandatory } = this.props;
    const { oldValue, isInputEmpty } = this.state;

    //
    // We have a current value (aka defaultValue prop)
    // and the widget it's not in a filtering panel
    if (!filterWidget && !!defaultValue) {
      const list = [defaultValue];
      if (!mandatory) {
        list.push(this.getPlaceholderKeyCaption());
      }

      const inputValue = computeInputTextFromSelectedItem(defaultValue);
      if (inputValue !== oldValue) {
        this.inputSearch.value = inputValue;
        this.setState({ oldValue: inputValue, isInputEmpty: false, list });
      } else if (isInputEmpty) {
        this.setState({ isInputEmpty: false, list });
      }
    }
    //
    // We don't have a current value (aka defaultValue prop)
    // but there was a value in the text input when we checked last time (aka oldValue state)
    // => clear the text field
    else if (oldValue && !defaultValue) {
      this.inputSearch.value = computeInputTextFromSelectedItem(null);
      this.setState({ oldValue: this.inputSearch.value, isInputEmpty: true });
    }
  };

  getPlaceholderKeyCaption = () => {
    const { mainProperty, placeholder } = this.props;
    const caption = mainProperty?.clearValueText || placeholder || 'none';
    return { key: KEY_None, caption };
  };

  getNewRecordKeyCaptionIfAvailable = () => {
    const { newRecordCaption } = this.props;
    return newRecordCaption != null
      ? { key: KEY_New, caption: newRecordCaption }
      : null;
  };

  getAdvancedSearchKeyCaptionIfAvailable = () => {
    const { advSearchWindowId, advSearchCaption } = this.props;
    return advSearchWindowId
      ? { key: KEY_AdvancedSearch, caption: advSearchCaption }
      : null;
  };

  handleTemporarySelection = (selected) => {
    //console.log('handleTemporarySelection', { selected });
    this.setState({ selected });
  };

  /**
   * @method focus
   * @summary this is a method called from a top level component to focus the widget field
   */
  focus = () => {
    this.inputSearch && this.inputSearch.focus();
  };

  render() {
    const { align, readonly, disabled, tabIndex, isOpen, idValue } = this.props;
    const {
      isInputEmpty,
      list,
      loading,
      selected,
      forceEmpty,
      isFocused,
      query,
      hasMoreResults,
    } = this.state;

    const showDropdown = query.length >= this.minQueryLength;

    const adaptiveWidth = this.props.forcedWidth
      ? this.props.forcedWidth
      : this.wrapper && this.wrapper.offsetWidth;
    const adaptiveHeight =
      showDropdown && isOpen && !isInputEmpty && this.props.forceHeight
        ? this.props.forceHeight - this.wrapper.offsetHeight
        : undefined;

    return (
      <TetherComponent
        attachment="top left"
        targetAttachment="bottom left"
        constraints={[
          {
            to: 'scrollParent',
          },
          {
            to: 'window',
            pin: ['bottom'],
          },
        ]}
        renderTarget={(ref) => (
          <div id={idValue || ''} ref={ref} className="raw-lookup-wrapper">
            <div
              className={classnames(
                'lookup-widget-wrapper lookup-widget-wrapper-bcg',
                {
                  'raw-lookup-disabled': disabled,
                  'input-disabled': readonly,
                  focused: isFocused,
                }
              )}
              ref={(ref) => (this.wrapper = ref)}
            >
              <div className={'input-dropdown input-block'}>
                <div
                  className={'input-editable' + (align ? ' text-' + align : '')}
                >
                  <input
                    ref={(c) => (this.inputSearch = c)}
                    type="text"
                    className="input-field js-input-field font-weight-semibold"
                    autoComplete="new-password"
                    readOnly={readonly}
                    disabled={readonly && !disabled}
                    tabIndex={tabIndex}
                    placeholder={this.props.item.emptyText}
                    onChange={this.handleInputTextChange}
                    onKeyDown={this.handleInputTextKeyDown}
                    onClick={this.handleInputTextClick}
                    onFocus={this.handleInputTextFocus}
                    onBlur={this.handleInputTextBlur}
                  />{' '}
                </div>
              </div>
            </div>
            {showDropdown && isOpen && !isInputEmpty && (
              <div>
                <SelectionDropdown
                  loading={loading}
                  options={list}
                  empty={`${counterpart.translate(
                    'widget.lookup.hasNoResults'
                  )}`}
                  forceEmpty={forceEmpty}
                  selected={selected}
                  width={
                    this.props.forcedWidth
                      ? this.props.forcedWidth
                      : this.wrapper && this.wrapper.offsetWidth
                  }
                  height={
                    this.props.forceHeight
                      ? this.props.forceHeight - this.wrapper.offsetHeight
                      : undefined
                  }
                  onChange={this.handleTemporarySelection}
                  onSelect={this.handleSelect}
                  onCancel={this.handleDropdownBlur}
                />
                {hasMoreResults && (
                  <div
                    className="input-dropdown-hasmore"
                    style={{
                      width: adaptiveWidth,
                      left:
                        parseInt(adaptiveWidth) > LOOKUP_SHOW_MORE_PIXEL_NO &&
                        !(
                          parseInt(adaptiveWidth) > 900 &&
                          this.inputSearch.value
                        ) &&
                        (this.inputSearch || !this.inputSearch.value)
                          ? '-2px'
                          : '0px',
                      top: parseInt(adaptiveHeight) + 28 + 'px',
                    }}
                  >
                    {` ${counterpart.translate(
                      'widget.lookup.hasMoreResults'
                    )}`}
                  </div>
                )}
              </div>
            )}
          </div>
        )}
      />
    );
  }
}

const mapStateToProps = (state) => ({
  filter: state.windowHandler.filter,
});

RawLookup.propTypes = {
  item: PropTypes.object,
  defaultValue: PropTypes.any,
  initialFocus: PropTypes.bool,
  autoFocus: PropTypes.bool,
  handleInputEmptyStatus: PropTypes.any,
  isOpen: PropTypes.bool,
  selected: PropTypes.object,
  forcedWidth: PropTypes.number,
  forceHeight: PropTypes.number,
  mainProperty: PropTypes.object,
  filterWidget: PropTypes.bool,
  attribute: PropTypes.bool, // is view attribute?
  lookupEmpty: PropTypes.bool,
  localClearing: PropTypes.any,
  fireDropdownList: PropTypes.bool,
  subentity: PropTypes.any,
  newRecordWindowId: PropTypes.any,
  newRecordCaption: PropTypes.any,
  parameterName: PropTypes.string,
  mandatory: PropTypes.bool,
  windowType: PropTypes.string,
  dataId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  tabId: PropTypes.string,
  rowId: PropTypes.string,
  entity: PropTypes.any,
  subentityId: PropTypes.string,
  viewId: PropTypes.string,
  isModal: PropTypes.bool,
  placeholder: PropTypes.string,
  align: PropTypes.string,
  readonly: PropTypes.bool,
  disabled: PropTypes.bool,
  tabIndex: PropTypes.number,
  idValue: PropTypes.string,
  advSearchCaption: PropTypes.string,
  advSearchWindowId: PropTypes.string,

  //
  // Callbacks and other functions:
  dispatch: PropTypes.func.isRequired,
  setNextProperty: PropTypes.func,
  onDropdownListToggle: PropTypes.func,
  onChange: PropTypes.func,
  enableAutofocus: PropTypes.func,
  updateItems: PropTypes.func,
  resetLocalClearing: PropTypes.func,
  typeaheadSupplier: PropTypes.func,

  //
  // mapStateToProps:
  filter: PropTypes.shape({
    visible: PropTypes.bool,
    boundingRect: PropTypes.object,
  }),
};

export default connect(mapStateToProps, null, null, { forwardRef: true })(
  RawLookup
);
