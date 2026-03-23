export const getDocSummaryDataFromState = (state) => {
  const { master } = state.windowHandler;
  const { documentSummaryElement } = master.layout;
  return documentSummaryElement
    ? master.data[documentSummaryElement.fields[0].field]
    : null;
};

export const getDocActionElementFromState = (state) => {
  const { master } = state.windowHandler;
  const { docActionElement } = master.layout;
  return docActionElement;
};
