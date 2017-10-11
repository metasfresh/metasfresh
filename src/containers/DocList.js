import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import DocumentList from '../components/app/DocumentList';
import Container from '../components/Container';

import {
    getWindowBreadcrumb
} from '../actions/MenuActions';

import {
    updateUri
} from '../actions/AppActions';

import {
    selectTableItems,
    setLatestNewDocument
} from '../actions/WindowActions';

class DocList extends Component {
    constructor(props){
        super(props);

        this.state = {
            modalTitle: '',
            modalDescription: '',
            notfound: false
        }
    }

    componentDidMount = () => {
        const {dispatch, windowType, latestNewDocument} = this.props;
        dispatch(getWindowBreadcrumb(windowType));

        if(latestNewDocument){
            dispatch(selectTableItems([latestNewDocument], windowType));
            dispatch(setLatestNewDocument(null));
        }
    }

    componentDidUpdate = (prevProps) => {
        const {dispatch, windowType} = this.props;

        if(prevProps.windowType !== windowType){
            dispatch(getWindowBreadcrumb(windowType));
        }
    }

    updateUriCallback = (prop, value) => {
        const {dispatch, query, pathname} = this.props;
        dispatch(updateUri(pathname, query, prop, value));
    }

    setModalTitle = (title) => {
        this.setState({
            modalTitle: title
        })
    }

    setModalDescription = (desc) => {
        this.setState({
            modalDescription: desc
        })
    }

    setNotFound = (isNotFound) => {
        this.setState({
            notfound: isNotFound
        })
    }

    render() {
        const {
            windowType, breadcrumb, query, modal, selected, rawModal,
            indicator, processStatus, includedView, selectedWindowType
        } = this.props;

        const {
            modalTitle, notfound, modalDescription
        } = this.state;

        let refRowIds = [];
        if (query && query.refRowIds) {
            try {
                refRowIds = JSON.parse(query.refRowIds);
            }
            catch (e) {
                refRowIds = [];
            }
        }

        return (
            <Container
                entity="documentView"
                {...{modal, rawModal, breadcrumb, windowType, query, notfound,
                    selected, selectedWindowType, indicator, modalTitle,
                    processStatus, includedView}}
                setModalTitle={this.setModalTitle}
                setModalDescription={this.setModalDescription}
                modalDescription={modalDescription}
                showIndicator={!modal.visible && !rawModal.visible}
            >
                <div className="document-lists-wrapper">
                    <DocumentList
                        type="grid"
                        updateUri={this.updateUriCallback}
                        windowType={windowType}
                        defaultViewId={query.viewId}
                        defaultSort={query.sort}
                        defaultPage={parseInt(query.page)}
                        refType={query.refType}
                        refId={query.refId}
                        refTabId={query.refTabId}
                        refRowIds={refRowIds}
                        selectedWindowType={selectedWindowType}
                        selected={selected}
                        includedView={includedView}
                        inBackground={rawModal.visible}
                        inModal={modal.visible}
                        fetchQuickActionsOnInit={true}
                        processStatus={processStatus}
                        disablePaginationShortcuts={
                            modal.visible || rawModal.visible
                        }
                        setNotFound={this.setNotFound}
                        notfound={notfound}
                    />

                    {(includedView && includedView.windowType &&
                        includedView.viewId && !rawModal.visible &&
                        !modal.visible
                    ) && (
                        <DocumentList
                            type="includedView"
                            selected={selected}
                            selectedWindowType={selectedWindowType}
                            windowType={includedView.windowType}
                            defaultViewId={includedView.viewId}
                            fetchQuickActionsOnInit={true}
                            processStatus={processStatus}
                            isIncluded={true}
                            inBackground={false}
                            inModal={false}
                        />
                    )}
                </div>
            </Container>
        );
    }
}

DocList.propTypes = {
    dispatch: PropTypes.func.isRequired,
    breadcrumb: PropTypes.array.isRequired,
    query: PropTypes.object.isRequired,
    includedView: PropTypes.object.isRequired,
    pathname: PropTypes.string.isRequired,
    modal: PropTypes.object.isRequired,
    rawModal: PropTypes.object.isRequired,
    selected: PropTypes.array,
    indicator: PropTypes.string.isRequired,
    processStatus: PropTypes.string.isRequired
}

function mapStateToProps(state) {
    const {
        windowHandler, menuHandler, listHandler, appHandler, routing
    } = state;

    const {
        modal,
        rawModal,
        selected,
        selectedWindowType,
        latestNewDocument,
        indicator
    } = windowHandler || {
        modal: false,
        rawModal: false,
        selected: [],
        selectedWindowType: null,
        latestNewDocument: null,
        indicator: ''
    }

    const {
        includedView
    } = listHandler || {
        includedView: {}
    }

    const {
        processStatus
    } = appHandler || {
        processStatus: ''
    }

    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: []
    }

    const {
        pathname
    } = routing.locationBeforeTransitions || {
        pathname: ''
    }

    return {
        modal, breadcrumb, pathname, selected, indicator, includedView,
        latestNewDocument, rawModal, processStatus, selectedWindowType
    }
}

DocList = connect(mapStateToProps)(DocList)

export default DocList;
