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
