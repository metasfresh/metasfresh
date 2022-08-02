// NOTE: extracted from workflow in order to avoid cyclic dependencies errors

export const getWFProcessScreenLocation = ({ applicationId, wfProcessId }) => `/${applicationId}/wf/${wfProcessId}`;
