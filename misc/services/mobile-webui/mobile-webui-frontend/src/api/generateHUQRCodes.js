import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

export const postGenerateHUQRCodes = ({ wfProcessId, finishedGoodsReceiveLineId, qtyTUs }) => {
  return axios
    .post(`${apiBasePath}/manufacturing/generateHUQRCodes`, {
      wfProcessId,
      finishedGoodsReceiveLineId,
      qtyTUs,
    })
    .then((response) => unboxAxiosResponse(response));
};
