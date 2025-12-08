export const computeTestId = ({ testIdParam, captionKey, suffix }) => {
  if (testIdParam) {
    return testIdParam;
  } else if (captionKey) {
    return computeIdFromCaptionKey({ captionKey, suffix });
  } else {
    return null;
  }
};

export const computeId = ({ id, idParam, testIdParam, captionKey, suffix }) => {
  if (id) {
    return id;
  } else if (idParam) {
    return idParam;
  } else if (testIdParam) {
    // we precisely have to set an testId then don't generate an id
    return null;
  } else if (captionKey) {
    return computeIdFromCaptionKey({ captionKey, suffix });
  } else {
    return null;
  }
};

function computeIdFromCaptionKey({ captionKey, suffix }) {
  const dotIdx = captionKey.lastIndexOf('.');
  const lastPart = dotIdx > 0 ? captionKey.substring(dotIdx + 1) : captionKey;
  return lastPart + '-' + (suffix || 'button');
}
