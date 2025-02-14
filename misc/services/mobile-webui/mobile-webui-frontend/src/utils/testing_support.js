export const computeId = ({ id, idParam, captionKey }) => {
  if (id) {
    return id;
  } else if (idParam) {
    return idParam;
  } else if (captionKey) {
    const dotIdx = captionKey.lastIndexOf('.');
    const lastPart = dotIdx > 0 ? captionKey.substring(dotIdx + 1) : captionKey;
    return lastPart + '-button';
  } else {
    return null;
  }
};
