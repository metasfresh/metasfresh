import axios from 'axios';

export const getProcessLayout = (processId) => {
  return axios.get(`${config.API_URL}/process/${processId}/layout`);
};

export const startProcess = (processId, pinstanceId) => {
  return axios.get(
    `${config.API_URL}/process/${processId}/${pinstanceId}/start`
  );
};

export const getProcessData = ({
  processId,
  viewId,
  documentType,
  ids,
  tabId,
  rowId,
  selectedTab,
  childViewId,
  childViewSelectedIds,
  parentViewId,
  parentViewSelectedIds,
}) => {
  const payload = { processId };

  if (viewId) {
    payload.viewId = viewId;
    payload.viewDocumentIds = ids;

    if (childViewId) {
      payload.childViewId = childViewId;
      payload.childViewSelectedIds = childViewSelectedIds;
    }

    if (parentViewId) {
      payload.parentViewId = parentViewId;
      payload.parentViewSelectedIds =
        parentViewSelectedIds instanceof Array
          ? parentViewSelectedIds
          : [parentViewSelectedIds];
    }
  } else {
    payload.documentId = Array.isArray(ids) ? ids[0] : ids;
    payload.documentType = documentType;
    payload.tabId = tabId;
    payload.rowId = rowId;
  }

  if (selectedTab) {
    const { tabId, rowIds } = selectedTab;

    if (tabId && rowIds) {
      payload.selectedTab = {
        tabId,
        rowIds,
      };
    }
  }

  return axios.post(`${config.API_URL}/process/${processId}`, payload);
};

export const getProcessFileUrl = ({ processId, pinstanceId, filename }) => {
  let filenameNorm = filename ? filename : 'report.pdf';
  filenameNorm = filenameNorm.replace(/[/\\?%*:|"<>]/g, '-');
  filenameNorm = encodeURIComponent(filenameNorm);

  return `${config.API_URL}/process/${processId}/${pinstanceId}/print/${filenameNorm}`;
};
