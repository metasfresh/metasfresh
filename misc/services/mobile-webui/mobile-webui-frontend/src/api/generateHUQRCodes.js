import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

export const postGenerateHUQRCodes = ({ wfProcessId, finishedGoodsReceiveLineId, tuPackingInstructionsId, qtyTUs }) => {
  return axios
    .post(`${apiBasePath}/manufacturing/generateHUQRCodes`, {
      wfProcessId,
      finishedGoodsReceiveLineId,
      tuPackingInstructionsId,
      qtyTUs,
    })
    .then((response) => unboxAxiosResponse(response));
};
