import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { clearAllFilters } from '../../actions/FiltersActions';
import keymap from '../../shortcuts/keymap';
import Tooltips from '../tooltips/Tooltips';
import MenuOverlay from './MenuOverlay';

/**
 * @file Class based component.
 * @module Breadcrumb
 * @extends Component
 */
class Breadcrumb extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tooltipOpen: false,
      tooltipOnFirstlevel: false,
      tooltipOnFirstlevelPositionLeft: 0,
    };
  }

  /**
   * @method linkToPage
   * @summary ToDo: Describe the method.
   * @param {*} page
   */
  linkToPage = (page) => {
    window.location = '/window/' + page;
  };

  /**
   * @method linkToEntityPage
   * @summary ToDo: Describe the method.
   * @param {*} entity
   * @param {*} page
   */
  linkToEntityPage = (entity, page) => {
    const { dispatch } = this.props;
    dispatch(push(`/${entity}/${page}`));
  };

  /**
   * @method toggleTooltip
   * @summary ToDo: Describe the method.
   * @param {*} tooltip
   */
  toggleTooltip = (tooltip) => {
    this.setState({
      tooltipOpen: tooltip,
    });
  };

  /**
   * @method toggleTooltipOnFirstLevel
   * @summary ToDo: Describe the method.
   * @param {*} showTooltip
   */
  toggleTooltipOnFirstLevel = (showTooltip) => {
    const breadcrumbWrapper = document.getElementsByClassName(
      'header-breadcrumb-wrapper'
    )[0];
    const breadcrumbWrapperLeft =
      breadcrumbWrapper && breadcrumbWrapper.getBoundingClientRect().left;
    const elem = document.getElementsByClassName('header-item-last-level')[0];
    const elemWidth = elem && elem.offsetWidth;
    const elemLeft = elem && elem.getBoundingClientRect().left;

    const tooltipPositionLeft =
      elemLeft + 0.5 * elemWidth - breadcrumbWrapperLeft;

    this.setState({
      tooltipOnFirstlevel: showTooltip,
      tooltipOnFirstlevelPositionLeft: tooltipPositionLeft,
    });
  };

  /**
   * @method closeTooltips
   * @summary ToDo: Describe the method.
   */
  closeTooltips = () => {
    this.setState({
      tooltipOpen: false,
      tooltipOnFirstlevel: false,
    });
  };

  /**
   * @method handleClick
   * @summary ToDo: Describe the method.
   * @param {*} e
   * @param {*} menu
   */
  handleClick = (e, menu) => {
    const { handleMenuOverlay, windowType, filters, dispatch } = this.props;
    const { type } = menu;

    if (!filters.clearAll && type && type === 'window') {
      dispatch(clearAllFilters(true));
    }

    const noChildNodes =
      menu &&
      (menu.type === 'window' ||
        menu.type === 'board' ||
        (menu.children && menu.children.length === 0));

    if (menu && (menu.elementId || noChildNodes)) {
      if (windowType) {
        if (menu.type === 'board') {
          this.linkToEntityPage(menu.type, windowType);
        } else {
          this.linkToPage(windowType);
        }
      }
    } else {
      handleMenuOverlay(e, menu.nodeId);
    }

    this.toggleTooltip(false);
  };

  /**
   * @method renderBtn
   * @summary ToDo: Describe the method.
   * @param {*} menu
   * @param {*} index
   */
  renderBtn = (menu, index) => {
    const {
      handleMenuOverlay,
      menuOverlay,
      siteName,
      openModal,
      windowType,
      breadcrumb,
      docId,
    } = this.props;

    if (menu.type === 'page') {
      return this.renderSummaryBreadcrumb(menu.caption);
    }

    return (
      <div key={index} className="js-not-unselect">
        {index ? <span className="divider">/</span> : null}
        <div className="header-btn tooltip-parent">
          <div
            className={
              'header-item-container pointer ' +
              (menuOverlay === menu.nodeId ? 'header-item-open ' : '') +
              (!index ? 'header-item-container-static ' : '') +
              (breadcrumb && index === breadcrumb.length
                ? 'header-item-last-level'
                : '')
            }
            onClick={(e) => this.handleClick(e, menu)}
            onMouseEnter={
              index
                ? () =>
                    this.toggleTooltipOnFirstLevel(
                      breadcrumb && index === breadcrumb.length
                    )
                : () => this.toggleTooltip(true)
            }
            onMouseLeave={this.closeTooltips}
          >
            <span className="header-item icon-sm">
              {index ? menu.caption : <i className="meta-icon-menu" />}
            </span>
          </div>
          {menuOverlay === menu.nodeId && typeof menuOverlay !== 'undefined' && (
            <MenuOverlay
              {...{
                siteName,
                handleMenuOverlay,
                openModal,
                windowType,
                docId,
              }}
              nodeId={menu.nodeId}
              node={menu}
              onClickOutside={(e) => handleMenuOverlay(e, '')}
              disableOnClickOutside={menuOverlay !== menu.nodeId}
            />
          )}
        </div>
      </div>
    );
  };

  /**
   * @method renderSummaryBreadcrumb
   * @summary ToDo: Describe the method.
   * @param {*} text
   */
  renderSummaryBreadcrumb(text) {
    return [
      <div key="summary-1" className="divider">
        /
      </div>,
      <div key="summary-2" className="hidden-down header-breadcrumb-line">
        <span className="header-breadcrumb-sitename" title={text}>
          {text}
        </span>
      </div>,
    ];
  }

  /**
   * @method render
   * @summary ToDo: Describe the method.
   */
  render() {
    const { breadcrumb, docSummaryData, siteName } = this.props;

    const {
      tooltipOpen,
      tooltipOnFirstlevel,
      tooltipOnFirstlevelPositionLeft,
    } = this.state;

    return (
      <div className="header-breadcrumb-wrapper">
        {tooltipOpen && (
          <Tooltips
            extraClass="tooltip-home-menu"
            name={keymap.OPEN_NAVIGATION_MENU}
            action={counterpart.translate('mainScreen.navigation.tooltip')}
            type={''}
          />
        )}
        {tooltipOnFirstlevel && (
          <Tooltips
            {...{ tooltipOnFirstlevelPositionLeft }}
            name=""
            action={'Go to default documents list'}
            type={''}
          />
        )}

        <div className="header-breadcrumb">
          {this.renderBtn({ nodeId: '0' }, 0)}

          {breadcrumb &&
            breadcrumb.map((item, index) => this.renderBtn(item, index + 1))}

          {docSummaryData && this.renderSummaryBreadcrumb(docSummaryData.value)}

          {siteName && <div className="divider">/</div>}

          {siteName && (
            <div>
              <span className="header-item icon-sm" title={siteName}>
                {siteName}
              </span>
            </div>
          )}
        </div>
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {*} breadcrumb
 * @prop {func} dispatch
 * @prop {*} docId
 * @prop {*} docSummaryData
 * @prop {*} handleMenuOverlay
 * @prop {*} menuOverlay
 * @prop {*} openModal
 * @prop {*} siteName
 * @prop {*} windowType
 */
Breadcrumb.propTypes = {
  breadcrumb: PropTypes.any,
  dispatch: PropTypes.func.isRequired,
  docId: PropTypes.any,
  docSummaryData: PropTypes.any,
  handleMenuOverlay: PropTypes.any,
  menuOverlay: PropTypes.any,
  openModal: PropTypes.any,
  siteName: PropTypes.any,
  windowType: PropTypes.any,
  filters: PropTypes.object,
};

const mapStateToProps = ({ filters }) => ({ filters });

export default connect(mapStateToProps)(Breadcrumb);
