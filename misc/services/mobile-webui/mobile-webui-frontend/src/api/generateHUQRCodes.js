import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

export const postGenerateHUQRCodes = ({
  wfProcessId,
  finishedGoodsReceiveLineId,
  huPackingInstructionsId,
  numberOfHUs,
  numberOfCopies,
}) => {
  return axios
    .post(`${apiBasePath}/manufacturing/generateHUQRCodes`, {
      wfProcessId,
      finishedGoodsReceiveLineId,
      huPackingInstructionsId,
      numberOfHUs,
      numberOfCopies,
    })
    .then((response) => unboxAxiosResponse(response));
};
