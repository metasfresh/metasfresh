import PropTypes from 'prop-types';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import { Map } from 'immutable';

import RawWidget from '../RawWidget';

class AttributesDropdown extends Component {
  constructor(props) {
    super(props);

    this.state = {
      patchCallbacks: Map(),
    };
  }

  handleClickOutside = () => {
    const { onClickOutside } = this.props;

    // we need to wait for fetching all of PATCH fields on blur
    // to complete on updated instance
    // TODO: Figure out if it would be possible to rewrite this
    // using Promise.all somehow
    const requestsInterval = window.setInterval(() => {
      const intervalsLeft = this.state.patchCallbacks.size;

      if (intervalsLeft === 0) {
        window.clearInterval(requestsInterval);
        onClickOutside();
      }
    }, 10);
  };

  handlePatch = (prop, value, attrId) => {
    const { onPatch } = this.props;
    const { patchCallbacks } = this.state;
    const updatedCallbacks = patchCallbacks.set(attrId, true);

    this.setState(
      {
        patchCallbacks: updatedCallbacks,
      },
      () => {
        onPatch(prop, value, attrId, () => {
          const resolvedCallbacks = this.state.patchCallbacks.delete(attrId);

          this.setState({
            patchCallbacks: resolvedCallbacks,
          });
        });
      }
    );
  };

  renderFields = () => {
    const {
      tabIndex,
      layout,
      data,
      attributeType,
      onChange,
      attrId,
      disableOnClickOutside,
      enableOnClickOutside,
      isModal,
    } = this.props;

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
            onPatch={(prop, value) => this.handlePatch(prop, value, attrId)}
            onChange={onChange}
            disableOnClickOutside={disableOnClickOutside}
            enableOnClickOutside={enableOnClickOutside}
            tabIndex={tabIndex}
            isModal={isModal}
          />
        );
      });
    }
  };

  render() {
    return (
      <div className="attributes-dropdown panel-shadowed panel-primary panel-bordered panel-spaced">
        {this.renderFields()}
      </div>
    );
  }
}

AttributesDropdown.propTypes = {
  tabIndex: PropTypes.number,
  isModal: PropTypes.bool,
  data: PropTypes.object.isRequired,
  attributeType: PropTypes.string.isRequired,
  onChange: PropTypes.func.isRequired,
  attrId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  layout: PropTypes.array,
  onClickOutside: PropTypes.func,
  onPatch: PropTypes.func.isRequired,
  disableOnClickOutside: PropTypes.func.isRequired,
  enableOnClickOutside: PropTypes.func.isRequired,
};

export default onClickOutside(AttributesDropdown);
