import { formatQtyToHumanReadable } from '../../../../../utils/qtys';

export const computeStepScanUserInfoQtys = ({
  uom,
  lineQtyToIssue,
  lineQtyToIssueTolerancePerc,
  lineQtyToIssueRemaining,
}) => {
  return [
    {
      captionKey: 'general.QtyToPick_Total',
      value: formatQtyToHumanReadable({ qty: lineQtyToIssue, uom, tolerancePercent: lineQtyToIssueTolerancePerc }),
    },
    {
      captionKey: 'general.QtyToPick',
      value: formatQtyToHumanReadable({ qty: lineQtyToIssueRemaining, uom }),
    },
  ];
};
