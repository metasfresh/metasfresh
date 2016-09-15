import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import {push} from 'react-router-redux';
import '../../assets/css/header.css';

import {
    openModal
} from '../../actions/WindowActions';

class Subheader extends Component {
    constructor(props){
        super(props);
    }
    redirect = (where) => {
        const {dispatch} = this.props;
        dispatch(push(where));
    }
    openModal = (windowType) => {
        this.props.dispatch(openModal("Advanced edit", windowType));
    }
    render() {
        const { windowType } = this.props;
        return (
            <div className={"subheader-container overlay-shadow " + (this.props.open ? "subheader-open" : "")}>
                <div className="container">
                    <div className="row">
                        <div className="subheader-row">
                            <div className=" subheader-column">
                                <div className="subheader-item" onClick={()=> this.redirect('/window/143/NEW')}>
                                    <i className="meta-icon-report-1" /> New
                                </div>
                                <div className="subheader-item" onClick={()=> this.openModal(windowType + '&advanced=true')}><i className="meta-icon-edit" /> Advanced Edit</div>
                                <div className="subheader-item"><i className="meta-icon-print" /> Print</div>
                                <div className="subheader-item"><i className="meta-icon-message" /> Send message</div>
                                <div className="subheader-item"><i className="meta-icon-duplicate" /> Clone</div>
                                <div className="subheader-item"><i className="meta-icon-delete" /> Delete</div>
                                <div className="subheader-item"><i className="meta-icon-settings" /> Settings</div>
                                <div className="subheader-item"><i className="meta-icon-logout" /> Log out</div>
                            </div>
                            <div className=" subheader-column">
                                <div className="subheader-header">Actions</div>
                                <div className="subheader-item">Auftrag aus Angebot</div>
                                <div className="subheader-item">Bestellkontrolle zum Auf</div>
                                <div className="subheader-item">Generate PO from Sales order</div>
                                <div className="subheader-item">Geschaftspartnerbeziehung erstellen</div>
                            </div>
                            <div className=" subheader-column">
                                <div className="subheader-header">Reports</div>
                                <div className="subheader-item"><i className="meta-icon-generate-1" /> Generate new raport</div>
                                <div className="subheader-break" />
                                <div className="subheader-item"> <i className="meta-icon-report-1" /> 14/06/2016 14:13</div>
                                <div className="subheader-item"> <i className="meta-icon-report-1" /> 14/06/2016 14:09</div>
                                <div className="subheader-item"> <i className="meta-icon-report-1" /> 13/06/2016 09:36</div>
                            </div>
                            <div className=" subheader-column">
                                <div className="subheader-header">Attachments</div>
                                <div className="subheader-item"><i className="meta-icon-generate-1" /> Generate new raport</div>
                                <div className="subheader-break" />
                                <div className="subheader-item"> <i className="meta-icon-file" /> file_name.jpg</div>
                                <div className="subheader-item"> <i className="meta-icon-file" /> file_name.jpg</div>
                                <div className="subheader-item"> <i className="meta-icon-file" /> file_name.jpg</div>
                                <div className="subheader-item"> <i className="meta-icon-file" /> file_name.jpg</div>
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
function mapStateToProps(state) {
    return {
    }
}
Subheader = connect(mapStateToProps)(Subheader);

export default Subheader;
