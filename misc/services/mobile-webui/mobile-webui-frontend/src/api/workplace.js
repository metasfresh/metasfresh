import axios from 'axios';
import { apiBasePath } from '../constants';

export const postWorkplaceAssignment = async (workplaceId) => {
  return axios.post(`${apiBasePath}/workplace/${workplaceId}/assign`);
};
