import PropTypes from 'prop-types';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import { Map } from 'immutable';
import memoize from 'memoize-one';

import RawWidget from '../RawWidget';

class AttributesDropdown extends Component {
  constructor(props) {
    super(props);

    this.state = {
      patchCallbacks: Map(),
      focusedField: null,
    };
  }

  componentDidMount() {
    if (this.form) {
      this.focusField(0);
    }
  }

  focusField = idx => {
    const { focusedField } = this.state;
    const inputs = this.form.getElementsByClassName('js-input-field');

    if (inputs.length) {
      if (focusedField !== null) {
        this.blurField(focusedField);
      }

      this.setState(
        {
          focusedField: idx,
        },
        () => {
          inputs[idx] && inputs[idx].focus();
        }
      );
    }
  };

  blurField = idx => {
    const inputs = this.form.getElementsByClassName('js-input-field');

    inputs[idx] && inputs[idx].blur();
  };

  // Re-run fields length calculation whenever layout changes
  getFieldsLength = memoize((data, layout) => {
    let fieldsLength = 0;

    layout.forEach(item => {
      const widgetData = item.fields.map(elem => data[elem.field] || -1);

      if (widgetData && widgetData.length && widgetData[0].displayed) {
        fieldsLength += 1;
      }
    });

    return fieldsLength;
  });

  /*
   * To have a better control of the flow, we set the tabIndex for all inputs to -1
   * and focus/blur them programmatically. If we reach the end of the fields, modal should
   * be closed on next tab.
   */
  handleKeyDown = e => {
    const { focusedField } = this.state;
    const { onClickOutside, layout, data } = this.props;
    const fieldsLength = this.getFieldsLength(data, layout);

    if (e.key === 'Tab') {
      e.preventDefault();
      e.stopPropagation();

      if (focusedField + 1 < fieldsLength) {
        this.focusField(focusedField + 1);
      } else {
        onClickOutside();
      }
    }
  };

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

  handlePatch = (prop, value, id) => {
    const { handlePatch, attrId } = this.props;
    const { patchCallbacks } = this.state;
    const updatedCallbacks = patchCallbacks.set(id, true);

    return new Promise(res => {
      this.setState(
        {
          patchCallbacks: updatedCallbacks,
        },
        () => {
          return handlePatch(prop, value, attrId, () => {
            const resolvedCallbacks = this.state.patchCallbacks.delete(id);

            res();

            this.setState({
              patchCallbacks: resolvedCallbacks,
            });
          });
        }
      );
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
            handlePatch={(prop, value) => this.handlePatch(prop, value, idx)}
            handleChange={handleChange}
            disableOnClickOutside={disableOnClickOutside}
            enableOnClickOutside={enableOnClickOutside}
            tabIndex={tabIndex}
            isModal={isModal}
            attributeWidget={true}
          />
        );
      });
    }
  };

  render() {
    return (
      <div
        className="attributes-dropdown panel-shadowed panel-primary panel-bordered panel-spaced"
        ref={c => (this.form = c)}
        onKeyDown={this.handleKeyDown}
      >
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
  handleChange: PropTypes.func.isRequired,
  attrId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  layout: PropTypes.array,
  onClickOutside: PropTypes.func,
  handlePatch: PropTypes.func.isRequired,
  disableOnClickOutside: PropTypes.func.isRequired,
  enableOnClickOutside: PropTypes.func.isRequired,
};

export default onClickOutside(AttributesDropdown);
