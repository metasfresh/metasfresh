import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import { connect } from 'react-redux';

import {
  elementPathRequest,
  updateBreadcrumb,
} from '../../actions/MenuActions';
import { getTableId, getSelection } from '../../reducers/tables';
import keymap from '../../shortcuts/keymap';

import Actions from './Actions';
import BookmarkButton from './BookmarkButton';

const simplifyName = (name) => name.toLowerCase().replace(/\s/g, '');

/**
 * @file Class based component.
 * @module SubHeader
 * @extends Component
 */
class SubHeader extends Component {
  state = {
    pdfSrc: null,
    elementPath: '',
  };

  componentDidMount() {
    document.getElementsByClassName('js-subheader-column')[0].focus();

    const { entity, windowId } = this.props;
    const entityType = entity === 'board' ? 'board' : 'window';

    // Main dashboard view doesn't have a windowTyep and is throwing 404
    if (windowId) {
      elementPathRequest(entityType, this.props.windowId).then((response) => {
        this.setState({ elementPath: response.data });
      });
    }
  }

  /**
   * @method handleKeyDown
   * @summary ToDo: Describe the method.
   * @param {*} e
   */
  handleKeyDown = (e) => {
    const { closeSubheader } = this.props;

    switch (e.key) {
      case 'ArrowDown': {
        e.preventDefault();
        const activeElem = this.getItemActiveElem();
        if (activeElem.nextSibling) {
          activeElem.nextSibling.focus();
        }
        break;
      }

      case 'ArrowUp': {
        e.preventDefault();
        const activeEl = this.getItemActiveElem();
        if (activeEl.previousSibling) {
          activeEl.previousSibling.focus();
        }
        break;
      }

      case 'ArrowLeft': {
        e.preventDefault();
        const activeColumn = this.getColumnActiveElem();
        if (activeColumn.previousSibling) {
          activeColumn.previousSibling.focus();
          if (this.getItemActiveElem().nextSibling) {
            this.getItemActiveElem().nextSibling.focus();
          }
        }
        break;
      }

      case 'ArrowRight': {
        e.preventDefault();
        const activeCol = this.getColumnActiveElem();
        if (activeCol.nextSibling) {
          activeCol.nextSibling.focus();
          if (this.getItemActiveElem().nextSibling) {
            this.getItemActiveElem().nextSibling.focus();
          }
        }
        break;
      }

      case 'Enter':
        e.preventDefault();
        document.activeElement.click();
        break;

      case 'Escape':
        e.preventDefault();
        closeSubheader();
        break;
    }
  };

  /**
   * @method handleClickOutside
   * @summary ToDo: Describe the method.
   * @param {*} e
   */
  handleClickOutside = (event) => {
    const { closeSubheader } = this.props;
    const { target } = event;

    if (
      !target.classList.contains('btn-header') &&
      !target.parentElement.classList.contains('btn-header')
    ) {
      closeSubheader();
    }
  };

  /**
   * @method toggleAttachmentDelete
   * @summary ToDo: Describe the method.
   * @param {*} value
   */
  toggleAttachmentDelete = (value) => {
    this.setState({ attachmentHovered: value });
  };

  /**
   * @method getColumnActiveElem
   * @summary ToDo: Describe the method.
   */
  getColumnActiveElem = () => {
    const active = document.activeElement;
    if (active.classList.contains('js-subheader-item')) {
      return active.parentNode;
    } else {
      return active;
    }
  };

  /**
   * @method getItemActiveElem
   * @summary ToDo: Describe the method.
   */
  getItemActiveElem = () => {
    const active = document.activeElement;

    if (active.classList.contains('js-subheader-column')) {
      return active.childNodes[1];
    } else {
      return active;
    }
  };

  /**
   * @method handleUpdateBreadcrumb
   * @summary ToDo: Describe the method.
   * @param {*} nodes
   */
  handleUpdateBreadcrumb = (nodes) => {
    const { dispatch } = this.props;
    nodes.map((node) => dispatch(updateBreadcrumb(node)));
  };

  /**
   * @method handleDownloadSelected
   * @summary ToDo: Describe the method.
   * @param {*} event
   */
  handleDownloadSelected = (event) => {
    if (this.props.selected.length === 0) {
      event.preventDefault();
    }
  };

  /**
   * @method handleAboutButton
   * @summary ToDo: Describe the method.
   */
  handleAboutButton = () => {
    const {
      selected,
      activeTab,
      windowId,
      openModalRow,
      openModal,
    } = this.props;

    if (selected && selected.length) {
      openModalRow(
        windowId,
        'static',
        counterpart.translate('window.about.caption'),
        activeTab,
        selected,
        'about'
      );
    } else {
      openModal(
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

  /**
   * @method renderDocLink
   * @summary ToDo: Describe the method.
   * @param {*} action
   * @param {*} handler
   * @param {*} icon
   * @param {*} caption
   * @param {*} hotkey
   */
  renderDocLink = ({ action, handler, icon, caption, hotkey }) => {
    const { closeSubheader } = this.props;

    return (
      <div
        id={`subheaderNav_${simplifyName(caption)}`}
        key={action}
        className="subheader-item js-subheader-item"
        tabIndex={0}
        onClick={() => {
          handler();
          closeSubheader();
        }}
      >
        <i className={icon} />

        {caption}

        <span className="tooltip-inline">{hotkey}</span>
      </div>
    );
  };

  /**
   * @method renderDocLinks
   * @summary ToDo: Describe the method.
   */
  renderDocLinks = () => {
    const {
      dataId,
      docNo,
      handleClone,
      handleDelete,
      handleEmail,
      handleLetter,
      handlePrint,
      openModal,
      handleComments,
      standardActions,
      windowId,
    } = this.props;

    if (!dataId) {
      return false;
    }

    const docLinks = [
      {
        action: 'advancedEdit',
        handler: () => {
          openModal(windowId, 'window', 'Advanced edit', true);
        },
        icon: 'meta-icon-edit',
        caption: counterpart.translate('window.advancedEdit.caption'),
        hotkey: keymap.OPEN_ADVANCED_EDIT,
      },
      {
        action: 'clone',
        handler: () => {
          handleClone(windowId, dataId);
        },
        icon: 'meta-icon-duplicate',
        caption: counterpart.translate('window.clone.caption'),
        hotkey: keymap.CLONE_DOCUMENT,
      },
      {
        action: 'email',
        handler: () => {
          handleEmail();
        },
        icon: 'meta-icon-mail',
        caption: counterpart.translate('window.email.caption'),
        hotkey: keymap.OPEN_EMAIL,
      },
      {
        action: 'letter',
        handler: () => {
          handleLetter();
        },
        icon: 'meta-icon-letter',
        caption: counterpart.translate('window.letter.caption'),
        hotkey: keymap.OPEN_LETTER,
      },
      {
        action: 'print',
        handler: () => {
          handlePrint(windowId, dataId, docNo);
        },
        icon: 'meta-icon-print',
        caption: counterpart.translate('window.Print.caption'),
        hotkey: keymap.OPEN_PRINT_RAPORT,
      },
      {
        action: 'delete',
        handler: () => {
          handleDelete();
        },
        icon: 'meta-icon-delete',
        caption: counterpart.translate('window.Delete.caption'),
        hotkey: keymap.DELETE_DOCUMENT,
      },
      {
        action: 'comments',
        handler: handleComments,
        icon: 'meta-icon-message',
        caption: counterpart.translate('window.comments.caption'),
        hotkey: keymap.OPEN_COMMENTS,
      },
    ]
      .filter((docLink) => standardActions.has(docLink.action))
      .map((docLink) => {
        return this.renderDocLink(docLink);
      });

    return docLinks;
  };

  /**
   * @method renderNavColumn
   * @summary ToDo: Describe the method.
   */
  renderNavColumn = () => {
    const {
      closeSubheader,
      dataId,
      editmode,
      handleEditModeToggle,
      viewId,
      redirect,
      selected,
      siteName,
      windowId,
    } = this.props;
    const { elementPath } = this.state;

    let currentNode = elementPath;
    if (currentNode && currentNode.children) {
      do {
        currentNode = currentNode.children[currentNode.children.length - 1];
      } while (
        currentNode &&
        currentNode.children &&
        currentNode.type !== 'window'
      );
    }
    const editModeText = editmode
      ? counterpart.translate('window.closeEditMode')
      : counterpart.translate('window.openEditMode');

    return (
      <div className="subheader-column js-subheader-column" tabIndex={0}>
        <div className="subheader-header">
          <BookmarkButton
            isBookmark={currentNode && currentNode.favorite}
            nodeId={currentNode && currentNode.nodeId}
            transparentBookmarks={!!siteName}
            onUpdateData={this.handleUpdateBreadcrumb}
          >
            <span
              title={currentNode ? currentNode.caption : siteName}
              className="subheader-title"
            >
              {currentNode ? currentNode.caption : siteName}
            </span>
          </BookmarkButton>
        </div>

        <div className="subheader-break" />

        {windowId && (
          <div
            id={`subheaderNav_${simplifyName(
              counterpart.translate('window.new.caption')
            )}`}
            className="subheader-item js-subheader-item"
            tabIndex={0}
            onClick={() => {
              redirect('/window/' + windowId + '/new');
              closeSubheader();
            }}
          >
            <i className="meta-icon-report-1" />

            {counterpart.translate('window.new.caption')}

            <span className="tooltip-inline">{keymap.NEW_DOCUMENT}</span>
          </div>
        )}

        {dataId || (windowId && selected.length === 1) ? (
          <div
            id={`subheaderNav_${simplifyName(
              counterpart.translate('window.about.caption')
            )}`}
            className="subheader-item js-subheader-item"
            tabIndex={0}
            onClick={this.handleAboutButton}
          >
            <i className="meta-icon-more" />

            {counterpart.translate('window.about.caption')}

            <span className="tooltip-inline">{keymap.ABOUT_DOCUMENT}</span>
          </div>
        ) : null}

        {windowId && viewId && (
          <a
            className="subheader-item js-subheader-item"
            href={`${
              config.API_URL
            }/documentView/${windowId}/${viewId}/export/excel?selectedIds=${selected.join(
              ','
            )}`}
            download
            onClick={this.handleDownloadSelected}
            style={{
              opacity: selected.length === 0 ? '0.5' : 1,
            }}
          >
            {counterpart.translate('window.downloadSelected.caption')}
            {selected.length === 0 &&
              ` (${counterpart.translate(
                'window.downloadSelected.nothingSelected'
              )})`}
          </a>
        )}
        {this.renderDocLinks()}
        {editmode !== undefined && (
          <div
            id={`subheaderNav_${simplifyName(editModeText)}`}
            key={editmode}
            className="subheader-item js-subheader-item"
            tabIndex={0}
            onClick={() => {
              handleEditModeToggle();
              closeSubheader();
            }}
          >
            <i className="meta-icon-settings" />
            {editModeText}
            <span className="tooltip-inline">{keymap.TOGGLE_EDIT_MODE}</span>
          </div>
        )}
      </div>
    );
  };

  /**
   * @method renderActionsColumn
   * @summary ToDo: Describe the method.
   */
  renderActionsColumn = () => {
    const {
      windowId,
      viewId,
      dataId,
      selected,
      entity,
      openModal,
      openModalRow,
      closeSubheader,
      notfound,
      activeTab,
    } = this.props;

    return (
      <Actions
        key={1}
        windowType={windowId}
        viewId={viewId}
        entity={entity}
        openModal={openModal}
        openModalRow={openModalRow}
        closeSubheader={closeSubheader}
        notfound={notfound}
        docId={dataId ? dataId : viewId}
        selected={selected}
        activeTab={activeTab}
      />
    );
  };

  setRef = (ref) => (this.subHeader = ref);

  render() {
    return (
      <div
        className="subheader-container overlay-shadow subheader-open js-not-unselect"
        tabIndex={0}
        onKeyDown={this.handleKeyDown}
        ref={this.setRef}
      >
        <div className="container-fluid-subheader container-fluid">
          <div className="subheader-row">
            {this.renderNavColumn()}
            {this.renderActionsColumn()}
          </div>
        </div>
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {func} activeTab
 * @prop {*} closeSubheader
 * @prop {*} dataId
 * @prop {string} dispatch
 * @prop {*} docNo
 * @prop {*} editmode
 * @prop {*} entity
 * @prop {*} handleClone
 * @prop {*} handleDelete
 * @prop {*} handleEmail
 * @prop {*} handleLetter
 * @prop {*} handleEditModeToggle
 * @prop {*} handlePrint
 * @prop {*} notfound
 * @prop {func} openModal
 * @prop {func} openModalRow
 * @prop {*} viewId
 * @prop {*} redirect
 * @prop {*} selected
 * @prop {*} siteName
 * @prop {*} standardActions
 * @prop {string} viewId
 * @prop {string} windowId
 */
SubHeader.propTypes = {
  activeTab: PropTypes.string,
  closeSubheader: PropTypes.any,
  dataId: PropTypes.any,
  dispatch: PropTypes.func.isRequired,
  docNo: PropTypes.any,
  editmode: PropTypes.any,
  entity: PropTypes.any,
  handleClone: PropTypes.any,
  handleDelete: PropTypes.any,
  handleEmail: PropTypes.any,
  handleComments: PropTypes.func,
  handleLetter: PropTypes.any,
  handleEditModeToggle: PropTypes.any,
  handlePrint: PropTypes.any,
  notfound: PropTypes.any,
  openModal: PropTypes.func,
  openModalRow: PropTypes.func,
  viewId: PropTypes.string,
  redirect: PropTypes.any,
  selected: PropTypes.any,
  siteName: PropTypes.any,
  standardActions: PropTypes.any,
  windowId: PropTypes.string,
};

const mapStateToProps = (state, props) => {
  const { windowId, documentId, viewId } = props;
  const activeTab = state.windowHandler.master.layout.activeTab;
  const tabId = activeTab ? activeTab : null;
  const tableId = getTableId({ windowId, viewId, tabId, docId: documentId });
  const selector = getSelection();

  return {
    standardActions: state.windowHandler.master.standardActions,
    selected: selector(state, tableId),
  };
};

export default connect(mapStateToProps)(onClickOutside(SubHeader));
