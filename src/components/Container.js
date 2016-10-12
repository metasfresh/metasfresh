import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Header from '../components/app/Header';
import NotificationHandler from '../components/app/NotificationHandler';

class Container extends Component {
    constructor(props){
        super(props);
    }
    render() {

        const {
            docActionElem,
            docStatusData,
            docNoElement,
            docNoData,
            docSummaryData,
            dataId,
            windowType,
            breadcrumb,
            references,
            showSidelist,
            siteName,
            connectionError
        } = this.props;

        return (
            <div>
                <Header
                    docStatus = {docActionElem}
                    docStatusData = {docStatusData}
                    docNo = {docNoElement}
                    docNoData = {docNoData}
                    docSummaryData = {docSummaryData}
                    dataId={dataId}
                    windowType={windowType}
                    breadcrumb={breadcrumb}
                    references={references}
                    showSidelist={showSidelist}
                    siteName = {siteName}
                />
                {connectionError && <ErrorScreen />}
                <NotificationHandler />
                <div className="header-sticky-distance container-fluid">
                    {this.props.children}
                </div>
            </div>
        );
    }
}

export default Container;
