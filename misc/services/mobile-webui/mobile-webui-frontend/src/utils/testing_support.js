export const computeId = ({ id, idParam, captionKey, suffix }) => {
  if (id) {
    return id;
  } else if (idParam) {
    return idParam;
  } else if (captionKey) {
    const dotIdx = captionKey.lastIndexOf('.');
    const lastPart = dotIdx > 0 ? captionKey.substring(dotIdx + 1) : captionKey;
    return lastPart + '-' + (suffix || 'button');
  } else {
    return null;
  }
};

export const computeTestId = ({ testIdParam, captionKey, suffix }) => {
  return computeId({ idParam: testIdParam, captionKey, suffix });
};
