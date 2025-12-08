import axios from 'axios';
import { unboxAxiosResponse } from '../utils';

const ANDROID_PRINTER_PROXY_URL = 'http://localhost:8001/print';

export const postToAndroidPrinterProxy = ({ printerUri, fileName, fileUri, dataBase64Encoded }) => {
  return axios
    .post(ANDROID_PRINTER_PROXY_URL, {
      printerUri,
      fileName,
      fileUri,
      dataBase64Encoded,
    })
    .then((response) => unboxAxiosResponse(response));
};
