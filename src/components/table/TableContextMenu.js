import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import { referencesRequest } from '../../actions/GenericActions';
import { setFilter } from '../../actions/ListActions';
import keymap from '../../shortcuts/keymap';

class TableContextMenu extends Component {
  constructor(props) {
    super(props);
    this.state = {
      contextMenu: {
        x: 0,
        y: 0,
      },
      references: [],
    };
  }

  componentDidMount() {
    const {
      x,
      y,
      fieldName,
      supportZoomInto,
      supportFieldEdit,
      docId,
    } = this.props;

    this.setPosition(
      x,
      y,
      fieldName,
      supportZoomInto,
      supportFieldEdit,
      this.contextMenu
    );

    if (docId) {
      this.getReferences();
    }
  }

  getPosition = (dir, pos, element) => {
    if (element) {
      const windowSize = dir === 'x' ? window.innerWidth : window.innerHeight;
      const elementSize =
        dir === 'x' ? element.offsetWidth : element.offsetHeight;

      if (windowSize - pos > elementSize) {
        return pos;
      } else {
        return windowSize - elementSize;
      }
    }
  };

  setPosition = (x, y, fieldName, supportZoomInto, supportFieldEdit, elem) => {
    this.setState({
      contextMenu: {
        x: this.getPosition('x', x, elem),
        y: this.getPosition('y', y, elem),
        fieldName,
        supportZoomInto,
        supportFieldEdit,
      },
    });
  };

  getReferences = () => {
    const { docId, tabId, type, selected } = this.props;

    referencesRequest('window', type, docId, tabId, selected[0]).then(
      response => {
        this.setState({
          references: response.data.references,
        });
      }
    );
  };

  handleReferenceClick = (refType, filter) => {
    const { dispatch, type, docId, tabId, selected } = this.props;

    dispatch(setFilter(filter, refType));

    window.open(
      `/window/${refType}?refType=${type}&refId=${docId}&refTabId=${tabId}&refRowIds=${JSON.stringify(
        selected || []
      )}`,
      '_blank'
    );
  };

  render() {
    const {
      blur,
      selected,
      mainTable,
      handleAdvancedEdit,
      handleOpenNewTab,
      handleDelete,
      handleFieldEdit,
      handleZoomInto,
    } = this.props;

    const { contextMenu, references } = this.state;

    const isSelectedOne = selected.length === 1;
    const showFieldEdit =
      isSelectedOne &&
      mainTable &&
      contextMenu.supportFieldEdit &&
      handleFieldEdit;

    return (
      <div
        ref={c => {
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
          'context-menu context-menu-open' + ' panel-bordered panel-primary'
        }
        tabIndex="0"
        onBlur={blur}
      >
        {contextMenu.supportZoomInto && (
          <div
            className="context-menu-item"
            onClick={() => handleZoomInto(contextMenu.fieldName)}
          >
            <i className="meta-icon-share" /> Zoom into
          </div>
        )}

        {showFieldEdit && (
          <div className="context-menu-item" onClick={handleFieldEdit}>
            <i className="meta-icon-edit" /> Edit field value
          </div>
        )}

        {(contextMenu.supportZoomInto || showFieldEdit) && (
          <hr className="context-menu-separator" />
        )}

        {isSelectedOne &&
          !mainTable && (
            <div className="context-menu-item" onClick={handleAdvancedEdit}>
              <i className="meta-icon-edit" /> Advanced edit
              <span className="tooltip-inline">{keymap.ADVANCED_EDIT}</span>
            </div>
          )}

        {mainTable && (
          <div className="context-menu-item" onClick={handleOpenNewTab}>
            <i className="meta-icon-file" /> Open in new tab
            <span className="tooltip-inline">{keymap.OPEN_SELECTED}</span>
          </div>
        )}

        {handleDelete && (
          <div className="context-menu-item" onClick={handleDelete}>
            <i className="meta-icon-trash" /> Delete
            <span className="tooltip-inline">{keymap.REMOVE_SELECTED}</span>
          </div>
        )}

        {references && <hr className="context-menu-separator" />}

        {references &&
          references.map((item, index) => (
            <div
              className="context-menu-item"
              key={index}
              onClick={() => {
                this.handleReferenceClick(item.documentType, item.filter);
              }}
            >
              <i className="meta-icon-share" /> {item.caption}
            </div>
          ))}
      </div>
    );
  }
}

TableContextMenu.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

export default connect()(TableContextMenu);
