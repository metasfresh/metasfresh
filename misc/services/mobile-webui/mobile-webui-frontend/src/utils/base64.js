export const base64Encode = (string) => {
  return string ? new Buffer(string).toString('base64') : null;
};
