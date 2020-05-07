import counterpart from 'counterpart';
import React, { Component } from 'react';

import { addAttachment } from '../../actions/EmailActions';
import FileInput from '../FileInput';

class Attachments extends Component {
  constructor(props) {
    super(props);
  }

  componentDidUpdate(prevProps) {
    const { attachments } = this.props;
    if (prevProps !== attachments) {
      this.clearFile();
    }
  }

  addAttachment = e => {
    const { emailId, getEmail } = this.props;
    addAttachment(emailId, e.target.files[0]).then(() => {
      getEmail(emailId);
    });
  };

  clearFile = () => {
    document.getElementsByClassName('attachment-input')[1].value = '';
  };

  render() {
    const { attachments } = this.props;
    return (
      <div className="email-attachments-wrapper">
        {attachments &&
          attachments.map((item, index) => {
            return (
              <div className="attachment" key={index}>
                <div className="attachment-text">
                  {/*{item[Object.keys(item)[0]]}*/}
                  {item.caption}
                </div>
                <div className="input-icon input-icon-lg">
                  {/*<i className="meta-icon-close-1"/>*/}
                </div>
              </div>
            );
          })}
        <div>
          <span className="add-attachment">
            <form>
              <i className="meta-icon-attachments" />
              <FileInput
                name="myImage"
                placeholder={counterpart.translate(
                  'window.email.addattachment'
                )}
                className="attachment-input"
                onChange={this.addAttachment}
              />
            </form>
          </span>
        </div>
      </div>
    );
  }
}

export default Attachments;
