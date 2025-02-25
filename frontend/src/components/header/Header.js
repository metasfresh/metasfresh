import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';

import history from '../../services/History';
import { deleteDocument, getPrintingOptions } from '../../api/window';
import { duplicateRequest } from '../../actions/GenericActions';
import {
  clearMasterData,
  openModal,
  openPrintingOptionsModal,
  printDocument,
  resetPrintingOptions,
  setPrintingOptions,
} from '../../actions/WindowActions';
import { setBreadcrumb, updateBreadcrumb } from '../../actions/MenuActions';

import keymap from '../../shortcuts/keymap';
import GlobalContextShortcuts from '../keyshortcuts/GlobalContextShortcuts';

import WidgetWrapper from '../../containers/WidgetWrapper';
import Indicator from '../app/Indicator';
import Prompt from '../app/Prompt';
import NewEmail from '../email/NewEmail';
import Inbox from '../inbox/Inbox';
import NewLetter from '../letter/NewLetter';
import Tooltips from '../tooltips/Tooltips';
import Breadcrumb from './breadcrumb/Breadcrumb';
import SideList from './SideList';
import Subheader, {
  ACTION_ABOUT_DOCUMENT,
  ACTION_BREADCRUMB_CLICK,
  ACTION_CLONE_DOCUMENT,
  ACTION_DELETE_DOCUMENT,
  ACTION_DOWNLOAD_SELECTED,
  ACTION_NEW_DOCUMENT,
  ACTION_OPEN_ADVANCED_EDIT,
  ACTION_OPEN_COMMENTS,
  ACTION_OPEN_EMAIL,
  ACTION_OPEN_LETTER,
  ACTION_OPEN_PRINT_RAPORT,
  ACTION_TOGGLE_EDIT_MODE,
} from './SubHeader';
import UserDropdown from './UserDropdown';

import logo from '../../assets/images/metasfresh_logo_green_thumb.png';
import {
  getDocActionElementFromState,
  getDocSummaryDataFromState,
} from '../../reducers/windowHandlerUtils';
import { isShowCommentsMarker } from '../../utils/tableHelpers';
import { getIndicatorFromState } from '../../reducers/windowHandler';
import { getSelection, getTableId } from '../../reducers/tables';
import RedirectHandler from './RedirectHandler';
import { requestRedirect } from '../../reducers/redirect';

const PROMPT_TYPE_CONFIRM_DELETE = 'confirmDelete';

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
   * @method openInbox
   * @summary Shows inbox
   */
  openInbox = () => this.setState({ isInboxOpen: true });

  /**
   * @method closeInbox
   * @summary Hides inbox
   */
  closeInbox = () => this.setState({ isInboxOpen: false });

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
   * @summary Reset breadcrumbs after clicking the logo
   */
  handleDashboardLink = () => {
    const { dispatch } = this.props;

    dispatch(setBreadcrumb([]));
    history.push('/');
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

  openModal = (
    windowId,
    modalType,
    caption,
    isAdvanced,
    selected,
    childViewId = null,
    childViewSelectedIds = null,
    staticModalType = null
  ) => {
    const { dispatch, viewId } = this.props;

    dispatch(
      openModal({
        title: caption,
        windowId,
        modalType,
        isAdvanced,
        viewId,
        viewDocumentIds: selected,
        childViewId,
        childViewSelectedIds,
        staticModalType,
      })
    );
  };

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
      openModal({
        title: caption,
        windowId,
        modalType,
        tabId,
        rowId,
        staticModalType,
      })
    );
  };

  /**
   * @method handlePrint
   * @summary This does the actual printing, checking first the available options. If no options available will directly print
   * @param {string} windowId
   * @param {string} docId
   * @param {string} docNo
   */
  handlePrint = async (windowId, docId, docNo) => {
    const { dispatch } = this.props;

    try {
      const response = await getPrintingOptions({
        entity: 'window',
        windowId,
        docId,
      });

      if (response.status === 200) {
        const { options, caption } = response.data;
        // update in the store the printing options
        dispatch(setPrintingOptions(response.data));

        // in case there are no options we directly print and reset the printing options in the store
        if (!options) {
          printDocument({
            windowId,
            documentId: docId,
            documentNo: docNo,
          });
          dispatch(resetPrintingOptions());
        } else {
          // otherwise we open the modal and we will reset the printing options in the store after the doc is printed
          dispatch(
            openPrintingOptionsModal({
              title: caption,
              windowId,
              documentId: docId,
              documentNo: docNo,
            })
          );
        }
      }
    } catch (error) {
      console.error(error);
    }
  };

  /**
   * @method handleClone
   * @summary ToDo: Describe the method
   * @param {string} windowId
   * @param {string} docId
   */
  handleClone = (windowId, docId) => {
    duplicateRequest('window', windowId, docId).then((response) => {
      if (response && response.data && response.data.id) {
        this.redirect(`/window/${windowId}/${response.data.id}`);
      }
    });
  };

  handleEmail = () => {
    this.setState({ isEmailOpen: true });
  };

  handleCloseEmail = () => {
    this.setState({ isEmailOpen: false });
  };

  handleLetter = () => {
    this.setState({ isLetterOpen: true });
  };

  handleCloseLetter = () => {
    this.setState({ isLetterOpen: false });
  };

  handleDelete = () => {
    this.showPrompt({
      type: PROMPT_TYPE_CONFIRM_DELETE,
      title: counterpart.translate('window.Delete.caption'),
      text: counterpart.translate('window.delete.message'),
      submitCaption: counterpart.translate('window.delete.confirm'),
      cancelCaption: counterpart.translate('window.delete.cancel'),
    });
  };

  showPrompt = ({
    type,
    title,
    text,
    submitCaption,
    cancelCaption,
    ...params
  }) => {
    this.setState({
      prompt: {
        type,
        title,
        text,
        submitCaption,
        cancelCaption,
        ...params,
      },
    });
  };

  closePrompt = () => {
    this.setState({ prompt: null });
  };

  handlePromptSubmitClick = () => {
    const { dispatch } = this.props;
    const { prompt } = this.state;
    if (!prompt) return;

    const { type } = prompt;

    if (type === PROMPT_TYPE_CONFIRM_DELETE) {
      const { windowId, dataId: documentId } = this.props;

      deleteDocument({ windowId, documentId }).then(() => {
        this.closePrompt();
        dispatch(clearMasterData());
        dispatch(() => this.redirectBackAfterDelete({ windowId }));
      });
    } else {
      console.log(`Unknown prompt ${type}`, { prompt });
    }
  };

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

  handleSidelistToggle = (id = null) => {
    const { sideListTab } = this.state;

    const isSideListShow = id !== null && id !== sideListTab;

    this.toggleScrollScope(isSideListShow);

    this.setState({
      isSideListShow,
      sideListTab: id !== sideListTab ? id : null,
    });
  };

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

  closeDropdownOverlay = () => this.closeOverlays('dropdown');

  closeSubheader = () => this.closeOverlays('isSubheaderShow');

  redirect = (where) => {
    const { dispatch } = this.props;
    dispatch(requestRedirect(where));
  };

  redirectBackAfterDelete = ({ windowId }) => {
    if (!history.length) {
      // history length not available, be optimistic and go back
      history.go(-1);
    } else if (history.length > 1) {
      history.go(-1);
    } else if (windowId) {
      // we are at first page => create a new view
      history.push(`/window/${windowId}`);
    } else {
      history.push(`/`);
    }
  };

  handleAboutButton = () => {
    const { windowId, tabId, selected } = this.props;

    if (selected?.length === 1) {
      this.openModalRow(
        windowId,
        'static',
        counterpart.translate('window.about.caption'),
        tabId,
        selected,
        'about'
      );
    } else {
      this.openModal(
        windowId,
        'static',
        counterpart.translate('window.about.caption'),
        null,
        null,
        null,
        null,
        'about'
      );
    }
  };

  handleUpdateBreadcrumb = (nodes) => {
    const { dispatch } = this.props;
    nodes.map((node) => dispatch(updateBreadcrumb(node)));
  };

  handleDownloadSelected = (event) => {
    if (this.props.selected.length === 0) {
      event.preventDefault();
    }
  };

  handleAction = ({ action, payload }) => {
    const { windowId, dataId } = this.props;

    switch (action) {
      case ACTION_BREADCRUMB_CLICK: {
        this.handleUpdateBreadcrumb(payload);
        break;
      }
      case ACTION_TOGGLE_EDIT_MODE: {
        const { handleEditModeToggle } = this.props;
        handleEditModeToggle();
        this.closeSubheader();
        break;
      }
      case ACTION_ABOUT_DOCUMENT: {
        this.handleAboutButton();
        break;
      }
      case ACTION_DOWNLOAD_SELECTED: {
        this.handleDownloadSelected();
        break;
      }
      case ACTION_NEW_DOCUMENT: {
        this.redirect('/window/' + windowId + '/new');
        this.closeSubheader();
        break;
      }
      case ACTION_OPEN_ADVANCED_EDIT: {
        this.openModal(
          windowId,
          'window',
          counterpart.translate('window.advancedEdit.caption'),
          true
        );
        this.closeSubheader();
        break;
      }
      case ACTION_CLONE_DOCUMENT: {
        this.handleClone(windowId, dataId);
        this.closeSubheader();
        break;
      }
      case ACTION_OPEN_EMAIL: {
        this.handleEmail();
        this.closeSubheader();
        break;
      }
      case ACTION_OPEN_LETTER: {
        this.handleLetter();
        this.closeSubheader();
        break;
      }
      case ACTION_OPEN_PRINT_RAPORT: {
        const { docNoData } = this.props;
        const docNo = docNoData?.value;
        this.closeSubheader();
        return this.handlePrint(windowId, dataId, docNo);
        // break;
      }
      case ACTION_DELETE_DOCUMENT: {
        this.handleDelete();
        this.closeSubheader();
        break;
      }
      case ACTION_OPEN_COMMENTS: {
        this.handleComments();
        this.closeSubheader();
        break;
      }
      default: {
        console.log(`Action ${action} not implemented`, { payload });
        break;
      }
    }
  };

  render() {
    const {
      docSummaryData,
      siteName,
      docNoData,
      docStatus,
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
      plugins,
      indicator,
      saveStatus,
      isShowComments,
      hasComments,
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
        <RedirectHandler />
        {prompt && (
          <Prompt
            title={prompt.title}
            text={prompt.text}
            buttons={{
              submit: prompt.submitCaption,
              cancel: prompt.cancelCaption,
            }}
            onSubmitClick={this.handlePromptSubmitClick}
            onCancelClick={this.closePrompt}
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
                  onClick={this.closeSubheader}
                  onMouseEnter={() =>
                    this.toggleTooltip(keymap.OPEN_ACTIONS_MENU)
                  }
                  onMouseLeave={() => this.toggleTooltip('')}
                  className={classnames(
                    'btn-square btn-header',
                    'tooltip-parent js-not-unselect',
                    {
                      'btn-meta-default-dark btn-subheader-open btn-header-open':
                        isSubheaderShow,
                      'btn-meta-primary': !isSubheaderShow,
                    }
                  )}
                >
                  <i className="position-relative meta-icon-more">
                    {isShowComments && hasComments && (
                      <span
                        className="notification-number size-sm"
                        title={counterpart.translate('window.comments.caption')}
                      />
                    )}
                  </i>

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
                  alt="logo"
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
                    <WidgetWrapper
                      renderMaster={true}
                      dataSource="doc-status"
                      type="primary"
                      entity="window"
                      windowId={windowId}
                      dataId={dataId}
                      docId={docId}
                      noLabel={true}
                      dropdownOpenCallback={this.closeDropdownOverlay}
                      // caption/description/widgetType/fields
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
                  onClick={() => this.closeOverlays('', this.openInbox)}
                  onMouseEnter={() =>
                    this.toggleTooltip(keymap.OPEN_INBOX_MENU)
                  }
                  onMouseLeave={() => this.toggleTooltip('')}
                >
                  <span className="header-item header-item-badge icon-lg">
                    <i className="meta-icon-notifications" />
                    {inbox.unreadCount > 0 && (
                      <span className="notification-number size-md">
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
                  close={this.closeInbox}
                  onFocus={this.openInbox}
                  disableOnClickOutside={true}
                  inbox={inbox}
                />

                <UserDropdown
                  ref={this.udRef}
                  open={isUDOpen}
                  handleUDOpen={this.handleUDOpen}
                  disableOnClickOutside={true}
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
                        'btn-meta-default-bright btn-header-open':
                          isSideListShow,
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
            <Indicator
              indicator={indicator}
              isDocumentNotSaved={isDocumentNotSaved}
              error={saveStatus?.error ? saveStatus?.reason : ''}
              exception={saveStatus?.error ? saveStatus?.exception : null}
            />
          )}
        </nav>

        {isSubheaderShow && (
          <Subheader
            closeSubheader={this.closeSubheader}
            openModal={this.openModal}
            openModalRow={this.openModalRow}
            onMenuItemAction={this.handleAction}
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
              ? () =>
                  this.openModal(
                    windowId,
                    'window',
                    counterpart.translate('window.advancedEdit.caption'),
                    true
                  )
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

Header.propTypes = {
  activeTab: PropTypes.any,
  breadcrumb: PropTypes.any,
  dataId: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  dispatch: PropTypes.func.isRequired,
  docId: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  docSummaryData: PropTypes.any,
  docNoData: PropTypes.any,
  docStatus: PropTypes.any,
  dropzoneFocused: PropTypes.any,
  editmode: PropTypes.any,
  entity: PropTypes.any,
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
  tabId: PropTypes.string,
  indicator: PropTypes.string,
  saveStatus: PropTypes.object,
  isShowComments: PropTypes.bool,
  hasComments: PropTypes.bool,
  selected: PropTypes.array,
};

const mapStateToProps = (state, ownProps) => {
  const {
    master: { saveStatus },
  } = state.windowHandler;

  const activeTab = state.windowHandler.master.layout.activeTab;
  const tabId = activeTab ? activeTab : null;

  const { windowId, viewId, docId: documentId } = ownProps;
  const tableId = getTableId({ windowId, viewId, tabId, docId: documentId });
  const selector = getSelection();
  const selected = selector(state, tableId);

  return {
    tabId,
    inbox: state.appHandler.inbox,
    me: state.appHandler.me,
    plugins: state.pluginsHandler.files,
    docStatus: getDocActionElementFromState(state),
    docSummaryData: getDocSummaryDataFromState(state),
    isShowComments: isShowCommentsMarker(state),
    indicator: getIndicatorFromState({ state }),
    saveStatus,
    selected,
  };
};

export default connect(mapStateToProps)(Header);
