import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Header from './header/Header';
import ErrorScreen from './app/ErrorScreen';
import NotificationHandler from './notifications/NotificationHandler';

class Container extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {
            docActionElem, docStatusData, docNoElement, docNoData, docSummaryData,
            dataId, windowType, breadcrumb, references, actions, showSidelist,
            siteName, connectionError, noMargin, entity, children, query, attachments
        } = this.props;

        return (
            <div>
                <Header
                    entity={entity}
                    docStatus = {docActionElem}
                    docStatusData = {docStatusData}
                    docNo = {docNoElement}
                    docNoData = {docNoData}
                    docSummaryData = {docSummaryData}
                    dataId={dataId}
                    windowType={windowType}
                    breadcrumb={breadcrumb}
                    references={references}
                    actions={actions}
                    attachments={attachments}
                    showSidelist={showSidelist}
                    siteName = {siteName}
                    query={query}
                />
                {connectionError && <ErrorScreen />}
                <NotificationHandler />
                <div
                    className={
                        'header-sticky-distance js-unselect ' +
                        (noMargin ? 'dashboard' : 'container-fluid')
                    }
                >
                    {children}
                </div>
            </div>
        );
    }
}

Container.propTypes = {
    connectionError: PropTypes.bool
};

const mapStateToProps = state => ({
    connectionError: state.windowHandler.connectionError || false
});

export default connect(mapStateToProps)(Container);
