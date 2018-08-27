import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';

import {
  deleteRequest,
  duplicateRequest,
  openFile,
} from '../../actions/GenericActions';
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

const mapStateToProps = state => ({
  inbox: state.appHandler.inbox,
  me: state.appHandler.me,
  pathname: state.routing.locationBeforeTransitions.pathname,
  plugins: state.pluginsHandler.files,
});

class Header extends Component {
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

  static propTypes = {
    dispatch: PropTypes.func.isRequired,
    inbox: PropTypes.object.isRequired,
    me: PropTypes.object.isRequired,
  };

  componentDidMount() {
    this.initEventListeners();
  }

  componentWillUnmount() {
    this.toggleScrollScope(false);
    this.removeEventListeners();
  }

  componentWillUpdate(nextProps) {
    const { dropzoneFocused } = this.props;

    if (
      nextProps.dropzoneFocused !== dropzoneFocused &&
      nextProps.dropzoneFocused
    ) {
      this.closeOverlays();
    }
  }

  componentDidUpdate(prevProps) {
    // const {dispatch, pathname} = this.props;

    if (
      prevProps.me.language !== undefined &&
      JSON.stringify(prevProps.me.language) !==
        JSON.stringify(this.props.me.language)
    ) {
      /*
            dispatch(replace(''));
            dispatch(replace(pathname));
            */

      // Need to reload page completely when current locale gets changed
      window.location.reload(false);
    }
  }

  initEventListeners = () => {
    document.addEventListener('scroll', this.handleScroll);
  };

  removeEventListeners = () => {
    document.removeEventListener('scroll', this.handleScroll);
  };

  handleInboxOpen = state => {
    this.setState({ isInboxOpen: !!state });
  };

  handleInboxToggle = () => {
    this.setState({ isInboxOpen: !this.state.isInboxOpen });
  };

  handleUDOpen = state => {
    this.setState({ isUDOpen: !!state });
  };

  handleUDToggle = () => {
    this.setState({ isUDOpen: !this.state.isUDOpen });
  };

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

  handleScroll = event => {
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

  handleDashboardLink = () => {
    const { dispatch } = this.props;
    dispatch(push('/'));
  };

  toggleScrollScope = open => {
    if (!open) {
      document.body.style.overflow = 'auto';
    } else {
      document.body.style.overflow = 'hidden';
    }
  };

  toggleTooltip = tooltip => {
    this.setState({ tooltipOpen: tooltip });
  };

  openModal = (
    windowType,
    type,
    caption,
    isAdvanced,
    selected,
    childViewId,
    childViewSelectedIds
  ) => {
    const { dispatch, query } = this.props;
    dispatch(
      openModal(
        caption,
        windowType,
        type,
        null,
        null,
        isAdvanced,
        query && query.viewId,
        selected,
        null,
        null,
        null,
        null,
        childViewId,
        childViewSelectedIds
      )
    );
  };

  openModalRow = (windowType, type, caption, tabId, rowId) => {
    const { dispatch } = this.props;

    dispatch(openModal(caption, windowType, type, tabId, rowId));
  };

  handlePrint = (windowType, docId, docNo) => {
    openFile(
      'window',
      windowType,
      docId,
      'print',
      windowType + '_' + (docNo ? docNo : docId) + '.pdf'
    );
  };

  handleClone = (windowType, docId) => {
    const { dispatch } = this.props;

    duplicateRequest('window', windowType, docId).then(response => {
      if (response && response.data && response.data.id) {
        dispatch(push(`/window/${windowType}/${response.data.id}`));
      }
    });
  };

  handleDelete = () => {
    this.setState({
      prompt: Object.assign({}, this.state.prompt, { open: true }),
    });
  };

  handleEmail = () => {
    this.setState({ isEmailOpen: true });
  };

  handleLetter = () => {
    this.setState({ isLetterOpen: true });
  };

  handleCloseEmail = () => {
    this.setState({ isEmailOpen: false });
  };

  handleCloseLetter = () => {
    this.setState({ isLetterOpen: false });
  };

  handlePromptCancelClick = () => {
    this.setState({
      prompt: Object.assign({}, this.state.prompt, { open: false }),
    });
  };

  handlePromptSubmitClick = (windowType, docId) => {
    const { dispatch, handleDeletedStatus } = this.props;

    this.setState(
      {
        prompt: Object.assign({}, this.state.prompt, { open: false }),
      },
      () => {
        deleteRequest('window', windowType, null, null, [docId]).then(() => {
          handleDeletedStatus(true);
          dispatch(push('/window/' + windowType));
        });
      }
    );
  };

  handleDocStatusToggle = close => {
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
      clickedItem == 'isSubheaderShow' ? !isSubheaderShow : false;

    this.setState(state, callback);

    if (
      document.getElementsByClassName('js-dropdown-toggler')[0] &&
      clickedItem !== 'dropdown'
    ) {
      this.handleDocStatusToggle(true);
    }
  };

  redirect = where => {
    const { dispatch } = this.props;
    dispatch(push(where));
  };

  render() {
    const {
      docSummaryData,
      siteName,
      docNoData,
      docStatus,
      docStatusData,
      windowType,
      dataId,
      breadcrumb,
      showSidelist,
      inbox,
      entity,
      query,
      showIndicator,
      // TODO: We should be using indicator from the state instead of another variable
      isDocumentNotSaved,
      notfound,
      docId,
      me,
      editmode,
      handleEditModeToggle,
      activeTab,
      plugins,
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
            onSubmitClick={() =>
              this.handlePromptSubmitClick(windowType, dataId)
            }
          />
        )}

        <nav
          className={
            'header header-super-faded js-not-unselect ' +
            (scrolled ? 'header-shadow' : '')
          }
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
                  className={
                    'btn-square btn-header ' +
                    'tooltip-parent ' +
                    (isSubheaderShow
                      ? 'btn-meta-default-dark ' +
                        'btn-subheader-open btn-header-open'
                      : 'btn-meta-primary')
                  }
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
                  windowType={windowType}
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
              <div className="header-center">
                <img
                  src={logo}
                  className="header-logo pointer"
                  onClick={this.handleDashboardLink}
                />
              </div>
              <div className="header-right-side">
                {docStatus && (
                  <div
                    className="hidden-sm-down tooltip-parent"
                    onClick={() => this.toggleTooltip('')}
                    onMouseEnter={() => this.toggleTooltip(keymap.DOC_STATUS)}
                    onMouseLeave={() => this.toggleTooltip('')}
                  >
                    <MasterWidget
                      entity="window"
                      windowType={windowType}
                      dataId={dataId}
                      widgetData={[docStatusData]}
                      noLabel
                      type="primary"
                      dropdownOpenCallback={() => {
                        this.closeOverlays('dropdown');
                      }}
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
                  className={
                    'header-item-container ' +
                    'header-item-container-static ' +
                    'pointer tooltip-parent ' +
                    (isInboxOpen ? 'header-item-open ' : '')
                  }
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
                  open={isInboxOpen}
                  close={this.handleInboxOpen}
                  onFocus={() => this.handleInboxOpen(true)}
                  disableOnClickOutside={!isInboxOpen}
                  inbox={inbox}
                />

                <UserDropdown
                  open={isUDOpen}
                  handleUDOpen={this.handleUDOpen}
                  disableOnClickOutside={!isUDOpen}
                  redirect={this.redirect}
                  shortcut={keymap.OPEN_AVATAR_MENU}
                  toggleTooltip={this.toggleTooltip}
                  tooltipOpen={tooltipOpen}
                  me={me}
                  plugins={plugins}
                />

                {showSidelist && (
                  <div
                    className={
                      'tooltip-parent btn-header ' +
                      'side-panel-toggle btn-square ' +
                      (isSideListShow
                        ? 'btn-meta-default-bright ' + 'btn-header-open'
                        : 'btn-meta-primary')
                    }
                    onClick={() => {
                      this.closeOverlays();
                      this.handleSidelistToggle(0);
                    }}
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
            <Indicator isDocumentNotSaved={isDocumentNotSaved} />
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
            redirect={this.redirect}
            disableOnClickOutside={!isSubheaderShow}
            breadcrumb={breadcrumb}
            notfound={notfound}
            query={query}
            entity={entity}
            dataId={dataId}
            windowType={windowType}
            viewId={query && query.viewId}
            siteName={siteName}
            editmode={editmode}
            handleEditModeToggle={handleEditModeToggle}
            activeTab={activeTab}
          />
        )}

        {showSidelist &&
          isSideListShow && (
            <SideList
              windowType={windowType ? windowType : ''}
              closeOverlays={this.closeOverlays}
              closeSideList={this.handleSidelistToggle}
              isSideListShow={isSideListShow}
              disableOnClickOutside={!showSidelist}
              docId={dataId}
              defaultTab={sideListTab}
              open
            />
          )}

        {isEmailOpen && (
          <NewEmail
            windowId={windowType ? windowType : ''}
            docId={dataId}
            handleCloseEmail={this.handleCloseEmail}
          />
        )}
        {isLetterOpen && (
          <NewLetter
            windowId={windowType ? windowType : ''}
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
                  this.openModal(windowType, 'window', 'Advanced edit', true)
              : undefined
          }
          handlePrint={
            dataId
              ? () => this.handlePrint(windowType, dataId, docNoData.value)
              : undefined
          }
          handleEmail={this.handleEmail}
          handleLetter={this.handleLetter}
          handleDelete={dataId ? this.handleDelete : undefined}
          handleClone={
            dataId ? () => this.handleClone(windowType, dataId) : undefined
          }
          redirect={
            windowType
              ? () => this.redirect('/window/' + windowType + '/new')
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

export default connect(mapStateToProps)(Header);
