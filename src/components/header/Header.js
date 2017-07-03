import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {push, replace} from 'react-router-redux';
import counterpart from 'counterpart';

import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

import Subheader from './SubHeader';
import UserDropdown from './UserDropdown';
import Breadcrumb from './Breadcrumb';
import MasterWidget from '../widget/MasterWidget';
import SideList from './SideList';
import Indicator from '../app/Indicator';
import Inbox from '../inbox/Inbox';
import Tooltips from '../tooltips/Tooltips';
import Prompt from '../app/Prompt';

import {
    openModal
} from '../../actions/WindowActions';

import {
    deleteRequest,
    openFile
} from '../../actions/GenericActions';

import keymap from '../../keymap.js';
import GlobalContextShortcuts from '../shortcuts/GlobalContextShortcuts';
import { ShortcutManager } from 'react-shortcuts';
const shortcutManager = new ShortcutManager(keymap);

class Header extends Component {
    constructor(props){
        super(props);

        this.state = {
            isSubheaderShow: false,
            isSideListShow: false,
            sideListTab: null,
            isMenuOverlayShow: false,
            menuOverlay: null,
            scrolled: false,
            isInboxOpen: false,
            isUDOpen: false,
            tooltipOpen: '',
            prompt: {
                open: false
            }
        }
    }

    componentDidMount() {
        document.addEventListener('scroll', this.handleScroll);
    }

    componentWillUnmount() {
        document.removeEventListener('scroll', this.handleScroll);
        this.toggleScrollScope(false);
    }

    componentWillUpdate = (nextProps) => {
        const {dropzoneFocused} = this.props;
        if(
            nextProps.dropzoneFocused !== dropzoneFocused &&
            nextProps.dropzoneFocused
        ){
            this.closeOverlays();
        }
    }
    
    componentDidUpdate = (prevProps) => {
        const {dispatch, pathname} = this.props;
        if(
            prevProps.me.language !== undefined &&
            JSON.stringify(prevProps.me.language) !==
            JSON.stringify(this.props.me.language)
        ){
            dispatch(replace(''));
            dispatch(replace(pathname));
        }
    }

    getChildContext = () => {
        return { shortcuts: shortcutManager }
    }

    handleInboxOpen = (state) => {
        this.setState({
            isInboxOpen: !!state
        });
    }

    handleUDOpen = (state) => {
        this.setState({
            isUDOpen: !!state
        })
    }

    handleMenuOverlay = (e, nodeId) => {
        const {isSubheaderShow, isSideListShow} = this.state;
        e && e.preventDefault();

        let toggleBreadcrumb = () => {
            this.setState({
                menuOverlay: nodeId
            }, () => {
                if(nodeId !== '') {
                    this.setState({
                        isMenuOverlayShow: true
                    });
                } else {
                    this.setState({
                        isMenuOverlayShow: false
                    });
                }
            });
        }

        if(!isSubheaderShow && !isSideListShow){
            toggleBreadcrumb();
        }
    }

    handleScroll = (event) => {
        const target = event.srcElement;
        let scrollTop = target && target.body.scrollTop;

        if(!scrollTop){
            scrollTop = document.documentElement.scrollTop;
        }

        if(scrollTop > 0) {
            this.setState({
                scrolled: true
            })
        } else {
            this.setState({
                scrolled: false
            })
        }
    }

    handleDashboardLink = () => {
        const {dispatch} = this.props;
        dispatch(push('/'));
    }

    toggleScrollScope = (open) => {
        if(!open){
            document.body.style.overflow = 'auto';
        }else{
            document.body.style.overflow = 'hidden';
        }
    }

    toggleTooltip = (tooltip) => {
        this.setState({
            tooltipOpen: tooltip
        });
    }

    openModal = (windowType, type, caption, isAdvanced) => {
        const {dispatch, query} = this.props;
        dispatch(openModal(
            caption, windowType, type, null, null, isAdvanced,
            query && query.viewId
        ));
    }

    handlePrint = (windowType, docId, docNo) => {
        openFile(
            'window', windowType, docId, 'print',
            windowType + '_' + (docNo ? docNo : docId) + '.pdf'
        );
    }

    handleDelete = () => {
        this.setState({
            prompt: Object.assign({}, this.state.prompt, {
                open: true
            })
        });
    }

    handlePromptCancelClick = () => {
        this.setState({
            prompt: Object.assign({}, this.state.prompt, {
                open: false
            })
        });
    }

    handlePromptSubmitClick = (windowType, docId) => {
        const {dispatch, handleDeletedStatus} = this.props;

        this.setState({
            prompt: Object.assign({}, this.state.prompt, {
                open: false
            })
        }, () => {
            deleteRequest('window', windowType, null, null, [docId])
                .then(() => {
                    handleDeletedStatus(true);
                    dispatch(push('/window/' + windowType));
                });
            }
        );
    }

    handleDocStatusToggle = (close) => {
        const elem = document.getElementsByClassName('js-dropdown-toggler')[0];

        if(close) {
            elem.blur();
        } else {
            if(document.activeElement === elem) {
                elem.blur();
            } else {
                elem.focus();
            }
        }
    }

    handleSidelistToggle = (id = null, sideListTab) => {

        this.toggleScrollScope(id !== null);

        this.setState({
            isSideListShow: id !== null && id !== sideListTab,
            sideListTab: id !== sideListTab ? id : null
        });
    }

    closeOverlays = (clickedItem, callback) => {
        const {isSubheaderShow} = this.state;

        this.setState({
            menuOverlay: null,
            isMenuOverlayShow: false,
            isInboxOpen: false,
            isUDOpen: false,
            isSideListShow: false,
            sideListTab: null,
            isSubheaderShow:
                (clickedItem == 'isSubheaderShow' ? !isSubheaderShow : false),
            tooltipOpen: ''
        }, callback);

        if(
            document.getElementsByClassName('js-dropdown-toggler')[0] &&
            (clickedItem != 'dropdown')
        ){
            this.handleDocStatusToggle(true);
        }
    }

    redirect = (where) => {
        const {dispatch} = this.props;
        dispatch(push(where));
    }

    render() {
        const {
            docSummaryData, siteName, docNoData, docStatus,
            docStatusData, windowType, dataId, breadcrumb, showSidelist,
            inbox, selected, entity, query, showIndicator, isDocumentNotSaved,
            selectedWindowType, notfound, docId, me, editmode,
            handleEditModeToggle
        } = this.props;

        const {
            isSubheaderShow, isSideListShow, menuOverlay, isInboxOpen, scrolled,
            isMenuOverlayShow, tooltipOpen, prompt, sideListTab, isUDOpen
        } = this.state;

        return (
            <div>
            {
                prompt.open &&
                <Prompt
                    title="Delete"
                    text="Are you sure?"
                    buttons={{submit: 'Delete', cancel: 'Cancel'}}
                    onCancelClick={this.handlePromptCancelClick}
                    onSubmitClick={() =>
                        this.handlePromptSubmitClick(windowType, dataId)
                    }
                />
            }

                <nav
                    className={
                        'header header-super-faded js-not-unselect ' +
                        (scrolled ? 'header-shadow': '')
                    }
                >
                    <div className="container-fluid">
                        <div className="header-container">
                            <div className="header-left-side">
                                <div
                                    onClick={() =>
                                        this.closeOverlays('isSubheaderShow')
                                    }
                                    onMouseEnter={() =>
                                        this.toggleTooltip(
                                            keymap
                                                .GLOBAL_CONTEXT
                                                .OPEN_ACTIONS_MENU
                                        )
                                    }
                                    onMouseLeave={() => this.toggleTooltip('')}
                                    className={
                                        'btn-square btn-header ' +
                                        'tooltip-parent ' +
                                        (isSubheaderShow ?
                                            'btn-meta-default-dark ' +
                                            'btn-subheader-open btn-header-open'
                                            : 'btn-meta-primary')
                                        }
                                >
                                    <i className="meta-icon-more" />

                                    {tooltipOpen ===
                                        keymap.GLOBAL_CONTEXT.OPEN_ACTIONS_MENU
                                        && <Tooltips
                                            name={
                                                keymap
                                                    .GLOBAL_CONTEXT
                                                    .OPEN_ACTIONS_MENU
                                            }
                                            action={
                                                counterpart.translate(
                                                'mainScreen.actionMenu.tooltip'
                                                )
                                            }
                                            type={''}
                                        /> }
                                </div>

                                <Breadcrumb
                                    {...{breadcrumb, windowType, docSummaryData,
                                        dataId, siteName, menuOverlay, docId,
                                        isDocumentNotSaved}}
                                    handleMenuOverlay={this.handleMenuOverlay}
                                    openModal={this.openModal}
                                />

                            </div>
                            <div className="header-center">
                                <img
                                    src={logo}
                                    className="header-logo pointer"
                                    onClick={() => this.handleDashboardLink()}
                                />
                            </div>
                            <div className="header-right-side">
                                {docStatus &&
                                    <div
                                        className="hidden-sm-down tooltip-parent"
                                        onClick={() => this.toggleTooltip('')}
                                        onMouseEnter={() =>
                                            this.toggleTooltip(
                                                keymap
                                                    .GLOBAL_CONTEXT
                                                    .DOC_STATUS
                                            )
                                        }
                                        onMouseLeave={() =>
                                            this.toggleTooltip('')
                                        }
                                    >
                                        <MasterWidget
                                            entity="window"
                                            windowType={windowType}
                                            dataId={dataId}
                                            widgetData={[docStatusData]}
                                            noLabel={true}
                                            type="primary"
                                            dropdownOpenCallback={()=>{
                                                this.closeOverlays('dropdown')
                                            }}
                                            {...docStatus}
                                        />
                                        { tooltipOpen ===
                                            keymap
                                                .GLOBAL_CONTEXT
                                                .DOC_STATUS
                                            && <Tooltips
                                                name={
                                                    keymap
                                                        .GLOBAL_CONTEXT
                                                        .DOC_STATUS
                                                }
                                                action= {
                                                counterpart.translate(
                                                'mainScreen.docStatus.tooltip'
                                                )
                                                }
                                                type={''}
                                            />
                                        }
                                    </div>
                                }

                                <div
                                    className={
                                        'header-item-container ' +
                                        'header-item-container-static ' +
                                        'pointer tooltip-parent ' +
                                        (isInboxOpen ? 'header-item-open ' : '')
                                    }
                                    onClick={() =>
                                        this.closeOverlays('', () =>
                                            this.handleInboxOpen(true)
                                    )}
                                    onMouseEnter={() =>
                                        this.toggleTooltip(
                                            keymap
                                                .GLOBAL_CONTEXT
                                                .OPEN_INBOX_MENU
                                        )
                                    }
                                    onMouseLeave={() => this.toggleTooltip('')}
                                >
                                    <span
                                        className="header-item header-item-badge icon-lg"
                                    >
                                        <i
                                            className="meta-icon-notifications"
                                        />
                                        {inbox.unreadCount > 0 &&
                                            <span
                                                className="notification-number"
                                            >
                                                {inbox.unreadCount}
                                            </span>
                                        }
                                    </span>
                                    { tooltipOpen ===
                                        keymap
                                            .GLOBAL_CONTEXT
                                            .OPEN_INBOX_MENU &&
                                        <Tooltips
                                            name={
                                                keymap
                                                    .GLOBAL_CONTEXT
                                                    .OPEN_INBOX_MENU
                                            }
                                            action= {
                                                counterpart.translate(
                                                    'mainScreen.inbox.tooltip'
                                                )
                                            }
                                            type={''}
                                        />
                                    }
                                </div>

                                <Inbox
                                    open={isInboxOpen}
                                    close={this.handleInboxOpen}
                                    disableOnClickOutside={!isInboxOpen}
                                    inbox={inbox}
                                />

                                <UserDropdown
                                    open={isUDOpen}
                                    handleUDOpen={this.handleUDOpen}
                                    disableOnClickOutside={!isUDOpen}
                                    redirect={this.redirect}
                                    shortcut={
                                        keymap.GLOBAL_CONTEXT.OPEN_AVATAR_MENU}
                                    toggleTooltip={this.toggleTooltip}
                                    {...{tooltipOpen, me}}
                                />

                                {showSidelist &&
                                    <div
                                        className={
                                            'tooltip-parent btn-header ' +
                                            'side-panel-toggle btn-square ' +
                                            (isSideListShow ?
                                                'btn-meta-default-bright ' +
                                                'btn-header-open'
                                                : 'btn-meta-primary')
                                        }
                                        onClick={() => {
                                            this.closeOverlays();
                                            this.handleSidelistToggle(0);
                                        }}
                                        onMouseEnter={() =>
                                            this.toggleTooltip(
                                                keymap
                                                    .GLOBAL_CONTEXT
                                                    .OPEN_SIDEBAR_MENU_0
                                            )
                                        }
                                        onMouseLeave={() =>
                                            this.toggleTooltip('')
                                        }
                                    >
                                        <i className="meta-icon-list" />
                                        { tooltipOpen ===
                                            keymap
                                                .GLOBAL_CONTEXT
                                                .OPEN_SIDEBAR_MENU_0 &&
                                            <Tooltips
                                                name={
                                                    keymap
                                                        .GLOBAL_CONTEXT
                                                        .OPEN_SIDEBAR_MENU_0
                                                }
                                                action={
                                                    counterpart.translate(
                                                    'mainScreen.sideList.tooltip'
                                                    )
                                                }
                                                type={''}
                                            />
                                        }

                                    </div>
                                }
                            </div>
                        </div>
                    </div>
                    {showIndicator && <Indicator {...{isDocumentNotSaved}}/>}
                </nav>

                {isSubheaderShow && <Subheader
                    closeSubheader={() => this.closeOverlays('isSubheaderShow')}
                    docNo={docNoData && docNoData.value}
                    openModal={this.openModal}
                    handlePrint={this.handlePrint}
                    handleDelete={this.handleDelete}
                    redirect={this.redirect}
                    disableOnClickOutside={!isSubheaderShow}
                    {...{breadcrumb, notfound, query, entity,
                        selectedWindowType, selected, dataId, windowType,
                        siteName, editmode, handleEditModeToggle
                    }}
                />}

                {showSidelist && isSideListShow && <SideList
                    windowType={windowType ? windowType : ''}
                    closeOverlays={this.closeOverlays}
                    closeSideList={this.handleSidelistToggle}
                    isSideListShow={isSideListShow}
                    disableOnClickOutside={!showSidelist}
                    docId={dataId}
                    defaultTab={sideListTab}
                    open={true}
                />}

                <GlobalContextShortcuts
                    handleSidelistToggle={(id) =>
                        showSidelist &&
                        this.handleSidelistToggle(id, sideListTab)}
                    handleMenuOverlay={isMenuOverlayShow ?
                        () => this.handleMenuOverlay('', '') :
                        () => this.closeOverlays('',
                            ()=> this.handleMenuOverlay('', '0')
                        )
                    }
                    handleInboxOpen = {isInboxOpen ?
                        () => this.handleInboxOpen(false) :
                        () => this.handleInboxOpen(true)
                    }
                    handleUDOpen = {() => this.handleUDOpen(!isUDOpen)}
                    openModal = {dataId ?
                        () => this.openModal(
                            windowType, 'window', 'Advanced edit', true
                        ) : ''
                    }
                    handlePrint={dataId ?
                        () => this.handlePrint(
                            windowType, dataId, docNoData.value
                        ) : ''
                    }
                    handleDelete={dataId ? this.handleDelete: ''}
                    redirect={windowType ?
                        () => this.redirect('/window/'+ windowType +'/new') : ''
                    }
                    handleDocStatusToggle={
                        document
                            .getElementsByClassName('js-dropdown-toggler')[0] ?
                            this.handleDocStatusToggle : ''
                    }
                    handleEditModeToggle={handleEditModeToggle}
                    closeOverlays={this.closeOverlays}
                />
            </div>
        )
    }
}

Header.propTypes = {
    dispatch: PropTypes.func.isRequired,
    selected: PropTypes.array.isRequired,
    inbox: PropTypes.object.isRequired,
    me: PropTypes.object.isRequired
};

function mapStateToProps(state) {
    const {windowHandler, appHandler, routing} = state;

    const {
        inbox,
        me
    } = appHandler || {
        inbox: {},
        me: {}
    }

    const {
        selected,
        selectedWindowType
    } = windowHandler || {
        selected: [],
        selectedWindowType: null
    }

    const {
        pathname
    } = routing.locationBeforeTransitions || {
        pathname: ''
    }

    return {
        selected,
        inbox,
        selectedWindowType,
        me,
        pathname
    }
}

Header.childContextTypes = {
    shortcuts: PropTypes.object.isRequired
}

Header = connect(mapStateToProps)(Header)

export default Header
