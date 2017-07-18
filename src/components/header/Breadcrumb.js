import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import {push} from 'react-router-redux';
import counterpart from 'counterpart';

import MenuOverlay from './MenuOverlay';
import Tooltips from '../tooltips/Tooltips';
import keymap from '../../keymap.js';

class Breadcrumb extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tooltipOpen: false,
            tooltipOnFirstlevel: false,
            tooltipOnFirstlevelPositionLeft: 0
        }
    }

    linkToPage = (page) => {
        const {dispatch} = this.props;
        dispatch(push('/window/' + page));
    }

    toggleTooltip = (tooltip) => {
        this.setState({
            tooltipOpen: tooltip
        })
    }

    toggleTooltipOnFirstLevel = (showTooltip) => {
        const breadcrumbWrapper =
            document.getElementsByClassName('header-breadcrumb-wrapper')[0];
        const breadcrumbWrapperLeft =
            breadcrumbWrapper && breadcrumbWrapper.getBoundingClientRect().left;
        const elem =
             document.getElementsByClassName('header-item-last-level')[0];
        const elemWidth = elem && elem.offsetWidth;
        const elemLeft = elem && elem.getBoundingClientRect().left;

        const tooltipPositionLeft =
            elemLeft + 0.5*elemWidth - breadcrumbWrapperLeft;

        this.setState({
            tooltipOnFirstlevel: showTooltip,
            tooltipOnFirstlevelPositionLeft: tooltipPositionLeft
        })
    }

    closeTooltips = () => {
        this.setState({
            tooltipOpen: false,
            tooltipOnFirstlevel: false
        })
    }

    handleClick = (e, menu) => {
        const {
            handleMenuOverlay, windowType
        } = this.props;

        const noChildNodes = menu && ((menu.type === 'window') || (menu.children && (menu.children.length === 0)));

        if(menu && (menu.elementId || noChildNodes)) {
            (windowType) && this.linkToPage(windowType);
        } else {
            handleMenuOverlay(e, menu.nodeId);
        }

        this.toggleTooltip(false);
    }

    renderBtn = (menu, index) => {
        const {
            handleMenuOverlay, menuOverlay, siteName, openModal, windowType,
            breadcrumb, docId
        } = this.props;

        return (<div key={index}>
            {index ? <span className="divider">/</span> : null}
            <div className="header-btn tooltip-parent">
                <div
                    className={'header-item-container pointer ' +
                        (menuOverlay === menu.nodeId ?
                            'header-item-open ' : '') +
                        (!index ? 'header-item-container-static ': '') +
                        (breadcrumb && index===
                        breadcrumb.length?'header-item-last-level':'')
                    }
                    onClick={(e) => this.handleClick(e, menu)}
                    onMouseEnter={index ?
                        ()=> this.toggleTooltipOnFirstLevel(
                            breadcrumb && index=== breadcrumb.length
                            ) :
                        () => this.toggleTooltip(true)}
                    onMouseLeave={this.closeTooltips}
                >
                    <span className="header-item icon-sm">
                        {index ?
                            menu.caption :
                            <i className="meta-icon-menu" />
                        }
                    </span>
                </div>
                {menuOverlay === menu.nodeId &&
                    <MenuOverlay
                        {...{siteName, handleMenuOverlay, openModal, windowType,
                            docId}}
                        nodeId={menu.nodeId}
                        node={menu}
                        onClickOutside={e => handleMenuOverlay(e, '')}
                        disableOnClickOutside={menuOverlay !== menu.nodeId}
                    />
                }
            </div>
        </div>)
    }

    render() {
        const {
            breadcrumb, docSummaryData, siteName
        } = this.props;

        const {
            tooltipOpen, tooltipOnFirstlevel, tooltipOnFirstlevelPositionLeft
        } = this.state;

        return (
            <div className="header-breadcrumb-wrapper">
                {tooltipOpen &&
                    <Tooltips
                        extraClass="tooltip-home-menu"
                        name={keymap.GLOBAL_CONTEXT.OPEN_NAVIGATION_MENU}
                        action= {
                            counterpart.translate(
                                'mainScreen.navigation.tooltip'
                                )
                        }
                        type={''}
                    />
                }
                {tooltipOnFirstlevel &&
                    <Tooltips
                        {...{tooltipOnFirstlevelPositionLeft}}
                        name=""
                        action={'Go to default documents list'}
                        type={''}
                    />
                }

                <div className="header-breadcrumb">
                    {this.renderBtn({nodeId: '0'}, 0)}

                    {breadcrumb && breadcrumb.map((item, index) =>
                        this.renderBtn(item, index+1)
                    )}

                {docSummaryData && <div className="divider">/</div>}

                {docSummaryData &&
                    <div
                        className="hidden-xs-down header-breadcrumb-line"
                    >
                        <span
                            className="header-breadcrumb-sitename"
                        >
                            {docSummaryData.value}
                        </span>
                    </div>
                }

                {siteName && <div className="divider">/</div>}

                {siteName &&
                    <div>
                        <span
                            className="header-item icon-sm"
                        >{siteName}</span>
                    </div>
                }
            </div>
        </div>
        )
    }
}

Breadcrumb.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Breadcrumb = connect()(Breadcrumb)

export default Breadcrumb
