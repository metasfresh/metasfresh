import axios from 'axios';
import { apiBasePath } from '../constants';

// TODO move it workplaceManager
export const postWorkplaceAssignment = async (workplaceId) => {
  return axios.post(`${apiBasePath}/workplace/${workplaceId}/assign`);
};
