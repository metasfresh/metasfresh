import counterpart from 'counterpart';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import { addNotification, createUrlAttachment } from '../../actions/AppActions';

class AttachUrl extends Component {
  state = {
    url: '',
    name: '',
    nameFromUrl: '',
  };

  updateNameFromUrl = url => {
    // generate name from URL by getting part after last / and before ? or #
    this.setState({
      // TODO: handle edge cases like URL with trailing slash
      nameFromUrl: url
        .split('/')
        .pop()
        .split('#')[0]
        .split('?')[0],
    });
  };

  handleChangeName = ({ target: { value: name } }) => {
    this.setState({ name });
  };

  handleBlurName = () => {
    // generate name from URL if name is not set
    const { name, url } = this.state;

    if (!name) {
      this.updateNameFromUrl(url);
    }
  };

  handleChangeUrl = ({ target: { value: url } }) => {
    this.setState({ url });
    this.updateNameFromUrl(url);
  };

  handleClick = event => {
    event.stopPropagation();
    event.persist();

    const {
      windowId,
      documentId,
      onClose,
      dispatch,
      fetchAttachments,
    } = this.props;
    const { url, name, nameFromUrl } = this.state;

    createUrlAttachment({
      windowId,
      documentId,
      url,
      name: name || nameFromUrl,
    })
      .then(() => {
        onClose(event);
        fetchAttachments();
      })
      .catch(() => {
        dispatch(
          addNotification(
            counterpart.translate('window.attachment.url.title'),
            counterpart.translate('window.attachment.url.error'),
            5000,
            'error'
          )
        );
      });
  };

  render() {
    const { onClose } = this.props;
    const { url, name, nameFromUrl } = this.state;

    // TODO: increase max-len or find another way to avoid this
    // (following this rule here decreases this JSX' readability)
    /* eslint-disable max-len */
    return (
      <div className="screen-freeze">
        <div className="panel panel-modal panel-attachurl panel-modal-primary">
          <div className="panel-attachurl-header-wrapper">
            <div className="panel-attachurl-header panel-attachurl-header-top">
              <span className="attachurl-headline">
                {counterpart.translate('window.attachment.url.title')}
              </span>
              <div
                className="input-icon input-icon-lg attachurl-icon-close"
                onClick={onClose}
              >
                <i className="meta-icon-close-1" />
              </div>
            </div>
            <div className="panel-attachurl-header panel-attachurl-bright">
              <div className="panel-attachurl-data-wrapper">
                <span className="attachurl-label">
                  {counterpart.translate('window.attachment.url.url')}
                </span>
                <input
                  className="attachurl-input"
                  type="url"
                  onChange={this.handleChangeUrl}
                  value={url}
                />
              </div>
            </div>
            <div className="panel-attachurl-header panel-attachurl-bright">
              <div className="panel-attachurl-data-wrapper">
                <span className="attachurl-label">
                  {counterpart.translate('window.attachment.url.name')}
                </span>
                <input
                  className="attachurl-input"
                  type="text"
                  onBlur={this.handleBlurName}
                  onChange={this.handleChangeName}
                  value={name || nameFromUrl}
                />
              </div>
            </div>
          </div>
          <div className="panel-attachurl-footer">
            <button
              onClick={this.handleClick}
              className="btn btn-meta-success btn-sm btn-submit"
            >
              {counterpart.translate('window.attachment.url.create')}
            </button>
          </div>
        </div>
      </div>
    );
    /* eslint-enable max-len */
  }
}

export default connect()(AttachUrl);
