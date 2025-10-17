import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { updateHeaderEntry } from '../../../actions/HeaderActions';
import { trl } from '../../../utils/translations';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';

export const useHeaderUpdate = ({
  url,
  locatorName,
  huDisplayName,
  productName,
  uom,
  qtyBooked,
  qtyCount,
  attributes,
}) => {
  const dispatch = useDispatch();
  useEffect(() => {
    const values = [
      {
        caption: trl('inventory.locator'),
        value: locatorName,
        hidden: locatorName === undefined,
      },
      {
        caption: trl('inventory.HU'),
        value: huDisplayName,
        hidden: huDisplayName === undefined,
      },
      {
        caption: trl('inventory.product'),
        value: productName,
        hidden: productName === undefined,
      },
      {
        caption: trl('inventory.qtyBooked'),
        value: formatQtyToHumanReadableStr({ qty: qtyBooked, uom }),
        hidden: qtyBooked === undefined,
      },
      {
        caption: trl('inventory.qtyCount'),
        value: formatQtyToHumanReadableStr({ qty: qtyCount, uom }),
        hidden: qtyCount === undefined,
      },
    ];

    attributes?.forEach((attribute) => {
      values.push({
        caption: attribute.caption,
        value: attribute.value,
      });
    });

    dispatch(updateHeaderEntry({ location: url, values }));
  }, [url, productName, locatorName, uom, qtyBooked, qtyCount, attributes, dispatch]);
};
