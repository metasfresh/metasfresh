import counterpart from 'counterpart';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import TetherComponent from 'react-tether';
import ReactDOM from 'react-dom';
import _ from 'lodash';
import Moment from 'moment-timezone';

import { closeFilterBox, openFilterBox } from '../../actions/WindowActions';

import { convertDateToReadable } from '../../utils/dateHelpers';
import { isFocusableWidgetType } from '../../utils/widgetHelpers';
import keymap from '../../shortcuts/keymap';
import ModalContextShortcuts from '../keyshortcuts/ModalContextShortcuts';
import { DATE_FIELD_FORMATS } from '../../constants/Constants';

import OverlayField from '../app/OverlayField';
import Tooltips from '../tooltips/Tooltips.js';
import WidgetWrapper from '../../containers/WidgetWrapper';
import {
  getViewFilterParameterDropdown,
  getViewFilterParameterTypeahead,
} from '../../api/view';
import { prepareParameterValueForBackend } from '../../utils/filterHelpers';

/**
 * @file Class based component.
 * @module FiltersItem
 * @extends Component
 * This component is responsible for rendering the actual widgets for filtering.
 * It stores a local copy of filters (since filters data come without values,
 * we need to cross-reference active filters with filters widgets to get the value
 * of fields) and active filters (to store values before submitting them to the
 * backend), which are then synced with the API via `Filters` class when applied.
 *
 * @TODO: Filters should be stored in the redux state, and the merge should also
 * happen there. This way we wouldn't have to listen for props changes in the
 * lifecycle methods as updated props would be passed directly.
 */
class FiltersItem extends PureComponent {
  constructor(props) {
    super(props);

    const { active, data } = props;
    let activeFilter = null;
    if (active) {
      activeFilter = active.find((item) => item.filterId === data.filterId);
    }

    this.state = {
      filter: _.cloneDeep(props.data),
      activeFilter: activeFilter ? _.cloneDeep(activeFilter) : null,
      isTooltipShow: false,
      maxWidth: null,
      maxHeight: null,
    };
  }

  UNSAFE_componentWillMount() {
    this.init();
  }

  UNSAFE_componentWillReceiveProps(nextProps) {
    const { active } = this.props;

    if (JSON.stringify(active) !== JSON.stringify(nextProps.active)) {
      this.init();
    }
  }

  componentDidMount() {
    this.mounted = true;

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
    this.mounted = false;

    const { closeFilterBox } = this.props;

    if (this.widgetsContainer) {
      this.widgetsContainer.removeEventListener('scroll', this.handleScroll);
    }

    closeFilterBox();
  }

  /**
   * @method init
   * @summary This function merges filters with applied filters to get the
   * values for widgets when `active` filter props change, or component is mounted
   */
  init = () => {
    const { data, active } = this.props;
    let activeFilter = null;
    if (active) {
      activeFilter = active.find((item) => item.filterId === data.filterId);
    }

    if (data.parameters) {
      this.mergeData(data.parameters);

      if (
        activeFilter &&
        activeFilter.parameters &&
        activeFilter.filterId === data.filterId
      ) {
        this.mergeData(activeFilter.parameters, true);
      }
    }
  };

  /**
   * @method setValue
   * @summary Called from the widgets to set the filter value. It then pushes the
   * change to the active filter.
   *
   * @param {object|array} parameter
   * @param {*} value
   * @param {*} id
   * @param {*} valueTo
   */
  setValue = (parameter, value, id, valueTo = '') => {
    if (!Array.isArray(parameter)) {
      parameter = [parameter];
    }

    parameter = parameter.map((param) => ({
      parameterName: param.parameterName ? param.parameterName : param,
      value,
      valueTo,
    }));

    const { filter, activeFilter } = this.mergeSingle(parameter, true);

    return new Promise((resolve) => {
      this.setState({ filter, activeFilter }, () => resolve(true));
    });
  };

  /**
   * @method mergeData
   * @summary Fetches the cross-merged filters/activeFilters data and saves
   * the result to the local state
   *
   * @param {obj} parameters - filter parameters object
   * @param {bool} active - defines if we're merging filter or active filter
   * parameters
   */
  mergeData = (parameters, active = false) => {
    const { filter, activeFilter } = this.mergeSingle(parameters, active);

    this.setState({ filter, activeFilter });
  };

  /**
   * @method mergeSingle
   * @summary This method syncs values between filters/active filters. It takes an array
   * of params (be it initial parameters, or updated when the widget value changes) and
   * traverses the local filter and activeFilter objects updating the values when needed.
   * If value for parameter exists it will be updated, if not - nulled. In case of active
   * filters, paremeters without values will be removed (and in case there are no more
   * parameters left the filter will be removed from active)
   *
   * @param {obj} parameters - filter parameters object
   * @param {bool} active - defines if we're merging filter or active filter
   * parameters
   */
  mergeSingle = (parameters, active) => {
    const { activeFilter, filter } = this.state;
    let newActiveFilter = activeFilter
      ? { ...activeFilter }
      : {
          filterId: filter.filterId,
          parameters: [],
        };
    let value = '';
    let valueTo = '';
    let activeValue = '';
    let activeValueTo = '';
    const paramsMap = {};
    const updatedParameters = {};

    parameters.forEach((parameter) => {
      const parameterName = parameter.parameterName;

      // if filter has defaultValue, update local filters data to include
      // it for displaying

      // for active filter
      if (active) {
        value = parameter.value != null ? parameter.value : '';
        valueTo = parameter.valueTo != null ? parameter.valueTo : '';

        // if filter has value property, use it instead of defaultValue
        activeValue =
          parameter.value !== undefined
            ? parameter.value
            : parameter.defaultValue;
        activeValueTo =
          parameter.valueTo !== undefined
            ? parameter.valueTo
            : parameter.defaultValueTo;
      }

      // we need this hashmap to easily now which parameters in the local `filter`
      // should be updated
      paramsMap[parameterName] = {
        value,
        valueTo,
        activeValue,
        activeValueTo,
      };

      // update values for active filters, as we then bubble them up to use
      // this data in PATCH request updating them on the server
      const updateActive = !!(active || (!active && parameter.defaultValue));

      if (updateActive) {
        updatedParameters[parameterName] = {
          activeValue,
          activeValueTo,
        };
      }
    });

    // updated activeFilter parameters
    const parametersArray = [];

    newActiveFilter.parameters.forEach((param) => {
      if (!updatedParameters[param.parameterName]) {
        // copy params that were not updated
        parametersArray.push({
          ...param,
          defaultValue: null,
          defaultValueTo: null,
        });
      }
    });

    _.forEach(updatedParameters, ({ activeValue, activeValueTo }, key) => {
      parametersArray.push({
        parameterName: key,
        value: activeValue,
        valueTo: activeValueTo,
        defaultValue: null,
        defaultValueTo: null,
      });
    });

    // if there are no parameters, filter is not active anymore so null it
    if (_.size(parametersArray)) {
      newActiveFilter.parameters = _.map(parametersArray, (val) => val);
    } else {
      newActiveFilter = null;
    }

    const returnFilter = {
      ...filter,
      parameters: filter.parameters.map((param) => {
        if (paramsMap[param.parameterName]) {
          const { value, valueTo } = paramsMap[param.parameterName];

          if (value !== null && value !== '') {
            return {
              ...param,
              value: convertDateToReadable(param.widgetType, value),
              valueTo: convertDateToReadable(param.widgetType, valueTo),
            };
          }
          return {
            ...param,
            value: '',
            valueTo: '',
          };
        }

        return param;
      }),
    };

    return { filter: returnFilter, activeFilter: newActiveFilter };
  };

  /**
   * @method handleScroll
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleScroll = () => {
    const { openFilterBox } = this.props;
    const { top, left, bottom, right } =
      this.widgetsContainer.getBoundingClientRect();

    openFilterBox({ top, left, bottom, right });
  };

  /**
   * @method checkFilterTypeByName
   * @summary Gets the corresponding widgetType from the filters data
   * @param {*} filterItem - the active one that does not contain the widgetType
   * @returns {string} widgetType
   */
  checkFilterTypeByName = (filterItem) => {
    const {
      filter: { parameters },
    } = this.state;

    const targetFilter = parameters.filter(
      (item) => item.field === filterItem.parameterName
    );
    return targetFilter[0] ? targetFilter[0].widgetType : '';
  };

  /**
   * updates the items for the case when there is no active filters, does this update within the existing default values
   * @param {array} toChange
   */
  updateItems = (toChange) => {
    if (this.mounted) {
      const { filter } = this.state;

      if (filter.parameters) {
        filter.parameters.map((filterItem) => {
          if (filterItem.parameterName === toChange.widgetField) {
            filterItem.defaultValue = toChange.value;
            filterItem.value = toChange.value;
            filterItem.valueTo = toChange.valueTo;
          }
          return filterItem;
        });
      }
      this.setState({ filter });
    }
  };

  /**
   * @method handleApply
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleApply = () => {
    const { applyFilters, closeFilterMenu, returnBackToDropdown, isActive } =
      this.props;
    const { filter, activeFilter } = this.state;

    if (
      (filter &&
        filter.parametersLayoutType === 'singleOverlayField' &&
        !filter.parameters[0].value) ||
      (filter.parameters && activeFilter === null)
    ) {
      return this.handleClear();
    }

    if (!filter.parameters) {
      this.setState(
        {
          activeFilter: filter,
        },
        () => {
          applyFilters(this.state.activeFilter, () => {
            closeFilterMenu();
            returnBackToDropdown && returnBackToDropdown();
          });
        }
      );
    } else {
      // update the active filter with the defaultValue if value from active filter is empty
      let activeFilterClone = _.cloneDeep(activeFilter);
      if (!isActive) {
        activeFilterClone = filter;
        activeFilterClone.parameters.map((afcItem) => {
          let filterType = this.checkFilterTypeByName(afcItem);
          if (filterType === 'YesNo') {
            // YesNo filters (checkboxes) can be either null, true or false
            afcItem.value = afcItem.defaultValue;
          }
          return afcItem;
        });
      }
      applyFilters(activeFilterClone, () => {
        closeFilterMenu();
        returnBackToDropdown && returnBackToDropdown();
      });
    }
  };

  /**
   * @method handleClear
   * @summary clears this filter completely, removing it from the active filters
   */
  handleClear = () => {
    const { clearFilters, closeFilterMenu, returnBackToDropdown } = this.props;
    const { filter } = this.state;

    clearFilters(filter);
    closeFilterMenu();
    returnBackToDropdown && returnBackToDropdown();
  };

  /**
   * @method toggleTooltip
   * @summary shows/hides tooltip
   * @param {bool} visible
   */
  toggleTooltip = (visible) => {
    this.setState({
      isTooltipShow: visible,
    });
  };

  // wrappers around toggleTooltip to skip creating anonymous functions on render
  showTooltip = () => this.toggleTooltip(true);
  hideTooltip = () => this.toggleTooltip(false);

  prepareLookupContextFromState = (filterId) => {
    const { filter } = this.state;

    // shall not happen:
    if (filter?.filterId !== filterId) {
      console.warn(
        'prepareLookupContextFromState: called with wrong filterId',
        { filterId, filter }
      );
      return {};
    }

    const context = {};

    if (Array.isArray(filter.parameters)) {
      filter.parameters.forEach((parameter) => {
        context[parameter.parameterName] = prepareParameterValueForBackend({
          value: parameter.value,
          defaultValue: parameter.defaultValue,
          widgetType: parameter.widgetType,
        });
      });
    }

    return context;
  };

  typeaheadSupplier = ({
    docType: windowId,
    docId: viewId, // NOTE: for some reason docId is the viewId and not the viewId which is undefined
    subentityId: filterId,
    propertyName: parameterName,
    query,
  }) => {
    const context = this.prepareLookupContextFromState(filterId);
    delete context[parameterName];

    return getViewFilterParameterTypeahead({
      windowId,
      viewId,
      filterId,
      parameterName,
      query,
      context,
    });
  };

  dropdownValuesSupplier = ({
    docType: windowId,
    viewId,
    subentityId: filterId,
    propertyName: parameterName,
  }) => {
    const context = this.prepareLookupContextFromState(filterId);
    delete context[parameterName];

    return getViewFilterParameterDropdown({
      windowId,
      viewId,
      filterId,
      parameterName,
      context,
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
      closeFilterMenu,
      captionValue,
      openedFilter,
    } = this.props;
    const { filter, isTooltipShow, maxWidth, maxHeight } = this.state;
    const style = {};
    let autoFocusedField = false;

    if (maxWidth) {
      style.width = maxWidth;
      style.maxHeight = maxHeight;
    }

    const panelCaption = this.props.panelCaption
      ? this.props.panelCaption
      : filter.caption;

    return (
      <div>
        {data.parametersLayoutType === 'singleOverlayField' ? (
          <div className="screen-freeze js-not-unselect light">
            <OverlayField
              type={windowType}
              filter
              captionValue={captionValue}
              layout={filter}
              handlePatch={this.setValue}
              handleChange={this.setValue}
              closeOverlay={outsideClick ? outsideClick : closeFilterMenu}
              handleSubmit={this.handleApply}
              {...{ windowType, onShow, onHide, viewId }}
            />
          </div>
        ) : (
          <div
            className="filter-menu filter-widget"
            style={style}
            ref={(c) => (this.widgetsContainer = c)}
          >
            <div className="filter-controls">
              <div>
                {counterpart.translate('window.activeFilter.caption')}:
                <span className="filter-active">{panelCaption}</span>
              </div>
              {isActive && (
                <span className="filter-clear" onClick={this.handleClear}>
                  {counterpart.translate('window.clearFilter.caption')}
                  <i className="meta-icon-trash" />
                </span>
              )}
            </div>
            <div
              className={`form-group row filter-content filter-${data.filterId}`}
            >
              <div className="col-sm-12">
                {filter.parameters &&
                  filter.parameters.map((item, index) => {
                    const { widgetType } = item;
                    let autoFocus = false;
                    item.field = item.parameterName;

                    if (
                      !autoFocusedField &&
                      isFocusableWidgetType(widgetType)
                    ) {
                      autoFocusedField = true;
                      autoFocus = true;
                    }

                    return (
                      <WidgetWrapper
                        dataSource="filter-item"
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
                        handleChange={(property, value, id, valueTo) => {
                          if (
                            (DATE_FIELD_FORMATS[widgetType] &&
                              Moment.isMoment(value)) ||
                            !DATE_FIELD_FORMATS[widgetType]
                          ) {
                            this.setValue(
                              property,
                              value,
                              id,
                              valueTo,
                              filter.filterId,
                              item.defaultValue
                            );
                          }
                        }}
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
                          widgetType,
                          onShow,
                          onHide,
                          isFilterActive: isActive,
                          updateItems: this.updateItems,
                          typeaheadSupplier: this.typeaheadSupplier,
                          dropdownValuesSupplier: this.dropdownValuesSupplier,
                          autoFocus,
                        }}
                      />
                    );
                  })}
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
                renderTarget={(ref) =>
                  filter.isActive && !filter.parameters ? (
                    <span ref={ref} />
                  ) : (
                    <button
                      ref={ref}
                      className="applyBtn btn btn-sm btn-success"
                      onClick={this.handleApply}
                      onMouseEnter={this.showTooltip}
                      onMouseLeave={this.hideTooltip}
                    >
                      {counterpart.translate('window.apply.caption')}
                    </button>
                  )
                }
                renderElement={(ref) =>
                  isTooltipShow && (
                    <Tooltips
                      ref={ref}
                      className="filter-tooltip"
                      name={keymap.DONE}
                      action={counterpart.translate('window.apply.caption')}
                      type={''}
                    />
                  )
                }
              />
            </div>
          </div>
        )}
        <ModalContextShortcuts
          done={this.handleApply}
          visibleFilter={openedFilter}
        />
      </div>
    );
  }
}

FiltersItem.propTypes = {
  filtersWrapper: PropTypes.object,
  panelCaption: PropTypes.string,
  active: PropTypes.array,
  data: PropTypes.object,
  notValidFields: PropTypes.any,
  isActive: PropTypes.bool,
  windowType: PropTypes.string,
  viewId: PropTypes.string,
  captionValue: PropTypes.string,
  openedFilter: PropTypes.bool,

  //
  // Callbacks and other functions:
  applyFilters: PropTypes.func.isRequired,
  clearFilters: PropTypes.func,
  onShow: PropTypes.func,
  onHide: PropTypes.func,
  closeFilterMenu: PropTypes.func,
  outsideClick: PropTypes.func,
  returnBackToDropdown: PropTypes.func,

  //
  // mapDispatchToProps:
  openFilterBox: PropTypes.func.isRequired,
  closeFilterBox: PropTypes.func.isRequired,
};

export default connect(null, {
  openFilterBox,
  closeFilterBox,
})(FiltersItem);
