import PropTypes from "prop-types";
import React, { Component } from "react";
import { connect } from "react-redux";
import TetherComponent from "react-tether";
import ReactDOM from "react-dom";

import { autocompleteRequest } from "../../../actions/GenericActions";
import { getViewAttributeTypeahead } from "../../../actions/ViewAttributesActions";
import {
  openModal,
  allowOutsideClick,
  disableOutsideClick
} from "../../../actions/WindowActions";
import LookupList from "./LookupList";

class RawLookup extends Component {
  constructor(props) {
    super(props);

    this.state = {
      query: "",
      list: [],
      isInputEmpty: true,
      selected: null,
      loading: false,
      oldValue: "",
      shouldBeFocused: true,
      validLocal: true
    };
  }

  componentDidMount() {
    const { selected, defaultValue, initialFocus } = this.props;

    this.handleValueChanged();

    if (selected) {
      this.inputSearch.value = selected.caption;
    } else {
      this.handleBlur(this.clearState);
    }

    if (defaultValue) {
      this.inputSearch.value = defaultValue.caption;
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
      fireClickOutside,
      handleInputEmptyStatus,
      filterWidget,
      lookupEmpty,
      localClearing,
      resetLocalClearing,
      fireDropdownList
    } = this.props;

    const { shouldBeFocused } = this.state;

    if (localClearing && !defaultValue) {
      this.inputSearch.value = "";
      resetLocalClearing();
    }

    if (autoFocus && !this.inputSearch.value && shouldBeFocused) {
      this.inputSearch.focus();

      this.setState({
        shouldBeFocused: false
      });
    }

    defaultValue &&
      prevProps.defaultValue !== defaultValue &&
      handleInputEmptyStatus(false);

    if (fireClickOutside && prevProps.fireClickOutside !== fireClickOutside) {
      if (defaultValue !== null && typeof defaultValue !== "undefined") {
        if (defaultValue.caption !== this.inputSearch.value) {
          this.inputSearch.value = defaultValue.caption || "";
        }
      }
    }

    if (filterWidget && lookupEmpty && defaultValue === null) {
      this.inputSearch.value = defaultValue;
    }

    if (fireDropdownList && prevProps.fireDropdownList !== fireDropdownList) {
      this.handleChange("", true);
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
      this.props.onDropdownListToggle(false);
    }
  };

  clearState = () => {
    this.setState({
      list: [],
      isInputEmpty: true,
      selected: null,
      loading: false,
      query: ""
    });
  };

  navigate = reverse => {
    const { selected, list } = this.state;

    if (list.length === 0) {
      // Case of selecting row for creting new instance
      this.setState({
        selected: "new"
      });
    } else {
      // Case of selecting regular list items
      if (typeof selected === "number") {
        const selectTarget = selected + (reverse ? -1 : 1);

        if (typeof list[selectTarget] !== "undefined") {
          this.setState({
            selected: selectTarget
          });
        }
      } else if (typeof list[0] !== "undefined") {
        this.setState({
          selected: 0
        });
      }
    }
  };

  handleSelect = select => {
    const {
      onChange,
      handleInputEmptyStatus,
      mainProperty,
      setNextProperty,
      filterWidget,
      subentity
    } = this.props;

    let mainProp = mainProperty[0];

    this.setState({
      selected: null
    });

    if (filterWidget) {
      const promise = onChange(mainProp.parameterName, select);

      if (promise) {
        promise.then(() => {
          setNextProperty(mainProp.parameterName);
        });
      } else {
        setNextProperty(mainProp.parameterName);
      }
    } else {
      if (subentity === "quickInput") {
        onChange(mainProperty[0].field, select, () =>
          setNextProperty(mainProp.field)
        );
      } else {
        const promise = onChange(mainProp.field, select);

        if (promise) {
          promise.then(() => {
            setNextProperty(mainProp.field);
          });
        } else {
          setNextProperty(mainProp.field);
        }
      }
    }

    if (select) {
      this.inputSearch.value = select.caption;
    }

    handleInputEmptyStatus(false);

    this.handleBlur();
  };

  handleAddNew = () => {
    const {
      dispatch,
      newRecordWindowId,
      newRecordCaption,
      filterWidget,
      parameterName,
      mainProperty
    } = this.props;

    dispatch(
      openModal(
        newRecordCaption,
        newRecordWindowId,
        "window",
        null,
        null,
        null,
        null,
        null,
        "NEW",
        filterWidget ? parameterName : mainProperty[0].field
      )
    );
  };

  handleBlur = callback => {
    const { dispatch, onHandleBlur } = this.props;

    this.props.onDropdownListToggle(false);
    callback && callback();

    onHandleBlur && onHandleBlur();
    dispatch(allowOutsideClick());
  };

  handleChange = (handleChangeOnFocus, allowEmpty) => {
    const {
      recent,
      windowType,
      dataId,
      filterWidget,
      parameterName,
      tabId,
      rowId,
      entity,
      subentity,
      subentityId,
      viewId,
      mainProperty,
      handleInputEmptyStatus,
      enableAutofocus,
      dispatch
    } = this.props;

    dispatch(disableOutsideClick());

    enableAutofocus();

    if (this.inputSearch.value || allowEmpty) {
      !allowEmpty && handleInputEmptyStatus(false);

      this.setState({
        isInputEmpty: false,
        loading: true,
        query: this.inputSearch.value
      });

      this.props.onDropdownListToggle(true);

      let typeaheadRequest;
      if (entity === "documentView" && !filterWidget) {
        typeaheadRequest = getViewAttributeTypeahead(
          windowType,
          viewId,
          dataId,
          mainProperty[0].field,
          this.inputSearch.value
        );
      } else {
        typeaheadRequest = autocompleteRequest({
          docId: filterWidget ? viewId : dataId,
          docType: windowType,
          entity,
          propertyName: filterWidget ? parameterName : mainProperty[0].field,
          query: this.inputSearch.value,
          rowId,
          subentity,
          subentityId,
          tabId
        });
      }

      typeaheadRequest.then(response => {
        let values = response.data.values || [];

        this.setState({
          list: values,
          loading: false,
          selected: 0,
          validLocal:
            values.length === 0 && handleChangeOnFocus !== true ? false : true
        });
      });
    } else {
      this.setState({
        isInputEmpty: true,
        query: this.inputSearch.value,
        list: recent
      });

      handleInputEmptyStatus(true);
    }
  };

  handleKeyDown = e => {
    const { listenOnKeys, listenOnKeysFalse } = this.props;
    const { selected, list, query } = this.state;

    //need for prevent fire event onKeyDown 'Enter' from TableItem
    listenOnKeys && listenOnKeysFalse && listenOnKeysFalse();

    switch (e.key) {
      case "ArrowDown":
        e.preventDefault();
        this.navigate();
        break;

      case "ArrowUp":
        e.preventDefault();
        this.navigate(true);
        break;

      case "ArrowLeft":
        e.preventDefault();
        this.handleChange();
        break;

      case "Enter":
        e.preventDefault();

        if (selected === "new") {
          this.handleAddNew(query);
        } else if (selected !== null) {
          this.handleSelect(list[selected]);
        }

        break;

      case "Escape":
        e.preventDefault();
        this.handleBlur();
        break;

      case "Tab":
        this.handleBlur();
        break;
    }
  };

  handleValueChanged = () => {
    const { defaultValue, filterWidget } = this.props;
    const { oldValue, isInputEmpty } = this.state;

    if (!filterWidget && !!defaultValue && this.inputSearch) {
      const init = defaultValue;
      const inputValue = init.caption;

      if (inputValue !== oldValue) {
        this.inputSearch.value = inputValue;

        this.setState({
          oldValue: inputValue,
          isInputEmpty: false,
          validLocal: true,
          list: [init]
        });
      } else if (isInputEmpty) {
        this.setState({
          isInputEmpty: false,
          list: [init]
        });
      }
    } else if (oldValue && !defaultValue && this.inputSearch) {
      const inputEmptyValue = defaultValue;

      if (inputEmptyValue !== oldValue) {
        this.inputSearch.value = inputEmptyValue;

        this.setState({
          oldValue: inputEmptyValue,
          isInputEmpty: true
        });
      }
    }
  };

  render() {
    const {
      isModal,
      align,
      newRecordCaption,
      placeholder,
      readonly,
      disabled,
      tabIndex,
      isOpen
    } = this.props;

    const { isInputEmpty, list, query, loading, selected } = this.state;
    const SEARCH_ICON_WIDTH = 38;
    return (
      <div
        className={
          "raw-lookup-wrapper raw-lookup-wrapper-bcg" +
          (disabled ? " raw-lookup-disabled" : "") +
          (readonly ? " input-disabled" : "")
        }
        onKeyDown={this.handleKeyDown}
        ref={c => (this.rawLookup = c)}
      >
        <TetherComponent
          attachment="top left"
          targetAttachment="bottom left"
          constraints={[
            {
              to: "scrollParent"
            },
            {
              to: "window",
              pin: ["bottom"]
            }
          ]}
        >
          <div className={"input-dropdown input-block"}>
            <div
              className={"input-editable" + (align ? " text-xs-" + align : "")}
            >
              <input
                ref={c => (this.inputSearch = c)}
                type="text"
                className="input-field js-input-field font-weight-semibold"
                readOnly={readonly}
                disabled={readonly && !disabled}
                tabIndex={tabIndex}
                placeholder={placeholder}
                onChange={this.handleChange}
              />
            </div>
          </div>

          {isOpen &&
            !isInputEmpty && (
              <LookupList
                ref={c => (this.lookupList = c)}
                loading={loading}
                selected={selected}
                list={list}
                query={query}
                isInputEmpty={isInputEmpty}
                disableOnClickOutside={!isOpen}
                creatingNewDisabled={isModal}
                newRecordCaption={newRecordCaption}
                handleSelect={this.handleSelect}
                handleAddNew={this.handleAddNew}
                onClickOutside={this.handleBlur}
                style={{
                  width: `${this.rawLookup.offsetWidth + SEARCH_ICON_WIDTH}px`
                }}
              />
            )}
        </TetherComponent>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  filter: state.windowHandler.filter
});

RawLookup.propTypes = {
  dispatch: PropTypes.func.isRequired
};

export default connect(mapStateToProps)(RawLookup);
