import React, { Component } from 'react';
import counterpart from 'counterpart';
import FileInput from 'react-file-input';

import {
    addAttachment
} from '../../actions/EmailActions';

class Attachments extends Component {
    constructor(props) {
        super(props);
    }

    addAttachment=(e)=> {
        const{emailId} = this.props;
        addAttachment(emailId, e.target.files[0]);
        console.log('Selected file:', e.target.files[0]);
    }

    render() {
        const {attachments} = this.props;
        console.log(attachments);
        return (
            <div className="email-attachments-wrapper">
                {attachments && attachments.map((item, index) => {
                    return(
                        <div 
                            className="attachment"
                            key={index}
                        >
                            <div className="attachnemt-text">
                                {item[Object.keys(item)[0]] }
                            </div>
                            <div className="input-icon input-icon-lg">
                                <i className="meta-icon-close-1"/>
                            </div>
                        </div>
                    )
                })}
                <div>
                    <span 
                        className="add-attachment"
                    >
                    
                    <form>
                        <i className="meta-icon-attachments"/>
                        <FileInput name="myImage"
                            placeholder={counterpart.translate('window.email.addattachment')}
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
