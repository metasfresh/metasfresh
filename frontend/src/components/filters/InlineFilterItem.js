/**
 * Filter element displayed inline for frequent filters
 **/
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import { allowShortcut, disableShortcut } from '../../actions/WindowActions';

import RawWidget from '../widget/RawWidget';
import { parseDateToReadable } from './Filters';

class InlineFilterItem extends Component {
  constructor(props) {
    super(props);

    this.state = {
      filter: props.parentFilter,
    };
  }

  UNSAFE_componentWillMount() {
    this.init();
  }

  UNSAFE_componentWillReceiveProps(props) {
    const { active } = this.props;

    if (JSON.stringify(active) !== JSON.stringify(props.active)) {
      this.init();
    }
  }

  init = () => {
    const { active, parentFilter } = this.props;
    const { filter } = this.state;
    let activeFilter;

    if (active) {
      activeFilter = active.find(
        (item) => item.filterId === parentFilter.filterId
      );
    }

    if (
      filter.type &&
      activeFilter &&
      activeFilter.parameters &&
      activeFilter.filterId === filter.filterId
    ) {
      activeFilter.parameters.map((item) => {
        this.mergeData(
          item.parameterName,
          item.value != null ? item.value : '',
          item.valueTo != null ? item.valueTo : ''
        );
      });
    } else if (filter.parameters) {
      filter.parameters.map((item) => {
        this.mergeData(item.parameterName, '');
      });
    }
  };

  setValue = (property, value, id, valueTo) => {
    //TODO: LOOKUPS GENERATE DIFFERENT TYPE OF PROPERTY parameters
    // IT HAS TO BE UNIFIED
    //
    // OVERWORKED WORKAROUND
    if (Array.isArray(property)) {
      property.map((item) => {
        this.mergeData(item.parameterName, value, valueTo);
      });
    } else {
      this.mergeData(property, value, valueTo);
    }
  };

  mergeData = (property, value, valueTo) => {
    this.setState((prevState) => ({
      filter: Object.assign({}, prevState.filter, {
        parameters: prevState.filter.parameters.map((param) => {
          if (param.parameterName === property) {
            return Object.assign({}, param, {
              value: parseDateToReadable(param.widgetType, value),
              valueTo: parseDateToReadable(param.widgetType, valueTo),
            });
          } else {
            return param;
          }
        }),
      }),
    }));
  };

  handleApply = () => {
    const { applyFilters } = this.props;
    const { filter } = this.state;

    if (filter && !filter.parameters[0].value) {
      return this.handleClear();
    }

    applyFilters(filter);
  };

  handleClear = () => {
    const { clearFilters } = this.props;
    const { filter } = this.state;

    clearFilters(filter);
  };

  render() {
    const {
      data,
      id,
      windowType,
      onShow,
      onHide,
      viewId,
      modalVisible,
      timeZone,
      allowShortcut,
      disableShortcut,
    } = this.props;
    const { filter } = this.state;

    return (
      <RawWidget
        entity="documentView"
        subentity="filter"
        subentityId={filter.filterId}
        handlePatch={this.handleApply}
        handleChange={this.setValue}
        widgetType={data.widgetType}
        fields={[{ ...data, emptyText: data.caption }]}
        type={data.type}
        widgetData={[data]}
        range={data.range}
        caption={data.caption}
        noLabel={true}
        filterWidget={true}
        {...{
          id,
          viewId,
          windowType,
          onShow,
          onHide,
          modalVisible,
          timeZone,
          allowShortcut,
          disableShortcut,
        }}
      />
    );
  }
}

const mapStateToProps = (state) => {
  const { appHandler, windowHandler } = state;

  return {
    modalVisible: windowHandler.modal.visible,
    timeZone: appHandler.me.timeZone,
  };
};

InlineFilterItem.propTypes = {
  active: PropTypes.bool,
  data: PropTypes.object,
  parentFilter: PropTypes.object,
  onShow: PropTypes.func,
  onHide: PropTypes.func,
  viewId: PropTypes.number,
  id: PropTypes.number,
  applyFilters: PropTypes.func,
  clearFilters: PropTypes.func,
  windowType: PropTypes.string,
  allowShortcut: PropTypes.func.isRequired,
  disableShortcut: PropTypes.func.isRequired,
  modalVisible: PropTypes.bool.isRequired,
  timeZone: PropTypes.string.isRequired,
};

export default connect(
  mapStateToProps,
  {
    allowShortcut,
    disableShortcut,
  }
)(InlineFilterItem);
