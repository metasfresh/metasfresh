import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import { connect, useSelector } from 'react-redux';

import { elementPathRequest } from '../../api';
import {
  getMasterViewStandardActions,
  getSelection,
  getTableId,
} from '../../reducers/tables';
import keymap from '../../shortcuts/keymap';

import Actions from './Actions';
import BookmarkButton from './BookmarkButton';
import { DocumentAction } from '../../constants/DocumentAction';
import { getMasterDocumentStandardActions } from '../../reducers/windowHandler';

const simplifyName = (name) => name.toLowerCase().replace(/\s/g, '');

/** The Actions Menu (ALT-1) */
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

  getColumnActiveElem = () => {
    const active = document.activeElement;
    if (active.classList.contains('js-subheader-item')) {
      return active.parentNode;
    } else {
      return active;
    }
  };

  getItemActiveElem = () => {
    const active = document.activeElement;

    if (active.classList.contains('js-subheader-column')) {
      return active.childNodes[1];
    } else {
      return active;
    }
  };

  render() {
    const {
      windowId,
      viewId,
      dataId,
      editmode,
      selected,
      siteName,
      entity,
      openModal,
      openModalRow,
      closeSubheader,
      notfound,
      activeTabId,
    } = this.props;
    const { onMenuItemAction } = this.props;
    const { elementPath } = this.state;
    return (
      <div
        className="subheader-container overlay-shadow subheader-open js-not-unselect"
        tabIndex={0}
        onKeyDown={this.handleKeyDown}
      >
        <div className="container-fluid-subheader container-fluid">
          <div className="subheader-row">
            <MenuNavigationColumn
              windowId={windowId}
              viewId={viewId}
              dataId={dataId}
              editmode={editmode}
              selected={selected}
              siteName={siteName}
              elementPath={elementPath ? elementPath : null}
              onAction={onMenuItemAction}
            />
            <Actions
              windowType={windowId}
              viewId={viewId}
              entity={entity}
              openModal={openModal}
              openModalRow={openModalRow}
              closeSubheader={closeSubheader}
              notfound={notfound}
              docId={dataId ? dataId : viewId}
              selected={selected}
              activeTab={activeTabId}
            />
          </div>
        </div>
      </div>
    );
  }
}

SubHeader.propTypes = {
  activeTabId: PropTypes.string,
  closeSubheader: PropTypes.any,
  dataId: PropTypes.any,
  dispatch: PropTypes.func.isRequired,
  editmode: PropTypes.any,
  entity: PropTypes.any,
  onMenuItemAction: PropTypes.func.isRequired,
  notfound: PropTypes.any,
  openModal: PropTypes.func,
  openModalRow: PropTypes.func,
  viewId: PropTypes.string,
  selected: PropTypes.any,
  siteName: PropTypes.any,
  windowId: PropTypes.string,
};

const mapStateToProps = (state, props) => {
  const { windowId, documentId, viewId } = props;
  const activeTabId = state.windowHandler.master.layout.activeTab ?? null;
  const tableId = getTableId({
    windowId,
    viewId,
    tabId: activeTabId,
    docId: documentId,
  });
  const selector = getSelection();

  return {
    selected: selector(state, tableId),
    activeTabId,
  };
};

export default connect(mapStateToProps)(onClickOutside(SubHeader));

//
//
//
//
//

export const getStandardActions = ({ state, windowId, documentId, viewId }) => {
  if (!windowId) {
    return [];
  }

  if (viewId) {
    return getMasterViewStandardActions({ state, windowId, viewId });
  } else if (documentId) {
    return getMasterDocumentStandardActions({ state, windowId, documentId });
  } else {
    return [];
  }
};

const useStandardActions = ({ windowId, documentId, viewId }) => {
  return useSelector((state) =>
    getStandardActions({ state, windowId, documentId, viewId })
  );
};

//
//
//
//
//

const MenuNavigationColumn = ({
  windowId,
  viewId,
  dataId,
  editmode,
  selected,
  siteName,
  elementPath,
  onAction,
}) => {
  const standardActions = useStandardActions({
    windowId,
    documentId: dataId,
    viewId,
  });

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

  return (
    <div className="subheader-column js-subheader-column" tabIndex={0}>
      <div className="subheader-header">
        <BookmarkButton
          isBookmark={currentNode && currentNode.favorite}
          nodeId={currentNode && currentNode.nodeId}
          transparentBookmarks={!!siteName}
          onUpdateData={(data) =>
            onAction({ action: DocumentAction.BREADCRUMB_CLICK, payload: data })
          }
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

      <MenuItem
        action={DocumentAction.NEW_DOCUMENT}
        captionKey="window.new.caption"
        icon="meta-icon-report-1"
        hotkey={keymap.NEW_DOCUMENT}
        onAction={onAction}
        visible={standardActions.includes(DocumentAction.NEW_DOCUMENT)}
      />
      <MenuItem
        action={DocumentAction.ABOUT_DOCUMENT}
        captionKey="window.about.caption"
        icon="meta-icon-more"
        hotkey={keymap.ABOUT_DOCUMENT}
        onAction={onAction}
        visible={dataId || (windowId && selected.length === 1)}
      />
      <DownloadSelectedMenuItem
        windowId={windowId}
        viewId={viewId}
        selected={selected}
        onAction={onAction}
      />
      <DocumentStandardActionMenuItems
        enabledActions={standardActions}
        onAction={onAction}
        visible={!!windowId && !!dataId}
      />
      <MenuItem
        action={DocumentAction.TOGGLE_EDIT_MODE}
        captionKey={editmode ? 'window.closeEditMode' : 'window.openEditMode'}
        icon="meta-icon-settings"
        hotkey={keymap.TOGGLE_EDIT_MODE}
        onAction={onAction}
        visible={editmode !== undefined}
      />
    </div>
  );
};
MenuNavigationColumn.propTypes = {
  windowId: PropTypes.string,
  viewId: PropTypes.string,
  dataId: PropTypes.string,
  editmode: PropTypes.bool,
  selected: PropTypes.array,
  siteName: PropTypes.string,
  elementPath: PropTypes.object,
  onAction: PropTypes.func.isRequired,
};

//
//
//
//
//

const STANDARD_ACTIONS_AVAILABLE = [
  {
    action: DocumentAction.OPEN_ADVANCED_EDIT,
    icon: 'meta-icon-edit',
    captionKey: 'window.advancedEdit.caption',
    hotkey: keymap.OPEN_ADVANCED_EDIT,
  },
  {
    action: DocumentAction.CLONE_DOCUMENT,
    icon: 'meta-icon-duplicate',
    captionKey: 'window.clone.caption',
    hotkey: keymap.CLONE_DOCUMENT,
  },
  {
    action: DocumentAction.OPEN_EMAIL,
    icon: 'meta-icon-mail',
    captionKey: 'window.email.caption',
    hotkey: keymap.OPEN_EMAIL,
  },
  {
    action: DocumentAction.OPEN_LETTER,
    icon: 'meta-icon-letter',
    captionKey: 'window.letter.caption',
    hotkey: keymap.OPEN_LETTER,
  },
  {
    action: DocumentAction.OPEN_PRINT_RAPORT,
    icon: 'meta-icon-print',
    captionKey: 'window.Print.caption',
    hotkey: keymap.OPEN_PRINT_RAPORT,
  },
  {
    action: DocumentAction.DELETE_DOCUMENT,
    icon: 'meta-icon-delete',
    captionKey: 'window.Delete.caption',
    hotkey: keymap.DELETE_DOCUMENT,
  },
  {
    action: DocumentAction.OPEN_COMMENTS,
    icon: 'meta-icon-message',
    captionKey: 'window.comments.caption',
    hotkey: keymap.OPEN_COMMENTS,
  },
];

const DocumentStandardActionMenuItems = ({
  enabledActions,
  visible = true,
  onAction,
}) => {
  if (!visible || !enabledActions || enabledActions.length <= 0) return null;

  return STANDARD_ACTIONS_AVAILABLE.map(
    ({ action, icon, captionKey, hotkey }) => (
      <MenuItem
        key={action}
        action={action}
        captionKey={captionKey}
        icon={icon}
        hotkey={hotkey}
        visible={enabledActions.includes(action)}
        onAction={onAction}
      />
    )
  );
};
DocumentStandardActionMenuItems.propTypes = {
  enabledActions: PropTypes.array,
  visible: PropTypes.bool,
  onAction: PropTypes.func.isRequired,
};

//
//
//
//
//

const MenuItem = ({
  action,
  captionKey,
  caption: captionParam,
  icon,
  hotkey,
  visible = true,
  onAction,
}) => {
  if (!visible) return null;

  const caption = captionParam ?? counterpart.translate(captionKey);

  return (
    <div
      id={`subheaderNav_${simplifyName(caption)}`}
      key={action}
      className="subheader-item js-subheader-item"
      tabIndex={0}
      onClick={() => onAction({ action: action })}
    >
      <i className={icon} />
      {caption}
      <span className="tooltip-inline">{hotkey}</span>
    </div>
  );
};
MenuItem.propTypes = {
  action: PropTypes.string.isRequired,
  captionKey: PropTypes.string,
  caption: PropTypes.string,
  icon: PropTypes.string,
  hotkey: PropTypes.string,
  visible: PropTypes.any,
  onAction: PropTypes.func.isRequired,
};

//
//
//
//
//

const DownloadSelectedMenuItem = ({ windowId, viewId, selected, onAction }) => {
  if (!windowId || !viewId) return null;

  const hasSelection = selected && selected?.length > 0;
  if (!hasSelection) return null;

  return (
    <a
      className="subheader-item js-subheader-item"
      href={`${
        config.API_URL
      }/documentView/${windowId}/${viewId}/export/excel?selectedIds=${selected.join(
        ','
      )}`}
      download
      onClick={() => onAction({ action: DocumentAction.DOWNLOAD_SELECTED })}
    >
      {counterpart.translate('window.downloadSelected.caption')}
    </a>
  );
};
DownloadSelectedMenuItem.propTypes = {
  windowId: PropTypes.string,
  viewId: PropTypes.string,
  selected: PropTypes.array,
  onAction: PropTypes.func,
};
