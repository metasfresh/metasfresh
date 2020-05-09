export function referencesEventSource({
  windowId,
  documentId,
  tabId,
  rowId,
  onPartialResult,
  onComplete,
}) {
  const url = `${config.API_URL}/window/${windowId}/${documentId}${
    rowId ? `/${tabId}/${rowId}` : ''
  }/references/sse`;

  var eventSource = new EventSource(url, { withCredentials: true });

  eventSource.onmessage = (event) => {
    const data = JSON.parse(event.data);
    if (data.type === 'PARTIAL_RESULT') {
      onPartialResult(data.partialGroup);
    } else if (data.type === 'COMPLETED') {
      eventSource.close();
      onComplete && onComplete();
    }
  };

  eventSource.onerror = () => {
    eventSource.close();
  };

  return eventSource;
}
