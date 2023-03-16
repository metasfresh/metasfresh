import { formatQtyToHumanReadableStr } from '../../../../../utils/qtys';

export const computeStepScanUserInfoQtys = ({
  uom,
  lineQtyToIssue,
  lineQtyToIssueTolerance,
  lineQtyToIssueRemaining,
}) => {
  return [
    {
      captionKey: 'general.QtyToPick_Total',
      value: formatQtyToHumanReadableStr({ qty: lineQtyToIssue, uom, tolerance: lineQtyToIssueTolerance }),
    },
    {
      captionKey: 'general.QtyToPick',
      value: formatQtyToHumanReadableStr({ qty: lineQtyToIssueRemaining, uom }),
    },
  ];
};
