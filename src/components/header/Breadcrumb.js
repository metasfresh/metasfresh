import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import {push} from 'react-router-redux';

import MenuOverlay from './MenuOverlay';
import MasterWidget from '../widget/MasterWidget';

class Breadcrumb extends Component {
	constructor(props) {
		super(props);
	}

    linkToPage = (page) => {
        const {dispatch} = this.props;
        dispatch(push("/window/"+page));
    }

    renderBtn = (menu, index) => {
        const {handleMenuOverlay, menuOverlay, windowType, siteName} = this.props;
        return (<span key={index}>
            {!!index && <span className="divider">/</span>}
            <div
                title={!!index && menu.children.captionBreadcrumb}
                className={"notification-container pointer " +
                    (menuOverlay === menu.nodeId ? "notification-open " : "")
                }
                onClick={ !(menu && menu.children && menu.children.elementId) ?
                    e => handleMenuOverlay(e, menu.nodeId) : (windowType ? e => this.linkToPage(windowType) : '' )
                }
            >
                <span className={"notification icon-sm"}>
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
                />
            }
        </span>)
    }

	render() {
        const {
            breadcrumb, homemenu, windowType, docNo, docNoData, docSummaryData, dataId,
            siteName, menuOverlay, handleMenuOverlay
        } = this.props;

        return (
            <span className="header-breadcrumb">
                {this.renderBtn(homemenu, 0)}

                {breadcrumb && breadcrumb.map((item, index) =>
                    this.renderBtn(item,index+1)
                )}

                {docNo && <span className="divider">/</span>}

                {docNo && <span className="header-input-id header-input-sm">
                    <MasterWidget
                        windowType={windowType}
                        dataId={dataId}
                        widgetData={[docNoData]}
                        noLabel={true}
                        icon={true}
                        {...docNo}
                    />
                </span>}

                {docSummaryData && <div className="header-breadcrumb">
                    <span>{docSummaryData.value}</span>
                </div>}

                {siteName && <span className="divider">/</span>}

                {siteName && <div className="header-breadcrumb">
                    <span>{siteName}</span>
                </div>}

            </span>
        )
	}
}

Breadcrumb.propTypes = {
	dispatch: PropTypes.func.isRequired
};

Breadcrumb = connect()(Breadcrumb)

export default Breadcrumb
