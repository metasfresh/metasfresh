import counterpart from 'counterpart';
import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { getImageAction, postImageAction } from '../../actions/AppActions';
import Loader from '../app/Loader';

/**
 * @file Function based component.
 * @module Placeholder
 * @param {object} props
 */
const Placeholder = (props) => (
  <div className="image-placeholder">
    <div className="placeholder-value">{props.children}</div>
  </div>
);

/**
 * @file Class based component.
 * @module Image
 * @extends Component
 */
class Image extends Component {
  constructor(props) {
    super(props);

    this.state = {
      imageSrc: '',
      usingCamera: false,
      isLoading: false,
      stream: {},
    };
  }

  /**
   * @method componentDidMount
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  componentDidMount() {
    const { data } = this.props;

    if (!data.value) {
      return;
    }

    return this.updateImagePreview(data.value);
  }

  /**
   * @method isCameraAvailable
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  isCameraAvailable() {
    return (
      (!~location.protocol.indexOf('https') ||
        !~location.href.indexOf('localhost')) &&
      navigator.mediaDevices &&
      navigator.mediaDevices.getUserMedia
    );
  }

  /**
   * @method uploadBlob
   * @summary ToDo: Describe the method
   * @param {*} blob
   * @todo Write the documentation
   */
  uploadBlob(blob) {
    const { data, handlePatch } = this.props;

    let fd = new FormData();
    fd.append('file', blob);

    return new Promise((resolve) => {
      this.setState(
        {
          isLoading: true,
        },
        () => {
          resolve();
        }
      );
    })
      .then(() => {
        return postImageAction(fd);
      })
      .then((imageId) => {
        return this.updateImagePreview(imageId);
      })
      .then((imageId) => {
        return handlePatch(data.field, imageId);
      })
      .then(() => {
        return new Promise((resolve) => {
          this.setState(
            {
              isLoading: false,
            },
            () => {
              resolve();
            }
          );
        });
      });
  }

  /**
   * @method updateImagePreview
   * @summary ToDo: Describe the method
   * @param {*} id
   * @todo Write the documentation
   */
  updateImagePreview(id) {
    return getImageAction(id)
      .then((blob) => {
        return new Promise((resolve) => {
          let reader = new FileReader();
          reader.onload = function () {
            return resolve(reader.result);
          };
          reader.readAsDataURL(blob);
        });
      })
      .then((imageBase64) => {
        this.setState({
          imageSrc: imageBase64,
        });

        return id;
      });
  }

  /**
   * @method takeSnapshot
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  takeSnapshot() {
    const width = this.camera.offsetWidth;
    const height = this.camera.offsetHeight;

    let canvas = document.createElement('canvas');
    canvas.width = width;
    canvas.height = height;

    let context = canvas.getContext('2d');
    context.drawImage(this.camera, 0, 0, width, height);

    // upload the picture taken
    canvas.toBlob((blob) => {
      this.uploadBlob(blob);
      this.stopUsingCamera();
    });
  }

  /**
   * @method stopUsingCamera
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  stopUsingCamera() {
    const { stream } = this.state;

    return new Promise((resolve) => {
      this.setState(
        {
          usingCamera: false,
        },
        () => {
          // stop using camera
          stream.getVideoTracks()[0].stop();
          resolve();
        }
      );
    });
  }

  /**
   * @method handleCamera
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleCamera() {
    this.setState(
      {
        usingCamera: true,
      },
      () => {
        navigator.mediaDevices
          .getUserMedia({
            video: {
              facingMode: 'user',
              width: 400,
              height: 300,
            },
          })
          .then((stream) => {
            this.camera.srcObject = stream;
            this.camera.onloadedmetadata = () => {
              this.camera.play();
            };

            this.camera.addEventListener('click', () => this.takeSnapshot());
            this.setState({
              stream: stream,
            });
          });
      }
    );
  }

  /**
   * @method handleUploadFiler
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleUploadFile() {
    this.uploadBlob(this.imageInput.files[0]);
  }

  /**
   * @method handleKeyDown
   * @summary ToDo: Describe the method
   * @param {object} event
   * @todo Write the documentation
   */
  handleKeyDown = (e) => {
    switch (e.key) {
      case 'Escape':
        e.preventDefault();
        this.stopUsingCamera();
        break;
    }
  };

  /**
   * @method handleClear
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleClear = () => {
    const { handlePatch, data } = this.props;
    handlePatch(data.field, null);
    this.imageInput.value = '';
    this.setState({
      imageSrc: '',
    });
  };

  /**
   * @method renderVideoPreview
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  renderVideoPreview() {
    const { isLoading } = this.state;
    return (
      <div className={'camera-preview' + (isLoading ? ' loading' : '')}>
        <video ref={(c) => (this.camera = c)} />
        {isLoading && <div className="preview-loader" />}
      </div>
    );
  }

  /**
   * @method renderRegularCameraControl
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  renderRegularCameraControl() {
    return (
      <div
        className="btn btn-block btn-meta-outline-secondary btn-sm mb-1"
        onClick={() => this.handleCamera()}
      >
        <i className="meta-icon-photo" />
        {counterpart.translate('widget.takeFromCamera.caption')}
      </div>
    );
  }

  /**
   * @method renderImagePreview
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  renderImagePreview() {
    const { isLoading, imageSrc } = this.state;
    const { fields } = this.props;

    if (isLoading)
      return (
        <Placeholder>
          <Loader />
        </Placeholder>
      );
    else if (imageSrc)
      return (
        <div className="image-preview">
          <img src={imageSrc} alt="image" className="img-fluid" />
        </div>
      );
    else return <Placeholder>{fields[0].emptyText}</Placeholder>;
  }

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { imageSrc, usingCamera } = this.state;
    const { readonly } = this.props;

    return (
      <div
        className="form-control-label image-widget"
        onKeyDown={this.handleKeyDown}
        tabIndex={0}
      >
        {this.renderImagePreview()}

        {usingCamera && this.renderVideoPreview()}

        {!readonly && (
          <div className=" image-source-options">
            <label className="btn btn-meta-outline-secondary btn-sm mb-1">
              <input
                className="input"
                type="file"
                onChange={(e) => this.handleUploadFile(e)}
                ref={(c) => (this.imageInput = c)}
              />
              <div className="text-content">
                <i className="meta-icon-upload" />
                {counterpart.translate('widget.uploadPhoto.caption')}
              </div>
            </label>
            {this.isCameraAvailable() && this.renderRegularCameraControl()}
            {imageSrc && (
              <div
                className="btn btn-meta-outline-secondary btn-sm"
                onClick={() => this.handleClear()}
              >
                <i className="meta-icon-close-alt" />
                {counterpart.translate('widget.clearPhoto.caption')}
              </div>
            )}
          </div>
        )}
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {*} children
 * @todo Check props. Which proptype? Required or optional?
 */
Placeholder.propTypes = {
  children: PropTypes.any,
};

/**
 * @typedef {object} Props Component props
 * @prop {*} data
 * @prop {*} handlePatch
 * @prop {*} fields
 * @prop {*} readonly
 * @todo Check props. Which proptype? Required or optional?
 */
Image.propTypes = {
  data: PropTypes.any,
  handlePatch: PropTypes.any,
  fields: PropTypes.any,
  readonly: PropTypes.any,
};

export default Image;
