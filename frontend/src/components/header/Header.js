import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';

import history from '../../services/History';
import { getPrintingOptions } from '../../api/window';
import { deleteRequest } from '../../api';
import { duplicateRequest } from '../../actions/GenericActions';
import {
  openModal,
  printDocument,
  resetPrintingOptions,
  setPrintingOptions,
  openPrintingOptionsModal,
} from '../../actions/WindowActions';
import { setBreadcrumb } from '../../actions/MenuActions';

import keymap from '../../shortcuts/keymap';
import GlobalContextShortcuts from '../keyshortcuts/GlobalContextShortcuts';

import WidgetWrapper from '../../containers/WidgetWrapper';
import Indicator from '../app/Indicator';
import Prompt from '../app/Prompt';
import NewEmail from '../email/NewEmail';
import Inbox from '../inbox/Inbox';
import NewLetter from '../letter/NewLetter';
import Tooltips from '../tooltips/Tooltips';
import Breadcrumb from './Breadcrumb';
import SideList from './SideList';
import Subheader from './SubHeader';
import UserDropdown from './UserDropdown';

import logo from '../../assets/images/metasfresh_logo_green_thumb.png';
import {
  getDocActionElementFromState,
  getDocSummaryDataFromState,
} from '../../reducers/windowHandlerUtils';
import { isShowCommentsMarker } from '../../utils/tableHelpers';

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
    deletePrompt: { open: false },
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

  /**
   * @method openModalRow
   * @summary ToDo: Describe the method
   * @param {string} windowId
   * @param {*} modalType
   * @param {*} caption
   * @param {string} tabId
   * @param {string} rowId
   * @param {string} rowId
   * @param {*} staticModalType
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

  handleDelete = () => {
    this.setState({ deletePrompt: { ...this.state.deletePrompt, open: true } });
  };

  handleDeletePromptCancelClick = () => {
    this.setState({
      deletePrompt: { ...this.state.deletePrompt, open: false },
    });
  };

  handleDeletePromptSubmitClick = () => {
    const { handleDeletedStatus, windowId, dataId } = this.props;

    this.setState(
      { deletePrompt: { ...this.state.deletePrompt, open: false } },
      () => {
        deleteRequest('window', windowId, null, null, [dataId]).then(() => {
          handleDeletedStatus(true);
          this.redirectBackAfterDelete({ windowId });
        });
      }
    );
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

  /**
   * @method redirect
   * @summary Redirect to a page
   * @param {string} where
   */
  redirect = (where) => {
    history.push(where);
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
      deletePrompt,
      sideListTab,
      isUDOpen,
      isEmailOpen,
      isLetterOpen,
    } = this.state;

    return (
      <div>
        {deletePrompt && deletePrompt.open && (
          <Prompt
            title={counterpart.translate('window.Delete.caption')}
            text={counterpart.translate('window.delete.message')}
            buttons={{
              submit: counterpart.translate('window.delete.confirm'),
              cancel: counterpart.translate('window.delete.cancel'),
            }}
            onCancelClick={this.handleDeletePromptCancelClick}
            onSubmitClick={this.handleDeletePromptSubmitClick}
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
 * @prop {bool} hasComments - used to indicate comments available for the details view
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
  saveStatus: PropTypes.object,
  isShowComments: PropTypes.bool,
  hasComments: PropTypes.bool,
};

const mapStateToProps = (state) => {
  const {
    indicator,
    master: { saveStatus },
  } = state.windowHandler;

  return {
    inbox: state.appHandler.inbox,
    me: state.appHandler.me,
    plugins: state.pluginsHandler.files,
    docStatus: getDocActionElementFromState(state),
    docSummaryData: getDocSummaryDataFromState(state),
    isShowComments: isShowCommentsMarker(state),
    indicator,
    saveStatus,
  };
};

export default connect(mapStateToProps)(Header);
