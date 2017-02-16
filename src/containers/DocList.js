import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import DocumentList from '../components/app/DocumentList';
import Container from '../components/Container';
import Modal from '../components/app/Modal';
import RawModal from '../components/app/RawModal';

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
            modalTitle: ''
        }
    }

    componentDidMount = () => {
        const {dispatch, windowType, latestNewDocument} = this.props;

        dispatch(getWindowBreadcrumb(windowType));

        if(latestNewDocument){
            dispatch(selectTableItems([latestNewDocument]));
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

    render() {
        const {
            windowType, breadcrumb, query, actions, modal, selected, references,
            rawModal
        } = this.props;

        const {
            modalTitle
        } = this.state;

        return (
            <Container
                entity="documentView"
                breadcrumb={breadcrumb}
                windowType={windowType}
                actions={actions}
                references={references}
                query={query}
            >
                {modal.visible &&
                    <Modal
                        windowType={modal.type}
                        data={modal.data}
                        layout={modal.layout}
                        rowData={modal.rowData}
                        tabId={modal.tabId}
                        rowId={modal.rowId}
                        modalTitle={modal.title}
                        modalType={modal.modalType}
                        modalViewId={modal.viewId}
                        query={query}
                        selected={selected}
                        viewId={query.viewId}
                     />
                 }
                 {rawModal.visible &&
                     <RawModal
                         modalTitle={modalTitle}
                     >
                         <DocumentList
                             type="grid"
                             windowType={parseInt(rawModal.type)}
                             defaultViewId={rawModal.viewId}
                             selected={selected}
                             setModalTitle={this.setModalTitle}
                         />
                     </RawModal>
                 }
                 <DocumentList
                     type="grid"
                     updateUri={this.updateUriCallback}
                     windowType={parseInt(windowType)}
                     defaultViewId={query.viewId}
                     defaultSort={query.sort}
                     defaultPage={parseInt(query.page)}
                     refType={query.refType}
                     refId={query.refId}
                     selected={selected}
                     inBackground={rawModal.visible}
                     fetchQuickActionsOnInit={true}
                 />
            </Container>
        );
    }
}

DocList.propTypes = {
    dispatch: PropTypes.func.isRequired,
    breadcrumb: PropTypes.array.isRequired,
    query: PropTypes.object.isRequired,
    search: PropTypes.string.isRequired,
    pathname: PropTypes.string.isRequired,
    modal: PropTypes.object.isRequired,
    rawModal: PropTypes.object.isRequired,
    selected: PropTypes.array,
    actions: PropTypes.array.isRequired,
    references: PropTypes.array.isRequired
}

function mapStateToProps(state) {
    const { windowHandler, menuHandler, routing } = state;

    const {
        modal,
        rawModal,
        selected,
        latestNewDocument
    } = windowHandler || {
        modal: false,
        rawModal: false,
        selected: [],
        latestNewDocument: null
    }

    const {
        actions,
        references,
        breadcrumb
    } = menuHandler || {
        actions: [],
        refereces: [],
        breadcrumb: []
    }

    const {
        search,
        pathname
    } = routing.locationBeforeTransitions || {
        search: '',
        pathname: ''
    }


    return {
        modal,
        breadcrumb,
        search,
        pathname,
        actions,
        selected,
        latestNewDocument,
        references,
        rawModal
    }
}

DocList = connect(mapStateToProps)(DocList)

export default DocList;
