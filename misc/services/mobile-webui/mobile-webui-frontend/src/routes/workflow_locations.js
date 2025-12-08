// NOTE: extracted from workflow in order to avoid cyclic dependencies errors

import { toUrl } from '../utils';

export const getWFProcessScreenLocation = ({ applicationId, wfProcessId, back }) => {
  return toUrl(`/${applicationId}/wf/${wfProcessId}`, { back });
};
