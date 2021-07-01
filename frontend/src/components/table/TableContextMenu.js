import PropTypes from 'prop-types';
import React, { Component, Fragment } from 'react';
import { connect } from 'react-redux';
import counterpart from 'counterpart';

import Loader from '../app/Loader';

import { referencesEventSource } from '../../api/documentReferences';
import {
  buildRelatedDocumentsViewUrl,
  mergeReferencesToReferences,
} from '../../utils/documentReferencesHelper';
import { setFilter } from '../../actions/ListActions';
import keymap from '../../shortcuts/keymap';
import {
  DROPDOWN_OFFSET_BIG,
  TBL_CONTEXT_MENU_HEIGHT,
  TBL_CONTEXT_MENU_MAX_Y,
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
    const { windowId, docId, tabId, selected, updateTableHeight } = this.props;

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
        let offset;
        const { references, contextMenu } = this.state;
        const { y: initialY } = contextMenu;
        const mainPanel = document.querySelector('.panel-vertical-scroll');

        if (references.length > 2) {
          // for more than 5 links we add scroll, no of links is not limited
          if (references.length > 5) {
            this.contextMenu.style.height = TBL_CONTEXT_MENU_HEIGHT;
            this.contextMenu.style.overflowY = 'auto';
          }
          updateTableHeight(DROPDOWN_OFFSET_BIG);
          offset = DROPDOWN_OFFSET_BIG;
          // mainPanel.scrollTop = mainPanel.scrollHeight;

          this.setState((prevState) => {
            const { y: lastY } = prevState.contextMenu;
            return {
              contextMenu: {
                ...prevState.contextMenu,
                y: lastY - offset,
              },
            };
          });
        } else if (initialY > TBL_CONTEXT_MENU_MAX_Y) {
          // routine to calculate and adjust position for bottom of the table clicks, also scroll automatically
          const beforeAssign = mainPanel.scrollTop;
          // mainPanel.scrollTop = mainPanel.scrollHeight;
          let offset = initialY - mainPanel.scrollTop;
          this.setState((prevState) => {
            return {
              contextMenu: {
                ...prevState.contextMenu,
                y: offset + beforeAssign,
              },
            };
          });
        }

        this.setState({ loadingReferences: false });
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

  render() {
    const {
      blur,
      selected,
      mainTable,
      handleAdvancedEdit,
      handleDelete,
      handleFieldEdit,
      handleZoomInto,
    } = this.props;

    const { contextMenu } = this.state;

    const isSelectedOne = selected.length === 1;
    const showFieldEdit =
      isSelectedOne &&
      mainTable &&
      contextMenu.supportFieldEdit &&
      handleFieldEdit;

    return (
      <div
        ref={(c) => {
          this.contextMenu = c;
          if (c) {
            c.focus();
          }
        }}
        style={{
          left: contextMenu.x,
          top: contextMenu.y,
        }}
        className={
          'context-menu context-menu-open panel-bordered panel-primary'
        }
        tabIndex="0"
        onBlur={blur}
      >
        {contextMenu.supportZoomInto && (
          <div
            className="context-menu-item"
            onClick={() => handleZoomInto(contextMenu.fieldName)}
          >
            <i className="meta-icon-share" />
            {` ${counterpart.translate('window.table.zoomInto')}`}
          </div>
        )}

        {showFieldEdit && (
          <div className="context-menu-item" onClick={handleFieldEdit}>
            <i className="meta-icon-edit" />
            {` ${counterpart.translate('window.table.editField')}`}
            <span className="tooltip-inline">{keymap.FAST_INLINE_EDIT}</span>
          </div>
        )}

        {(contextMenu.supportZoomInto || showFieldEdit) && (
          <hr className="context-menu-separator" />
        )}

        {isSelectedOne && !mainTable && (
          <div className="context-menu-item" onClick={handleAdvancedEdit}>
            <i className="meta-icon-edit" />
            {` ${counterpart.translate('window.table.advancedEdit')}`}
            <span className="tooltip-inline">{keymap.ADVANCED_EDIT}</span>
          </div>
        )}

        {mainTable && (
          <div className="context-menu-item" onClick={this.handleOpenNewTab}>
            <i className="meta-icon-file" />
            {` ${counterpart.translate('window.table.openInNewTab')}`}
            <span className="tooltip-inline">{keymap.OPEN_SELECTED}</span>
          </div>
        )}

        {handleDelete && (
          <div className="context-menu-item" onClick={handleDelete}>
            <i className="meta-icon-trash" />
            {` ${counterpart.translate('window.delete.caption')}`}
            <span className="tooltip-inline">{keymap.REMOVE_SELECTED}</span>
          </div>
        )}

        {this.renderReferences()}
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
          <div
            className="context-menu-item"
            key={`reference_${reference.id}`}
            onClick={() => {
              this.handleReferenceClick(
                reference.targetWindowId,
                reference.filter
              );
            }}
          >
            <i className="meta-icon-share" /> {reference.caption}
          </div>
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
};

export default connect()(TableContextMenu);
