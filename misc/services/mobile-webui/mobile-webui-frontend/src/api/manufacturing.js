import axios from 'axios';
import { apiBasePath } from '../constants';

export function manufacturingReceiptReqest({ wfProcessId, activityId, receiptObject }) {
  const data = {
    wfProcessId,
    wfActivityId: activityId,
    ...receiptObject,
  };

  return axios.post(`${apiBasePath}/manufacturing/event`, data);
}
