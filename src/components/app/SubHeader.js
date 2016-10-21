import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import {push} from 'react-router-redux';
import '../../assets/css/header.css';

import {
    openModal
} from '../../actions/WindowActions';

import {
    setFilter
} from '../../actions/ListActions';

import {
    getRelatedDocuments,
    setReferences,
    getDocumentActions,
    setActions
} from '../../actions/MenuActions';

class Subheader extends Component {
    constructor(props){
        super(props);
    }
    componentDidMount() {
        const {dispatch, windowType, dataId} = this.props;
        windowType && dataId && dispatch(getRelatedDocuments(windowType, dataId)).then((response) => {
            dispatch(setReferences(response.data.references));
        });
        windowType && dataId && dispatch(getDocumentActions(windowType, dataId)).then((response) => {
            dispatch(setActions(response.data.actions));
        });
    }

    redirect = (where) => {
        const {dispatch} = this.props;
        dispatch(push(where));
    }

    openModal = (windowType) => {
        this.props.dispatch(openModal("Advanced edit", windowType));
    }

    handleReferenceClick = (type, filter) => {
        const {dispatch} = this.props;
        dispatch(setFilter(filter));
        dispatch(push("/window/" + type));
    }

    render() {
        const { windowType, onClick, references, actions, dataId } = this.props;
        return (
            <div className={"subheader-container overlay-shadow subheader-open"}>
                <div className="container-fluid">
                    <div className="row">
                        <div className="subheader-row">
                            <div className=" subheader-column" onClick = {onClick}>
                                {windowType && <div className="subheader-item" onClick={()=> this.redirect('/window/'+ windowType +'/new')}>
                                    <i className="meta-icon-report-1" /> New
                                </div>}
                                {dataId && <div className="subheader-item" onClick={()=> this.openModal(windowType + '&advanced=true')}><i className="meta-icon-edit" /> Advanced Edit</div>}
                                <div className="subheader-item"><i className="meta-icon-print" /> Print</div>
                                <div className="subheader-item"><i className="meta-icon-message" /> Send message</div>
                                <div className="subheader-item"><i className="meta-icon-duplicate" /> Clone</div>
                                <div className="subheader-item"><i className="meta-icon-delete" /> Delete</div>
                                <div className="subheader-item"><i className="meta-icon-settings" /> Settings</div>
                                <div className="subheader-item" onClick={()=> this.redirect('/logout')}><i className="meta-icon-logout" /> Log out</div>
                            </div>
                            <div className=" subheader-column">
                                <div className="subheader-header">Actions</div>
                                <div className="subheader-break" />
                                { actions && !!actions.length ? actions.map((item, key) =>
                                    <div
                                        className="subheader-item"
                                        onClick={() => this.redirect("/window/" + item.actionId)}
                                        key={key}
                                    >
                                        {item.caption}
                                    </div>
                                ) : <div className="subheader-item subheader-item-disabled">There is no actions</div>}
                            </div>
                            <div className=" subheader-column">
                                <div>
                                    <div className="subheader-header">Referenced documents</div>
                                    <div className="subheader-break" />
                                    { references && !!references.length ? references.map((item, key) =>
                                        <div
                                            className="subheader-item"
                                            onClick={() => this.handleReferenceClick(item.documentType, item.filter)}
                                            key={key}
                                        >
                                            {item.caption}
                                        </div>
                                    ) : <div className="subheader-item subheader-item-disabled">There is no referenced document</div>}
                                </div>
                            </div>
                            <div className="subheader-column">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}





Subheader.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Subheader = connect()(Subheader);

export default Subheader;
