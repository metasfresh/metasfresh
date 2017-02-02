import React from 'react';
import { getImageAction, postImageAction } from '../../actions/ImageActions';
import { updateProcess } from '../../actions/WindowActions';

export default class Image extends React.Component{
    constructor(props){
        super(props);

        this.state = {
            imageSrc: ''
        };
    }

    componentDidMount(){
        const {data} = this.props;

        if (!data.value){
            return;
        }

        return this.updateImagePreview(data.value);
    }

    takeSnapshot(stream) {
        let img = document.createElement('img');
        const width = this.camera.offsetWidth;
        const height = this.camera.offsetHeight;

        let canvas = document.createElement('canvas');
        canvas.width = width;
        canvas.height = height;

        let context = canvas.getContext('2d');
        context.drawImage(this.camera, 0, 0, width, height);

        // upload the picture taken
        canvas.toBlob(blob => {
            this.uploadBlob(blob)
                .then(() => {
                    this.setState({
                        usingCamera: false
                    }, () => {
                        // stop using camera
                        stream.getVideoTracks()[0].stop();
                    })
                })
        })
    }

    handleCamera(){
        this.setState({
            usingCamera: true
        }, () => {
            navigator.mediaDevices.getUserMedia({
                video: {
                    facingMode: "user",
                    width: 400,
                    height: 300
                }
            })
                .then(stream => {
                    this.camera.src = window.URL.createObjectURL(stream);
                    this.camera.onloadedmetadata = (e) => {
                        this.camera.play();
                    };

                    this.camera.addEventListener('click', () => this.takeSnapshot(stream));
                })
        });
    }

    handleUploadFile(e){
        this.uploadBlob(this.imageInput.files[0]);
    }

    uploadBlob(blob){
        return new Promise(resolve => {
            this.setState({
                isLoading: true
            }, () => {
                resolve();
            });
        })
            .then(() => {
                const {data, processId, pinstanceId} = this.props;

                let fd = new FormData();
                fd.append('file', blob);

                return postImageAction(fd)
                    .then(imageId => {
                        return this.updateImagePreview(imageId)
                    })
                    .then(imageId => {
                        return updateProcess(processId, pinstanceId, [
                            {
                                "op": "replace",
                                "path": data.field,
                                "value": imageId
                            }
                        ])
                    })
                    .then(() => {
                        return new Promise(resolve => {
                            this.setState({
                                isLoading: false
                            }, () => {
                                resolve();
                            })
                        })
                    })
            })

    }

    updateImagePreview(id){
        return getImageAction(id)
            .then(blob => {
                return new Promise(resolve => {
                    let reader = new FileReader();
                    reader.onload = function () {
                        return resolve(reader.result);
                    };
                    reader.readAsDataURL(blob);
                });
            })
            .then(imageBase64 => {
                this.setState({
                    imageSrc: imageBase64
                });

                return id;
            });
    }

    renderImagePreview(src){
        return <img src={src} alt="image" className="img-fluid" />
    }

    renderVideoPreview(){
        const {isLoading} = this.state;
        return <div className={'camera-preview' + (isLoading ? ' loading' : '')}>
                <video ref={c => this.camera = c} />
                {isLoading && <div className="preview-loader"></div>}
            </div>
    }

    renderImagePlaceholder(text){
        return <div className="image-placeholder">
            <div className="placeholder-value">{text}</div>
        </div>
    }

    render(){
        const { imageSrc, usingCamera } = this.state;
        const { fields } = this.props;

        return <div className="row">
            <div className="col-sm-4 form-control-label">
                {imageSrc ? this.renderImagePreview(imageSrc) : this.renderImagePlaceholder(fields[0].emptyText) }
            </div>

            {usingCamera && this.renderVideoPreview()}

            <div className="col-sm-4 form-control-label image-source-options">
                <div className="row">
                    <div className="col-sm-12">
                        <label className="btn btn-meta-outline-secondary btn-sm">
                            <input className="input" type="file" onChange={(e) => this.handleUploadFile(e)} ref={c => this.imageInput = c} />
                            <div className="text-content">
                                <i className="meta-icon-upload" />
                                Upload a photo
                            </div>
                        </label>
                    </div>
                    {
                        navigator.mediaDevices &&
                        <div className="col-sm-12">
                            <div className="btn btn-meta-outline-secondary btn-sm" onClick={(e) => this.handleCamera()}>
                                <i className="meta-icon-photo"/>
                                Take from camera
                            </div>
                        </div>
                    }
                </div>
            </div>

        </div>
    }
}
