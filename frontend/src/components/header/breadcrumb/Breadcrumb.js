import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { clearAllFilters } from '../../../actions/FiltersActions';
import keymap from '../../../shortcuts/keymap';
import Tooltips from '../../tooltips/Tooltips';
import MenuOverlay from '../main_menu/MenuOverlay';
import { requestRedirect } from '../../../reducers/redirect';

class Breadcrumb extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tooltipOpen: false,
      tooltipOnFirstlevel: false,
      tooltipOnFirstlevelPositionLeft: 0,
    };
  }

  linkToPage = (windowId) => {
    const { dispatch } = this.props;
    dispatch(requestRedirect(`/window/${windowId}`));
  };

  linkToEntityPage = (entity, page) => {
    const { dispatch } = this.props;
    dispatch(requestRedirect(`/${entity}/${page}`));
  };

  toggleTooltip = (tooltip) => {
    this.setState({
      tooltipOpen: tooltip,
    });
  };

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

  renderSummaryBreadcrumb(text, readOnly) {
    return [
      <div key="summary-1" className="divider">
        /
      </div>,
      <div key="summary-2" className="hidden-down header-breadcrumb-line">
        {readOnly && readOnly.isReadOnly && readOnly.reason && (
          <span title={readOnly.reason}>
            <i className="meta-icon-latch divider" />
          </span>
        )}

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

          {docSummaryData &&
            this.renderSummaryBreadcrumb(docSummaryData.value, {
              isReadOnly: docSummaryData['readonly'],
              reason: docSummaryData['readonly-reason'],
            })}

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

Breadcrumb.propTypes = {
  breadcrumb: PropTypes.any,
  dispatch: PropTypes.func.isRequired,
  docId: PropTypes.any,
  docSummaryData: PropTypes.any,
  handleMenuOverlay: PropTypes.func,
  menuOverlay: PropTypes.any,
  openModal: PropTypes.func,
  siteName: PropTypes.any,
  windowType: PropTypes.any,
  filters: PropTypes.object,
};

const mapStateToProps = (state) => {
  return {
    filters: state.filters,
  };
};

export default connect(mapStateToProps)(Breadcrumb);
