import React from 'react';
import { useSelector } from 'react-redux';
import { getHeaderEntries } from '../reducers/headers';

export const ViewHeader = () => {
  const headersEntries = useSelector((state) => getHeaderEntries(state));
  const flatEntries = headersEntries
    .filter((headersEntry) => !headersEntry.hidden && Array.isArray(headersEntry.values))
    .reduce((acc, headersEntry) => acc.concat(headersEntry.values), []);

  if (!flatEntries) return null;

  return (
    <table className="table view-header is-size-6">
      <tbody>
        {flatEntries.map((entry, i) => (
          <tr key={i}>
            <th>{entry.caption}</th>
            <td>{entry.value}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};
