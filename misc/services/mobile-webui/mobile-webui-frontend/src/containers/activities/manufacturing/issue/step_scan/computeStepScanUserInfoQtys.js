import { formatQtyToHumanReadable } from '../../../../../utils/qtys';

export const computeStepScanUserInfoQtys = ({
  uom,
  lineQtyToIssue,
  lineQtyToIssueTolerance,
  lineQtyToIssueRemaining,
}) => {
  return [
    {
      captionKey: 'general.QtyToPick_Total',
      value: formatQtyToHumanReadable({ qty: lineQtyToIssue, uom, tolerance: lineQtyToIssueTolerance }),
    },
    {
      captionKey: 'general.QtyToPick',
      value: formatQtyToHumanReadable({ qty: lineQtyToIssueRemaining, uom }),
    },
  ];
};
