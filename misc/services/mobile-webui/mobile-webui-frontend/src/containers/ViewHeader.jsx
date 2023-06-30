import React from 'react';
import { useSelector } from 'react-redux';
import { getEntryItemsFromState } from '../reducers/headers';

export const ViewHeader = () => {
  const entryItems = useSelector((state) => getEntryItemsFromState(state));

  if (entryItems.length <= 0) return null;

  return (
    <table className="table view-header is-size-6">
      <tbody>
        {entryItems.map((entry, i) => (
          <tr key={i}>
            <th>{entry.caption}</th>
            <td>{entry.value}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};
