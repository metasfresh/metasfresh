import PropTypes from 'prop-types';
import React, { Component, Fragment } from 'react';
import { connect } from 'react-redux';
import counterpart from 'counterpart';
import cx from 'classnames';

import Loader from '../app/Loader';

import { referencesEventSource } from '../../api/documentReferences';
import {
  buildRelatedDocumentsViewUrl,
  mergeReferencesToReferences,
} from '../../utils/documentReferencesHelper';
import { setFilter } from '../../actions/ListActions';
import keymap from '../../shortcuts/keymap';
import {
  TBL_CONTEXT_Y_OFFSET,
  TBL_CONTEXT_X_OFFSET,
  TBL_CONTEXT_MENU_X_MAX,
  TBL_CONTEXT_MENU_Y_MAX,
  TBL_CONTEXT_POPUP_HEIGHT,
} from '../../constants/Constants';

class TableContextMenu extends Component {
  constructor(props) {
    super(props);
    this.state = {
      contextMenu: {
        x: props.x,
        y: props.y,
      },
      loadingReferences: false,
      references: [],
      display: 'block',
    };
  }

  componentDidMount() {
    const { x, y, fieldName, supportZoomInto, supportFieldEdit, docId } =
      this.props;

    this.updateContextMenuState(
      x,
      y,
      fieldName,
      supportZoomInto,
      supportFieldEdit,
      this.contextMenu
    );

    if (docId) {
      this.loadReferences();
    }
  }

  componentWillUnmount = () => {
    this.closeEventSource();
  };

  closeEventSource = () => {
    if (this.eventSource) {
      this.eventSource.close();
      delete this.eventSource;
    }
  };

  adjustElementPositionToFitInScreen = (dir, pos, element) => {
    if (element) {
      const windowSize = dir === 'x' ? window.innerWidth : window.innerHeight;
      const elementSize =
        dir === 'x' ? element.offsetWidth : element.offsetHeight;

      if (windowSize - pos > elementSize) {
        return pos;
      } else {
        return windowSize - elementSize;
      }
    } else {
      return pos;
    }
  };

  updateContextMenuState = (
    x,
    y,
    fieldName,
    supportZoomInto,
    supportFieldEdit,
    elem
  ) => {
    this.setState({
      contextMenu: {
        x: this.adjustElementPositionToFitInScreen('x', x, elem),
        y: this.adjustElementPositionToFitInScreen('y', y, elem),
        fieldName,
        supportZoomInto,
        supportFieldEdit,
      },
    });
  };

  loadReferences = () => {
    const { windowId, docId, tabId, selected } = this.props;

    this.setState({ loadingReferences: true });

    this.closeEventSource();
    this.eventSource = referencesEventSource({
      windowId: windowId,
      documentId: docId,
      tabId: tabId,
      rowId: selected[0],

      onPartialResult: (partialGroup) => {
        this.setState({
          loadingReferences: true,
          references: mergeReferencesToReferences(
            this.state.references,
            partialGroup.references
          ),
        });
      },

      onComplete: () => {
        this.setState({ display: 'block', loadingReferences: false });
      },
    });
  };

  handleReferenceClick = (targetWindowId, filter) => {
    const { dispatch, windowId, docId, tabId, selected } = this.props;

    const url = buildRelatedDocumentsViewUrl({
      targetWindowId,
      windowId,
      documentId: docId,
      tabId,
      rowIds: selected,
    });

    dispatch(setFilter(filter, targetWindowId));
    window.open(url, '_blank');
  };

  handleOpenNewTab = () => {
    const { onOpenNewTab, windowId, selected } = this.props;

    onOpenNewTab({
      windowId,
      rowIds: selected,
    });
  };

  onZoomIntoClicked = async () => {
    const { handleZoomInto } = this.props;
    const { contextMenu } = this.state;

    this.setState({ loadingZoomInto: true });
    try {
      await handleZoomInto(contextMenu.fieldName);
    } finally {
      this.setState({ loadingZoomInto: false });
    }
  };

  render() {
    const {
      blur,
      selected,
      mainTable,
      handleAdvancedEdit,
      handleDelete,
      handleFieldEdit,
      supportOpenRecord,
      docId,
    } = this.props;

    const { contextMenu, loadingZoomInto } = this.state;

    const positionY =
      contextMenu.y > TBL_CONTEXT_MENU_Y_MAX
        ? contextMenu.y - TBL_CONTEXT_Y_OFFSET
        : contextMenu.y;
    const positionX =
      contextMenu.x > TBL_CONTEXT_MENU_X_MAX
        ? contextMenu.x - TBL_CONTEXT_X_OFFSET
        : contextMenu.x;
    const isSingleRowSelected = selected.length === 1;

    const isShowZoomIntoOption = contextMenu.supportZoomInto;
    const isShowEditOption =
      isSingleRowSelected &&
      mainTable &&
      contextMenu.supportFieldEdit &&
      handleFieldEdit;
    const isShowAdvancedEditOption =
      isSingleRowSelected && !mainTable && handleAdvancedEdit;
    const isShowOpenInNewTabOption =
      supportOpenRecord && mainTable && isSingleRowSelected;
    const isShowDeleteOption = !!handleDelete;

    const isDisplayed =
      isShowZoomIntoOption ||
      isShowEditOption ||
      isShowAdvancedEditOption ||
      isShowOpenInNewTabOption ||
      isShowDeleteOption;

    return (
      <div
        ref={(c) => {
          this.contextMenu = c;
          if (c) {
            c.focus();
          }
        }}
        style={{
          left: positionX,
          top: positionY,
          display: isDisplayed ? 'block' : 'none',
          height: docId ? TBL_CONTEXT_POPUP_HEIGHT : '',
        }}
        className={
          'context-menu context-menu-open panel-bordered panel-primary'
        }
        tabIndex="0"
        onBlur={blur}
      >
        <div className="context-menu-main-options">
          {isShowZoomIntoOption && (
            <ContextMenuItem
              caption={counterpart.translate('window.table.zoomInto')}
              icon="meta-icon-share"
              onClick={this.onZoomIntoClicked}
              disabled={loadingZoomInto}
            />
          )}

          {isShowEditOption && (
            <ContextMenuItem
              caption={counterpart.translate('window.table.editField')}
              icon="meta-icon-edit"
              onClick={handleFieldEdit}
            />
          )}

          {(isShowZoomIntoOption || isShowEditOption) && (
            <hr className="context-menu-separator" />
          )}

          {isShowAdvancedEditOption && (
            <ContextMenuItem
              caption={counterpart.translate('window.table.advancedEdit')}
              icon="meta-icon-edit"
              shortcut={keymap.ADVANCED_EDIT}
              onClick={handleAdvancedEdit}
            />
          )}

          {isShowOpenInNewTabOption && (
            <ContextMenuItem
              caption={counterpart.translate('window.table.openInNewTab')}
              icon="meta-icon-file"
              shortcut={keymap.OPEN_SELECTED}
              onClick={this.handleOpenNewTab}
            />
          )}

          {isShowDeleteOption && (
            <ContextMenuItem
              caption={counterpart.translate('window.delete.caption')}
              icon="meta-icon-trash"
              shortcut={keymap.REMOVE_SELECTED}
              onClick={handleDelete}
            />
          )}
        </div>

        {docId && (
          <div className="context-menu-references">
            {this.renderReferences()}
          </div>
        )}
      </div>
    );
  }

  renderReferences() {
    const { loadingReferences, references } = this.state;

    if (!references && !loadingReferences) {
      return;
    }

    return (
      <Fragment>
        <hr className="context-menu-separator" />
        {references.map((reference) => (
          <ContextMenuItem
            key={`reference_${reference.id}`}
            caption={reference.caption}
            icon="meta-icon-share"
            onClick={() => {
              this.handleReferenceClick(
                reference.targetWindowId,
                reference.filter
              );
            }}
          />
        ))}
        {loadingReferences ? <Loader /> : null}
      </Fragment>
    );
  }
}

TableContextMenu.propTypes = {
  dispatch: PropTypes.func.isRequired,
  onOpenNewTab: PropTypes.func,
  x: PropTypes.number,
  y: PropTypes.number,
  fieldName: PropTypes.string,
  supportZoomInto: PropTypes.bool,
  supportFieldEdit: PropTypes.bool,
  docId: PropTypes.string,
  tabId: PropTypes.string,
  windowId: PropTypes.string,
  selected: PropTypes.array,
  blur: PropTypes.func,
  mainTable: PropTypes.bool,
  handleAdvancedEdit: PropTypes.func,
  handleDelete: PropTypes.func,
  handleFieldEdit: PropTypes.func,
  handleZoomInto: PropTypes.func,
  updateTableHeight: PropTypes.func,
  supportOpenRecord: PropTypes.bool,
};

const ContextMenuItem = ({
  icon,
  caption,
  shortcut,
  onClick,
  pending = false,
}) => {
  return (
    <div
      className={cx('context-menu-item', {
        'context-menu-item-pending': pending,
      })}
      onClick={!pending ? onClick : null}
    >
      {icon && <i className={icon} />}
      {` ${caption}`}
      {shortcut && <span className="tooltip-inline">{shortcut}</span>}
    </div>
  );
};

ContextMenuItem.propTypes = {
  caption: PropTypes.string.isRequired,
  icon: PropTypes.string,
  shortcut: PropTypes.string,
  onClick: PropTypes.func.isRequired,
  pending: PropTypes.bool,
};

export default connect()(TableContextMenu);
