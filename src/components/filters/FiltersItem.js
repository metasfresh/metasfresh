import counterpart from 'counterpart';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import TetherComponent from 'react-tether';
import ReactDOM from 'react-dom';
import _ from 'lodash';

import keymap from '../../shortcuts/keymap';
import OverlayField from '../app/OverlayField';
import ModalContextShortcuts from '../keyshortcuts/ModalContextShortcuts';
import Tooltips from '../tooltips/Tooltips.js';
import RawWidget from '../widget/RawWidget';
import {
  openFilterBox,
  closeFilterBox,
  parseDateWithCurrenTimezone,
} from '../../actions/WindowActions';
import { DATE_FIELDS } from '../../constants/Constants';
class FiltersItem extends Component {
  constructor(props) {
    super(props);

    const { active, data } = props;
    let activeFilter = null;
    if (active) {
      activeFilter = active.find(item => item.filterId === data.filterId);
    }

    this.state = {
      filter: _.cloneDeep(props.data),
      activeFilter: activeFilter ? _.cloneDeep(activeFilter) : null,
      isTooltipShow: false,
      maxWidth: null,
      maxHeight: null,
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

  componentDidMount() {
    if (this.widgetsContainer) {
      this.widgetsContainer.addEventListener('scroll', this.handleScroll);
    }

    if (this.props.filtersWrapper && this.widgetsContainer) {
      /* eslint-disable react/no-find-dom-node */
      const widgetElement = ReactDOM.findDOMNode(this.widgetsContainer);
      const buttonElement = widgetElement.closest('.filter-wrapper');
      const buttonClientRect = buttonElement.getBoundingClientRect();
      const wrapperElement = ReactDOM.findDOMNode(this.props.filtersWrapper);
      /* eslint-enablereact/no-find-dom-node */
      const wrapperRight = wrapperElement.getBoundingClientRect().right;
      const documentElement = wrapperElement.closest('.document-lists-wrapper');
      const documentClientRect = documentElement.getBoundingClientRect();

      if (parent) {
        const offset = ~~(
          documentClientRect.right -
          wrapperRight +
          buttonClientRect.width
        );
        const height =
          ~~(documentClientRect.top + documentClientRect.height) -
          ~~(buttonClientRect.top + buttonClientRect.height);

        this.setState({
          maxWidth: offset,
          maxHeight: height,
        });
      }
    }
  }

  componentWillUnmount() {
    const { dispatch } = this.props;

    if (this.widgetsContainer) {
      this.widgetsContainer.removeEventListener('scroll', this.handleScroll);
    }

    dispatch(closeFilterBox());
  }

  init = () => {
    const { filter, activeFilter } = this.state;

    if (filter.parameters) {
      filter.parameters.map(item => {
        this.mergeData(
          item.parameterName,
          '',
          '',
          // if filter has defaultValue, update local filters data to include
          // it for displaying
          item.defaultValue ? true : false,
          item.defaultValue,
          item.defaultValueTo
        );
      });

      if (
        activeFilter &&
        activeFilter.parameters &&
        activeFilter.filterId === filter.filterId
      ) {
        activeFilter.parameters.map(item => {
          this.mergeData(
            item.parameterName,
            item.value != null ? item.value : '',
            item.valueTo != null ? item.valueTo : '',
            true,
            // if filter has value property, use it instead of defaultValue
            item.value !== undefined ? item.value : item.defaultValue,
            item.valueTo !== undefined ? item.valueTo : item.defaultValueTo
          );
        });
      }
    }
  };

  setValue = (property, value, id, valueTo = '', filterId, defaultValue) => {
    const { resetInitialValues } = this.props;

    // if user changed field value and  defaultValue is not null, then we need
    // to reset it's initial value so that it won't be set
    if (defaultValue != null) {
      resetInitialValues && resetInitialValues(filterId, property);
    }

    //TODO: LOOKUPS GENERATE DIFFERENT TYPE OF PROPERTY parameters
    // IT HAS TO BE UNIFIED
    //
    // OVERWORKED WORKAROUND
    if (Array.isArray(property)) {
      property.map(item => {
        this.mergeData(
          item.parameterName,
          value,
          valueTo,
          true,
          value,
          valueTo
        );
      });
    } else {
      this.mergeData(property, value, valueTo, true, value, valueTo);
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

  mergeData = (
    property,
    value,
    valueTo,
    updateActive,
    activeValue,
    activeValueTo
  ) => {
    let { activeFilter, filter } = this.state;

    // update values for active filters, as we then bubble them up to use
    // this data in PATCH request updating them on the server
    if (updateActive) {
      let paramExists = false;

      if (!activeFilter) {
        activeFilter = {
          filterId: filter.filterId,
          parameters: [],
        };
      }

      const updatedParameters = activeFilter.parameters.map(param => {
        if (param.parameterName === property) {
          paramExists = true;

          return {
            ...param,
            value: this.parseDateToReadable(param.widgetType, activeValue),
            valueTo: this.parseDateToReadable(param.widgetType, activeValueTo),
            defaultValue: null,
            defaultValueTo: null,
          };
        } else {
          return {
            ...param,
            defaultValue: null,
            defaultValueTo: null,
          };
        }
      });

      if (!paramExists) {
        updatedParameters.push({
          parameterName: property,
          value: activeValue,
          valueTo: activeValueTo,
          defaultValue: null,
          defaultValueTo: null,
        });
      }

      activeFilter = {
        ...activeFilter,
        parameters: updatedParameters,
      };
    }

    this.setState(prevState => ({
      filter: {
        ...prevState.filter,
        parameters: prevState.filter.parameters.map(param => {
          if (param.parameterName === property) {
            return {
              ...param,
              value: this.parseDateToReadable(param.widgetType, value),
              valueTo: this.parseDateToReadable(param.widgetType, valueTo),
            };
          } else {
            return param;
          }
        }),
      },
      activeFilter,
    }));
  };

  handleScroll = () => {
    const { dispatch } = this.props;
    const {
      top,
      left,
      bottom,
      right,
    } = this.widgetsContainer.getBoundingClientRect();

    dispatch(openFilterBox({ top, left, bottom, right }));
  };

  handleApply = () => {
    const { applyFilters, closeFilterMenu, returnBackToDropdown } = this.props;
    const { filter, activeFilter } = this.state;

    if (
      filter &&
      filter.parametersLayoutType === 'singleOverlayField' &&
      !filter.parameters[0].value
    ) {
      return this.handleClear();
    }

    applyFilters(activeFilter, () => {
      closeFilterMenu();
      returnBackToDropdown && returnBackToDropdown();
    });
  };

  handleClear = () => {
    const {
      clearFilters,
      closeFilterMenu,
      returnBackToDropdown,
      resetInitialValues,
    } = this.props;
    const { filter } = this.state;

    resetInitialValues && resetInitialValues(filter.filterId);
    clearFilters(filter);
    closeFilterMenu();
    returnBackToDropdown && returnBackToDropdown();
  };

  toggleTooltip = visible => {
    this.setState({
      isTooltipShow: visible,
    });
  };

  render() {
    const {
      data,
      notValidFields,
      isActive,
      windowType,
      onShow,
      onHide,
      viewId,
      outsideClick,
      captionValue,
      openedFilter,
      panelCaption,
    } = this.props;
    const { filter, isTooltipShow, maxWidth, maxHeight } = this.state;
    const style = {};

    if (maxWidth) {
      style.width = maxWidth;
      style.maxHeight = maxHeight;
    }

    return (
      <div>
        {data.parametersLayoutType === 'singleOverlayField' ? (
          <OverlayField
            type={windowType}
            filter
            captionValue={captionValue}
            layout={filter}
            handlePatch={this.setValue}
            handleChange={this.setValue}
            closeOverlay={outsideClick}
            handleSubmit={this.handleApply}
            {...{ windowType, onShow, onHide, viewId }}
          />
        ) : (
          <div
            className="filter-menu filter-widget"
            style={style}
            ref={c => (this.widgetsContainer = c)}
          >
            <div className="filter-controls">
              <div>
                {counterpart.translate('window.activeFilter.caption')}:
                <span className="filter-active">{panelCaption}</span>
              </div>
              {isActive && (
                <span
                  className="filter-clear"
                  onClick={() => this.handleClear()}
                >
                  {counterpart.translate('window.clearFilter.caption')}
                  <i className="meta-icon-trash" />
                </span>
              )}
            </div>
            <div className="form-group row filter-content">
              <div className="col-sm-12">
                {filter.parameters &&
                  filter.parameters.map((item, index) => (
                    <RawWidget
                      entity="documentView"
                      subentity="filter"
                      subentityId={filter.filterId}
                      handlePatch={(property, value, id, valueTo) =>
                        this.setValue(
                          property,
                          value,
                          id,
                          valueTo,
                          filter.filterId,
                          item.defaultValue
                        )
                      }
                      handleChange={(property, value, id, valueTo) =>
                        this.setValue(
                          property,
                          value,
                          id,
                          valueTo,
                          filter.filterId,
                          item.defaultValue
                        )
                      }
                      widgetType={item.widgetType}
                      fields={[item]}
                      type={item.type}
                      widgetData={[item]}
                      key={index}
                      id={index}
                      range={item.range}
                      caption={item.caption}
                      noLabel={false}
                      filterWidget={true}
                      {...{
                        viewId,
                        windowType,
                        onShow,
                        onHide,
                      }}
                    />
                  ))}
              </div>
              <div className="col-sm-12 text-right">
                {notValidFields && (
                  <div className="input-error">
                    {counterpart.translate('window.noMandatory.caption')}
                  </div>
                )}
              </div>
            </div>
            <div className="filter-btn-wrapper">
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
              >
                <button
                  className="applyBtn btn btn-sm btn-success"
                  onClick={this.handleApply}
                  onMouseEnter={() => this.toggleTooltip(true)}
                  onMouseLeave={() => this.toggleTooltip(false)}
                >
                  {counterpart.translate('window.apply.caption')}
                </button>
                {isTooltipShow && (
                  <Tooltips
                    className="filter-tooltip"
                    name={keymap.APPLY}
                    action={counterpart.translate('window.apply.caption')}
                    type={''}
                  />
                )}
              </TetherComponent>
            </div>
          </div>
        )}
        <ModalContextShortcuts
          apply={this.handleApply}
          visibleFilter={openedFilter}
        />
      </div>
    );
  }
}

FiltersItem.propTypes = {
  dispatch: PropTypes.func.isRequired,
  applyFilters: PropTypes.func.isRequired,
  resetInitialValues: PropTypes.func,
  clearFilters: PropTypes.func,
  filtersWrapper: PropTypes.any,
  panelCaption: PropTypes.string,
  active: PropTypes.array,
};

export default connect()(FiltersItem);
