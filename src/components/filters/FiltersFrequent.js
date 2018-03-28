import cx from 'classnames';
import counterpart from 'counterpart';
import React, { Component, Fragment } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import currentDevice from 'current-device';
import FiltersDateStepper from './FiltersDateStepper';
import FiltersItem from './FiltersItem';
import { DATE_FIELD_TYPES, TIME_FIELD_TYPES } from '../../constants/Constants';

const classes = 'btn btn-filter btn-meta-outline-secondary btn-sm';

class FiltersFrequent extends Component {
  state = { openFilterId: null };
  deviceType = null;

  constructor(props) {
    super(props);

    this.deviceType = currentDevice.type;
  }

  toggleFilter = index => {
    this.setState({
      openFilterId: index,
    });
  };

  handleClickOutside = () => {
    this.outsideClick();
  };

  outsideClick = () => {
    const { widgetShown, dropdownToggled, allowOutsideClick } = this.props;
    if (allowOutsideClick) {
      !widgetShown && this.toggleFilter(null);
      dropdownToggled();
    }
  };

  render() {
    const {
      data,
      windowType,
      notValidFields,
      viewId,
      handleShow,
      applyFilters,
      clearFilters,
      active,
      modalVisible,
    } = this.props;

    const { openFilterId } = this.state;

    return (
      <div className="filter-wrapper">
        {data.map((item, index) => {
          const parameter = item.parameters[0];
          const filterType = parameter.widgetType;
          const dateStepper =
            // keep implied information (e.g. for refactoring)
            item.frequent &&
            item.parameters.length === 1 &&
            parameter.showIncrementDecrementButtons &&
            item.isActive &&
            DATE_FIELD_TYPES.includes(filterType) &&
            !TIME_FIELD_TYPES.includes(filterType);

          return (
            <div className="filter-wrapper" key={index}>
              {dateStepper && (
                <FiltersDateStepper
                  active={active[index]}
                  applyFilters={applyFilters}
                  filter={item}
                />
              )}

              <button
                onClick={() => this.toggleFilter(index, item)}
                className={cx(classes, {
                  ['btn-select']: openFilterId === index,
                  ['btn-active']: item.isActive,
                  ['btn-distance']: !dateStepper,
                })}
                tabIndex={modalVisible ? -1 : 0}
              >
                <i className="meta-icon-preview" />
                {item.isActive &&
                item.parameters &&
                item.parameters.length === 1 &&
                item.captionValue ? (
                  <Fragment>
                    {`${item.caption}: `}
                    {item.captionValue}
                  </Fragment>
                ) : (
                  `${
                    this.deviceType === 'desktop'
                      ? counterpart.translate('window.filters.caption2')
                      : ''
                  }: ${item.caption}`
                )}
              </button>

              {dateStepper && (
                <FiltersDateStepper
                  active={active[index]}
                  applyFilters={applyFilters}
                  filter={item}
                  next
                />
              )}

              {openFilterId === index && (
                <FiltersItem
                  captionValue={item.captionValue}
                  key={index}
                  windowType={windowType}
                  data={item}
                  closeFilterMenu={() => this.toggleFilter()}
                  clearFilters={clearFilters}
                  applyFilters={applyFilters}
                  notValidFields={notValidFields}
                  isActive={item.isActive}
                  active={active}
                  onShow={() => handleShow(true)}
                  onHide={() => handleShow(false)}
                  viewId={viewId}
                  outsideClick={this.outsideClick}
                />
              )}
            </div>
          );
        })}
      </div>
    );
  }
}

const mapStateToProps = state => {
  const { allowOutsideClick, modal } = state.windowHandler;

  return {
    allowOutsideClick,
    modalVisible: modal.visible,
  };
};

export default connect(mapStateToProps)(onClickOutside(FiltersFrequent));
