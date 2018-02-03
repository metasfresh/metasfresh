import PropTypes from "prop-types";
import React, { Component } from "react";
import onClickOutside from "react-onclickoutside";
import { connect } from "react-redux";

import RawWidget from "../RawWidget";

class AttributesDropdown extends Component {
  constructor(props) {
    super(props);

    this.state = {
      clickedOutside: false,
      focused: false
    };
  }

  handleClickOutside = () => {
    const { onClickOutside } = this.props;
    const { focused } = this.state;

    if (focused) {
      return;
    }

    //we need to blur all fields, to patch them before completion
    this.dropdown.focus();

    //we need to wait for fetching all of PATCH fields on blur
    //to complete on updated instance
    if (!focused) {
      return onClickOutside();
    }

    this.setState({
      clickedOutside: true
    });
  };

  handleFocus = () => {
    this.setState({
      focused: true
    });
  };

  handlePatch = (prop, value, attrId) => {
    const { handlePatch, onClickOutside } = this.props;

    handlePatch(prop, value, attrId, () => {
      const { focused, clickedOutside } = this.state;
      if (!focused && clickedOutside) {
        onClickOutside();
      }

      this.setState({
        focused: false
      });
    });
  };

  handleBlur = willPatch => {
    const { clickedOutside } = this.state;
    const { onClickOutside } = this.props;

    if (!willPatch && !clickedOutside) {
      return;
    }

    this.setState(
      {
        focused: false,
        clickedOutside: clickedOutside
      },
      () => {
        if (!willPatch) {
          onClickOutside();
        }
      }
    );
  };

  renderFields = () => {
    const {
      tabIndex,
      layout,
      data,
      attributeType,
      handleChange,
      attrId
    } = this.props;

    if (layout) {
      return layout.map((item, id) => {
        const widgetData = item.fields.map(elem => data[elem.field] || -1);
        return (
          <RawWidget
            entity={attributeType}
            widgetType={item.widgetType}
            fields={item.fields}
            dataId={attrId}
            widgetData={widgetData}
            gridAlign={item.gridAlign}
            key={id}
            type={item.type}
            caption={item.caption}
            handleBlur={this.handleBlur}
            handlePatch={(prop, value) => this.handlePatch(prop, value, attrId)}
            handleFocus={this.handleFocus}
            handleChange={handleChange}
            tabIndex={tabIndex}
          />
        );
      });
    }
  };

  render() {
    return (
      <div
        className="attributes-dropdown panel-shadowed panel-primary panel-bordered panel-spaced"
        ref={c => {
          this.dropdown = c;
          c && c.focus();
        }}
      >
        {this.renderFields()}
      </div>
    );
  }
}

AttributesDropdown.propTypes = {
  dispatch: PropTypes.func.isRequired
};

export default connect(state => ({
  pendingIndicator: state.windowHandler.indicator
}))(onClickOutside(AttributesDropdown));
