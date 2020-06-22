import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import classnames from 'classnames';

import { deleteRequest } from '../../api';
import { duplicateRequest, openFile } from '../../actions/GenericActions';
import { openModal } from '../../actions/WindowActions';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';
import keymap from '../../shortcuts/keymap';
import Indicator from '../app/Indicator';
import Prompt from '../app/Prompt';
import NewEmail from '../email/NewEmail';
import Inbox from '../inbox/Inbox';
import NewLetter from '../letter/NewLetter';
import GlobalContextShortcuts from '../keyshortcuts/GlobalContextShortcuts';
import Tooltips from '../tooltips/Tooltips';
import MasterWidget from '../widget/MasterWidget';
import Breadcrumb from './Breadcrumb';
import SideList from './SideList';
import Subheader from './SubHeader';
import UserDropdown from './UserDropdown';

/**
 * @file The Header component is shown in every view besides Modal or RawModal in frontend. It defines
 * the top bar with different menus and icons in metasfresh WebUI. It hosts the action menu,
 * breadcrumb, logo, notification menu, avatar and sidelist menu.
 * @module Header
 * @extends PureComponent
 */
class Header extends PureComponent {
  state = {
    isSubheaderShow: false,
    isSideListShow: false,
    sideListTab: null,
    isMenuOverlayShow: false,
    menuOverlay: null,
    scrolled: false,
    isInboxOpen: false,
    isUDOpen: false,
    tooltipOpen: '',
    isEmailOpen: false,
    prompt: { open: false },
  };

  udRef = React.createRef();
  inboxRef = React.createRef();

  componentDidMount() {
    this.initEventListeners();
  }

  componentWillUnmount() {
    this.toggleScrollScope(false);
    this.removeEventListeners();
  }

  UNSAFE_componentWillUpdate(nextProps) {
    const { dropzoneFocused } = this.props;

    if (
      nextProps.dropzoneFocused !== dropzoneFocused &&
      nextProps.dropzoneFocused
    ) {
      this.closeOverlays();
    }
  }

  componentDidUpdate(prevProps, prevState) {
    if (
      prevProps.me.language !== undefined &&
      JSON.stringify(prevProps.me.language) !==
        JSON.stringify(this.props.me.language) &&
      !Array.isArray(prevProps.me.language)
    ) {
      // Need to reload page completely when current locale gets changed
      window.location.reload(false);
    } else if (
      this.state.isUDOpen &&
      !prevState.isUDOpen &&
      !!this.udRef.current
    ) {
      this.udRef.current.enableOnClickOutside();
    } else if (
      !this.state.isUDOpen &&
      prevState.isUDOpen &&
      !!this.udRef.current
    ) {
      this.udRef.current.disableOnClickOutside();
    } else if (
      this.state.isInboxOpen &&
      !prevState.isInboxOpen &&
      !!this.inboxRef.current
    ) {
      this.inboxRef.current.enableOnClickOutside();
    } else if (
      !this.state.isInboxOpen &&
      prevState.isInboxOpen &&
      !!this.inboxRef.current
    ) {
      this.inboxRef.current.disableOnClickOutside();
    }
  }

  /**
   * @method initEventListeners
   * @summary ToDo: Describe the method
   */
  initEventListeners = () => {
    document.addEventListener('scroll', this.handleScroll);
  };

  /**
   * @method removeEventListeners
   * @summary ToDo: Describe the method
   */
  removeEventListeners = () => {
    document.removeEventListener('scroll', this.handleScroll);
  };

  /**
   * @method handleInboxOpen
   * @summary ToDo: Describe the method
   * @param {object} state
   */
  handleInboxOpen = (state) => {
    this.setState({ isInboxOpen: !!state });
  };

  /**
   * @method handleInboxToggle
   * @summary ToDo: Describe the method
   */
  handleInboxToggle = () => {
    this.setState({ isInboxOpen: !this.state.isInboxOpen });
  };

  /**
   * @method handleUDOpen
   * @summary ToDo: Describe the method
   * @param {object} state
   */
  handleUDOpen = (state) => {
    this.setState({ isUDOpen: !!state });
  };

  /**
   * @method handleUDScroll
   * @summary ToDo: Describe the method
   */
  handleUDToggle = () => {
    this.setState({ isUDOpen: !this.state.isUDOpen });
  };

  /**
   * @method handleMenuOverlay
   * @summary ToDo: Describe the method
   * @param {object} e
   * @param {string} nodeId
   */
  handleMenuOverlay = (e, nodeId) => {
    const { isSubheaderShow, isSideListShow } = this.state;

    if (e) {
      e.preventDefault();
    }

    let toggleBreadcrumb = () => {
      this.setState(
        {
          menuOverlay: nodeId,
        },
        () => {
          if (nodeId !== '') {
            this.setState({ isMenuOverlayShow: true });
          } else {
            this.setState({ isMenuOverlayShow: false });
          }
        }
      );
    };

    if (!isSubheaderShow && !isSideListShow) {
      toggleBreadcrumb();
    }
  };

  /**
   * @method handleScroll
   * @summary ToDo: Describe the method
   * @param {object} event
   */
  handleScroll = (event) => {
    const target = event.srcElement;
    let scrollTop = target && target.body.scrollTop;

    if (!scrollTop) {
      scrollTop = document.documentElement.scrollTop;
    }

    if (scrollTop > 0) {
      this.setState({ scrolled: true });
    } else {
      this.setState({ scrolled: false });
    }
  };

  /**
   * @method handleDashboardLink
   * @summary ToDo: Describe the method
   */
  handleDashboardLink = () => {
    const { dispatch } = this.props;
    dispatch(push('/'));
  };

  /**
   * @method toggleScrollScope
   * @summary ToDo: Describe the method
   * @param {bool} open
   */
  toggleScrollScope = (open) => {
    if (!open) {
      document.body.style.overflow = 'auto';
    } else {
      document.body.style.overflow = 'hidden';
    }
  };

  /**
   * @method toggleTooltip
   * @summary ToDo: Describe the method
   * @param {object} event
   */
  toggleTooltip = (tooltip) => {
    this.setState({ tooltipOpen: tooltip });
  };

  /**
   * @method handleComments
   * @summary opens the modal with the comments panel
   */
  handleComments = () => {
    const { windowId } = this.props;
    this.openModal(
      windowId,
      'static',
      counterpart.translate('window.comments.caption'),
      null,
      null,
      null,
      null,
      'comments'
    );
  };

  /**
   * @method openModel
   * @summary ToDo: Describe the method
   * @param {string} windowId
   * @param {*} modalType
   * @param {*} caption
   * @param {bool} isAdvanced
   * @param {*} selected
   * @param {*} childViewId
   * @param {*} childViewSelectedIds
   * @param {*} staticModalType
   */
  openModal = (
    windowId,
    modalType,
    caption,
    isAdvanced,
    selected,
    childViewId,
    childViewSelectedIds,
    staticModalType
  ) => {
    const { dispatch, viewId } = this.props;

    dispatch(
      openModal(
        caption,
        windowId,
        modalType,
        null,
        null,
        isAdvanced,
        viewId,
        selected,
        null,
        null,
        null,
        null,
        childViewId,
        childViewSelectedIds,
        staticModalType
      )
    );
  };

  /**
   * @method openModalRow
   * @summary ToDo: Describe the method
   * @param {string} windowId
   * @param {*} modalType
   * @param {*} caption
   * @param {string} tabId
   * @param {string} rowId
   * @param {string} rowId
   */
  openModalRow = (
    windowId,
    modalType,
    caption,
    tabId,
    rowId,
    staticModalType
  ) => {
    const { dispatch } = this.props;

    dispatch(
      openModal(
        caption,
        windowId,
        modalType,
        tabId,
        rowId,
        false,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        staticModalType
      )
    );
  };

  /**
   * @method handlePrint
   * @summary ToDo: Describe the method
   * @param {string} windowId
   * @param {string} docId
   * @param {string} docNo
   */
  handlePrint = (windowId, docId, docNo) => {
    openFile(
      'window',
      windowId,
      docId,
      'print',
      `${windowId}_${docNo ? `${docNo}` : `${docId}`}.pdf`
    );
  };

  /**
   * @method handleClone
   * @summary ToDo: Describe the method
   * @param {string} windowId
   * @param {string} docId
   */
  handleClone = (windowId, docId) => {
    const { dispatch } = this.props;

    duplicateRequest('window', windowId, docId).then((response) => {
      if (response && response.data && response.data.id) {
        dispatch(push(`/window/${windowId}/${response.data.id}`));
      }
    });
  };

  /**
   * @method handleDelete
   * @summary ToDo: Describe the method
   */
  handleDelete = () => {
    this.setState({
      prompt: Object.assign({}, this.state.prompt, { open: true }),
    });
  };

  /**
   * @method handleEmail
   * @summary ToDo: Describe the method
   */
  handleEmail = () => {
    this.setState({ isEmailOpen: true });
  };

  /**
   * @method handleLetter
   * @summary ToDo: Describe the method
   */
  handleLetter = () => {
    this.setState({ isLetterOpen: true });
  };

  /**
   * @method handleCloseEmail
   * @summary ToDo: Describe the method
   */
  handleCloseEmail = () => {
    this.setState({ isEmailOpen: false });
  };

  /**
   * @method handleCloseLetter
   * @summary ToDo: Describe the method
   */
  handleCloseLetter = () => {
    this.setState({ isLetterOpen: false });
  };

  /**
   * @method handlePromptCancelClick
   * @summary ToDo: Describe the method
   */
  handlePromptCancelClick = () => {
    this.setState({
      prompt: Object.assign({}, this.state.prompt, { open: false }),
    });
  };

  /**
   * @method handlePromptScroll
   * @summary ToDo: Describe the method
   * @param {string} windowId
   * @param {docId} docId
   */
  handlePromptSubmitClick = (windowId, docId) => {
    const { dispatch, handleDeletedStatus } = this.props;

    this.setState(
      {
        prompt: Object.assign({}, this.state.prompt, { open: false }),
      },
      () => {
        deleteRequest('window', windowId, null, null, [docId]).then(() => {
          handleDeletedStatus(true);
          dispatch(push(`/window/${windowId}`));
        });
      }
    );
  };

  /**
   * @method handleDocStatusToggle
   * @summary ToDo: Describe the method
   * @param {object} event
   */
  handleDocStatusToggle = (close) => {
    const elem = document.getElementsByClassName('js-dropdown-toggler')[0];

    if (close) {
      elem.blur();
    } else {
      if (document.activeElement === elem) {
        elem.blur();
      } else {
        elem.focus();
      }
    }
  };

  /**
   * @method handleSidelistToggle
   * @summary ToDo: Describe the method
   * @param {string} id
   */
  handleSidelistToggle = (id = null) => {
    const { sideListTab } = this.state;

    const isSideListShow = id !== null && id !== sideListTab;

    this.toggleScrollScope(isSideListShow);

    this.setState({
      isSideListShow,
      sideListTab: id !== sideListTab ? id : null,
    });
  };

  /**
   * @method closeOverlays
   * @summary ToDo: Describe the method
   * @param {object} clickedItem
   */
  closeOverlays = (clickedItem, callback) => {
    const { isSubheaderShow } = this.state;

    const state = {
      menuOverlay: null,
      isMenuOverlayShow: false,
      isInboxOpen: false,
      isUDOpen: false,
      isSideListShow: false,
      tooltipOpen: '',
    };

    if (clickedItem) {
      delete state[clickedItem];
    }

    state.isSubheaderShow =
      clickedItem === 'isSubheaderShow' ? !isSubheaderShow : false;

    this.setState(state, callback);

    if (
      document.getElementsByClassName('js-dropdown-toggler')[0] &&
      clickedItem !== 'dropdown'
    ) {
      this.handleDocStatusToggle(true);
    }
  };

  /**
   * @method redirect
   * @summary ToDo: Describe the method
   * @param {*} where
   */
  redirect = (where) => {
    const { dispatch } = this.props;
    dispatch(push(where));
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   */
  render() {
    const {
      docSummaryData,
      siteName,
      docNoData,
      docStatus,
      docStatusData,
      dataId,
      breadcrumb,
      showSidelist,
      inbox,
      entity,
      viewId,
      showIndicator,
      windowId,
      // TODO: We should be using indicator from the state instead of another variable
      isDocumentNotSaved,
      notFound,
      docId,
      me,
      editmode,
      handleEditModeToggle,
      activeTab,
      plugins,
      indicator,
    } = this.props;

    const {
      isSubheaderShow,
      isSideListShow,
      menuOverlay,
      isInboxOpen,
      scrolled,
      isMenuOverlayShow,
      tooltipOpen,
      prompt,
      sideListTab,
      isUDOpen,
      isEmailOpen,
      isLetterOpen,
    } = this.state;

    return (
      <div>
        {prompt.open && (
          <Prompt
            title="Delete"
            text="Are you sure?"
            buttons={{ submit: 'Delete', cancel: 'Cancel' }}
            onCancelClick={this.handlePromptCancelClick}
            onSubmitClick={() => this.handlePromptSubmitClick(windowId, dataId)}
          />
        )}

        <nav
          className={classnames('header header-super-faded', {
            'header-shadow': scrolled,
          })}
        >
          <div className="container-fluid">
            <div className="header-container">
              <div className="header-left-side">
                <div
                  onClick={() => this.closeOverlays('isSubheaderShow')}
                  onMouseEnter={() =>
                    this.toggleTooltip(keymap.OPEN_ACTIONS_MENU)
                  }
                  onMouseLeave={() => this.toggleTooltip('')}
                  className={classnames(
                    'btn-square btn-header',
                    'tooltip-parent js-not-unselect',
                    {
                      'btn-meta-default-dark btn-subheader-open btn-header-open': isSubheaderShow,
                      'btn-meta-primary': !isSubheaderShow,
                    }
                  )}
                >
                  <i className="meta-icon-more" />

                  {tooltipOpen === keymap.OPEN_ACTIONS_MENU && (
                    <Tooltips
                      name={keymap.OPEN_ACTIONS_MENU}
                      action={counterpart.translate(
                        'mainScreen.actionMenu.tooltip'
                      )}
                      type=""
                    />
                  )}
                </div>

                <Breadcrumb
                  breadcrumb={breadcrumb}
                  windowType={windowId}
                  docSummaryData={docSummaryData}
                  dataId={dataId}
                  siteName={siteName}
                  menuOverlay={menuOverlay}
                  docId={docId}
                  isDocumentNotSaved={isDocumentNotSaved}
                  handleMenuOverlay={this.handleMenuOverlay}
                  openModal={this.openModal}
                />
              </div>
              <div className="header-center js-not-unselect">
                <img
                  src={logo}
                  className="header-logo pointer"
                  onClick={this.handleDashboardLink}
                />
              </div>
              <div className="header-right-side">
                {docStatus && (
                  <div
                    className="hidden-sm-down tooltip-parent js-not-unselect"
                    onClick={() => this.toggleTooltip('')}
                    onMouseEnter={() => this.toggleTooltip(keymap.DOC_STATUS)}
                  >
                    <MasterWidget
                      entity="window"
                      windowType={windowId}
                      dataId={dataId}
                      docId={docId}
                      activeTab={activeTab}
                      widgetData={[docStatusData]}
                      noLabel
                      type="primary"
                      dropdownOpenCallback={() =>
                        this.closeOverlays('dropdown')
                      }
                      {...docStatus}
                    />
                    {tooltipOpen === keymap.DOC_STATUS && (
                      <Tooltips
                        name={keymap.DOC_STATUS}
                        action={counterpart.translate(
                          'mainScreen.docStatus.tooltip'
                        )}
                        type=""
                      />
                    )}
                  </div>
                )}

                <div
                  className={classnames(
                    'header-item-container',
                    'header-item-container-static',
                    'pointer tooltip-parent js-not-unselect',
                    {
                      'header-item-open': isInboxOpen,
                    }
                  )}
                  onClick={() =>
                    this.closeOverlays('', () => this.handleInboxOpen(true))
                  }
                  onMouseEnter={() =>
                    this.toggleTooltip(keymap.OPEN_INBOX_MENU)
                  }
                  onMouseLeave={() => this.toggleTooltip('')}
                >
                  <span className="header-item header-item-badge icon-lg">
                    <i className="meta-icon-notifications" />
                    {inbox.unreadCount > 0 && (
                      <span className="notification-number">
                        {inbox.unreadCount}
                      </span>
                    )}
                  </span>
                  {tooltipOpen === keymap.OPEN_INBOX_MENU && (
                    <Tooltips
                      name={keymap.OPEN_INBOX_MENU}
                      action={counterpart.translate('mainScreen.inbox.tooltip')}
                      type={''}
                    />
                  )}
                </div>

                <Inbox
                  ref={this.inboxRef}
                  open={isInboxOpen}
                  close={this.handleInboxOpen}
                  onFocus={() => this.handleInboxOpen(true)}
                  disableOnClickOutside={true}
                  inbox={inbox}
                />

                <UserDropdown
                  ref={this.udRef}
                  open={isUDOpen}
                  handleUDOpen={this.handleUDOpen}
                  disableOnClickOutside={true}
                  redirect={this.redirect}
                  shortcut={keymap.OPEN_AVATAR_MENU}
                  toggleTooltip={this.toggleTooltip}
                  tooltipOpen={tooltipOpen}
                  me={me}
                  plugins={plugins}
                />

                {showSidelist && (
                  <div
                    className={classnames(
                      'tooltip-parent btn-header',
                      'side-panel-toggle btn-square',
                      'js-not-unselect',
                      {
                        'btn-meta-default-bright btn-header-open': isSideListShow,
                        'btn-meta-primary': !isSideListShow,
                      }
                    )}
                    onClick={() => this.handleSidelistToggle(0)}
                    onMouseEnter={() =>
                      this.toggleTooltip(keymap.OPEN_SIDEBAR_MENU_0)
                    }
                    onMouseLeave={() => this.toggleTooltip('')}
                  >
                    <i className="meta-icon-list" />
                    {tooltipOpen === keymap.OPEN_SIDEBAR_MENU_0 && (
                      <Tooltips
                        name={keymap.OPEN_SIDEBAR_MENU_0}
                        action={counterpart.translate(
                          /* eslint-disable max-len */
                          'mainScreen.sideList.tooltip'
                          /* eslint-enable max-len */
                        )}
                        type=""
                      />
                    )}
                  </div>
                )}
              </div>
            </div>
          </div>

          {showIndicator && (
            <Indicator {...{ isDocumentNotSaved, indicator }} />
          )}
        </nav>

        {isSubheaderShow && (
          <Subheader
            closeSubheader={() => this.closeOverlays('isSubheaderShow')}
            docNo={docNoData && docNoData.value}
            openModal={this.openModal}
            openModalRow={this.openModalRow}
            handlePrint={this.handlePrint}
            handleClone={this.handleClone}
            handleDelete={this.handleDelete}
            handleEmail={this.handleEmail}
            handleLetter={this.handleLetter}
            handleComments={this.handleComments}
            redirect={this.redirect}
            disableOnClickOutside={!isSubheaderShow}
            breadcrumb={breadcrumb}
            notfound={notFound}
            entity={entity}
            dataId={dataId}
            documentId={docId}
            windowId={windowId}
            viewId={viewId}
            siteName={siteName}
            editmode={editmode}
            handleEditModeToggle={handleEditModeToggle}
            activeTab={activeTab}
          />
        )}

        {showSidelist && isSideListShow && (
          <SideList
            windowId={windowId ? windowId : ''}
            closeOverlays={this.closeOverlays}
            closeSideList={this.handleSidelistToggle}
            isSideListShow={isSideListShow}
            disableOnClickOutside={!showSidelist}
            docId={dataId}
            defaultTab={sideListTab}
            viewId={viewId}
            open
          />
        )}

        {isEmailOpen && (
          <NewEmail
            windowId={windowId ? windowId : ''}
            docId={dataId}
            handleCloseEmail={this.handleCloseEmail}
          />
        )}
        {isLetterOpen && (
          <NewLetter
            windowId={windowId ? windowId : ''}
            docId={dataId}
            handleCloseLetter={this.handleCloseLetter}
          />
        )}
        <GlobalContextShortcuts
          handleSidelistToggle={this.handleSidelistToggle}
          handleMenuOverlay={
            isMenuOverlayShow
              ? () => this.handleMenuOverlay('', '')
              : () =>
                  this.closeOverlays('', () => this.handleMenuOverlay('', '0'))
          }
          handleInboxToggle={this.handleInboxToggle}
          handleUDToggle={this.handleUDToggle}
          openModal={
            dataId
              ? () => this.openModal(windowId, 'window', 'Advanced edit', true)
              : undefined
          }
          handlePrint={
            dataId
              ? () => this.handlePrint(windowId, dataId, docNoData.value)
              : undefined
          }
          handleEmail={this.handleEmail}
          handleComments={this.handleComments}
          handleLetter={this.handleLetter}
          handleDelete={dataId ? this.handleDelete : undefined}
          handleClone={
            dataId ? () => this.handleClone(windowId, dataId) : undefined
          }
          redirect={
            windowId
              ? () => this.redirect(`/window/${windowId}/new`)
              : undefined
          }
          handleDocStatusToggle={
            document.getElementsByClassName('js-dropdown-toggler')[0]
              ? this.handleDocStatusToggle
              : undefined
          }
          handleEditModeToggle={handleEditModeToggle}
          closeOverlays={this.closeOverlays}
        />
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {*} activeTab
 * @prop {*} breadcrumb
 * @prop {string} dataId
 * @prop {func} dispatch
 * @prop {string} docId
 * @prop {*} docSummaryData
 * @prop {*} docNoData
 * @prop {*} docStatus
 * @prop {*} docStatusData
 * @prop {*} dropzoneFocused
 * @prop {*} editmode
 * @prop {*} entity
 * @prop {*} handleDeletedStatus
 * @prop {*} handleEditModeToggle
 * @prop {object} inbox
 * @prop {bool} isDocumentNotSaved
 * @prop {object} me
 * @prop {*} notFound
 * @prop {*} plugins
 * @prop {*} viewId
 * @prop {*} showSidelist
 * @prop {*} showIndicator
 * @prop {*} siteName
 * @prop {*} windowId
 */
Header.propTypes = {
  activeTab: PropTypes.any,
  breadcrumb: PropTypes.any,
  dataId: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  dispatch: PropTypes.func.isRequired,
  docId: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  docSummaryData: PropTypes.any,
  docNoData: PropTypes.any,
  docStatus: PropTypes.any,
  docStatusData: PropTypes.any,
  dropzoneFocused: PropTypes.any,
  editmode: PropTypes.any,
  entity: PropTypes.any,
  handleDeletedStatus: PropTypes.any,
  handleEditModeToggle: PropTypes.any,
  inbox: PropTypes.object.isRequired,
  isDocumentNotSaved: PropTypes.bool,
  me: PropTypes.object.isRequired,
  notFound: PropTypes.any,
  plugins: PropTypes.any,
  viewId: PropTypes.string,
  showSidelist: PropTypes.any,
  showIndicator: PropTypes.bool,
  siteName: PropTypes.any,
  windowId: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  indicator: PropTypes.string,
};

/**
 * @method mapStateToProps
 * @summary ToDo: Describe the method
 * @param {object} state
 */
const mapStateToProps = (state) => ({
  inbox: state.appHandler.inbox,
  me: state.appHandler.me,
  pathname: state.routing.locationBeforeTransitions.pathname,
  plugins: state.pluginsHandler.files,
  indicator: state.windowHandler.indicator,
});

export default connect(mapStateToProps)(Header);
