import { formatQtyToHumanReadableStr } from '../../../../../utils/qtys';

export const computeStepScanUserInfoQtys = ({
  // `lineUom` is the BOM-line demand uom (e.g. kg); `uom` (the step/HU stocking uom, e.g. Stk)
  // may differ when the HU is stocked in a different uom than the line demands. The two rows
  // below are LINE-level quantities, so they must be shown in `lineUom`, not the step uom.
  lineUom,
  lineQtyToIssue,
  lineQtyToIssueTolerance,
  lineQtyToIssueRemaining,
}) => {
  return [
    {
      captionKey: 'general.QtyToPick_Total',
      value: formatQtyToHumanReadableStr({ qty: lineQtyToIssue, uom: lineUom, tolerance: lineQtyToIssueTolerance }),
    },
    {
      captionKey: 'general.QtyToPick',
      value: formatQtyToHumanReadableStr({ qty: lineQtyToIssueRemaining, uom: lineUom }),
    },
  ];
};
