import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import {push} from 'react-router-redux';

import MenuOverlay from './MenuOverlay';
import Tooltips from '../tooltips/Tooltips';
import keymap from '../../keymap.js';

class Breadcrumb extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tooltipOpen: false
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

    handleClick = (e, menu) => {
        const {
            handleMenuOverlay, windowType, dataId
        } = this.props;

        if(menu && menu.children && menu.children.elementId) {
            (windowType && dataId) && this.linkToPage(windowType);
        } else {
            handleMenuOverlay(e, menu.nodeId);
        }

        this.toggleTooltip(false);
    }

    renderBtn = (menu, index) => {
        const {
            handleMenuOverlay, menuOverlay, siteName, openModal, windowType,
            docId
        } = this.props;

        return (<div key={index}>
            {index ? <span className="divider">/</span> : null}
            <div className="header-btn tooltip-parent">
                <div
                    title={(index && menu.children.captionBreadcrumb) ?
                        menu.children.captionBreadcrumb : ''
                    }
                    className={'header-item-container pointer ' +
                        (menuOverlay === menu.nodeId ?
                            'header-item-open ' : '') +
                        (!index ? 'header-item-container-static ': '')
                    }
                    onClick={(e) => this.handleClick(e, menu)}
                    onMouseEnter={index ? '' : () => this.toggleTooltip(true)}
                    onMouseLeave={() => this.toggleTooltip(false)}
                >
                    <span className="header-item icon-sm">
                        {index ?
                            menu.children.captionBreadcrumb :
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

        const {tooltipOpen} = this.state;

        return (
            <div className="header-breadcrumb-wrapper">
                {tooltipOpen &&
                    <Tooltips
                        extraClass="tooltip-home-menu"
                        name={keymap.GLOBAL_CONTEXT.OPEN_NAVIGATION_MENU}
                        action={'Navigation'}
                        type={''}
                    />
                }
                <div className="header-breadcrumb">
                    {this.renderBtn({nodeId: '0'}, 0)}

                    {breadcrumb && breadcrumb.map((item, index) =>
                        this.renderBtn(item, index+1)
                    )}
                </div>

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
        )
    }
}

Breadcrumb.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Breadcrumb = connect()(Breadcrumb)

export default Breadcrumb
