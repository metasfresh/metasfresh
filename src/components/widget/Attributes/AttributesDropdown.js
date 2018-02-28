import PropTypes from 'prop-types';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';

import RawWidget from '../RawWidget';

class AttributesDropdown extends Component {
  constructor(props) {
    super(props);

    this.state = {
      clickedOutside: false,
      patchCallbacks: [],
    };
  }

  setInitialState() {
    const { layout } = this.props;
    let patchCallbacks = [];

    if (layout) {
      patchCallbacks = layout.map(() => this.handlePatch);
    }

    this.setState({
      patchCallbacks,
    });
  }

  componentWillMount() {
    this.setInitialState();
  }

  handleClickOutside = () => {
    const { onClickOutside } = this.props;

    this.setState(
      {
        clickedOutside: true,
      },
      () => {
        //we need to wait for fetching all of PATCH fields on blur
        //to complete on updated instance
        Promise.all(this.state.patchCallbacks).then(() => onClickOutside());
      }
    );
  };

  handlePatch = (prop, value, attrId) => {
    const { handlePatch, onClickOutside } = this.props;

    return handlePatch(prop, value, attrId, () => {
      const { focused, clickedOutside } = this.state;
      if (!focused && clickedOutside) {
        onClickOutside();
      }

      return true;
    });
  };

  renderFields = () => {
    const {
      tabIndex,
      layout,
      data,
      attributeType,
      handleChange,
      attrId,
      disableOnClickOutside,
      enableOnClickOutside,
    } = this.props;
    const { patchCallbacks } = this.state;

    if (layout) {
      return layout.map((item, idx) => {
        const widgetData = item.fields.map(elem => data[elem.field] || -1);
        return (
          <RawWidget
            entity={attributeType}
            widgetType={item.widgetType}
            fields={item.fields}
            dataId={attrId}
            widgetData={widgetData}
            gridAlign={item.gridAlign}
            key={idx}
            type={item.type}
            caption={item.caption}
            handlePatch={(prop, value) =>
              patchCallbacks[idx](prop, value, attrId)
            }
            handleFocus={this.handleFocus}
            handleChange={handleChange}
            disableOnClickOutside={disableOnClickOutside}
            enableOnClickOutside={enableOnClickOutside}
            tabIndex={tabIndex}
          />
        );
      });
    }
  };

  render() {
    return (
      <div className="attributes-dropdown panel-shadowed
          panel-primary panel-bordered panel-spaced">
        {this.renderFields()}
      </div>
    );
  }
}

AttributesDropdown.propTypes = {
  tabIndex: PropTypes.number,
  data: PropTypes.object.isRequired,
  attributeType: PropTypes.string.isRequired,
  handleChange: PropTypes.func.isRequired,
  attrId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  layout: PropTypes.array,
  onClickOutside: PropTypes.func,
  handlePatch: PropTypes.func.isRequired,
  disableOnClickOutside: PropTypes.func.isRequired,
  enableOnClickOutside: PropTypes.func.isRequired,
};

export default onClickOutside(AttributesDropdown);
