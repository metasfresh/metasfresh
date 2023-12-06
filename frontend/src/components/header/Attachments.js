import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import SpinnerOverlay from '../app/SpinnerOverlay';
import AttachUrl from './AttachUrl';
import {
  deleteAttachment,
  getAttachments,
  getAttachmentUrl,
} from '../../api/window';

/**
 * @file Class based component.
 * @module Attachments
 * @extends Component
 */
class Attachments extends Component {
  state = {
    data: null,
    attachmentHovered: null,
    isAttachUrlOpen: false,
  };

  /**
   * @method componentDidMount
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  componentDidMount = () => {
    this.fetchAttachments();
  };

  isDocumentNotFound = () => {
    const { windowType, docId } = this.props;
    return !windowType || docId === 'notfound';
  };

  /**
   * @method fetchAttachments
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  fetchAttachments = () => {
    const { windowType: windowId, docId: documentId } = this.props;

    if (this.isDocumentNotFound()) {
      this.setState({ data: [] });
      return;
    }

    getAttachments({ windowId, documentId })
      .then((response) => {
        this.setState({ data: response.data }, () => {
          if (this.attachments) {
            this.attachments.focus();
          }
        });
      })
      .catch((ex) => {
        console.log('Got error while fetching the attachments', { ex });
        this.setState({ data: [] });
      });
  };

  /**
   * @method toggleAttachmentDelete
   * @summary ToDo: Describe the method
   * @param {*} value
   * @todo Write the documentation
   */
  toggleAttachmentDelete = (value) => {
    this.setState({
      attachmentHovered: value,
    });
  };

  /**
   * @method handleClickAttachUrl
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleClickAttachUrl = () => {
    this.setState({ isAttachUrlOpen: true });
  };

  /**
   * @method handleCloseAttachUrl
   * @summary ToDo: Describe the method
   * @param {*} event
   * @todo Write the documentation
   */
  handleCloseAttachUrl = (event) => {
    event.stopPropagation();
    this.setState({ isAttachUrlOpen: false });
  };

  handleClickAttachment = (attachmentEntryId) => {
    const { windowType: windowId, docId: documentId } = this.props;
    const url = getAttachmentUrl({ windowId, documentId, attachmentEntryId });
    window.open(url, '_blank');
  };

  handleDeleteAttachment = (e, attachmentEntryId) => {
    const { windowType: windowId, docId: documentId } = this.props;
    e.stopPropagation();
    if (
      window.confirm(
        `${counterpart.translate('window.attachment.deleteQuestion')}`
      )
    ) {
      deleteAttachment({ windowId, documentId, attachmentEntryId })
        .then(() => getAttachments({ windowId, documentId }))
        .then((response) => this.setState({ data: response.data }));
    }
  };

  /**
   * @method handleKeyDown
   * @summary ToDo: Describe the method
   * @param {*} event
   * @todo Write the documentation
   */
  handleKeyDown = (e) => {
    const active = document.activeElement;

    const keyHandler = (e, dir) => {
      const sib = dir ? 'nextSibling' : 'previousSibling';
      e.preventDefault();
      if (active.classList.contains('js-subheader-item')) {
        active[sib] && active[sib].focus();
      } else {
        active.childNodes[0].focus();
      }
    };

    switch (e.key) {
      case 'ArrowDown':
        keyHandler(e, true);
        break;
      case 'ArrowUp':
        keyHandler(e, false);
        break;
      case 'Enter':
        e.preventDefault();
        document.activeElement.click();
        break;
    }
  };

  /**
   * @method renderActions
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  renderActions = () => {
    const { windowType, docId } = this.props;
    const { isAttachUrlOpen } = this.state;

    return (
      <div className="subheader-attachurl" onClick={this.handleClickAttachUrl}>
        {counterpart.translate('window.attachment.url.add')}

        {isAttachUrlOpen && (
          <AttachUrl
            windowId={windowType}
            documentId={docId}
            handleClose={this.handleCloseAttachUrl}
            fetchAttachments={this.fetchAttachments}
          />
        )}
      </div>
    );
  };

  /**
   * @method renderData
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  renderData = () => {
    const { data, attachmentHovered } = this.state;

    return data.map((item, key) => (
      <div
        className="subheader-item subheader-item-ellipsis js-subheader-item"
        key={key}
        tabIndex={0}
        onMouseEnter={() => {
          if (item.allowDelete) {
            this.toggleAttachmentDelete(item.id);
          }
        }}
        onMouseLeave={() => {
          this.toggleAttachmentDelete(null);
        }}
        onClick={() => {
          this.handleClickAttachment(item.id);
        }}
      >
        {item.name}
        {attachmentHovered === item.id && (
          <div
            className="subheader-additional-box"
            onClick={(e) => {
              this.handleDeleteAttachment(e, item.id);
            }}
          >
            <i className="meta-icon-delete" />
          </div>
        )}
      </div>
    ));
  };

  /**
   * @method renderEmpty
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  renderEmpty = () => (
    <div className="subheader-item subheader-item-disabled">
      {counterpart.translate('window.sideList.attachments.empty')}
    </div>
  );

  renderAttachmentSpinner = () => {
    return (
      <div className="side-attachment-wrapper">
        <SpinnerOverlay iconSize={50} />
      </div>
    );
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { data } = this.state;
    let actions, content;

    if (this.isDocumentNotFound()) {
      content = this.renderEmpty();
    } else if (data) {
      content = data.length ? this.renderData() : this.renderEmpty();
      actions = this.renderActions();
    } else {
      content = this.renderAttachmentSpinner();
    }

    console.log('render', {
      content,
      actions,
      isDocumentNotFound: this.isDocumentNotFound(),
    });
    return (
      <div
        onKeyDown={this.handleKeyDown}
        ref={(c) => {
          this.attachments = c;
        }}
        tabIndex={0}
      >
        {content}
        {actions}
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {string} windowType
 * @prop {string} docId
 * @todo Check props. Which proptype? Required or optional?
 */
Attachments.propTypes = {
  windowType: PropTypes.string.isRequired,
  docId: PropTypes.string.isRequired,
};

export default Attachments;
