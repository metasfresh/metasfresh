/**
 * @param formats the activity's `customQRCodeFormats` (each carries `id` = `JsonScannableCodeFormat.id`)
 * @param id the line's `cuLabelFormatId` (null / undefined = no effective format)
 * @returns the format with that id, or undefined
 */
export const selectCULabelFormat = (formats, id) => {
  if (id == null) {
    return undefined;
  }
  return (formats ?? []).find((format) => format?.id === id);
};
