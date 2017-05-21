import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import Moment from 'moment';
import Modal from '../components/app/Modal';

import {
    getAvailableLang,
    setUserLang,
    getNotifications,
    getNotificationsSuccess
} from '../actions/AppActions';

import Container from '../components/Container';
import RawList from '../components/widget/List/RawList';

class Settings extends Component {
    constructor(props){
        super(props);

        this.state={
            langs: [],
            value: '',
            hideHeader: false
        }
    }

    componentDidMount = () => {
        const {dispatch} = this.props;

        dispatch(getAvailableLang()).then(response => {
            const {values, defaultValue} = response.data;
            let chosenLang = '';

            values.map(item => {
                const key = Object.keys(item)[0];
                if(key === defaultValue){
                    chosenLang = item
                }
            })

            this.setState({
                langs: values,
                value: chosenLang
            })
        });
    }

    patch = (value) => {
        const {dispatch} = this.props;

        dispatch(setUserLang(value)).then(() => {
            this.setState({
                value: value
            }, () => {
                Moment.locale(Object.keys(value)[0]);
                this.refresh();
            });
        });
    }

    refresh = () => {
        const {dispatch} = this.props;
        this.setState({
            hideHeader: true
        }, () => {
            this.setState({
                hideHeader: false
            })
        })

        dispatch(getNotifications()).then(response => {
            dispatch(getNotificationsSuccess(
                response.data.notifications,
                response.data.unreadCount
            ));
        });
    }

    render() {
        const {rawModal, modal} = this.props;
        const {langs, value, hideHeader} = this.state;
        return (
            <Container
                hideHeader={hideHeader}
                siteName="Settings"
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
                        rawModalVisible={rawModal && rawModal.visible}
                     />
                 }
                <div className="window-wrapper">
                    <div className="row">
                        <div className="col-sm-6">
                            <div
                                tabIndex={0}
                                className="panel panel-spaced panel-distance panel-bordered panel-primary"
                            >
                                <div className="elements-line">
                                    <div className="form-group row">
                                        <div
                                            className="form-control-label col-sm-3"
                                        >
                                            Language
                                        </div>
                                        <div className="col-sm-9">
                                            <RawList
                                                list={langs}
                                                onSelect={option =>
                                                    this.patch(option)
                                                }
                                                selected={value}
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </Container>
        );
    }
}

function mapStateToProps(state) {
    const { windowHandler } = state;

    const {
        modal,
        rawModal
    } = windowHandler || {
        modal: {},
        rawModal: {}
    };

    return {
        modal,
        rawModal
    }
}

Settings.propTypes = {
    dispatch: PropTypes.func.isRequired,
    modal: PropTypes.object.isRequired,
    rawModal: PropTypes.object.isRequired
};

Settings = connect(mapStateToProps)(Settings);

export default Settings
