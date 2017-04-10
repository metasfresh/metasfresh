import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import Header from './header/Header';
import ErrorScreen from './app/ErrorScreen';

class Container extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {
            docActionElem, docStatusData, docNoElement, docNoData,
            docSummaryData, dataId, windowType, breadcrumb, references, actions,
            showSidelist, siteName, connectionError, noMargin, entity, children,
            query, attachments, showIndicator, isDocumentNotSaved, hideHeader
        } = this.props;

        return (
            <div>
                {
                    // Forcing refresh component
                    !hideHeader && <Header
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
                        showIndicator={showIndicator}
                        isDocumentNotSaved={isDocumentNotSaved}
                    />
                }
                {connectionError && <ErrorScreen />}
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
