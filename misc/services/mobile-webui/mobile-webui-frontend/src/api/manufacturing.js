import axios from 'axios';
import { apiBasePath } from '../constants';

export function manufacturingReqest({ wfProcessId, activityId, receiptObject }) {
  const data = {
    wfProcessId,
    wfActivityId: activityId,
    ...receiptObject,
  };

  return axios.post(`${apiBasePath}/manufacturing/event`, data);
}
