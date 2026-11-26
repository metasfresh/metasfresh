/** The `message` of the WebUI's JSON error body (see WebuiExceptionHandler), else the raw body text. */
export const serverErrorMessage = async (response) => {
  const text = await response.text().catch(() => '');
  try {
    return JSON.parse(text).message ?? text;
  } catch {
    return text;
  }
};
