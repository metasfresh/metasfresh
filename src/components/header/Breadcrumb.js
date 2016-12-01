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

	render() {
        const {breadcrumb, windowType, docNo, docNoData, docSummaryData, dataId, siteName, menuOverlay, handleMenuOverlay} = this.props;

        return (
            <span className="header-breadcrumb">
                {breadcrumb && breadcrumb.map((item, index) =>
                    <span key={index}>
                        {!!index && <span className="divider">/</span>}
                        <span title={(index === 0 ? "" : item.children.captionBreadcrumb)}
                            className={ (!item.children.elementId ? "menu-overlay-expand " : (docNo ? 'menu-overlay-link' : '')) + (index === 0 ? "ico-home" : "")}
                            onClick={ !item.children.elementId ?  e => handleMenuOverlay(e, item.nodeId) : (windowType ? e => this.linkToPage(windowType) : '' )}
                        >
                            {(index === 0) ? <i className="meta-icon-menu" /> : item && item.children && item.children.captionBreadcrumb}
                        </span>
                        {menuOverlay === item.nodeId &&
                            <MenuOverlay
                                nodeId={item.nodeId}
                                node={item}
                                onClickOutside={e => handleMenuOverlay(e, "")}
                                disableOnClickOutside={menuOverlay !== item.nodeId}
                                siteName={siteName}
                                index={index}
                            />
                        }
                    </span>
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
