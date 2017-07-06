import axios from 'axios';

export function createEmail(windowId, documentId){
    return axios.post(
        config.API_URL + '/mail', {
            "documentPath": {
                "documentId": documentId,
                "windowId": windowId
            }
        }
    );
}

export function sendEmail(emailId){
    return axios.post(config.API_URL + '/mail/' + emailId + '/send');
}

export function addAttachment(emailId, file){
    console.log(file);
    return axios.post(config.API_URL + '/mail/' + emailId + '/field/attachments', {
        "emailId": emailId,
        "file": file
    });
}
