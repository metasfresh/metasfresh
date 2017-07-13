import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import counterpart from 'counterpart';

import Indicator from './Indicator';
import {
    closeRawModal
} from '../../actions/WindowActions';

import {
    deleteView
} from '../../actions/AppActions';

import keymap from '../../keymap.js';
import ModalContextShortcuts from '../shortcuts/ModalContextShortcuts';
import { ShortcutManager } from 'react-shortcuts';
import Tooltips from '../tooltips/Tooltips.js';
const shortcutManager = new ShortcutManager(keymap);

class RawModal extends Component {
    constructor(props) {
        super(props);

        this.state = {
            scrolled: false,
            isTooltipShow: false
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

        modalContent &&
            modalContent.addEventListener('scroll', this.handleScroll);
    }

    componentWillUnmount() {
        const modalContent = document.querySelector('.js-panel-modal-content');

        modalContent &&
            modalContent.removeEventListener('scroll', this.handleScroll);
    }

    toggleTooltip = (visible) => {
        this.setState({
            isTooltipShow: visible
        });
    }

    getChildContext = () => {
        return { shortcuts: shortcutManager }
    }

    handleScroll = (event) => {
        const scrollTop = event.srcElement.scrollTop;

        this.setState({
            scrolled: scrollTop > 0
        })
    }

    handleClose = () => {
        const {closeCallback, viewId, windowType} = this.props;
        const {isNew} = this.state;

        closeCallback && closeCallback(isNew);
        deleteView(windowType, viewId);
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
            modalTitle, children, modalDescription
        } = this.props;

        const {
            scrolled, isTooltipShow
        } = this.state;

        return (
            <div
                className="screen-freeze raw-modal"
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
                            <span className="panel-modal-description">
                                {modalDescription ? modalDescription : ''}
                            </span>
                        </span>

                        <div className="items-row-2">
                            <button
                                className="btn btn-meta-outline-secondary btn-distance-3 btn-md"
                                onClick={this.handleClose}
                                tabIndex={0}
                                onMouseEnter={() =>
                                    this.toggleTooltip(true)
                                }
                                onMouseLeave={() => this.toggleTooltip(false)}
                            >
                                {counterpart.translate('modal.actions.done')}
                            {isTooltipShow &&
                                <Tooltips
                                    name={keymap.MODAL_CONTEXT.APPLY}
                                    action={counterpart.translate('modal.actions.done')}
                                    type={''}
                                />
                            }
                            </button>
                        </div>
                    </div>
                    <Indicator />
                    <div
                        className="panel-modal-content js-panel-modal-content"
                        ref={c => { c && c.focus()}}
                    >
                        {children}
                    </div>
                    <ModalContextShortcuts
                        apply={this.handleClose}
                    />
                </div>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    modalVisible: state.windowHandler.modal.visible || false
});

RawModal.childContextTypes = {
    shortcuts: PropTypes.object.isRequired
}

RawModal.propTypes = {
    dispatch: PropTypes.func.isRequired,
    modalVisible: PropTypes.bool
};

RawModal = connect(mapStateToProps)(RawModal)

export default RawModal
