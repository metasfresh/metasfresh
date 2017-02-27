import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    closeRawModal
} from '../../actions/WindowActions';

class RawModal extends Component {
    constructor(props) {
        super(props);

        this.state = {
            scrolled: false
        }
    }

    componentDidMount() {
        // Dirty solution, but use only if you need to
        // there is no way to affect body
        // because body is out of react app range
        // and css dont affect parents
        // but we have to change scope of scrollbar
        document.body.style.overflow = 'hidden';

        const modalContent = document.querySelector('.js-panel-modal-content')

        modalContent && modalContent.addEventListener('scroll', this.handleScroll);
    }

    componentWillUnmount() {
        const modalContent = document.querySelector('.js-panel-modal-content');

        modalContent && modalContent.removeEventListener('scroll', this.handleScroll);
    }

    handleScroll = (event) => {
        const scrollTop = event.srcElement.scrollTop;

        this.setState({
            scrolled: scrollTop > 0
        })
    }

    handleClose = () => {
        const {closeCallback} = this.props;
        const {isNew} = this.state;

        closeCallback && closeCallback(isNew);
        this.removeModal();
    }

    removeModal = () => {
        const {dispatch, modalVisible} = this.props;

        dispatch(closeRawModal());

        if (!modalVisible){
            document.body.style.overflow = 'auto';
        }
    }

    render() {
        const {
            modalTitle, children
        } = this.props;

        const {
            scrolled
        } = this.state;

        return (
            <div
                className="screen-freeze js-not-unselect raw-modal"
            >
                <div className="panel panel-modal panel-modal-primary">
                    <div
                        className={
                            'panel-modal-header ' +
                            (scrolled ? 'header-shadow': '')
                        }
                    >
                        <span className="panel-modal-header-title">
                            {modalTitle ? modalTitle : 'Modal'}
                        </span>
                        <div className="items-row-2">
                            <button
                                className="btn btn-meta-outline-secondary btn-distance-3 btn-md"
                                onClick={this.handleClose}
                                tabIndex={0}
                            >
                                Done
                            </button>
                        </div>
                    </div>
                    <div
                        className="panel-modal-content js-panel-modal-content"
                        ref={c => { c && c.focus()}}
                    >
                        {children}
                    </div>
                </div>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    modalVisible: state.windowHandler.modal.visible || false
});

RawModal.propTypes = {
    dispatch: PropTypes.func.isRequired,
    modalVisible: PropTypes.bool
};

RawModal = connect(mapStateToProps)(RawModal)

export default RawModal
