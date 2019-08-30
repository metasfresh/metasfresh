import React, { Component } from 'react';
import Dropzone from 'react-dropzone';
import PropTypes from 'prop-types';

/**
 * @file The DropzoneWrapper allows the user to drag&drop files onto the UI. These files are
 * then uploaded to metasfresh backend.
 * @module Dropzone
 * @extends Component
 */
class DropzoneWrapper extends Component {
  constructor(props) {
    super(props);

    this.state = {
      dragActive: false,
    };
  }

  /**
   * @method handleDropFile
   * @summary ToDo: Describe the method
   * @param {*} accepted
   * @param {*} rejected
   */
  handleDropFile = (accepted, rejected) => {
    const { handleDropFile, handleRejectDropped } = this.props;

    this.handleDragEnd();

    for (const file of accepted) {
      handleDropFile(file);
    }

    for (const file of rejected) {
      handleRejectDropped(file);
    }
  };

  /**
   * @method handleDragStart
   * @summary ToDo: Describe the method
   */
  handleDragStart = () => {
    const { handleDragStart } = this.props;
    const { dragActive } = this.state;

    this.setState(
      {
        dragActive: true,
      },
      () => {
        dragActive && handleDragStart();
      }
    );
  };

  /**
   * @method handleDragEnd
   * @summary ToDo: Describe the method
   */
  handleDragEnd = () => {
    this.setState({
      dragActive: false,
    });
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   */
  render() {
    const { dragActive } = this.state;

    return (
      <Dropzone
        className={
          'document-file-dropzone' +
          (dragActive ? ' document-file-dropzone-active' : '')
        }
        disablePreview={true}
        disableClick={true}
        onDragEnter={this.handleDragStart}
        onDragLeave={this.handleDragEnd}
        onDrop={this.handleDropFile}
      >
        {this.props.children}
        <div className="document-file-dropzone-backdrop">
          <span className="document-file-dropzone-info">
            <i className="meta-icon-upload-1" /> Drop files here
          </span>
        </div>
      </Dropzone>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {*} children
 * @prop {func} handleDragStart
 * @prop {func} handleDropFile
 * @prop {func} handleRejectDropped
 */
DropzoneWrapper.propTypes = {
  children: PropTypes.any,
  handleDragStart: PropTypes.any,
  handleDropFile: PropTypes.any,
  handleRejectDropped: PropTypes.any,
};

export default DropzoneWrapper;
