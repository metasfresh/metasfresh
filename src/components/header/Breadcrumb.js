import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import {push} from 'react-router-redux';

import MenuOverlay from './MenuOverlay';
import MasterWidget from '../widget/MasterWidget';
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
        dispatch(push("/window/" + page));
    }

    toggleTooltip = (tooltip) => {
        this.setState(
            Object.assign({}, this.state, {
                tooltipOpen: tooltip
            })
        );
    }

    renderBtn = (menu, index) => {
        const {
            handleMenuOverlay, menuOverlay, windowType, siteName, openModal
        } = this.props;
        
        return (<div key={index}>
            {!!index && <span className="divider">/</span>}
            <div className={"header-btn tooltip-parent"}>
                <div
                    title={!!index && menu.children.captionBreadcrumb ? menu.children.captionBreadcrumb : ''}
                    className={"header-item-container pointer " +
                        (menuOverlay === menu.nodeId ? "header-item-open " : "") +
                        (!index ? "header-item-container-static ": "")
                    }
                    onClick={ !(menu && menu.children && menu.children.elementId) ?
                        e => {handleMenuOverlay(e, menu.nodeId), this.toggleTooltip(false)} :
                        (windowType ?
                            e => {this.linkToPage(windowType), this.toggleTooltip(false)} :
                            e => this.toggleTooltip(false)
                        )
                    }
                    onMouseEnter={!!index ? '' : (e) => this.toggleTooltip(true)}
                    onMouseLeave={(e) => this.toggleTooltip(false)}
                >
                    <span className={"header-item icon-sm"}>
                        {!!index ? menu.children.captionBreadcrumb : <i className="meta-icon-menu" />}
                    </span>
                </div>
                {menuOverlay === menu.nodeId &&
                    <MenuOverlay
                        nodeId={menu.nodeId}
                        node={menu}
                        onClickOutside={e => handleMenuOverlay(e, "")}
                        disableOnClickOutside={menuOverlay !== menu.nodeId}
                        siteName={siteName}
                        handleMenuOverlay={handleMenuOverlay}
                        openModal={openModal}
                    />
                }
            </div>
        </div>)
    }

	render() {
        const {
            breadcrumb, homemenu, windowType, docNo, docNoData, docSummaryData, dataId,
            siteName, menuOverlay, handleMenuOverlay, openModal
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
                    {this.renderBtn(homemenu, 0)}

                    {breadcrumb && breadcrumb.map((item, index) =>
                        this.renderBtn(item,index+1)
                    )}

                    {docNo && <div className="divider">/</div>}

                    {docNo && <div className="header-input-id header-input-sm">
                        <MasterWidget
                            windowType={windowType}
                            dataId={dataId}
                            widgetData={[docNoData]}
                            noLabel={true}
                            icon={true}
                            {...docNo}
                        />
                </div>}

                    {docSummaryData && <div className="hidden-xs-down header-breadcrumb-line">
                        <span className=" header-breadcrumb-sitename">{docSummaryData.value}</span>
                    </div>}

                    {siteName && <div className="divider">/</div>}

                    {siteName && <div className="hidden-xs-down">
                        <span>{siteName}</span>
                    </div>}

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
