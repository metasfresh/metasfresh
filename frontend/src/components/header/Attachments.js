import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';

import { deleteRequest } from '../../api';
import { attachmentsRequest, openFile } from '../../actions/GenericActions';
import Loader from '../app/Loader';
import AttachUrl from './AttachUrl';

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

  /**
   * @method fetchAttachments
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  fetchAttachments = () => {
    const { windowType, docId } = this.props;

    attachmentsRequest('window', windowType, docId).then((response) => {
      this.setState({ data: response.data }, () => {
        if (this.attachments) {
          this.attachments.focus();
        }
      });
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

  /**
   * @method handleClickAttachment
   * @summary ToDo: Describe the method
   * @param {*} id
   * @todo Write the documentation
   */
  handleClickAttachment = (id) => {
    const { windowType, docId } = this.props;
    openFile('window', windowType, docId, 'attachments', id);
  };

  /**
   * @method handleDeleteAttachment
   * @summary ToDo: Describe the method
   * @param {*} event
   * @param {*} id
   * @todo Write the documentation
   */
  handleDeleteAttachment = (e, id) => {
    const { windowType, docId } = this.props;
    e.stopPropagation();
    if (
      window.confirm(
        `${counterpart.translate('window.attachment.deleteQuestion')}`
      )
    ) {
      deleteRequest('window', windowType, docId, null, null, 'attachments', id)
        .then(() => {
          return attachmentsRequest('window', windowType, docId);
        })
        .then((response) => {
          this.setState({
            data: response.data,
          });
        });
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

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { data } = this.state;
    let actions, content;

    if (data) {
      content = data.length ? this.renderData() : this.renderEmpty();
      actions = this.renderActions();
    } else {
      content = <Loader />;
    }

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
