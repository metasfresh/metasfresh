/**
 * Filter element displayed inline for frequent filters
 **/
// import counterpart from 'counterpart';
import React, { Component } from 'react';
// import { connect } from 'react-redux';
// import PropTypes from 'prop-types';
// import TetherComponent from 'react-tether';

// import keymap from '../../shortcuts/keymap';
// import OverlayField from '../app/OverlayField';
// import ModalContextShortcuts from '../keyshortcuts/ModalContextShortcuts';
// import Tooltips from '../tooltips/Tooltips.js';
import RawWidget from '../widget/RawWidget';
import {
  // openFilterBox,
  // closeFilterBox,
  parseDateWithCurrenTimezone,
} from '../../actions/WindowActions';
import { DATE_FIELDS } from '../../constants/Constants';

class InlineFilterItem extends Component {
  constructor(props) {
    super(props);

    this.state = {
      localFilter: props.data,
      // isTooltipShow: false,
    };
  }

  componentWillMount() {
    this.init();
  }

  componentWillReceiveProps(props) {
    const { active } = this.props;

    if (JSON.stringify(active) !== JSON.stringify(props.active)) {
      this.init();
    }
  }

  // componentDidMount() {
  //   if (this.widgetsContainer) {
  //     this.widgetsContainer.addEventListener('scroll', this.handleScroll);
  //   }
  // }

  // componentWillUnmount() {
  //   const { dispatch } = this.props;

  //   if (this.widgetsContainer) {
  //     this.widgetsContainer.removeEventListener('scroll', this.handleScroll);
  //   }

  //   dispatch(closeFilterBox());
  // }

  init = () => {
    const { active, data, filter } = this.props;
    const { localFilter } = this.state;
    let activeFilter;

    // if (active) {
    //   activeFilter = active.find(item => filter.filterId === data.filterId);
    // }

    // if (
    //   localFilter.type &&
    //   activeFilter &&
    //   activeFilter.parameters &&
    //   activeFilter.filterId === filter.filterId
    // ) {
    //   activeFilter.parameters.map(item => {
    //     this.mergeData(
    //       item.parameterName,
    //       item.value != null ? item.value : '',
    //       item.valueTo != null ? item.valueTo : ''
    //     );
    //   });
    // }
    // else if (localFilter.type) {
    //   filter.parameters.map(item => {
    //     this.mergeData(item.parameterName, '');
    //   });
    // }
  };

  setValue = (property, value, id, valueTo) => {
    //TODO: LOOKUPS GENERATE DIFFERENT TYPE OF PROPERTY parameters
    // IT HAS TO BE UNIFIED
    //
    // OVERWORKED WORKAROUND
    if (Array.isArray(property)) {
      property.map(item => {
        this.mergeData(item.parameterName, value, valueTo);
      });
    } else {
      this.mergeData(property, value, valueTo);
    }
  };

  // TODO: Fix the timezone issue
  // Right now, it's ignoring the returning timezone from back-end
  // and use the browser's default timezone
  parseDateToReadable = (widgetType, value) => {
    if (DATE_FIELDS.indexOf(widgetType) > -1) {
      return parseDateWithCurrenTimezone(value);
    }
    return value;
  };

  mergeData = (property, value, valueTo) => {
    this.setState(prevState => ({
      filter: Object.assign({}, prevState.filter, {
        parameters: prevState.filter.parameters.map(param => {
          if (param.parameterName === property) {
            return Object.assign({}, param, {
              value: this.parseDateToReadable(param.widgetType, value),
              valueTo: this.parseDateToReadable(param.widgetType, valueTo),
            });
          } else {
            return param;
          }
        }),
      }),
    }));
  };

  // handleScroll = () => {
  //   const { dispatch } = this.props;
  //   const {
  //     top,
  //     left,
  //     bottom,
  //     right,
  //   } = this.widgetsContainer.getBoundingClientRect();
  //   dispatch(openFilterBox({ top, left, bottom, right }));
  // };

  handleApply = () => {
    const { applyFilters, closeFilterMenu } = this.props;
    const { filter } = this.state;

    if (
      filter &&
      filter.parametersLayoutType === 'singleOverlayField' &&
      !filter.parameters[0].value
    ) {
      return this.handleClear();
    }

    applyFilters(filter, () => {
      closeFilterMenu();
    });
  };

  handleClear = () => {
    const { clearFilters } = this.props;
    const { filter } = this.state;

    clearFilters(filter);
    // closeFilterMenu();
    // returnBackToDropdown && returnBackToDropdown();
  };

  // toggleTooltip = visible => {
  //   this.setState({
  //     isTooltipShow: visible,
  //   });
  // };

  render() {
    const {
      data,
      id,
      filter,
      // filterId,
      // notValidFields,
      // isActive,
      windowType,
      onShow,
      onHide,
      viewId,
      // outsideClick,
      // captionValue,
    } = this.props;

    const { localFilter } = this.state;

    return (
      <RawWidget
        entity="documentView"
        subentity="filter"
        subentityId={filter.filterId}
        handlePatch={this.setValue}
        handleChange={this.setValue}
        widgetType={data.widgetType}
        fields={[data]}
        type={data.type}
        widgetData={[data]}
        id={id}
        range={data.range}
        caption={data.caption}
        noLabel={false}
        filterWidget={true}
        {...{
          viewId,
          windowType,
          onShow,
          onHide,
        }}
      />
    );
  }
}

// FiltersItem.propTypes = {
//   dispatch: PropTypes.func.isRequired,
// };

// export default connect()(FiltersItem);
export default InlineFilterItem;
